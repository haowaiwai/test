package com.lenovo.demo.camerademo;

/*import net.youmi.android.AdListener;
 import net.youmi.android.AdManager;
 import net.youmi.android.AdView;*/
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import dalvik.system.VMRuntime;

public class CameraDemo extends Activity {
	// 相机相册存储Uri
	public static final Uri STORAGE_URI = Images.Media.EXTERNAL_CONTENT_URI;
	private static final int REQUEST_CODE_TAKE_PICTURE = 2;
	private final static int CWJ_HEAP_SIZE = 6 * 1024 * 1024;
	private static Matrix m = new Matrix();
	private static int targetWidth = 240, targetHeight = 320;
	private static Bitmap bm;
	private Gallery gv = null;
	private Button btn_pre = null, btn_behind = null;
	// 相册数据集
	private Cursor cursor;
	// GalleryAdapter
	private GalleryAdapter adpter;
	private String filename;
	// 记录删除位置
	private int pos;
	// Menu
	private final static int MENU_ABOUT = Menu.FIRST;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);
		gv = (Gallery) findViewById(R.id.pic);
		if (null != cursor) {
			cursor.close();
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		cursor = this.getContentResolver().query(STORAGE_URI, null, null, null,
				"_id DESC");
		adpter = new GalleryAdapter(this, cursor);
		gv.setAdapter(adpter);
		gv.setSelection(0);
		gv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				filename = cursor.getString(1);
				// Toast.makeText(Shexiangtou.this, filename, 0).show();
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse("file://" + filename),
						"image/*");
				startActivity(intent);
			}
		});

		// 长按删除
		gv.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				pos = position;
				final View iView = view;
				new AlertDialog.Builder(CameraDemo.this)
						.setTitle("删除这张图片吗?")
						.setPositiveButton("删除",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										String fname = (String) iView.getTag();
										delete(fname);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {

									}
								}).create().show();

				return false;
			}

		});

		//前置摄像头
		btn_pre = (Button) findViewById(R.id.btn);
		btn_pre.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra("camerasensortype", 2);
				intent.putExtra("needpath", true);
				intent.putExtra("needuri", true);
				startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
			}
		});

		//后置摄像头
		btn_behind = (Button) findViewById(R.id.btn_back);
		btn_behind.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra("camerasensortype", 1);
				intent.putExtra("needpath", true);
				intent.putExtra("needuri", true);
				intent.putExtra("showActionIcons", true);
				intent.putExtra("fullScreen", true);
				startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
			}
		});
	}

	// 获取图片
	public static Bitmap getBitMap(String filename) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 3;
		// options.inJustDecodeBounds = true;
		if (bm != null) {
			bm = null;
		}
		bm = BitmapFactory.decodeFile(filename, options);
		Bitmap newbm = Util.transform(m, bm, targetWidth, targetHeight, false,
				true);
		return newbm;

	}

	// 删除照片
	public void delete(String filename) {
		ContentResolver r = CameraDemo.this.getContentResolver();
		int i = r.delete(STORAGE_URI, "_data='" + filename + "'", null);
		if (i != 0) {
			Toast.makeText(CameraDemo.this, "删除成功", 0).show();
		} else {
			Toast.makeText(CameraDemo.this, "删除失败", 0).show();
		}
		cursor.close();
		r.notifyChange(STORAGE_URI, null);

		cursor = this.getContentResolver().query(STORAGE_URI, null, null, null,
				"_id DESC");
		adpter = new GalleryAdapter(this, cursor);
		gv = (Gallery) findViewById(R.id.pic);
		gv.setAdapter(adpter);
		Log.i("pos", pos + "");
		gv.setSelection(pos);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ABOUT, 0, "关于");

		return super.onCreateOptionsMenu(menu);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_TAKE_PICTURE: {
			// 重新加载数据
			cursor = this.getContentResolver().query(STORAGE_URI, null, null,
					null, "_id DESC");

			adpter = new GalleryAdapter(this, cursor);
			gv.setAdapter(adpter);
			gv.setSelection(0);
			break;
		}
		}
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case MENU_ABOUT:
			new AlertDialog.Builder(CameraDemo.this)
					.setTitle("关于")
					.setMessage("让我们一起支持乐phone吧")
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).create().show();
			break;

		default:
			break;
		}
		return true;
	}

}

class GalleryAdapter extends CursorAdapter {
	public void changeCursor(Cursor cursor) {
		super.changeCursor(cursor);
	}
	private final LayoutInflater inflater;
	private String filename = "";

	public GalleryAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		inflater = LayoutInflater.from(context);
	}

	public GalleryAdapter(Context context, Cursor c) {
		super(context, c);
		inflater = LayoutInflater.from(context);

	}

	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflater.inflate(R.layout.gallery, parent, false);
		return view;
	}

	public void bindView(View view, Context context, Cursor cursor) {
		ImageView iv = (ImageView) view.findViewById(R.id.pic_cell);
		filename = cursor.getString(1);
		view.setTag(filename);
		iv.setImageBitmap(CameraDemo.getBitMap(filename));
	}
}
