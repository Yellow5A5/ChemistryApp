package com.goudan.chemstudyingapp.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.more.NoteEdit;
import com.goudan.chemstudyingapp.restore.DataBase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class NoteListAdapter extends BaseAdapter{
	private List<Map<String, String>> lists;
	private Activity context;
	private ListView listView;
	private int requestCode = 0x11;
	private String BASE=null;

	public NoteListAdapter(Activity context, List<Map<String, String>> lists, ListView listView)
	{
		this.lists = lists;
		this.context = context;
		this.listView = listView;
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
	public View getView(final int position, View convertView, ViewGroup parentView) {
		convertView = LayoutInflater.from(context).inflate(R.layout.notelist_item, null);
		TextView text = (TextView)convertView.findViewById(R.id.notetext);
		ImageButton shareBtn = (ImageButton)convertView.findViewById(R.id.noteEdit);
		ImageButton deleteBtn = (ImageButton)convertView.findViewById(R.id.noteDelete);
		LinearLayout noteLayout = (LinearLayout)convertView.findViewById(R.id.noteLayout);
		
		shareBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				BASE = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
				fileSave();
				shareToTimeLine();
			}
		});
		deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String title = lists.get(position).get("title");
				String content = lists.get(position).get("content");
				String time = lists.get(position).get("time");
				DataBase.db.delete("note_inf", "news_title like ?", new String[]{title});
				lists.remove(position);
				notifyDataSetChanged();
			}
		});
		text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String title = lists.get(position).get("title");
				String content = lists.get(position).get("content");
				String time = lists.get(position).get("time");
				Intent intent = new Intent(context, NoteEdit.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", title);
				bundle.putString("content", content);
				bundle.putString("time", time);
				intent.putExtras(bundle);
				context.startActivityForResult(intent, requestCode);
			}
		});
		noteLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String title = lists.get(position).get("title");
				String content = lists.get(position).get("content");
				String time = lists.get(position).get("time");
				Intent intent = new Intent(context, NoteEdit.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", title);
				bundle.putString("content", content);
				bundle.putString("time", time);
				intent.putExtras(bundle);
				context.startActivityForResult(intent, requestCode);
			}
		});
		text.setText(lists.get(position).get("title"));
		return convertView;
	}
	
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
