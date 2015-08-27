package com.goudan.chemstudyingapp.adapter;

import java.util.HashMap;
import java.util.Map;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.restore.RestoreThing;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EMExpandListViewAdapter extends BaseExpandableListAdapter {
	private Context context;
	private RelativeLayout windowLayout,panelLayout;
	private LinearLayout binLayout;
	private WindowManager wm;
	private float width,height;
	private ImageView bin;
	private DisplayMetrics metric = new DisplayMetrics();
	//设置项目名称
	private String[] groupName={"直接加热仪器","间接加热仪器","存放仪器","漏斗/取用仪器","夹持仪器","其他仪器"};
	private String[][] childName = new String[][] {
									{ "试管"},
									{"烧杯", "坩埚" },
									{"集气瓶" },
									{"量筒","胶头滴管","锥形瓶","漏斗"},
									{"镊子","坩埚钳"},
									{"酒精灯","铁架台","研钵","水槽"}};
	private int[][] childImage = new int[][]{
									{R.drawable.equipment_test_tube},
									{R.drawable.equipment_beaker,R.drawable.equipment_crucible},
									{R.drawable.equipment_gas_collecting_bottle},
									{R.drawable.equipment_cylinder,R.drawable.equipment_drip_pipe,R.drawable.equipment_flask,R.drawable.equipment_funnel},
									{R.drawable.equipment_tweezers,R.drawable.equipment_crucible_tongs},
									{R.drawable.equipment_alcohol_burner,R.drawable.equipment_iron_support,R.drawable.equipment_mortar,R.drawable.equipment_sink}};
	private int preX,preY,X,Y;
	private boolean isShow=false;
	private int isTwice=0;
	private Map<ImageView,String> connection=new HashMap<ImageView,String>();
	private boolean isMove=false;
	private Vibrator vibrator=null;
	
	public EMExpandListViewAdapter(Context context,RelativeLayout windowLayout,RelativeLayout panelLayout,
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
		// TODO Auto-generated method stub
		return childName[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		ChildViewHolder viewHolder=new ChildViewHolder();
		convertView=LayoutInflater.from(context).inflate(R.layout.expandlistview_item_item_equipment, null);
		viewHolder.text=(TextView)convertView.findViewById(R.id.text_item);
		viewHolder.image=(ImageView)convertView.findViewById(R.id.imageview);
		viewHolder.text.setText(childName[groupPosition][childPosition]);
		viewHolder.image.setImageResource(childImage[groupPosition][childPosition]);
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
							
							ImageView image=new ImageView(context);
							RestoreThing.addMaterialImage(image);//存储新建的图片
							image.setImageResource(childImage[groupPosition][childPosition]);
							connection.put(image, childName[groupPosition][childPosition]);
							RestoreThing.addEquipment(childName[groupPosition][childPosition]);
//							for(String s:RestoreThing.equipmentlList)
//							{
//								Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//							}
							windowLayout.addView(image);
							RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)image.getLayoutParams();
							params.setMargins((int)event.getRawX()-image.getWidth()/2, (int)event.getRawY()-image.getHeight(), 0, 0);
							image .setLayoutParams(params);
							image.setOnLongClickListener(new OnLongClickListener() {
								
								@Override
								public boolean onLongClick(View arg0) {
									// TODO Auto-generated method stub
									if(!isMove)
									{
										binLayout.setVisibility(View.VISIBLE);
										vibrator.vibrate(new long[]{50,50,50,100,50}, -1);
									}
									return false;
								}
							});
							image.setOnTouchListener(new OnTouchListener() {
								
								@Override
								public boolean onTouch(View v, MotionEvent e) {
									// TODO Auto-generated method stub
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
//											isTwice++;
//											if(isTwice==2)
//											{
//												delete(v);
//												isTwice=0;
//											}else if(isTwice==1)
//											{
//												Thread t=new Thread(new Runnable() {
//													
//													@Override
//													public void run() {
//														// TODO Auto-generated method stub
//														try {
//															Thread.sleep(500);
//														} catch (InterruptedException e) {
//															// TODO Auto-generated catch block
//															e.printStackTrace();
//														}
//														isTwice=0;
//													}
//												});
//												t.start();
//											}
//											Toast.makeText(context, panelLayout.getBottom()+"\\"+panelLayout.getRight(), Toast.LENGTH_SHORT).show();
											
											return false;
//											break;
										case MotionEvent.ACTION_MOVE:
											X=(int)e.getRawX();
											Y=(int)e.getRawY();
											int dx=X-preX;
											int dy=Y-preY;
											int left=v.getLeft()+dx;    
											int top=v.getTop()+dy;
											int right=v.getRight()+dx;
											int bottom=v.getBottom()+dy;
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
//											}else if(bottom<binLayout.getBottom()&&right<binLayout.getRight()){
//												bin.setImageResource(R.drawable.recycle_bin_pressed);
//												Toast.makeText(context, panelLayout.getBottom()+"\\"+panelLayout.getRight(), Toast.LENGTH_SHORT).show();
//											}else{
////												bin.setImageResource(R.drawable.recycle_bin);
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
//											isMove=true;
											return false;
//											break;
									}
									return false;
								}

								
							});
						}
						isShow=false;
						break;
//						int dx=X-preX;
//						int dy=Y-preY;
//						int left=imageNow.getLeft()+dx;    
//						int top=imageNow.getTop()+dy;
//						int right=imageNow.getRight()+dx;
//						int bottom=imageNow.getBottom()+dy;
//						int left=imageNow.getLeft()-dx;    调大小
//						int top=imageNow.getTop()-dy;
//						int right=imageNow.getRight()+dx;
//						int bottom=imageNow.getBottom()+dy;
//						imageNow.layout(left, top, right, bottom);
				}
				return true;
			}
		});
		return convertView;
	}
	public void delete(View v) {
		// TODO Auto-generated method stub
		RestoreThing.removeEquipmentImage(v);//删除已有的View
		RestoreThing.removeEquipment(connection.get(v));
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
		view=LayoutInflater.from(context).inflate(R.layout.expandlistview_item_equipment, null);
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
	
	//展开指定的仪器的位置
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
		ImageView image;
	}

}
