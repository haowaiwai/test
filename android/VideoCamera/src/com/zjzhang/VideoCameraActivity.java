package com.zjzhang;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.graphics.PixelFormat;
import android.media.MediaRecorder;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;

public class VideoCameraActivity extends Activity implements
		SurfaceHolder.Callback, MediaRecorder.OnErrorListener,
		MediaRecorder.OnInfoListener {
	private static final int mVideoEncoder =MediaRecorder.VideoEncoder.H264;
	private static final String TAG = "VideoCamera";
	LocalSocket receiver, sender;
	LocalServerSocket lss;

	private MediaRecorder mMediaRecorder = null;
	boolean mMediaRecorderRecording = false;

	private SurfaceView mSurfaceView = null;
	private SurfaceHolder mSurfaceHolder = null;

	Thread t;
	Context mContext = this;


	RandomAccessFile raf = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		mSurfaceView = (SurfaceView) this.findViewById(R.id.surface_camera);
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceView.setVisibility(View.VISIBLE);

		receiver = new LocalSocket();
		try {
			lss = new LocalServerSocket("VideoCamera");
			receiver.connect(new LocalSocketAddress("VideoCamera"));
			receiver.setReceiveBufferSize(500000);
			receiver.setSendBufferSize(500000);
			sender = lss.accept();
			sender.setReceiveBufferSize(500000);
			sender.setSendBufferSize(500000);
		} catch (IOException e) {
			finish();
			return;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mMediaRecorderRecording) {
			stopVideoRecording();

			try {
				lss.close();
				receiver.close();
				sender.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		finish();
	}

	private void stopVideoRecording() {
		Log.d(TAG, "stopVideoRecording");
		if (mMediaRecorderRecording || mMediaRecorder != null) {
			if (t != null)
				t.interrupt();
			try {
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			releaseMediaRecorder();
		}
	}

	private void startVideoRecording() {
		Log.d(TAG, "startVideoRecording");
		(t = new Thread() {
			public void run() {
				int frame_size = 1024;
				byte[] buffer = new byte[1024 * 64];
				int num, number = 0;
				InputStream fis = null;
				try {
					fis = receiver.getInputStream();
				} catch (IOException e1) {
					return;
				}
				try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				number = 0;
				releaseMediaRecorder();


				//�����H264����MPEG_4_SP�ľ�Ҫ�������ҵ���Ӧ�����ò�������
				//avcC box H264�����ò���
				//esds box MPEG_4_SP �����ò���
				//��ʵ ����ֱ��� ����ֵ����Ļ�����Щ�����ǲ���仯�ģ�
				//��ô�Ҿ�ֻ��Ҫ�ڵ�һ�����е�ʱ��ȷ���Ϳ�����
				while (true) {
					try {

						num = fis.read(buffer, number, frame_size);
						number += num;
						if (num < frame_size) {
							break;
						}
					} catch (IOException e) {
						break;
					}
				}
				

				initializeVideo();
				number = 0;
				// �������������Ի�ȡ��Ƶ��
				DataInputStream dis=new DataInputStream(fis);
				
				//��ȡ��ǰ���32���Լ��Ŀ�ͷ
				try {
					dis.read(buffer,0,32);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					File file = new File("/sdcard/stream.h264");
					if (file.exists())
						file.delete();
					raf = new RandomAccessFile(file, "rw");
				} catch (Exception ex) {
					Log.v("System.out", ex.toString());
				}				
				
				
				
				
				//��Щ����Ҫ��Ӧ�����ڵ���Ƶ���ã������仯�Ļ���Ҫȥ����ȷ����
				//��Ȼ��֪���ǲ��ǲ�ͬ�Ļ����ǲ���һ����������ֻ��һ��HTC G7�����ԡ�
				byte[] h264sps={0x67,0x42,0x00,0x0C,(byte) 0x96,0x54,0x0B,0x04,(byte) 0xA2};
				byte[] h264pps={0x68,(byte) 0xCE,0x38,(byte) 0x80};
				byte[] h264head={0,0,0,1};
				try {
					raf.write(h264head);
					raf.write(h264sps);
					raf.write(h264head);
					raf.write(h264pps);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				while (true)
				{
					try {
						//��ȡÿ���ĳ���
						int h264length=dis.readInt();
						number =0;
						raf.write(h264head);
						while(number<h264length)
						{
							int lost=h264length-number;
							num = fis.read(buffer,0,frame_size<lost?frame_size:lost);
							Log.d(TAG,String.format("H264 %d,%d,%d", h264length,number,num));
							number+=num;
							raf.write(buffer, 0, num);
						}

					} catch (IOException e) {
						break;
					}
				}
			}
		}).start();

	}

	private boolean initializeVideo() {
		if (mSurfaceHolder==null)
			return false;
		mMediaRecorderRecording = true;
		if (mMediaRecorder == null)
			mMediaRecorder = new MediaRecorder();
		else
			mMediaRecorder.reset();

		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mMediaRecorder.setVideoFrameRate(20);
		mMediaRecorder.setVideoSize(352, 288);
		mMediaRecorder.setVideoEncoder(mVideoEncoder);
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
		mMediaRecorder.setMaxDuration(0);
		mMediaRecorder.setMaxFileSize(0);

		mMediaRecorder.setOutputFile(sender.getFileDescriptor());
		try {
			mMediaRecorder.setOnInfoListener(this);
			mMediaRecorder.setOnErrorListener(this);
			mMediaRecorder.prepare();
			mMediaRecorder.start();
		} catch (IOException exception) {
			releaseMediaRecorder();
			finish();
			return false;
		}
		return true;
	}

	private void releaseMediaRecorder() {
		Log.v(TAG, "Releasing media recorder.");
		if (mMediaRecorder != null) {
			if (mMediaRecorderRecording) {
				try {
					mMediaRecorder.setOnErrorListener(null);
					mMediaRecorder.setOnInfoListener(null);
					mMediaRecorder.stop();
				} catch (RuntimeException e) {
					Log.e(TAG, "stop fail: " + e.getMessage());
				}
				mMediaRecorderRecording = false;
			}

			mMediaRecorder.reset();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Log.d(TAG, "surfaceChanged");
		mSurfaceHolder = holder;
		if (!mMediaRecorderRecording) {
			initializeVideo();
			startVideoRecording();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated");
		mSurfaceHolder = holder;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed");
		mSurfaceHolder = null;
	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		switch (what) {
		case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN:
			Log.d(TAG, "MEDIA_RECORDER_INFO_UNKNOWN");
			break;
		case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
			Log.d(TAG, "MEDIA_RECORDER_INFO_MAX_DURATION_REACHED");
			break;
		case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED:
			Log.d(TAG, "MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED");
			break;
		}
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		if (what == MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN) {
			Log.d(TAG, "MEDIA_RECORDER_ERROR_UNKNOWN");
			finish();
		}
	}

}