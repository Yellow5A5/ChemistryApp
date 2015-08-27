package com.goudan.chemstudyingapp.more;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TeachTV extends Activity {

	private WebView webview;
	private int i = 0;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		// 实例化WebView对象
		webview = new WebView(this);
		
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webview.loadUrl(url);
				i++;
				return true;
			}
		});
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		Log.i("url",url);
		// 加载需要显示的网页
		webview.loadUrl(url);
		// 设置Web视图
		setContentView(webview);
	}

	@Override
	// 设置回退
	// 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (i > 0) {
				webview.goBack(); // goBack()表示返回WebView的上一页面
				i--;
				return true;
			} else if (i == 0) {
				finish();
			}
		}
		return false;
	}
}
