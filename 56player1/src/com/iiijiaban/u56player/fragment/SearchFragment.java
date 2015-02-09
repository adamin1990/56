package com.iiijiaban.u56player.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.iiijiaban.u56playermv.R;
import com.iiijiaban.u56player.adapter.ZhuTiAdapter;
import com.iiijiaban.u56player.beans.ZhuTi;
import com.iiijiaban.u56player.ui.VideoViewPlayingActivity;
import com.iiijiaban.u56player.util.JsonUtil;
import com.wole56.sdk.Video;

public class SearchFragment extends Fragment{
	private ListView videoList;
	private ArrayList<ZhuTi> mList;
	private ArrayList<ZhuTi> vList;
	
	private Handler handler;
	private String video;
	private ZhuTiAdapter vAdapter;
	private String searchText = null;
	private int page = 1;
	private boolean isresh = true;
    private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.searchfragment, container, false);
		
		context = getActivity();
		videoList = (ListView) view.findViewById(R.id.searchlist);
		
		mList = new ArrayList<ZhuTi>();
		vAdapter = new ZhuTiAdapter(context, mList);
		
		searchText = getArguments().getString("SearchText");
		
		videoList.setAdapter(vAdapter);
		init();
		return view;
		
	}
	@SuppressLint("HandlerLeak")
	private void init(){
		handler = new Handler(){

			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				vList = (ArrayList<ZhuTi>) msg.obj;
				vAdapter.resh(vList);
			}
			
		};
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				
				video = Video.searchVideo(context, searchText, 
						"20","1").toString();
				JsonUtil.getSearch(video, mList);
				Message message = Message.obtain();
				message.obj = mList;
				handler.sendMessage(message);
			}
			
		}).run();
		
		videoList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Intent intent = new Intent(context,
						VideoViewPlayingActivity.class);
				intent.putExtra("movie", mList.get(position));
				startActivity(intent);
			}
			
		});
		
		videoList.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0){
					page+=1;
					new Thread(new Runnable(){

						@Override
						public void run() {
							
							video = Video.searchVideo(context, searchText, 
									"20", Integer.toString(page)).toString();
							JsonUtil.getSearch(video, vList);
							Message message =Message.obtain();
							message.obj = vList;
							handler.sendMessage(message);
						}
						
					}).run();
					isresh = false;
				}
				
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
		});
	}
	
}
