package com.grid.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class GridTest extends Activity {
	private GridView gridview;
	private List<GridInfo> list;
	private GridAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridlayout);
		gridview = (GridView) findViewById(R.id.gridview);
		list = new ArrayList<GridInfo>();
		list.add(new GridInfo("name1","222"));
		list.add(new GridInfo("name2","222"));
		list.add(new GridInfo("name3","222"));
		list.add(new GridInfo("name4","222"));
		list.add(new GridInfo("name5","222"));
		list.add(new GridInfo("name6","222"));
		list.add(new GridInfo("name6","222"));
		list.add(new GridInfo("name7","222"));
		list.add(new GridInfo("name8","222"));
		list.add(new GridInfo("name9","222"));
		list.add(new GridInfo("name10","222"));
		adapter = new GridAdapter(this);
		adapter.setList(list);
		gridview.setAdapter(adapter);
	}
}