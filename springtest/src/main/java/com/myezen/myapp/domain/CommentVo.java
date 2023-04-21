package com.myezen.myapp.domain;

public class CommentVo {
	
	private int cidx;
	private String cwriter;
	private String ccontents;
	private int midx;
	private int bidx;
	private String ip;
	private String cwriteday;
	
	public int getCidx() {
		return cidx;
	}
	public void setCidx(int cidx) {
		this.cidx = cidx;
	}
	public String getCwriter() {
		return cwriter;
	}
	public void setCwriter(String cwriter) {
		this.cwriter = cwriter;
	}
	public String getCcontents() {
		return ccontents;
	}
	public void setCcontents(String ccontents) {
		this.ccontents = ccontents;
	}
	public int getMidx() {
		return midx;
	}
	public void setMidx(int midx) {
		this.midx = midx;
	}
	public int getBidx() {
		return bidx;
	}
	public void setBidx(int bidx) {
		this.bidx = bidx;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCwriteday() {
		return cwriteday;
	}
	public void setCwriteday(String cwriteday) {
		this.cwriteday = cwriteday;
	}
	
}
