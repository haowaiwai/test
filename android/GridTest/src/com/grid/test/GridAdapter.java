package com.grid.test;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Copyright (C) 2010,Under the supervision of China Telecom Corporation Limited
 * Guangdong Research Institute The New Vphone Project
 * 
 * @Author fonter.yang
 * @Create date£º2010-10-11
 * 
 */
public class GridAdapter extends BaseAdapter {

	private class GridHolder {
		TextView appText1;
		TextView appText2;
	}

	private Context context;

	private List<GridInfo> list;
	private LayoutInflater mInflater;

	public GridAdapter(Context c) {
		super();
		this.context = c;
	}

	public void setList(List<GridInfo> list) {
		this.list = list;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int index) {

		return list.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		GridHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.grid_item, null);
			holder = new GridHolder();
			holder.appText1 = (TextView) convertView.findViewById(R.id.itemText1);
			holder.appText2 = (TextView) convertView.findViewById(R.id.itemText2);
			convertView.setTag(holder);

		} else {
			holder = (GridHolder) convertView.getTag();

		}
		GridInfo info = list.get(index);
		if (info != null) {
			holder.appText1.setText(info.getName1());
			holder.appText2.setText(info.getName2());
		}
		return convertView;
	}

}