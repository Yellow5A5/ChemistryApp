package com.zhy.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Upload {
//	private static String srcPath = "E:\\main.php";  
/*	
 * public static void main(String[] args) {
		final ArrayList<String> array = new ArrayList<String>();
		array.add("E:\\main.php");
		array.add("E:\\jd-gui.exe");
		array.add("E:\\非单片机学习视频\\PHP从入门到精通笔记_韩顺平181页-官网整理.doc");
		
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
		for (int i = 0; i < array.size(); i++) {
			final int temp = i;
		fixedThreadPool.execute(new Runnable() {
			public void run() {
				uploadFile(array.get(temp));
				System.out.println("get the "+temp);
				}
			});
		}
	}
	*/
	
	  /* 上传文件至Server，uploadUrl：接收文件的处理页面 */
	  public void uploadFile(String srcPath)
	  {
	    String end = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "******";
	    try
	    {
	      URL url = new URL("http://4.yellow5a5.sinaapp.com/index.php?c=main&a=upload");
	      HttpURLConnection httpURLConnection = (HttpURLConnection) url
	          .openConnection();
	      // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
	      // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
	      httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
	      // 允许输入输出流
	      httpURLConnection.setDoInput(true);
	      httpURLConnection.setDoOutput(true);
	      httpURLConnection.setUseCaches(false);
	      // 使用POST方法
	      httpURLConnection.setRequestMethod("POST");
	      httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
	      httpURLConnection.setRequestProperty("Charset", "UTF-8");
	      httpURLConnection.setRequestProperty("Content-Type",
	          "multipart/form-data;boundary=" + boundary);

	      DataOutputStream dos = new DataOutputStream(
	          httpURLConnection.getOutputStream());
	      dos.writeBytes(twoHyphens + boundary + end);
	      dos.writeBytes("Content-Disposition: form-data; name=\"myfile\"; filename=\""
	          + srcPath.substring(srcPath.lastIndexOf("/") + 1)
	          + "\""
	          + end);
	      dos.writeBytes(end);

	      FileInputStream fis = new FileInputStream(srcPath);
	      byte[] buffer = new byte[8192]; // 8k
	      int count = 0;
	      // 读取文件
	      while ((count = fis.read(buffer)) != -1)
	      {
	        dos.write(buffer, 0, count);
	      }
	      fis.close();

	      dos.writeBytes(end);
	      dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
	      dos.flush();

	      InputStream is = httpURLConnection.getInputStream();
	      InputStreamReader isr = new InputStreamReader(is, "utf-8");
	      BufferedReader br = new BufferedReader(isr);
	      String result = br.readLine();
	      System.out.println(result);
	      dos.close();
	      is.close();

	    } catch (Exception e)
	    {
	      e.printStackTrace();
	      System.out.println(e.getMessage());
	    }
	  }
}
