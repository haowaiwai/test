package com.zxhwolfe;

import java.io.IOException;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class feelslike extends Activity implements Callback, OnClickListener {
	/** Called when the activity is first created. */
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private Camera mCamera;
	private boolean mPreviewRunning;
	private ImageView mImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.main);
		mSurfaceView = (SurfaceView) findViewById(R.id.camera);
		mImageView = (ImageView) findViewById(R.id.image);
		mImageView.setVisibility(View.GONE);
		mSurfaceView.setOnClickListener(this);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (mPreviewRunning) {
			mCamera.stopPreview();
		}
		Parameters params = mCamera.getParameters();
		params.setPictureFormat(PixelFormat.JPEG);// ����ͼƬ��ʽ
		// params.setPreviewSize(width, height);
		params.set("rotation", 90);
		mCamera.setParameters(params);
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mCamera.startPreview();
		mPreviewRunning = true;
	}

	private AutoFocusCallback mAutoFocusCallBack = new AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			Log.v("AutoFocusCallback", "AutoFocusCallback" + success);
			Camera.Parameters Parameters = mCamera.getParameters();
			Parameters.setPictureFormat(PixelFormat.JPEG);// ����ͼƬ��ʽ
			mCamera.setParameters(Parameters);
			mCamera.takePicture(mShutterCallback, null, mPictureCallback);
		}
	};

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera = Camera.open();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
		mCamera = null;
	}

	/**
	 * ���յĻص��ӿ�
	 */
	PictureCallback mPictureCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.v("PictureCallback", "��onPictureTaken��");
			if (data != null) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				mImageView.setImageBitmap(bitmap);
				mImageView.setVisibility(View.VISIBLE);
				mSurfaceView.setVisibility(View.GONE);
				if (mPreviewRunning) {
					mCamera.stopPreview();
					mPreviewRunning = false;
				}
			}
		}
	};
	/**
	 * ��������Źر�ʱ��Ļص��ӿڣ�ͨ������ӿ���֪ͨ�û����Źرյ��¼���
	 * ��ͨ����ڿ��Źرյ�ʱ�򶼻ᷢ��������������Ҫ�����ڸûص��ӿ��ж�����ֶ����� ���磺ʹ�豸��
	 */
	ShutterCallback mShutterCallback = new ShutterCallback() {
		public void onShutter() {
			// just log ,do nothing
			Log.v("ShutterCallback", "��onShutter��");
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			if (mCamera != null) {
				// mCamera.takePicture(null, null,mPictureCallback);
				mCamera.autoFocus(mAutoFocusCallBack);
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Log.v("onClick", "��onClick��");
		mCamera.autoFocus(mAutoFocusCallBack);
	}
}