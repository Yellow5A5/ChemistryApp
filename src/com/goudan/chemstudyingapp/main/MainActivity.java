package com.goudan.chemstudyingapp.main;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.adapter.EMExpandListViewAdapter;
import com.goudan.chemstudyingapp.adapter.MTExpandListViewAdapter;
import com.goudan.chemstudyingapp.introduction.GuidePageActivity;
import com.goudan.chemstudyingapp.introduction.IntroductionActivity;
import com.goudan.chemstudyingapp.more.More_menu;
import com.goudan.chemstudyingapp.restore.DataBase;
import com.goudan.chemstudyingapp.restore.Equation;
import com.goudan.chemstudyingapp.restore.EquationList;
import com.goudan.chemstudyingapp.restore.RestoreThing;
import com.goudan.chemstudyingapp.restore.UseTime;
import com.goudan.chemstudyingapp.service.FloatWindowService;
import com.goudan.chemstudyingapp.setting.Setting;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ImageButton startBtn,restartBtn,settingBtn,equipmentBtn,materialBtn,illustrationBtn;
	private LinearLayout listviewLayout,listviewLayoutMt,binLayout, helpGuideWindow;
	private RelativeLayout windowLayout,panelLayout;
	private ExpandableListView eListView,eListViewMt;
	private MTExpandListViewAdapter eListViewAdapterMt;
	private EMExpandListViewAdapter eListViewAdapter;
	private AutoCompleteTextView acTextMT, acTextEM;
	private ImageButton searchBtnMT, searchBtnEM;
	private ImageView bin;
	private boolean isShowEquipment=false;
	private boolean isShowMateria=false;
	private Animation in,out;
	private ImageView panelImage;
	private SharedPreferences save;
	private SharedPreferences.Editor editor;
	private int mode=0;//0为普通模式，1为引导模式
	private boolean exit=false;
	private ArrayAdapter<String> arrayMT, arrayEM;
	private final String[] EM = new String[] {"试管","烧杯", "坩埚" ,"集气瓶" ,"量筒","胶头滴管","锥形瓶","漏斗",
			"镊子","坩埚钳","酒精灯","铁架台","研钵","水槽"};
	private final String[] MT = new String[] {
			"二氧化碳","氧化二氮","一氧化氮","氧气","二氧化硫","二氧化氮","三氧化硫","氢气","硫化氢","氨气","氯气","一氧化碳","氟气",
			"盐酸","硫化钠溶液","硫酸铜","硫酸亚铁","硝酸亚铁","硫酸铁","硝酸铁","氟化氢溶液","碘化钠","溴化钠","氢氧化钡","硝酸铵","氢氧化钙","氯化镁","氯化钡","硫酸钠","次氯酸","稀硝酸","偏铝酸钠","氢氧化铜","氯化亚铁","氯化铁","氯化铜","氯化铝","水","硝酸银","氢氧化亚铁","氢氧化铁","硅酸钠","高锰酸钾","碳酸氢铵","氢氧化钾","烧碱","汞","浓盐酸","稀盐酸","浓硝酸","浓硫酸","稀硫酸","双氧水","亚硫酸","氯酸钾",
			"钙","白磷","钾","钠","氧化汞","硅","碘","二氧化锰","铝","镁","氢氧化镁","氢氧化铝","氧化锌","过氧化钠","氧化铁","二氧化硅","过氧化钠","氧化镁","硅","氧化亚铁","银","锌","铁","铜","四氧化三铁","连二亚硫酸钠","碳酸钙","碳酸钠","石灰乳","磷","硫","炭","氧化铜","氧化钙 ","碳酸氢钠",
			};
	private InputMethodManager imm;
	private String ACTION = "com.goudan.chemstudyingapp.service.FloatWindowService";
	private FloatWindowService.MyBinder binder;
	private Intent intent = new Intent();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		isGuide();//判断是否引导模式
		init();//定义控件
		loadAnimation();//加载动画
		setListener();//设置按钮监听
		if(mode==1)
		{
			
		}
//		startService(intent);
		if(DataBase.isHelpGuide)
		{
			helpGuideWindow.setVisibility(View.VISIBLE);
		}else{
			//启动service
			ServiceConnection conn = new ServiceConnection() {
				
				@Override
				public void onServiceDisconnected(ComponentName arg0) {
					Toast.makeText(MainActivity.this, "ceshi", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					binder = (FloatWindowService.MyBinder)service;
				}
			};
			intent.setAction(ACTION);
			bindService(intent, conn, Service.BIND_AUTO_CREATE);
		}
	}
	
	
	
	private void isGuide() {
		// TODO Auto-generated method stub
		save=getSharedPreferences("setting", 0);
		editor=save.edit();
		int useTime=0;
		useTime=save.getInt("usetime", 0);
		if(useTime==0)
		{
			mode=1;
			UseTime.mode=1;
		}else{
			mode=0;
			UseTime.mode=mode;
		}
		useTime++;
		UseTime.useTime=useTime;
		editor.putInt("usetime", useTime);
		editor.commit();
	}

	//定义控件
	private void init() {
		// TODO Auto-generated method stub
		windowLayout=(RelativeLayout)findViewById(R.id.relativelayout);
		panelLayout=(RelativeLayout)findViewById(R.id.panelLayout);
		startBtn=(ImageButton)findViewById(R.id.start);
		restartBtn=(ImageButton)findViewById(R.id.restart);
		settingBtn=(ImageButton)findViewById(R.id.setting);
		equipmentBtn=(ImageButton)findViewById(R.id.equipment);
		materialBtn=(ImageButton)findViewById(R.id.material);
		panelImage=(ImageView)findViewById(R.id.panel);
		binLayout=(LinearLayout)findViewById(R.id.binlayout);
		illustrationBtn=(ImageButton)findViewById(R.id.illustration);
		bin=(ImageView)findViewById(R.id.bin);
		//定义仪器的listview
		listviewLayout=(LinearLayout)findViewById(R.id.popuplistview);
		//定义材料的listview
		listviewLayoutMt=(LinearLayout)findViewById(R.id.popuplistview_material);
		eListView=(ExpandableListView)findViewById(R.id.expandablelist);
		eListViewMt=(ExpandableListView)findViewById(R.id.expandablelist_material);
		eListViewAdapter=new EMExpandListViewAdapter(MainActivity.this,windowLayout,panelLayout,binLayout,bin);
		eListView.setAdapter(eListViewAdapter);
		eListViewAdapterMt=new MTExpandListViewAdapter(MainActivity.this,windowLayout,panelLayout,binLayout,bin);
		eListViewMt.setAdapter(eListViewAdapterMt);
//		eListView.setGroupIndicator(this.getResources().getDrawable(R.drawable.listview_arrow_item));
		eListView.setGroupIndicator(null);//去掉Epandedlistview原有的箭头
		eListViewMt.setGroupIndicator(null);
		acTextMT = (AutoCompleteTextView)findViewById(R.id.acTextMT);
		acTextEM = (AutoCompleteTextView)findViewById(R.id.acTextEM);
		searchBtnMT = (ImageButton)findViewById(R.id.searchMT);
		searchBtnEM = (ImageButton)findViewById(R.id.searchEM);
		arrayMT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, MT);
		acTextMT.setAdapter(arrayMT);
		arrayEM = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, EM);
		acTextEM.setAdapter(arrayEM);
		//获取软键盘
		imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		helpGuideWindow = (LinearLayout)findViewById(R.id.helpguidewindow);
	}
	//加载动画
	public void loadAnimation()
	{
		in = AnimationUtils.loadAnimation(MainActivity.this, R.anim.main_listview_anim_in);
		out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.main_listview_anim_out);
	}
	//设置按钮监听
	private void setListener() {
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(DataBase.isHelpGuide)
				{
					boolean finishMt = false;
					boolean finishEm = false;
					for(String s: RestoreThing.materialList)
					{
						if("NH3,H2O".contains(s))
						{
							finishMt = true;
						}else{
							finishMt = false;
						}
					}
					for(String s: RestoreThing.equipmentList)
					{
						if("玻璃管，烧杯，铁架台，胶头滴管".contains(s))
						{
							finishEm = true;
						}else{
							finishEm = false;
						}
					}
					if(finishMt)
					{
						Intent intent = new Intent(MainActivity.this, GuidePageActivity.class);
						setResult(0x12, intent);
						finish();
					}
				}else{
					Intent intent = new Intent(MainActivity.this,IntroductionActivity.class);
					startActivity(intent);
				}
				
			}
		});
		restartBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				for(View v:RestoreThing.material)
				{
					windowLayout.removeView(v);
				}
				RestoreThing.equipmentList.clear();
				RestoreThing.equipment.clear();
				RestoreThing.materialList.clear();
				RestoreThing.material.clear();
				for(View v:RestoreThing.equipment)
				{
					windowLayout.removeView(v);
				}
			}
		});
		settingBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,Setting.class);
				startActivity(intent);
			}
		});
		equipmentBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!isShowEquipment)
				{
					if(isShowMateria)
					{
						listviewLayoutMt.setVisibility(View.GONE);
						isShowMateria=false;
					}
					listviewLayout.setVisibility(View.VISIBLE);
					listviewLayout.startAnimation(in);
					isShowEquipment=true;
				}else{
					listviewLayout.setVisibility(View.GONE);
					listviewLayout.startAnimation(out);
					isShowEquipment=false;
				}
			}
		});
		materialBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!isShowMateria)
				{
					if(isShowEquipment)
					{
						listviewLayout.setVisibility(View.GONE);
						isShowEquipment=false;
					}
					listviewLayoutMt.setVisibility(View.VISIBLE);
					listviewLayoutMt.startAnimation(in);
					isShowMateria=true;
				}else{
					listviewLayoutMt.setVisibility(View.GONE);
					listviewLayoutMt.startAnimation(out);
					isShowMateria=false;
				}
			}
		});
		illustrationBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,More_menu.class);
				startActivity(intent);
			}
		});
//		eListView.setOnChildClickListener(new OnChildClickListener() {
//			
//			@Override
//			public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition,
//					int childPosition, long id) {
//				// TODO Auto-generated method stub
//				
//				return false;
//			}
//		});
//		eListView.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View arg0, MotionEvent event) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		});
		panelImage.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if(imm.hideSoftInputFromWindow(acTextMT.getWindowToken(), 0)
						|| imm.hideSoftInputFromWindow(acTextEM.getWindowToken(), 0))
				{
					
				}else{
					if(isShowMateria)
					{
						listviewLayoutMt.setVisibility(View.GONE);
						listviewLayoutMt.startAnimation(out);
						isShowMateria=false;
					}
					if(isShowEquipment)
					{
						listviewLayout.setVisibility(View.GONE);
						listviewLayout.startAnimation(out);
						isShowEquipment=false;
					}
				}
				return false;
			}
		});
		searchBtnMT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				eListViewAdapterMt.expandGoal(eListViewMt, acTextMT.getText().toString().trim());
				acTextMT.setText("");
			}
		});
		searchBtnEM.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				eListViewAdapter.expandGoal(eListView, acTextEM.getText().toString().trim());
				acTextEM.setText("");
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//如果按下返回键的响应事件，退出程序
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(DataBase.isHelpGuide)
			{
				finish();
			}else{
				if(exit)
				{
					stopService(intent);
					DataBase.db.close();
					DataBase.isHelpGuide = false;
					finish();
					System.exit(0);
				}else{
					Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
					exit=true;
					Thread t=new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							exit=false;
						}
					});
					t.start();
				}
			}
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	
}
