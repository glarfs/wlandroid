package com.googlecode.wlandroid.test;

import junit.framework.TestCase;

import com.googlecode.wlandroid2.Util;

public class WlanDroidTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testUtilFilter(){
		assertFalse(Util.checkSSID(null));
		assertFalse(Util.checkSSID("WLAN_12"));
		assertTrue(Util.checkSSID("WLAN_123D"));
		assertTrue(Util.checkSSID("JAZZTEL_XXXX"));
		assertTrue(Util.checkSSID("VodafoneXXXX"));
		assertTrue(Util.checkSSID("Orange-XXXX"));
		
	}
	public void testGetHeader(){
		assertEquals(Util.getHeader("WLAN_12"),"12");
		assertEquals(Util.getHeader("WLAN_123D"),"123D");
		assertEquals(Util.getHeader("JAZZTEL_XXXX"),"XXXX");
		assertEquals(Util.getHeader("VodafoneXXXX"),"XXXX");
		assertEquals(Util.getHeader("Orange-123d"),"123d");			
	}
	
	public void testWlanDecode(){
		String essid = "WLAN_C34E";
		String bssid = "34:94:1D:E4:D6:72";

		String key=Util.calculateKey(essid,bssid);
		//checked
		assertEquals(key,"3bb3b34dd910c686afa5");
	}
	
	public void testZyxel(){
		String essid="WLAN_6B67";
		String bssid="00:1F:A4:FF:FF:FF";
		String key_expected="25B52D1655B113043CFD";
		String key_result=Util.calculateKey(essid, bssid);
		assertEquals(key_result,key_expected);
	}


}
