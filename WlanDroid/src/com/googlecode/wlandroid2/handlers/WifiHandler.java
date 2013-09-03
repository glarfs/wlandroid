package com.googlecode.wlandroid2.handlers;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;

public class WifiHandler {
	
	protected WifiManager wifiman;
	protected Context mContext;	
	public boolean disableOthers = true;
	
	public WifiHandler(Context context){
		this.mContext = context;
		wifiman = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
	}
   
	
	public List <ScanResult> getScanResults(){		
		return wifiman.getScanResults();
	}
	
	public boolean startScan(){
		if (!wifiman.isWifiEnabled()){
			wifiman.setWifiEnabled(true);
		}
		return wifiman.startScan();
	}
	public boolean stopScan(){
		//TODO stop scanning mode
		return true;
	}
	
	public boolean createNewWPA2Connection(String essid, String key){
		WifiConfiguration wc = new WifiConfiguration();		
		wc.SSID="\"".concat(essid).concat("\"");
		wc.status = WifiConfiguration.Status.DISABLED;
		//wc.hiddenSSID = true;
		wc.priority = 40;
		/**default**/
		//support both protocols
		wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		//ciphers
		wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		
		wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
		wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		if (key!=null){
			//wpa/wpa2
			wc.preSharedKey = "\"".concat(key).concat("\"");//with double quotes
			wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			//wep
			//wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			//wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			//if (isHexString(password)) wc.wepKeys[0] = password;
			//else wc.wepKeys[0] = "\"".concat(password).concat("\"");
			//wc.wepTxKeyIndex = 0

		}else{
			//open			
			wc.allowedAuthAlgorithms.clear();
			wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		}


		return createNewWifiConnection(wc);
		
	}
	public boolean createNewWifiConnection(WifiConfiguration config){
		boolean success=false;
		if (!wifiman.isWifiEnabled()){
			wifiman.setWifiEnabled(true);
		}		
		int netId=wifiman.addNetwork( config);
		
		if (netId != -1) {
			//new configuration ok
			success=wifiman.enableNetwork(netId, disableOthers);
			//enable network
			if (success){
				//new network stored
				wifiman.saveConfiguration();
			}
		}
		return success;		
		
	}

}
