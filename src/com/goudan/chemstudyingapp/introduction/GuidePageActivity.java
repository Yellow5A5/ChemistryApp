package com.goudan.chemstudyingapp.introduction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.adapter.GuideListAdapter;
import com.goudan.chemstudyingapp.main.MainActivity;
import com.goudan.chemstudyingapp.restore.DataBase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class GuidePageActivity extends Activity {
	private List<Map<String, String>> lists;
	private ListView listView;
	private ImageView backBtn;
	private ImageButton next;
	private GuideListAdapter adapter;
	private RelativeLayout layout1, layout2;
	private GifView gifView;
	private int num = 0;
	private int[] gifRes = new int[]{R.drawable.gif_anshuiandrshui1, R.drawable.gif_anshuiandrshui2
			, R.drawable.gif_anshuiandrshui3};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_huaxeguide);
		listView = (ListView)findViewById(R.id.guideList);
		backBtn = (ImageView)findViewById(R.id.backBtn);
		next = (ImageButton)findViewById(R.id.next);
		lists = new ArrayList<Map<String, String>>();
		layout1 = (RelativeLayout)findViewById(R.id.guideLayout1);
		layout2 = (RelativeLayout)findViewById(R.id.guideLayout2);
		gifView = (GifView)findViewById(R.id.gifview);
		Map<String, String> list = new HashMap<String, String>();
		list.put("text", "氨气和水的反应");
		lists.add(list);
		adapter = new GuideListAdapter(this, lists, listView);
		listView.setAdapter(adapter);
		next.setVisibility(View.GONE);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent= new Intent(GuidePageActivity.this, MainActivity.class);
				DataBase.isHelpGuide = true;
				startActivityForResult(intent, 0x12);

			}
		});
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(num < gifRes.length - 1)
				{
					num++;
					setGif(gifRes[num]);
					Log.i("lll",num+"//"+gifRes.length);
				}else{
					num = 0;
					layout1.setVisibility(View.VISIBLE);
					layout2.setVisibility(View.GONE);
					next.setVisibility(View.GONE);
					Intent intent = new Intent(GuidePageActivity.this, IntroductionActivity.class);
					startActivity(intent);
				}
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0x12&& resultCode == 0x12){
			layout1.setVisibility(View.GONE);
			layout2.setVisibility(View.VISIBLE);
			setGif(gifRes[num]);
			next.setVisibility(View.VISIBLE);
		}
	}

	protected void setGif(int res) {
		// 设置Gif图片源
		gifView.setGifImage(res);
		// 添加监听器
//					gifView.setOnClickListener(this);
		// 设置显示的大小，拉伸或者压缩
//					gifView.setShowDimension(800,350);
		// 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示
		gifView.setGifImageType(GifImageType.COVER);
		gifView.setVisibility(View.VISIBLE);
	}
}
