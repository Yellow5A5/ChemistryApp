package com.goudan.chemstudyingapp.more;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.adapter.GridViewAdapter;
import com.goudan.chemstudyingapp.setting.Setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class More_menu extends Activity {
	
	private GridView gridView;
	private GridViewAdapter sAdapter;
	private List<Map<String,Object>> lists;
	private ImageView backBtn;
	private int[] imageResource={R.drawable.more_m1,R.drawable.more_m2,
			R.drawable.more_m3,R.drawable.more_m4,R.drawable.more_m5, R.drawable.setting_button_note};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_more);
		gridView=(GridView)findViewById(R.id.more);
		backBtn=(ImageView)findViewById(R.id.backbtn);
		lists=new ArrayList<Map<String,Object>>();
		for(int i=0;i<imageResource.length;i++)
		{
			Map<String,Object> list=new HashMap<String,Object>();
			list.put("image", imageResource[i]);
			lists.add(list);
		}
		sAdapter=new GridViewAdapter(More_menu.this, lists);
		sAdapter.flag=false;
		gridView.setAdapter(sAdapter);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
