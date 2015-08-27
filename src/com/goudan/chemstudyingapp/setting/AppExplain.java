package com.goudan.chemstudyingapp.setting;

import com.example.chemstudyingapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AppExplain extends Activity{
	ImageView backBtn;
	TextView text;
	String s="1，用户点击仪器按钮从弹出菜单选择所需实验仪器；\r\n"
				+"2，点击原料按钮从弹出菜单选择所需反应原料；\r\n"
				+"3，长按原料/仪器，将其拖入回收站可以删除该原料/仪器；\r\n"
				+"4，点击开始按钮，应用会依据你选择的原料判断可否反应，并输出反应结果以及解析；\r\n"
				+"5，点击重置按钮，可以清空实验面板上所有的原料/仪器，重新选择所需原料/仪器；\r\n"
				+"6，点击扩展功能按钮，可以快速查看元素周期表，化学俗名，物质颜色，物质性质等化学基本知识，同时还能进入这个应用的论坛进行咨询/学习；\r\n"
				+"7，点击设置按钮，可以查看应用说明，和关于我们团队的信息，也可以给我们留言，检查应用更新，以及将应用分享到微信/微博/QQ/QQ空间。\r\n";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_appexplain);
		backBtn=(ImageView)findViewById(R.id.backbtn);
		text=(TextView)findViewById(R.id.text);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		text.setText(s);
	}
}
