package com.goudan.chemstudyingapp.jiazaiye;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.main.MainActivity;
import com.goudan.chemstudyingapp.restore.DataBase;
import com.goudan.chemstudyingapp.restore.EquationList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class LoadActivity extends Activity {
	private SharedPreferences preference;
	private SharedPreferences.Editor editor;
	private int useTime;
	private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jiazai);
        preference=getSharedPreferences("setting", 0);
        useTime=preference.getInt("usetime", 0);
		db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/my.db3", null);
		
		DataBase.db = db;
        Thread thread=new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				InputStream is1=null,is2=null,is3=null,is4=null,is5=null, is6 =null;
				try {
					is1=getAssets().open("material.txt");
					int size1=is1.available();
					byte[] buff1=new byte[size1];
					is1.read(buff1);
					String s1=new String(buff1,"UTF-8");
					EquationList.material=s1.split("\r\n");

					Log.i("ss",EquationList.material[0].split(",")[1]);
					Log.i("sss",EquationList.material[2].split(",")[1]);
					Log.i("sss",EquationList.material[2].split(",")[0]);
					
					is2=getAssets().open("gongshi.txt");
					int size2=is2.available();
					byte[] buff2=new byte[size2];
					is2.read(buff2);
					String s2=new String(buff2,"UTF-8");
					EquationList.result=s2.split("\r\n");
					
					is3=getAssets().open("tiaojian.txt");
					int size3=is3.available();
					byte[] buff3=new byte[size3];
					is3.read(buff3);
					String s3=new String(buff3,"UTF-8");
					EquationList.condition=s3.split("\r\n");
					
					is4=getAssets().open("xianxiang.txt");
					int size4=is4.available();
					byte[] buff4=new byte[size4];
					is4.read(buff4);
					String s4=new String(buff4,"UTF-8");
					EquationList.phenomenon=s4.split("\r\n");
					
					is5=getAssets().open("yiqi.txt");
					int size5=is5.available();
					byte[] buff5=new byte[size5];
					is5.read(buff5);
					String s5=new String(buff5,"UTF-8");
					EquationList.equipment=s5.split("\r\n");
					
					is6=getAssets().open("url.txt");
					int size6=is6.available();
					byte[] buff6=new byte[size6];
					is6.read(buff6);
					String s6=new String(buff6,"UTF-8");
					EquationList.url=s6.split("\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						is1.close();
						is2.close();
						is3.close();
						is4.close();
						is5.close();
						is6.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//				EquationList.init();
				if(useTime==0)
				{
					insertData(db, "title", "content", "time");
					Intent intent=new Intent(LoadActivity.this,GuideActivity.class);
					startActivity(intent);
				}else{
					Intent intent=new Intent(LoadActivity.this,MainActivity.class);
					startActivity(intent);//进入主界面
				}
				finish();//关闭加载页面
			}});
        thread.start();
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
}
