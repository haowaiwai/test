package com.icekirin.digudroid;

import com.icekirin.digudroid.data.F;
import com.icekirin.digudroid.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class DirectMessageTabActivity extends TabActivity{
	private TabHost tabs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friends);
        tabs = getTabHost();       

        Intent it1 = new Intent(this, DirectMessage.class);        
        it1.putExtra("type", F.MESSAGE_GET);
        TabHost.TabSpec getTab = tabs.newTabSpec("getTab");        
        getTab.setContent(it1);
        getTab.setIndicator(this.getResources().getString(R.string.direct_message),
        		this.getResources().getDrawable(R.drawable.friends3));        
        tabs.addTab(getTab);
        
//        Intent it2 = new Intent(this, DirectMessage.class);
//        it2.putExtra("type",  F.MESSAGE_SEND);                
//        TabHost.TabSpec sendTab = tabs.newTabSpec("sendTab");        
//        sendTab.setContent(it2);
//        sendTab.setIndicator("发件箱", this.getResources().getDrawable(R.drawable.friends1));            
//        tabs.addTab(sendTab);
        
        Intent it3 = new Intent(this, DirectMessage.class);
        it3.putExtra("type",  F.MESSAGE_SYSTEM);        
        TabHost.TabSpec sysTab = tabs.newTabSpec("sysTab");
        sysTab.setIndicator(this.getResources().getString(R.string.system_message),
        		this.getResources().getDrawable(R.drawable.friends1));        
        sysTab.setContent(it3);
        tabs.addTab(sysTab); 
        
        String action = getIntent().getExtras().get("action").toString();
      
        Intent it4 = new Intent(this, DirectMessageEditor.class);
        it4.putExtra("type",  F.MESSAGE_WRITE);
        if(action!=null && action.equals(F.ACTION_REPLY_DIRECT_MSG)){
        	it4.putExtra("action", F.ACTION_REPLY_DIRECT_MSG);
        }
        TabHost.TabSpec writeTab = tabs.newTabSpec("writeTab");
        writeTab.setIndicator(this.getResources().getString(R.string.write_direct_message),
        		this.getResources().getDrawable(R.drawable.friends1));        
        writeTab.setContent(it4);
        tabs.addTab(writeTab); 
                
        if(action!=null && action.equals(F.ACTION_REPLY_DIRECT_MSG)){
        	tabs.setCurrentTabByTag("writeTab");
        }
        

	}

}
