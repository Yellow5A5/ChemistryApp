package com.goudan.chemstudyingapp.more;

import java.io.IOException;
import java.io.InputStream;

import com.example.chemstudyingapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class More_suming extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.more_suming);
		final TextView text_suming = (TextView)findViewById(R.id.text_suming);
		final ImageView backBtn = (ImageView)this.findViewById(R.id.backbtn);
		try {
			InputStream is = getAssets().open("oldname.txt");
			int size = is.available();
			
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();		
			
			String text = new String(buffer,"GB2312");
			
			text_suming.setText(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
