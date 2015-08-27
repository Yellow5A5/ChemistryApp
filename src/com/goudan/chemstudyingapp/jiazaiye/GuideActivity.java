package com.goudan.chemstudyingapp.jiazaiye;

import java.util.ArrayList;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends Activity {
	private ViewPager viewPager;
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private ImageView mPage3;
	private ImageView mPage4;
	private ImageView mPage5;
	private ImageView mPage6;
	private int currIndex = 0;
	private ArrayList<View> views;
	private Button start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		viewPager=(ViewPager)findViewById(R.id.viewpager);
		mPage0 = (ImageView)findViewById(R.id.page0);
        mPage1 = (ImageView)findViewById(R.id.page1);
        mPage2 = (ImageView)findViewById(R.id.page2);
        mPage3 = (ImageView)findViewById(R.id.page3);
        mPage4 = (ImageView)findViewById(R.id.page4);
        mPage5 = (ImageView)findViewById(R.id.page5);
        mPage6 = (ImageView)findViewById(R.id.page6);
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.guide1, null);
        View view2 = mLi.inflate(R.layout.guide2, null);
        View view3 = mLi.inflate(R.layout.guide3, null);
        View view4 = mLi.inflate(R.layout.guide4, null);
        View view5 = mLi.inflate(R.layout.guide5, null);
        View view6 = mLi.inflate(R.layout.guide6, null);
        View view7 = mLi.inflate(R.layout.guide7, null);
        views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        views.add(view7);
        views.add(new View(this));
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			
			
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		viewPager.setAdapter(mPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:				
					mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
					mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
					break;
				case 1:
					mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
					mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));
					mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
					break;
				case 2:
					mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
					mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
					mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
					break;
				case 3:
					mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
					mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
					mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
					break;
				case 4:
					mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
					mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
					mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
					break;
				case 5:
					mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
					mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
					mPage6.setImageDrawable(getResources().getDrawable(R.drawable.page));
					break;
				case 6:
					mPage6.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
					mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
					break;
				case 7:
					Intent intent=new Intent(GuideActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
					break;
				}
				currIndex = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
