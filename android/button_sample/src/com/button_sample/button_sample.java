package com.button_sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class button_sample extends Activity implements OnClickListener
{
    private Button blue;
    private Button yellow;
    private LinearLayout linearLayout;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //��̬���밴ť
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);  //��ֱ����
        blue = new Button(this);
        blue.setText("blue");
        blue.setOnClickListener(this);  //ָʾ������Ϣ������ǰ��ͼ
        //blue.
        setContentView(R.layout.main);
    }
    
    @Override
    public void onClick(View v)
    {
        
    }
}