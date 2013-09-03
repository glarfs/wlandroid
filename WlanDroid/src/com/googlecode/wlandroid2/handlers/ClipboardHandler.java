package com.googlecode.wlandroid2.handlers;

import android.content.Context;
import 	android.text.ClipboardManager;

public class ClipboardHandler {
	 protected ClipboardManager clipboard;
	
	 public ClipboardHandler(Context context) { 
		 clipboard=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE); 
	}
	
	public void textToClipboard(String text){
		clipboard.setText(text);
	}
}
