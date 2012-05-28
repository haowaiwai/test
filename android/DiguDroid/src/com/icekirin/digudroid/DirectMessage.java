package com.icekirin.digudroid;

import java.util.ArrayList;

import com.icekirin.digudroid.data.DiguApi;
import com.icekirin.digudroid.data.DirectMessageAdapter;
import com.icekirin.digudroid.data.DirectMessageBean;
import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.util.DbPersist;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DirectMessage extends Activity{
	
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

	private ListView list;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private Context ctx;
    private ArrayList<DirectMessageBean> msgBeanList;
    private String type;
    private int page;
    private TextView		page_text;
    DirectMessageBean op_msgBean;
    Button refresh;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ctx = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.direct_message);
		refresh = (Button)findViewById(R.id.refresh);
		refresh.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				downLoadMessages();
			}
			
		});
		list = (ListView)findViewById(R.id.message_list);
		registerForContextMenu(list);
		page_text = (TextView) findViewById(R.id.page_text);
		type = getIntent().getExtras().get("type")+"";
		getMessagesByPage(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuItem i;
		i = menu.add(0, MENU_REFRESH, 0, U.R("public_btn_refresh"));
		i.setIcon(R.drawable.refresh2);
		i = menu.add(0, MENU_PREV, 1, U.R("public_btn_prevpage"));
		i.setIcon(R.drawable.left);
		i = menu.add(0, MENU_NEXT, 2, U.R("public_btn_nextpage"));
		i.setIcon(R.drawable.right);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_REFRESH: {
			downLoadMessages();
			return true;
		}
		case MENU_PREV: {
			if (page - 1 >= 0) {
				page--;
				getMessagesByPage(page);
			} else {
				U.dpost(ctx, U.R("twitterpage_txt_isfirstpage"));
			}
			return true;
		}
		case MENU_NEXT: {
			DbPersist dbp = new DbPersist(ctx);
			int msgCount = dbp.getDirectMessageCount(F.userName, type);
			int pageCount = msgCount / F.PAGE_SIZE;			
			dbp.close();
			if (page < pageCount - 1) {
				page++;
				getMessagesByPage(page);
			} else if (page == pageCount - 1) {
				if (!(msgCount % pageCount == 0)) {
					page++;
					getMessagesByPage(page);
				} else {
					U.dpost(ctx, U.R("twitterpage_btn_islastpage"));
				}
			} else {
				U.dpost(ctx, U.R("twitterpage_btn_islastpage"));
			}
			return true;
		}

		case MENU_WRITE: {
//			Intent it = new Intent().setClass(this, WriteMsgDialog.class);
//			it.putExtra("type", F.OP_TYPE_WRITE);
//			startActivity(it);
			return true;
		}
		case MENU_GUANG: {
//			getMsg2();
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
		menu.add(0, MENU_REPLY, 0, U.R("public_btn_reply"));
		menu.add(1, MENU_DELETE, 0, U.R("public_btn_delete"));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case (MENU_REPLY): {
			DirectMessageBean msg = msgBeanList.get(info.position);
			Intent it = new Intent().setClass(ctx, DirectMessageTabActivity.class);
			it.putExtra("action", F.ACTION_REPLY_DIRECT_MSG);
			U.getCacheData().toOperateDirectMsgBean = msg;
			startActivity(it);
			finish();
			return true;
		}

		case (MENU_DELETE): {
			op_msgBean = msgBeanList.get(info.position);
			AlertDialog alertDialog = new AlertDialog.Builder(this).setInverseBackgroundForced(true).setTitle(U.R("public_title_attention"))
			.setMessage(U.R("twitterpage_msg_confirm_delete")).setCancelable(false).setPositiveButton("Ok",
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
	 * 下载悄悄话
	 */
	private void downLoadMessages(){
    	progressDialog=ProgressDialog.show(DirectMessage.this, U.R("public_txt_plesse_wait"), U.R("public_txt_doing"), true);
    	new Thread(){
            @Override
			public void run(){
            	DiguApi api = new DiguApi();
            	
            	try {
            			msgBeanList = api.getDirectMessageBean(ctx, type);
				} catch (Exception e) {
					e.printStackTrace();
				}
            	handler.post(new Runnable() {
		            public void run() {
		            	list.setAdapter(new DirectMessageAdapter(ctx, msgBeanList));
		            	setPageText();
		            }
		        });
				progressDialog.dismiss();
            }
        }.start();
	}
	
	/**
	 * 数据库读取悄悄话
	 * @param i
	 */
	private void getMessagesByPage(int i){
		progressDialog = ProgressDialog.show(DirectMessage.this, U.R("public_txt_plesse_wait"), U.R("twitterpage_txt_get_local"), true);
		this.page = i;
		new Thread() {
			@Override
			public void run() {
				DbPersist dbp = new DbPersist(ctx);
				msgBeanList = dbp.queryDirectMessage(F.userName, page, type);
				dbp.close();
				if(msgBeanList.size()==0){
					try {
						DiguApi api = new DiguApi();
            			msgBeanList = api.getDirectMessageBean(ctx, type);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				handler.post(new Runnable() {
					public void run() {
						list.setAdapter(new DirectMessageAdapter(ctx, msgBeanList));
						setPageText();
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}
	/**
	 * 删除悄悄话 （网络和本地）
	 */
	public void deleteMsg() {
		
		progressDialog = ProgressDialog.show(DirectMessage.this, U.R("public_txt_plesse_wait"), U.R("public_txt_doing"), true);
		new Thread() {
			@Override
			public void run() {
				try {
					DiguApi api = new DiguApi();
					api.deleteDirectMessage(Long.parseLong(op_msgBean.getId()));//删除网络数据
					DbPersist dbp = new DbPersist(ctx);
					dbp.deleteDirectMessage(op_msgBean);//删除本地数据
					dbp.close();
					msgBeanList = api.getDirectMessageBean(ctx, type);
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					public void run() {
						list.setAdapter(new DirectMessageAdapter(ctx, msgBeanList));
						setPageText();
					}
				});
				progressDialog.dismiss();
			}
		}.start();
	}
	
	public void setPageText() {
		DbPersist dbp = new DbPersist(ctx);
		int msgCount = dbp.getDirectMessageCount(F.userName, type);
		dbp.close();
		//page_text.setText("第" + (page + 1) + "页 " + "每页" + F.PAGE_SIZE + "条 " + "共" + msgCount + "条");
		String [] str = {""+page + 1,""+F.PAGE_SIZE,""+msgCount}; 
		page_text.setText(getResources().getString(R.string.twitterpage_txt_page, str));
	}
}
