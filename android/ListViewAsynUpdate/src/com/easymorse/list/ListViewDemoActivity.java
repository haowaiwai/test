package com.easymorse.list;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;

public class ListViewDemoActivity extends ListActivity {
	
	private MyImageAndTextListAdapter listAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.setListAdapter(getAdapter());
	}
	
	private MyImageAndTextListAdapter getAdapter(){
		if(this.listAdapter==null){
			listAdapter=new MyImageAndTextListAdapter(this, getList());
		}
		return listAdapter;
	}

	private List<NewsBean> getList() {
		List<NewsBean> result = new ArrayList<NewsBean>();
		for(int i = 0;i<10;i++){			
			NewsBean tempNB = new NewsBean();
			tempNB.setTitle("title"+i);
			tempNB.setContent("content"+i);
			tempNB.setImage("http://g4.ykimg.com/01270F1F464BD0E447AE2100000000B07DBC93-FD8E-0AE4-3CDF-8383410521BC");
			result.add(tempNB);
		}
		return result;
	}
}