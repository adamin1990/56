package com.iiijiaban.u56player.beans;

import java.io.Serializable;

public class ZhuTi implements Serializable{
	private String vid; //��Ƶid
	private String cid; //����id
	private String title; //����
	private String tag; //��ǩ
	private String content; //��Ƶ���������ݣ�
	private String totaltime; //��Ƶ�ܳ���
	private String ming; //��Ƶ����ͼ��ַ  ��ͼ
	private String bimg; //��ͼ
	private String img;  //Сͼ
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTotaltime() {
		return totaltime;
	}
	public void setTotaltime(String totaltime) {
		this.totaltime = totaltime;
	}
	public String getMing() {
		return ming;
	}
	public void setMing(String ming) {
		this.ming = ming;
	}
	public String getBimg() {
		return bimg;
	}
	public void setBimg(String bing) {
		this.bimg = bing;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

}
