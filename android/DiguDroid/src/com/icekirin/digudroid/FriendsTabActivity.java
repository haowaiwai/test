package com.icekirin.digudroid;


import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.util.U;
import com.icekirin.digudroid.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class FriendsTabActivity extends TabActivity{
	private TabHost tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        tabs = getTabHost();        
        TabHost.TabSpec listTab = tabs.newTabSpec("list");
        Intent list = new Intent(this, Friends.class);
        listTab.setContent(list);
        listTab.setIndicator(U.R("my_friends"), this.getResources().getDrawable(R.drawable.friends3));
        list.putExtra("type", F.I_FOLLOW);
        tabs.addTab(listTab);
        
        TabHost.TabSpec repliesTab = tabs.newTabSpec("replies");
        Intent replies = new Intent(this, Friends.class);
        repliesTab.setContent(replies);
        repliesTab.setIndicator(U.R("my_followers"), this.getResources().getDrawable(R.drawable.friends1));
        replies.putExtra("type",  F.FOLLOW_ME);
        tabs.addTab(repliesTab);
	}

}
