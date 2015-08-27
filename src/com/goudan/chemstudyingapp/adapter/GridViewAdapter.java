package com.goudan.chemstudyingapp.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.imageUploader.UploadActivity;
import com.goudan.chemstudyingapp.more.More_BBS;
import com.goudan.chemstudyingapp.more.More_Color;
import com.goudan.chemstudyingapp.more.More_suming;
import com.goudan.chemstudyingapp.more.More_xingzhi;
import com.goudan.chemstudyingapp.more.More_zhouqibiao;
import com.goudan.chemstudyingapp.more.SmallNote;
import com.goudan.chemstudyingapp.setting.AboutUs;
import com.goudan.chemstudyingapp.setting.AppExplain;
import com.goudan.chemstudyingapp.setting.WriteToUs;

public class GridViewAdapter extends SimpleAdapter {
	
	private ViewHolder viewHolder;
	private RelativeLayout setting_relative;
	private ProgressDialog pd;
	private boolean isShow=false;
	
	private String BASE=null;
//	private ListView list;

	
	public boolean flag=true;//区别是调用设置还是调用了拓展功能
	private Context context;
	List<Map<String,Object>> lists;
	int resId;
	private int[] tag={1,2,3,4,5,6};
	
	public GridViewAdapter(Context context,List<Map<String, Object>> data) {
		super(context, data, 0, null, null);
		this.context=context;
		this.lists=data;

	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		viewHolder=new ViewHolder();
		convertView=LayoutInflater.from(context).inflate(R.layout.setting_gridview_item, null);
		viewHolder.image=(ImageView)convertView.findViewById(R.id.settingiamge);
		
		setting_relative =(RelativeLayout)convertView.findViewById(R.id.setting_Relative);
//		list = (ListView)convertView.findViewById(R.id.list_share);
		
		resId=(Integer)lists.get(position).get("image");
		viewHolder.image.setImageResource(resId);
		viewHolder.image.setId(tag[position]);
		viewHolder.image.setOnClickListener(new listener());
		return convertView;
	}
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==0x123)
			{
				pd.dismiss();
				Toast.makeText(context, "当前版本已经是最新版本", Toast.LENGTH_SHORT).show();
			}
		}};
	class listener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (flag) {

				switch (v.getId()) {
				case 1:
					Intent intent1 = new Intent(context, AppExplain.class);
					context.startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent(context, AboutUs.class);
					context.startActivity(intent2);
					break;
				case 3:
					Intent intent3= new Intent(context,WriteToUs.class);
					context.startActivity(intent3);
					break;
				case 4:
					pd=new ProgressDialog(context);
					pd.setTitle("检查更新");
					pd.setMessage("检查更新中，请稍后...");
					pd.show();
					Thread t=new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							handler.sendEmptyMessage(0x123);
						}
					});
					t.start();
					break;
				case 5:
					//照片笔记。
					Intent intent5 = new Intent(context,UploadActivity.class);
					context.startActivity(intent5);
					break;
					
				case 6:
					BASE = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
					fileSave();
					shareToTimeLine();
//					if(isShow){	//分享的入口 。改用直接用系统的分享方法，故这些舍弃了。（以后继续完善界面时考虑使用）
//						Setting.list.setVisibility(View.GONE);
//						Setting.list.startAnimation(AnimationUtils.loadAnimation(context, R.anim.main_listview_anim_out));
//						isShow=false;
//					}else{
//						Setting.list.setVisibility(View.VISIBLE);
//						Setting.list.startAnimation(AnimationUtils.loadAnimation(context, R.anim.main_listview_anim_in));
//						isShow=true;
//					}
					break;
//					Setting.gridView.setAlpha
					//分享应用。
//				     String[] name1 = new String[]{"1号君","2号君","3号君","4号君"};
//				     String[] name2 = new String[]{"8号君","7号君","6号君","5号君"};
//				     int[] imageIds = new int[]
//				    		 {R.drawable.m1,R.drawable.m1,R.drawable.m1,R.drawable.m1};
//						List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();//Map 有点像映射一样，string对应Object。
//						Log.e("1","0");
//						for (int i = 0; i < name1.length; i++) {
//							Map<String, Object> listItem = new HashMap<String, Object>();
//							listItem.put("header", imageIds[i]);
//							listItem.put("personName", name1[i]);
//							listItem.put("desc", name2[i]);
//							Log.e("1","1"+imageIds[i]);
//							Log.e("1","1"+name1[i]);
//							Log.e("1","1"+name2[i]);
//							listItems.add(listItem);
//						}
//						Log.e("1","1");
//						// 创建一个simpleadapter.
//						SimpleAdapter simpleAdapter = new SimpleAdapter(context, listItems,R.layout.list_share,
//								  new String[] { "personName", "header","desc" }, 
//								  new int[] { R.id.text1, R.id.imageview1, R.id.text2});
//						Log.e("1","2");
//						list.setAdapter(simpleAdapter);
//						Log.e("1","3");
						
//						
//						
//						list.setOnItemClickListener(new OnItemClickListener() {
//
//							@Override
//							public void onItemClick(AdapterView<?> parent, View view,
//									int position, long id) {
//								Toast.makeText(getApplicationContext(),name1[position]+"__"+name2[position], 1).show();
//							}
//						});
				
				}
			}else
			{

				switch (v.getId()) {
				case 1:
					 Intent intent1 = new Intent(context, More_zhouqibiao.class);
					 context.startActivity(intent1);
					break;
				case 2:
					 Intent intent2 = new Intent(context, More_suming.class);
					 context.startActivity(intent2);
					break;
				case 3:
					 Intent intent3 = new Intent(context, More_Color.class);
					 context.startActivity(intent3);
					break;
				case 4:
					 Intent intent4= new Intent(context,More_xingzhi.class);
					 context.startActivity(intent4);
					break;
				case 5:
					 Intent intent5= new Intent(context,More_BBS.class);
					 context.startActivity(intent5);
					break;
				case 6:
					Intent intent = new Intent(context, SmallNote.class);
					context.startActivity(intent);
					break;
				}

			}
		}
		
	}
	
	class ViewHolder
	{
		ImageView image;
	}
	
	//时间比较赶，下次要打包成方法。
	private void shareToTimeLine() {
		Intent intent =  new Intent();
//		intent.setAction(Intent.ACTION_SEND);
		intent.setAction("android.intent.action.SEND");//与上面等同
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT,"化学方程式下载地址二维码");
		intent.setType("image/*");
		//intent.setFlags(0x3000001);
//		intent.putExtra(Intent.EXTRA_STREAM	, Uri.fromFile(file));
		intent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file:////sdcard//chemsave//sharephoto.jpg"));


//		intent.putExtra(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
		context.startActivity(intent);
	}
	
	private void fileSave() {
		File path = new File(BASE + "/chemsave");
		if (!path.exists()) {// 目录存在返回false 
			path.mkdirs();// 创建一个目录 
		} 
		File f = new File(BASE + "/chemsave/sharephoto.jpg");// 创建文件
		
		if (!f.exists()) {
			//将raw中文件复制到内存卡中
			try{
	            File dir = new File(BASE);
	            if (!dir.exists())
	                dir.mkdir();
	            if (!f.exists()){
	                InputStream is = context.getResources().openRawResource(R.drawable.sharephoto);
	                //是不是因为这个没法路径~大概是用空的context是不能得到路径的？
	                FileOutputStream fos = new FileOutputStream(f);
	                byte[] buffer = new byte[8192];
	                int count = 0;
	                while ((count = is.read(buffer)) > 0){
	                    fos.write(buffer, 0, count);
	                }
	                fos.close();
	                is.close();
	            }
	        }catch (Exception e){ 
	        	e.printStackTrace(); 
	        }
		}
	}

}
