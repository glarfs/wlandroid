package com.googlecode.wlandroid2;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.wlandroid2.dto.WlanData;
import com.googlecode.wlandroid2.handlers.ClipboardHandler;
import com.googlecode.wlandroid2.handlers.WifiHandler;

public class WlanDroid extends Activity {
	//private TextView tv=null;
	private ListView lv=null;
	private CheckBox ckbFiltered = null;
	private WifiHandler wifiHandler;
	private ClipboardHandler clipboardHandler;
	private ArrayAdapter<WlanData> arrayAdapter;
	
	
	private boolean started=false;
	private boolean scan=true;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //tv = (EditText) findViewById(R.id.textView);
        //View header = (View)findViewById(R.id.header_Layout);
        lv = (ListView) findViewById(R.id.wlanList);
     

        lv.setTextFilterEnabled(true);        
        arrayAdapter = new ArrayAdapter<WlanData>(this,R.layout.list_item);  
        lv.setAdapter(arrayAdapter);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	WlanData data = WlanDroid.this.arrayAdapter.getItem(position);
            	// When clicked, show a toast with the TextView text
            	if (data.getKey()!=null || data.isOpen()){
            		boolean success = wifiHandler.createNewWPA2Connection(data.getEssid(),data.getKey());
              
	            	if (success){
	            		Toast.makeText(getApplicationContext(), R.string.added,Toast.LENGTH_SHORT).show();
	            		clipboardHandler.textToClipboard(data.getKey());
	            	}else{
	            		Toast.makeText(getApplicationContext(), R.string.notAdded,Toast.LENGTH_SHORT).show();
	            	}
        		}else{
        			Toast.makeText(getApplicationContext(), R.string.noKey,
        	                  Toast.LENGTH_SHORT).show();
        		}
              
              
            }
          });

        
        ckbFiltered = (CheckBox) findViewById(R.id.ckbFiltered);
        ckbFiltered.setChecked(true);
        Button btnScan =(Button) findViewById(R.id.scan);
        btnScan.setOnClickListener(new View.OnClickListener()  {

            public void onClick(View view) {            	
            	startScan();            	
            }
        });
        Button btnStop =(Button) findViewById(R.id.stop);
        btnStop.setOnClickListener(new View.OnClickListener()  {

            public void onClick(View view) {
            	stopScan();
            	
            }
        });
        TextView btnVersion =(TextView) findViewById(R.id.version);
        btnVersion.setOnClickListener(new View.OnClickListener()  {

            public void onClick(View view) {
            	Toast.makeText(getApplicationContext(),R.string.version,Toast.LENGTH_SHORT).show();
            	
            }
        });

    }
      
    @Override
    public void onStart() {
        super.onStart();
        wifiHandler = new WifiHandler(this);
        clipboardHandler = new ClipboardHandler(this);
        registerBroadCastReceiver();
        
        
    }
    
    @Override
	protected void onPause() {
		super.onPause();
		stopScan();
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (!started && scan){
            	started=true;
            	startScan();
            }

            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            	if (scan){
                analizeResults();
            	}
            }
          }
    };
    
    private void registerBroadCastReceiver() {
        // Create a filter with the broadcast intents we are interested in.
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        // Register for broadcasts of interest.
        registerReceiver(mBroadcastReceiver, filter, null, null);
    }
   


    protected void startScan(){
    	scan=true;
    	wifiHandler.startScan();
    	Toast.makeText(getApplicationContext(),R.string.scanning,Toast.LENGTH_SHORT).show();
    	
        
    }
    protected void stopScan(){
    	scan=false;
    	Toast.makeText(getApplicationContext(),R.string.noScanning,Toast.LENGTH_SHORT).show();    	
    	
        
    }
    protected void analizeResults(){
    	arrayAdapter.clear();   	
        
        List <ScanResult> list = wifiHandler.getScanResults();
        if (list!=null){
	        Iterator<ScanResult> it = list.iterator();
	        while(it.hasNext()){
	        	ScanResult scan = it.next();
	        	
	        	WlanData wlandata = null;
	        	boolean open = (scan.capabilities==null || scan.capabilities.length() ==0);
	        	if (ckbFiltered.isChecked() ){
	        		if( Util.checkSSID(scan.SSID)){
	        			String key = Util.calculateKey(scan.SSID,scan.BSSID);
	        			wlandata = new WlanData(scan.SSID,scan.BSSID,open,key);
	        		}else if (open){
	        			wlandata = new WlanData(scan.SSID,scan.BSSID,open);
	        		}
	        	}else{
	        		if( Util.checkSSID(scan.SSID)){
	        			String key = Util.calculateKey(scan.SSID,scan.BSSID);
	        			wlandata = new WlanData(scan.SSID,scan.BSSID,open,key);
	        		}else{
	        			wlandata = new WlanData(scan.SSID,scan.BSSID,open);
	        		}
	        	}
	        	if (wlandata!=null){
		        	if (scan.capabilities!=null){
		        		wlandata.setCapabilities(scan.capabilities);	        		
		        	}
		        	wlandata.setLevel(scan.level);	        		
		        	
		        	arrayAdapter.add(wlandata);
	        	}
	        	
	    	}
        }
        //arrayAdapter.sort();
        Toast.makeText(getApplicationContext(),R.string.scanned,Toast.LENGTH_SHORT).show();
        
    	
    }
}