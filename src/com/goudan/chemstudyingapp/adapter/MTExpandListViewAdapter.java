package com.goudan.chemstudyingapp.adapter;

import java.util.HashMap;
import java.util.Map;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.restore.RestoreThing;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MTExpandListViewAdapter extends BaseExpandableListAdapter {
	private Context context;
	private RelativeLayout windowLayout,panelLayout;
	private LinearLayout binLayout;
	private ImageView bin;
	//设置项目名称
	private String[] groupName={"气体","液体","固体"};
	private String[][] childName = new String[][] {
			{"二氧化碳","氧化二氮","一氧化氮","氧气","二氧化硫","二氧化氮","三氧化硫","氢气","硫化氢","氨气","氯气","一氧化碳","氟气"},
			{"盐酸","硫化钠溶液","硫酸铜","硫酸亚铁","硝酸亚铁","硫酸铁","硝酸铁","氟化氢溶液","碘化钠","溴化钠","氢氧化钡","硝酸铵","氢氧化钙","氯化镁","氯化钡","硫酸钠","次氯酸","稀硝酸","偏铝酸钠","氢氧化铜","氯化亚铁","氯化铁","氯化铜","氯化铝","水","硝酸银","氢氧化亚铁","氢氧化铁","硅酸钠","高锰酸钾","碳酸氢铵","氢氧化钾","烧碱","汞","浓盐酸","稀盐酸","浓硝酸","浓硫酸","稀硫酸","双氧水","亚硫酸","氯酸钾"},
			{"钙","白磷","钾","钠","氧化汞","硅","碘","二氧化锰","铝","镁","氢氧化镁","氢氧化铝","氧化锌","过氧化钠","氧化铁","二氧化硅","过氧化钠","氧化镁","硅","氧化亚铁","银","锌","铁","铜","四氧化三铁","连二亚硫酸钠","碳酸钙","碳酸钠","石灰乳","磷","硫","炭","氧化铜","氧化钙 ","碳酸氢钠"},
			};
	private String[][] childName1 = new String[][] {
			{"CO2","N2O","NO","O2","SO2","SO3","NO2","H2","H2S","NH3","Cl2","CO","F2"},
			{"HCl","Na2S","CuSO4","FeSO4","Fe(NO3)2","Fe2(SO4)3","Fe(NO3)3","HF","NaI","NaBr","Ba(OH)2","NH4NO3","Ca(OH)2","MgCl2","BaCl2","Na2SO4","HClO","HNO3(稀)","NaAlO2","Cu(OH)2","FeCl2","FeCl3","CuCl2","AlCl3","H2O","AgNO3","Fe(OH)2","Fe(OH)3","Na2SiO3","KMnO4","NH4HCO3","KOH","NaOH","Hg","HCl(浓)","HCl(稀)","HNO3(浓)","H2SO4(浓)","H2SO4(稀)","H2O2","H2SO3","KClO3"},
			{"Ca","P4","K","Na","HgO","Si","I2","MnO2","Al","Mg","Mg(OH)2","Al(OH)3","ZnO","Na2O2","Fe2O3","SiO2","Na2O2","MgO","Si","FeO","Ag","Zn","Fe","Cu","Fe3O4","Na2S2O4","CaCO3","Na2CO3","CaOH","P","S","C","CuO","CaO","NaHCO3"},
			};
//	private int[][] childImage = new int[][]{
//									{},
//									{ R.drawable.naoh, R.drawable.hcl_x, R.drawable.phenolphthalein_solution},
//									{},
//									{}};
	private int preX,preY,X,Y;
	private boolean isShow=false;
	private int isTwice=0;
	private Map<TextView,String> connection=new HashMap<TextView,String>();
	private WindowManager wm;
	private float width,height;
	private DisplayMetrics metric = new DisplayMetrics();
	private boolean isMove=false;
	private Vibrator vibrator=null;
	
	public MTExpandListViewAdapter(Context context,RelativeLayout windowLayout,RelativeLayout panelLayout,
			LinearLayout binLayout,ImageView bin)
	{
		this.context=context;
		this.windowLayout=windowLayout;
		this.panelLayout=panelLayout;
		this.binLayout=binLayout;
		this.bin=bin;
		wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
	    height = metric.heightPixels;
	    vibrator=(Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childName[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup viewGroup) {
		
		ChildViewHolder viewHolder=new ChildViewHolder();
		convertView=LayoutInflater.from(context).inflate(R.layout.expandlistview_item_item_material, null);
		viewHolder.text=(TextView)convertView.findViewById(R.id.text_item);
		viewHolder.text.setText(childName[groupPosition][childPosition]);
		convertView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				
				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:		
						isShow=true;
						break;
					case MotionEvent.ACTION_UP:	
						
						if(isShow)
						{
							String s=childName1[groupPosition][childPosition];
							SpannableString ss=new SpannableString(s);
							for(int i=0;i<ss.length();i++)
							{
								if(s.substring(i, i+1).matches("\\d"))
								{
									ss.setSpan(new RelativeSizeSpan(0.5f), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);									
								}
							}
							TextView textView=new TextView(context);
							textView.setText(ss);
							textView.setTextColor(Color.BLACK);
							textView.setTextSize(34);
							textView.setLines(1);
							windowLayout.addView(textView);
							RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)textView.getLayoutParams();
							params.setMargins((int)event.getRawX()-textView.getWidth()/2, (int)event.getRawY()-(int)textView.getTextSize(), 0, 0);
							textView.setLayoutParams(params);
//							ImageView image=new ImageView(context);
							RestoreThing.addMaterialImage(textView);//存储新建的图片
//							image.setImageResource(childImage[groupPosition][childPosition]);
							connection.put(textView, childName1[groupPosition][childPosition]);
							RestoreThing.addMateria(childName1[groupPosition][childPosition]);
//							for(String s:RestoreThing.materialList)
//							{
//								Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//							}
//							windowLayout.addView(image);
//							RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)image.getLayoutParams();
//							params.setMargins((int)event.getRawX()-image.getWidth()/2, (int)event.getRawY()-image.getHeight(), 0, 0);
//							image .setLayoutParams(params);
							textView.setOnLongClickListener(new OnLongClickListener() {
								
								@Override
								public boolean onLongClick(View arg0) {
									if(!isMove)
									{
										binLayout.setVisibility(View.VISIBLE);
										vibrator.vibrate(new long[]{50,50,50,100,50}, -1);
									}
									return false;
								}
							});
							textView.setOnTouchListener(new OnTouchListener() {
								
								@Override
								public boolean onTouch(View v, MotionEvent e) {
									switch (e.getAction())
									{
										case MotionEvent.ACTION_DOWN:
											
											preX=(int)e.getRawX();
											preY=(int)e.getRawY();
											isMove=false;
											return false;
//											break;
										case MotionEvent.ACTION_UP:
											if(binLayout.getVisibility()==View.VISIBLE)
											{
												if(e.getRawY()<binLayout.getBottom()&&e.getRawX()<binLayout.getRight()){
													delete(v);
												}
											}
											binLayout.setVisibility(View.GONE);
//											break;
											return false;
										case MotionEvent.ACTION_MOVE:
											X=(int)e.getRawX();
											Y=(int)e.getRawY();
											int dx=X-preX;
											int dy=Y-preY;
//											int left=v.getLeft()+dx;    
//											int top=v.getTop()+dy;
//											int right=v.getRight()+dx;
//											int bottom=v.getBottom()+dy;
											if(dx<-5||dy<-5||dx>5||dy>5)
											{
												isMove=true;
											}else{
//												isMove=false;
											}
//											if(bottom<panelLayout.getBottom()&&right<panelLayout.getRight())
//											{
//												v.layout(left, top, right, bottom);
//												
//											}else if(bottom<panelLayout.getBottom()&&right>panelLayout.getRight()){
//												v.layout(left-dx, top, right-dx, bottom);
//											}else if(bottom>panelLayout.getBottom()&&right<panelLayout.getRight()){
//												v.layout(left, top-dy, right, bottom-dy);
//											}
											RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)v.getLayoutParams();
											if(e.getRawY()<panelLayout.getBottom()&&e.getRawX()<panelLayout.getRight())
											{
												if(e.getRawY()<binLayout.getBottom()&&e.getRawX()<binLayout.getRight()){
													bin.setImageResource(R.drawable.recycle_bin_pressed);
												}else{
													bin.setImageResource(R.drawable.recycle_bin);
												}
												params.setMargins((int)e.getRawX()-v.getWidth()/2, (int)e.getRawY()-v.getHeight(), 0, 0);
											}else if(e.getRawY()<panelLayout.getBottom()&&e.getRawX()>panelLayout.getRight()){
												params.setMargins((int)panelLayout.getRight()-v.getWidth()/2, (int)e.getRawY()-v.getHeight(), 0, 0);
											}else if(e.getRawY()>panelLayout.getBottom()&&e.getRawX()<panelLayout.getRight()){
												params.setMargins((int)e.getRawX()-v.getWidth()/2,(int) panelLayout.getBottom()-v.getHeight(), 0, 0);
											}else{
												params.setMargins((int)panelLayout.getRight()-v.getWidth(), (int)panelLayout.getBottom()-v.getHeight(), 0, 0);
											}
											v.setLayoutParams(params);
											preX=(int)e.getRawX();
											preY=(int)e.getRawY();
											break;
									}
									return true;
								}

							});
						}
						isShow=false;
						break;
				}
				return true;
			}
		});
		return convertView;
	}

	public void delete(View v) {
		// TODO Auto-generated method stub
		RestoreThing.removeMaterialImage(v);//删除已有的View
		RestoreThing.removeMateria(connection.get(v));
		windowLayout.removeView(v);
		
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childName[groupPosition].length;
	}

	@Override
	public Object getGroup(int position) {
		// TODO Auto-generated method stub
		return groupName[position];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupName.length;
	}

	@Override
	public long getGroupId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getGroupView(int position, boolean isEpanded, View view, ViewGroup arg3) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=new ViewHolder();
		view=LayoutInflater.from(context).inflate(R.layout.expandlistview_item_materia, null);
		viewHolder.text=(TextView)view.findViewById(R.id.text);
		viewHolder.text.setText(groupName[position]);
		if(isEpanded)
		{
			viewHolder.text.setBackgroundResource(R.drawable.equipment_panel_pressed);
		}else{
			viewHolder.text.setBackgroundResource(R.drawable.equipment_panel);
		}
		return view;
	}
	
	

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	
	//展开指定的物品的位置
	public void expandGoal(ExpandableListView list, String goal)
	{
		int n = -1, m = -1;
		for(int i = 0; i < groupName.length; i++)
		{
			for(int j = 0; j < childName[i].length; j++)
			{
				if(goal.equals(childName[i][j]))
				{
					n = i;
					m = j;
					break;
				}
			}
		}
		if(n == -1 && m == -1 )
		{
			
		}else{
			list.expandGroup(n);
			list.setSelectedChild(n, m, true);
		}
	} 
	
	class ViewHolder
	{
		TextView text;
	}
	
	class ChildViewHolder
	{
		TextView text;
	}

}
