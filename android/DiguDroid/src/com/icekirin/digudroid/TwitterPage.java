package com.icekirin.digudroid;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.icekirin.digudroid.data.DiguApi;
import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.data.MsgAdapter;
import com.icekirin.digudroid.data.MsgBean;
import com.icekirin.digudroid.util.DbPersist;
import com.icekirin.digudroid.util.NetUtil;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class TwitterPage extends Activity {

	public static final int	MENU_REFRESH	= Menu.FIRST;		//刷新
	public static final int	MENU_PREV		= Menu.FIRST + 1;	//上页
	public static final int	MENU_NEXT		= Menu.FIRST + 2;	//下页
	public static final int	MENU_WRITE		= Menu.FIRST + 3;	//
	public static final int	MENU_GUANG		= Menu.FIRST + 4;	//
	public static final int	MENU_EXIT		= Menu.FIRST + 5;	//退出

	public static final int	MENU_REPLY		= Menu.FIRST + 7;
	public static final int	MENU_DELETE		= Menu.FIRST + 8;
	public static final int	MENU_FAV		= Menu.FIRST + 9;
	public static final int	MENU_ZZ			= Menu.FIRST + 10;

	private ProgressDialog	progressDialog;
	private Handler			handler			= new Handler();
	private int				page			= 0;
	private ListView		mMsgList;
	private Context			ctx;
	private EditText		mContent;
	private Button			refresh;
	private TextView		page_text;
	private List<MsgBean>	msgBeanList;
	private MsgBean op_msgBean;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.twitter_page);		
		this.ctx = this;
		//initUserHeadMap();
		page_text = (TextView) findViewById(R.id.page_text);
		mMsgList = (ListView) findViewById(R.id.msg_list);
		refresh = (Button)findViewById(R.id.refresh);
		refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getMsg();
			}
			
		});
		registerForContextMenu(mMsgList);
		mMsgList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				MsgBean msgBean = (MsgBean) view.getTag();
				if (msgBean.getPicPath().length() > 2) {
					Intent it = new Intent().setClass(ctx, MsgInfo.class);
					U.getCacheData().toDisplayBigPicMsgBean = msgBean;
					startActivity(it);
				}
			}
		});

		getLocalMsgByPage(0);

	}

	public void setLoginUser(String u, String p) {
		F.userName = u;
		F.passWord = p;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuItem i;
		i = menu.add(0, MENU_REFRESH, 0, U.R("twitterpage_btn_refresh"));
		i.setIcon(R.drawable.refresh2);
		i = menu.add(0, MENU_PREV, 1, U.R("twitterpage_btn_prevpage"));
		i.setIcon(R.drawable.left);
		i = menu.add(0, MENU_NEXT, 2, U.R("twitterpage_btn_nextpage"));
		i.setIcon(R.drawable.right);
		i = menu.add(0, MENU_WRITE, 3, U.R("twitterpage_btn_write"));
		i.setIcon(R.drawable.write);
		i = menu.add(0, MENU_GUANG, 4, U.R("twitterpage_btn_public"));
		i.setIcon(R.drawable.guang1);
		i = menu.add(0, MENU_EXIT, 5, U.R("twitterpage_btn_exit"));
		i.setIcon(R.drawable.exit2);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_REFRESH: {
			getMsg();
			return true;
		}
		case MENU_PREV: {
			if (page - 1 >= 0) {
				page--;
				getLocalMsgByPage(page);
			} else {
				U.dpost(ctx, U.R("twitterpage_txt_isfirstpage"));
			}
			return true;
		}
		case MENU_NEXT: {
			DbPersist dbp = new DbPersist(ctx);
			int msgCount = dbp.getMsgCount(F.userName);
			int pageCount = msgCount / F.PAGE_SIZE;
			dbp.close();
			if (page < pageCount - 1) {
				page++;
				getLocalMsgByPage(page);
			} else if (page == pageCount - 1) {
				if (!(msgCount % pageCount == 0)) {
					page++;
					getLocalMsgByPage(page);
				} else {
					U.dpost(ctx, U.R("twitterpage_btn_islastpage"));
				}
			} else {
				U.dpost(ctx, U.R("twitterpage_btn_islastpage"));
			}
			return true;
		}

		case MENU_WRITE: {
			Intent it = new Intent().setClass(this, WriteMsgDialog.class);
			it.putExtra("type", F.OP_TYPE_WRITE);
			startActivity(it);
			return true;
		}
		case MENU_GUANG: {
			getPubMsg();
			return true;
		}

		case MENU_EXIT: {
			this.finish();
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		menu.setHeaderTitle(U.R("twitterpage_title_msg_op"));
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		String msgUserName = msgBeanList.get(info.position).getUserBean().getName();
		if (F.userName.equals(msgUserName)) {
			//menu.add(0, MENU_FAV, 0, "收藏");
			menu.add(1, MENU_DELETE, 0, U.R("public_btn_delete"));
		} else {
			menu.add(0, MENU_REPLY, 0, U.R("public_btn_reply"));
			//menu.add(1, MENU_FAV, 0, "收藏");
			menu.add(2, MENU_ZZ, 0, U.R("public_btn_zz"));
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case (MENU_REPLY): {
			MsgBean msg = msgBeanList.get(info.position);
			Intent it = new Intent().setClass(ctx, WriteMsgDialog.class);
			it.putExtra("type", F.OP_TYPE_REPLY);
			U.getCacheData().toOperateMsgBean = msg;
			startActivity(it);
			//U.dpost(ctx,"MENU_REPLY"+info.position);
			return true;
		}
		case (MENU_ZZ): {
			MsgBean msg = msgBeanList.get(info.position);
			Intent it = new Intent().setClass(ctx, WriteMsgDialog.class);
			it.putExtra("type", F.OP_TYPE_ZZ);
			U.getCacheData().toOperateMsgBean = msg;
			startActivity(it);
			return true;
		}
		case (MENU_FAV): {
			return true;
		}
		case (MENU_DELETE): {
			op_msgBean = msgBeanList.get(info.position);
			AlertDialog alertDialog = new AlertDialog.Builder(this).setInverseBackgroundForced(true).setTitle(U.R("public_title_attention"))
			.setMessage(U.R("twitterpage_msg_confirm_delete")).setCancelable(false).setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							deleteMsg();
						}
					}).setNegativeButton(U.R("public_btn_cancel"), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					
				}
			}).create();
			alertDialog.show();
			return true;
		}
		}
		return super.onContextItemSelected(item);
	}
	
	/**
	 * 获取个人及好友消息，写入数据库，再从数据库中读取
	 */
	public void deleteMsg() {
		
		progressDialog = ProgressDialog.show(TwitterPage.this, U.R("public_txt_plesse_wait"), U.R("public_txt_doing"), true);
		new Thread() {
			@Override
			public void run() {
				try {
					DiguApi api = new DiguApi();
					api.deleteStatus(Long.parseLong(op_msgBean.getId()));//删除网络数据
					DbPersist dbp = new DbPersist(ctx);
					dbp.deleteMsg(op_msgBean);//删除本地数据
					dbp.close();
					msgBeanList = api.getMsgBeanOfFriendsTimeline(ctx);
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						mMsgList.setAdapter(new MsgAdapter(ctx, msgBeanList, F.PRIVATE_MSG));
						setPageText();
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}

	/**
	 * 获取个人及好友消息，写入数据库，再从数据库中读取
	 */
	public void getMsg() {
		progressDialog = ProgressDialog.show(TwitterPage.this, U.R("public_txt_plesse_wait"), U.R("twitterpage_txt_get_net"), true);
		new Thread() {
			@Override
			public void run() {
				try {
					DiguApi api = new DiguApi();
					msgBeanList = api.getMsgBeanOfFriendsTimeline(ctx);
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						mMsgList.setAdapter(new MsgAdapter(ctx, msgBeanList, F.PRIVATE_MSG));
						setPageText();
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}

	/**
	 * 获取广场消息，不写入数据库，不保存图片到本地
	 */
	public void getPubMsg() {
		progressDialog = ProgressDialog.show(TwitterPage.this, U.R("public_txt_plesse_wait"), U.R("twitterpage_txt_get_net"), true);
		new Thread() {
			@Override
			public void run() {
				DiguApi api = new DiguApi();
				msgBeanList = new ArrayList<MsgBean>();
				try {
					JSONArray jrr = api.getPublicTimeline();
					for (int i = 0; i < jrr.length(); i++) {
						JSONObject jo = jrr.getJSONObject(i);
						MsgBean mb = api.jsonToMsgBean(jo);						
						msgBeanList.add(mb);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						mMsgList.setAdapter(new MsgAdapter(ctx, msgBeanList, F.PUB_MSG));
					}
				});

				progressDialog.dismiss();
			}
		}.start();

	}

	/**
	 * 获取本地数据库信息,加入分页
	 */
	public void getLocalMsgByPage(int i) {
		progressDialog = ProgressDialog.show(TwitterPage.this, U.R("public_txt_plesse_wait"), U.R("twitterpage_txt_get_local"), true);
		this.page = i;
		new Thread() {
			@Override
			public void run() {
				DbPersist dbp = new DbPersist(ctx);
				msgBeanList = dbp.queryMsg(F.userName, page);
				dbp.close();
				//第一次，本地没消息，去网络取消息
				if(msgBeanList.size()==0){
					U.dout("no msg local");
					DiguApi api = new DiguApi();
					try {						
						msgBeanList = api.getMsgBeanOfFriendsTimeline(ctx);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace(); 
					}
				}
				handler.post(new Runnable() {
					public void run() {
						mMsgList.setAdapter(new MsgAdapter(ctx, msgBeanList, F.PRIVATE_MSG));
						setPageText();
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}

	boolean	sendflag	= false;



	/**
	 * 开一个线程 把本地存在的头像更新
	 */
	public void initUserHeadMap() {
		new Thread() {
			@Override
			public void run() {
				File path = new File(F.USER_HEAD_PATH);
				HashMap<String, Bitmap> userHeadMap = U.getCacheData().userHeadMap;
				if (path.isDirectory()) {
					File[] childs = path.listFiles();
					if ((childs != null) && (childs.length != 0)) {
						for (int i = 0; i < childs.length; i++) {
							try {
								String fstr = childs[i].getPath();
								String[] str = fstr.split("/");
								String name = str[str.length - 1]; //SIGN10120986_24x24.jpg
								if (name.length() >= 22) {
									String fileUrl = U.userHeadPath2Url(childs[i].getPath());
									NetUtil.updateUserHead(fileUrl);
								} else {
									Bitmap bm = BitmapFactory.decodeFile(childs[i].getPath());
									U.dout("add map for" + childs[i].getPath());
									userHeadMap.put(childs[i].getPath(), bm);
								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}.start();
	}

	/**
	 * 设置页码
	 */
	public void setPageText() {
		DbPersist dbp = new DbPersist(ctx);
		int msgCount = dbp.getMsgCount(F.userName);
		dbp.close();
		//page_text.setText("第" + (page + 1) + "页 " + "每页" + F.PAGE_SIZE + "条 " + "共" + msgCount + "条");
		String [] str = {""+page + 1,""+F.PAGE_SIZE,""+msgCount}; 
		page_text.setText(getResources().getString(R.string.twitterpage_txt_page, str));

	}

}