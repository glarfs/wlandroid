package com.googlecode.wlandroid2.dto;

import java.io.Serializable;

public class WlanData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6943488803347695259L;
	
	protected String essid=null;
	protected String bssid=null;
	protected String key=null;
	protected boolean open=false;
	protected String capabilities;
	protected int level;
	
	public WlanData(){
		
	}
	 
	public WlanData(String essid, String bssid, boolean open) {		
		this(essid,bssid);
		setOpen(open);
	}
	public WlanData(String essid, String bssid) {
		super();
		this.essid = essid;
		this.bssid = bssid;		
	}
	public WlanData(String essid, String bssid, boolean open, String key) {
		this(essid,bssid,open);
		this.key = key;
	}

	public WlanData(String essid, String bssid, String key) {
		this(essid,bssid);
		this.key = key;
	}
	public String getEssid() {
		return essid;
	}
	public void setEssid(String essid) {
		this.essid = essid;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bddid) {
		this.bssid = bddid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	@Override
	public String toString() {
		return level+"[essid=" + essid + ", bssid=" + bssid + ", open="+open+", key=" + key
				+", o:"+capabilities+ "]";
	}

	

}
