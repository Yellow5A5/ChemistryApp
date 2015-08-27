package com.goudan.chemstudyingapp.introduction;


import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.more.TeachTV;
import com.goudan.chemstudyingapp.restore.DataBase;
import com.goudan.chemstudyingapp.restore.Equation;
import com.goudan.chemstudyingapp.restore.EquationList;
import com.goudan.chemstudyingapp.restore.RestoreThing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IntroductionActivity extends Activity {
	private Button checkGif, lookVideo;
	private ImageView backButton;
	private GifView gifView;
	private RelativeLayout page;
	private LinearLayout guidePage;
	private TextView guideText;
	boolean isGifViewChange=true;
	int resource=0;
	boolean isClick=true;
	private TextView gongshi,xianxiang,yiqi,tiaojian;
	private int[] gifRes = new int[]{R.drawable.gif_hno3andcu, R.drawable.gif_h2nh3andh2o};
	private int isHno3Cu = 0, isH2nh3H2o = 0;
	private String url = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_scrollview_introduction);
		page = (RelativeLayout)findViewById(R.id.page);
		guidePage = (LinearLayout)findViewById(R.id.guidePage);
		guideText = (TextView)findViewById(R.id.guideText);
		backButton = (ImageView)this.findViewById(R.id.circle2);
		lookVideo = (Button)findViewById(R.id.videoBtn);
		checkGif = (Button)findViewById(R.id.checkgif);
		gifView=(GifView)findViewById(R.id.gifview);
		gongshi=(TextView)findViewById(R.id.gongshi);
		xianxiang=(TextView)findViewById(R.id.xianxiang);
		yiqi=(TextView)findViewById(R.id.yiqi);
		tiaojian=(TextView)findViewById(R.id.tiaojian);
		if(DataBase.isHelpGuide)
		{
			getGuideResult();
			DataBase.isHelpGuide = false;
		}else{
			getResult();//获取实验结果
		}
		
		setView();
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		checkGif.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isClick)
				{
					int haveResult=0;
					int mNum=0;
					int num=0;
					for(int i=0;i<EquationList.material.length;i++)
					{
						String[] length=EquationList.material[i].split(",");
						mNum=length.length;
						for(String s:RestoreThing.materialList)
						{
							if(i == 0)
							{
								if("Cu,HNO3(稀)".contains(s))
								{
									isHno3Cu++;
								}else{
									isHno3Cu = 0;
								}
								if("H2NH3,H2O".contains(s))
								{
									isH2nh3H2o++;
								}else{
									isH2nh3H2o = 0;
								}
							}
							for(String ll:length)
							{
								if(ll.equals(s))
								{
									haveResult++;
								}
							}
						}
						if(haveResult==mNum)
						{
							num=i;
							break;
						}
						haveResult=0;
					}
					if(haveResult==mNum)
					{
						if(isH2nh3H2o == 2)
						{
							startGif(gifRes[1]);
							isH2nh3H2o = 0;
						}else if(isHno3Cu == 2)
						{
							startGif(gifRes[1]);
							isHno3Cu = 0;
						}else{
							Toast.makeText(IntroductionActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
						}
					}
//					if(haveResult==mNum)
//					{
//						if(isGifViewChange)
//						{
//							// 设置Gif图片源
//							gifView.setGifImage(resource);
//							// 添加监听器
////							gifView.setOnClickListener(this);
//							// 设置显示的大小，拉伸或者压缩
////							gifView.setShowDimension(800,350);
//							// 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示
//							gifView.setGifImageType(GifImageType.COVER);
//						}
////						for(View v:RestoreThing.material)
////						{
////							v.setVisibility(View.GONE);
////						}
////						for(View v:RestoreThing.equipment)
////						{
////							v.setVisibility(View.GONE);
////						}
//						gifView.setVisibility(View.VISIBLE);
//						
//					}
//					click=false;
//				}else{
//					gifView.setVisibility(View.GONE);
//					click=true;
////					for(View v:RestoreThing.material)
////					{
////						v.setVisibility(View.VISIBLE);
////					}
////					for(View v:RestoreThing.equipment)
////					{
////						v.setVisibility(View.VISIBLE);
////					}
				}else{
					gifView.setVisibility(View.GONE);
					isClick=true;
				}
				
			}
		});
		gifView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				gifView.setVisibility(View.GONE);
				isClick=true;
			}
		});
		guideText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(IntroductionActivity.this, GuidePageActivity.class);
				startActivity(intent);
				finish();
			}
		});
		lookVideo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(url.equals("无"))
				{
					Toast.makeText(IntroductionActivity.this, "暂无视频", Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent(IntroductionActivity.this, TeachTV.class);
					Bundle bundle = new Bundle();
					bundle.putString("url", url);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
	}
	private void getGuideResult() {
		gongshi.setText("化学方程式：H2O+NH3==NH3·H2O");
		tiaojian.setText("实验条件：酚酞溶液");
		yiqi.setText("实验器材：玻璃管，烧杯，胶头滴管，铁架台");
		xianxiang.setText("实验现象：溶液有无色先变为蓝色，然后变为红色。");
	}
	//是否要进入引导界面
	private void setView() {
		if(RestoreThing.material.size() == 0)
		{
			page.setVisibility(View.GONE);
			guidePage.setVisibility(View.VISIBLE);
		}else{
			
		}
	}

	private void startGif(int res)
	{
		if(isGifViewChange)
		{
			// 设置Gif图片源
			gifView.setGifImage(res);
			// 添加监听器
//				gifView.setOnClickListener(this);
			// 设置显示的大小，拉伸或者压缩
//				gifView.setShowDimension(800,350);
			// 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示
			gifView.setGifImageType(GifImageType.COVER);
			isGifViewChange = false;
		}else{
			
		}
		gifView.setVisibility(View.VISIBLE);
	}

	private void getResult() {
		// TODO Auto-generated method stub
		int haveResult=0;
		int mNum=0;
		int num=0;
		int materialNum=0;
		for(String s:RestoreThing.materialList)
		{
			materialNum++;
		}
		for(int i=0;i<EquationList.material.length;i++)
		{
			String[] length=EquationList.material[i].split(",");
			mNum=length.length;
			for(String s:RestoreThing.materialList)
			{
				for(String ll:length)
				{
					if(ll.equals(s))
					{
						haveResult++;
					}else{
//						continue;
					}
				}
			}
			if(haveResult==mNum&&haveResult==materialNum)
			{
				num=i;
				break;
			}
			haveResult=0;
		}
//		for(Equation e:EquationList.equationList)
//		{
//			String[] length=e.getMaterial().split(",");
//			
////			Toast.makeText(IntroductionActivity.this, length.length, Toast.LENGTH_SHORT).show();
//			mNum=length.length;
//			for(String s:RestoreThing.materialList)
//			{
//				for(String ll:length)
//				{
//					if(ll.equals(s))
//					{
//						haveResult++;
//					}
//				}
////				Log.i("s",e.getMaterial()+length.length+"]"+haveResult+"["+s);
//			}
//			if(haveResult==mNum)
//			{
//				equation=e;
//				break;
//			}
//			haveResult=0;
//		}
		if(haveResult==mNum)
		{
			url = EquationList.url[num];
			String s="化学方程式："+EquationList.result[num];
			SpannableString ss=new SpannableString(s);
			for(int i=0;i<s.length();i++)
			{
				if(s.substring(i, i+1).matches("\\d"))
				{
//					ss.setSpan(new RelativeSizeSpan(0.5f), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					try{
						if(s.substring(i-1, i).matches("\\)"))
						{
							ss.setSpan(new RelativeSizeSpan(0.5f), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						}else if(s.substring(i-1, i).matches("\\W"))
						{
							
						}else if(s.substring(i-1, i).matches("\\D"))
						{
							ss.setSpan(new RelativeSizeSpan(0.5f), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						}
					}catch(Exception e){
						
					}
					
				}
			}
			gongshi.setText(ss);
			tiaojian.setText("实验条件："+EquationList.condition[num]);
			yiqi.setText("实验器材："+EquationList.equipment[num]);
			xianxiang.setText("实验现象："+EquationList.phenomenon[num]);
		}else{
			gongshi.setText("化学方程式：无");
			tiaojian.setText("实验条件：暂无");
			yiqi.setText("实验器材：暂无");
			xianxiang.setText("实验现象：这些物质不能反应哦");
		}
	}
	
}
