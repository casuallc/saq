package com.qing.test;

import com.qing.saq.SAQ;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private SAQ saq = new SAQ();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(layoutResID);
		
		saq.registe(this, getWindow().getDecorView());
		saq.init();
		
	}
	
}
