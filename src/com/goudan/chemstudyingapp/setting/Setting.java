package com.goudan.chemstudyingapp.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.adapter.GridViewAdapter;
import com.goudan.chemstudyingapp.main.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Setting extends Activity {
	
	public static ListView list;
	
	public static GridView gridView;
	private GridViewAdapter sAdapter;
	private List<Map<String,Object>> lists;
	private ImageView backBtn;
	private int[] imageResource={R.drawable.setting_btn1,R.drawable.setting_btn2,
			R.drawable.setting_btn3,R.drawable.setting_btn4,R.drawable.myadd,R.drawable.setting_btn5};
    private int[] imageIds = new int[]
    		{R.drawable.s0,R.drawable.s1,R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_setting);
		gridView=(GridView)findViewById(R.id.gridview);
		backBtn=(ImageView)findViewById(R.id.backbtn);
		lists=new ArrayList<Map<String,Object>>();
		for(int i=0;i<imageResource.length;i++)
		{
			Map<String,Object> list=new HashMap<String,Object>();
			list.put("image", imageResource[i]);
			lists.add(list);
		}
		sAdapter=new GridViewAdapter(Setting.this, lists);
		gridView.setAdapter(sAdapter);
		


		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();//Map 有点像映射一样，string对应Object。
		for (int i = 0; i < imageIds.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("header", imageIds[i]);
			listItems.add(listItem);
		}
		// 创建一个simpleadapter.
		SimpleAdapter simpleAdapter = new SimpleAdapter(Setting.this, listItems,R.layout.list_share,
									  new String[] {"header"}, 
									  new int[] {R.id.imageview1});
		list = (ListView)this.findViewById(R.id.list_share);
		list.setAdapter(simpleAdapter);
		list.setVisibility(View.GONE);
		
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,//设置背景模糊？
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
					finish();
			}
		});
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
			if(list.getVisibility()==View.VISIBLE)
			{
				list.setVisibility(View.GONE);
				list.startAnimation(AnimationUtils.loadAnimation(Setting.this, R.anim.main_listview_anim_out));
				Log.e("1","0");
			}else{
				finish();
				Log.e("1","1");
			}
            return true;
        }
        return super.onKeyDown(keyCode, event);
	}
}
