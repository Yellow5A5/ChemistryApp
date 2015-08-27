package com.goudan.chemstudyingapp.more;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.adapter.NoteListAdapter;
import com.goudan.chemstudyingapp.restore.DataBase;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class SmallNote extends Activity {
	private ImageView backBtn;
	private ListView listView;
	private String[] noteTitle = new String[]{"测试", "试试"};
	private NoteListAdapter adapter ;
	private List<Map<String, String>> lists;
	private Cursor cursor ;
	private String BASE=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_note);
		backBtn = (ImageView) findViewById(R.id.backbtn);
		listView = (ListView)findViewById(R.id.noteList);
		lists = new ArrayList<Map<String, String>>();
		adapter = new NoteListAdapter(this, lists,listView);
		getData();
		View v = LayoutInflater.from(this).inflate(R.layout.notelist_footerview, null);
		listView.addHeaderView(v);
		listView.setAdapter(adapter);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(SmallNote.this, NoteNewEdit.class);
				startActivityForResult(intent, 0x11);
			}
		});
	}
	
	public void getData()
	{
		if(lists != null)
		{
			lists.removeAll(lists);
		}
		if(DataBase.db != null)
		{
			cursor = DataBase.db.rawQuery("select * from note_inf", null);
			
		}
		if(cursor.moveToLast())
		{
			while(true)
			{
				Map<String, String> list = new HashMap<String, String>();
				list.put("title", cursor.getString(1));
				list.put("content", cursor.getString(2));
				list.put("time", cursor.getString(3));
				lists.add(list);
				if(!cursor.moveToPrevious())
				{
					break;
				}
			}
		}
		cursor.close();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 0x11 && requestCode == 0x11){
			getData();
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
}
