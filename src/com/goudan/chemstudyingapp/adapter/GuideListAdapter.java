package com.goudan.chemstudyingapp.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chemstudyingapp.R;
import com.goudan.chemstudyingapp.more.NoteEdit;
import com.goudan.chemstudyingapp.restore.DataBase;

public class GuideListAdapter  extends BaseAdapter{
	private List<Map<String, String>> lists;
	private Activity context;
	private ListView listView;
	private int requestCode = 0x11;

	public GuideListAdapter(Activity context, List<Map<String, String>> lists, ListView listView)
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
	public View getView(int position, View convertView, ViewGroup parentView) {
		convertView = LayoutInflater.from(context).inflate(R.layout.guidelist_item, null);
		TextView text = (TextView)convertView.findViewById(R.id.guidetext);
		LinearLayout noteLayout = (LinearLayout)convertView.findViewById(R.id.guideLayout);
		text.setText(lists.get(position).get("text"));
		return convertView;
	}
}
