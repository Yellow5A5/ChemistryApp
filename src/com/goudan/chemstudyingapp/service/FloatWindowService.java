package com.goudan.chemstudyingapp.service;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.jiazaiye.GuideActivity;
import com.goudan.chemstudyingapp.more.NoteDialog;

import android.app.AlertDialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class FloatWindowService extends Service {
	private  MyBinder binder = new MyBinder();
	//定义浮动窗口布局  
	private LinearLayout mFloatLayout;  
	private WindowManager.LayoutParams wmParams;  
    //创建浮动窗口设置布局参数的对象  
	private WindowManager mWindowManager; 
	private ImageButton mFloatView; 
	//获取手机屏幕的大小
	private DisplayMetrics metrics = new DisplayMetrics();
	private int windowWidth, windowHeight;
	private float RATE = 1.73f;
	private boolean isClick = false;
	private int preX, preY;
	
	public class MyBinder extends Binder
	{
		
	}

	@Override
	public void onCreate() {
		super.onCreate();
		createFloatView(); 
	}

	private void createFloatView() {
		wmParams = new WindowManager.LayoutParams();  
        //获取的是WindowManagerImpl.CompatModeWrapper  
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);          
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        windowWidth = metrics.widthPixels;
        windowHeight = metrics.heightPixels;
        //设置window type  
        wmParams.type = LayoutParams.TYPE_PHONE;   
        //设置图片格式，效果为背景透明  
        wmParams.format = PixelFormat.RGBA_8888;   
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）  
        wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;        
        //调整悬浮窗显示的停靠位置为左侧置顶  
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;         
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity  
        wmParams.x = 0;  
        wmParams.y = 0;  
  
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT; 
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
  
         /*// 设置悬浮窗口长宽数据 
        wmParams.width = 200; 
        wmParams.height = 80;*/  
     
        LayoutInflater inflater = LayoutInflater.from(getApplication());  
        //获取浮动窗口视图所在布局  
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);  
        //添加mFloatLayout  
        mWindowManager.addView(mFloatLayout, wmParams);  
        //浮动窗口按钮  
        mFloatView = (ImageButton)mFloatLayout.findViewById(R.id.float_id);  
          
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,  
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec  
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));  
        //设置监听浮动窗口的触摸移动  
        mFloatView.setOnTouchListener(new OnTouchListener()   
        {  

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					isClick=true;
					preX = (int) event.getRawX();
					preY = (int) event.getRawY();
				}
				if(event.getAction() == MotionEvent.ACTION_MOVE)
				{
					if((int) event.getRawX() - preX > 5 ||(int) event.getRawX() - preX < -5 ||
							(int) event.getRawY() - preY > 5 ||(int) event.getRawY() - preY < -5)
					{
						//getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标  
		                wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth()/2;  
		                //减25为状态栏的高度  
		                wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight(); 
		                 //刷新  
		                mWindowManager.updateViewLayout(mFloatLayout, wmParams); 
		                isClick = false;
					}
					
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					if(isClick)
					{
						Intent intent = new Intent();  
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setClass(getApplicationContext(),NoteDialog.class);  
						startActivity(intent);
						mFloatView.setVisibility(View.GONE);
					}else{
						if((int) event.getRawX() < windowHeight*RATE/2 && (int) event.getRawY() 
								> (int) event.getRawX()/RATE && (int) event.getRawY() < 
								windowHeight - (int) event.getRawX()/RATE)
						{
			                wmParams.x = 0;  
			                wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight();
			                mWindowManager.updateViewLayout(mFloatLayout, wmParams); 
						}else if(windowWidth - (int) event.getRawX() < windowHeight*RATE/2 && (int) event.getRawY() 
								> (windowWidth - (int) event.getRawX())/RATE && (int) event.getRawY() < 
								windowHeight - (windowWidth - (int) event.getRawX())/RATE)
						{
			                wmParams.x = windowWidth - mFloatView.getMeasuredWidth();  
			                wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight();
			                mWindowManager.updateViewLayout(mFloatLayout, wmParams); 
						}else if((int) event.getRawY() < windowHeight/2 && windowWidth - (int) event.getRawX() > 
									(int) event.getRawY()*RATE && (int) event.getRawX() > 
									(int) event.getRawY()*RATE)
						{
			                wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth()/2;  
			                wmParams.y = 0;
			                mWindowManager.updateViewLayout(mFloatLayout, wmParams); 
						}else
						{
			                wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth()/2;  
			                wmParams.y = windowHeight - mFloatView.getMeasuredHeight();
			                mWindowManager.updateViewLayout(mFloatLayout, wmParams); 
						}
					}
					
				}
				//此处必须返回false，否则OnClickListener获取不到监听
                return false;    
			}  
        });   
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mFloatLayout != null)  
        {  
            //移除悬浮窗口  
            mWindowManager.removeView(mFloatLayout);  
        } 
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mFloatView.setVisibility(View.VISIBLE); 
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

}
