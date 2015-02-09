package com.iiijiaban.u56player.fragment;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.iiijiaban.u56playermv.R;
import com.iiijiaban.u56player.beans.OneMovieBean;
import com.iiijiaban.u56player.beans.ZhuTi;
import com.iiijiaban.u56player.ui.DetailActivity;
import com.iiijiaban.u56player.ui.VideoViewPlayingActivity;
import com.iiijiaban.u56player.util.JsonUtil;
import com.iiijiaban.u56player.util.MyTools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wole56.sdk.Video;

public class VideoDetailFragment extends Fragment {
	private String vid;
	private ZhuTi movieBean;
	private OneMovieBean oBean;
	private ImageView imageView;
	private TextView nameTextView, tagTextView, timetView, pltTextView;
	private Button button;
    private TextView contView;
    private View pView;
    private ImageLoader imageloader=ImageLoader.getInstance();
    private DisplayImageOptions options;
	RelativeLayout r;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_movi_info, container, false);
		oBean = new OneMovieBean();
		imageView = (ImageView) view.findViewById(R.id.show_movie_info_imag_id);
		nameTextView = (TextView) view.findViewById(R.id.show_movie_info_name);
		tagTextView = (TextView) view.findViewById(R.id.show_movie_tag_name);
//		pltTextView = (TextView) view.findViewById(R.id.show_movie_pl2);
		timetView = (TextView) view.findViewById(R.id.show_movie_times);
		contView=(TextView)view.findViewById(R.id.show_movie_info);
		imageloader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.ic_launcher) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		.showImageOnFail(R.drawable.ic_launcher) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
		.displayer(new RoundedBitmapDisplayer(10))
		.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
		.cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
		.build(); // �������ù���DisplayImageOption���� 
		r=(RelativeLayout)view.findViewById(R.id.detailadlayout);
		AdView adView = new AdView(getActivity());
//		 ��������AppSid��Appsec���˺���������AdViewʵ����ǰ����
		 AdView.setAppSid(getActivity(),"c3314f57");
		 AdView.setAppSec(getActivity(),"c3314f57");
			// ���ü�����
			adView.setListener(new AdViewListener() {
				public void onAdSwitch() {
					Log.w("", "onAdSwitch");
				}
				public void onAdShow(JSONObject info) {
					Log.w("", "onAdShow " + info.toString());
				}
				public void onAdReady(AdView adView) {
					Log.w("", "onAdReady " + adView);
				}
				public void onAdFailed(String reason) {
					Log.w("", "onAdFailed " + reason);
				}
				public void onAdClick(JSONObject info) {
					Log.w("", "onAdClick " + info.toString());
				}
				public void onVideoStart() {
					Log.w("", "onVideoStart");
				}
				public void onVideoFinish() {
					Log.w("", "onVideoFinish");
				}
				@Override
				public void onVideoClickAd() {
					Log.w("", "onVideoFinish");
				}
				@Override
				public void onVideoClickClose() {
					Log.w("", "onVideoFinish");
				}
				@Override
				public void onVideoClickReplay() {
					Log.w("", "onVideoFinish");
				}
				@Override
				public void onVideoError() {
					Log.w("", "onVideoFinish");
				}
			});
			r.addView(adView);
		getDate();
		getInfo();
		showAd();
		return view;
	}
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			setDate();
		}

	};
	private void getInfo() {
		new th().start();
	}

	protected void setDate() {

		imageloader.displayImage(oBean.getBimg(), imageView, options);
		nameTextView.setText(oBean.getTitle());
		updateTextViewWithTimeFormat(timetView, Integer.parseInt(oBean.getTotaltime())/1000);
		tagTextView.setText(oBean.getTag());
//		try {
//			timetView.setText(MyTools.changeTime2(Long.parseLong(oBean.getTotaltime())));
//		} catch (Exception e) {
//		}
		contView.setText(oBean.getContent());
	}
	private void showAd() {
		
	}

	private void getDate() {
		movieBean = (ZhuTi) getActivity().getIntent().getSerializableExtra("movie");
		oBean.setTitle(movieBean.getTitle());
		oBean.setTag(movieBean.getTag());
		oBean.setContent(movieBean.getContent());
		oBean.setTotaltime(movieBean.getTotaltime());
		oBean.setId(movieBean.getVid());
		vid=movieBean.getVid();
		oBean.setBimg(movieBean.getBimg());
	}


	class th extends Thread {
		@Override
		public void run() {
			super.run();
			String string = Video.getVideoAddress(getActivity(),vid).toString();
			String jsString=Video.getVideoInfo(getActivity(), vid).toString();
			
			JsonUtil.getMovieinfo(string, oBean);
			JsonUtil.getInfo(jsString, oBean);
			handler.sendEmptyMessage(1);
		}
	} 
	/**
	 * 
	 * @param view
	 * @param second  ��
	 */
	private void updateTextViewWithTimeFormat(TextView view, int second) {
		int hh = second / 3600;
		int mm = second % 3600 / 60;
		int ss = second % 60;
		String strTemp = null;
		if (0 != hh) {
			strTemp = String.format("%02d:%02d:%02d", hh, mm, ss);
		} else {
			strTemp = String.format("%02d:%02d", mm, ss);
		}
		view.setText(strTemp);
	}
}
