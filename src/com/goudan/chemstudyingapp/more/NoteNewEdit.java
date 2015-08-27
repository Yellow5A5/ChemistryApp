package com.goudan.chemstudyingapp.more;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.restore.DataBase;

public class NoteNewEdit extends Activity{
	private EditText contentText;
	private ImageView backBtn;
	private EditText titleText;
	private SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
	private String title = "", content = "", time = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_note_edit);
		contentText = (EditText)findViewById(R.id.noteEditContent);
		backBtn = (ImageView)findViewById(R.id.backbtn);
		titleText = (EditText)findViewById(R.id.noteEditTitle);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				wantToSave();
			}
		});
	}
	public void wantToSave() {
		if(!titleText.getText().toString().equals(title) || !contentText.getText().toString().equals(content))
		{
			title = titleText.getText().toString();
			content = contentText.getText().toString();
			AlertDialog.Builder builder = new Builder(this)
							.setTitle("提示")
							.setMessage("是否保存当前化学笔记？")
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									finish();
								}

							})
							.setPositiveButton("保存", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									Date curDate = new Date(System.currentTimeMillis());//获取当前时间
									String new_time = formatter.format(curDate);
									insertData(DataBase.db, title, content, new_time);
									Intent intent = new Intent(NoteNewEdit.this, SmallNote.class);
									setResult(0x11,intent);
									finish();
								}

							});
			builder.create().show();
		}else{
			finish();
		}
	}
	
	
	private void insertData(SQLiteDatabase db, String title, String content, String time)
	{
		try
		{
			db.execSQL("insert into note_inf values(null, ?, ?, ?)", new String[]{title, content, time});
		}catch(Exception e){
			db.execSQL("create table note_inf(_id integer primary key autoincrement,"
					+ "news_title varchar(50)," + "news_content varchar(255), news_time varchar(255))");
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//如果按下返回键的响应事件，退出程序
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			wantToSave();
		}
		return super.onKeyDown(keyCode, event);
	}
}
