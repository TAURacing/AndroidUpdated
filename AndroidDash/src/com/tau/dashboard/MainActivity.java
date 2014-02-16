package com.tau.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * MainActivity for tau dashboard. Instantiates TextViews for displaying
 * dashboard parameter values. Also provides Handler for sending values to
 * update UI.
 * 
 * Need to handle opening/closing of app properly (currently not handled, only
 * creation of app is!).
 */
public class MainActivity extends Activity {

	private TextView rpmDisp;
	private TextView oilDisp;
	private TextView gearDisplay;
	private TextView batteryDisplay;
	private TextView lambdaDisplay;
	private TextView coolantDisplay;

	/*
	 * Handler connected to main thread (thread for UI handling), belongs to
	 * thread created in. Allows UI elements to be updated from another thread.
	 * 
	 * Need to clean up UDP message handling! Move to object orientation.
	 */
	private Handler mainHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();

			if (bundle.getString("RPM") != null) {
				rpmDisp.setText(bundle.get("RPM").toString());
			}
			if (bundle.getString("OIL") != null) {
				oilDisp.setText(bundle.get("OIL").toString());
			}
		}
	};

	/*
	 * Overrides onCreate from Activity class, always called onCreation of
	 * activity, i.e here the creation of app.
	 * 
	 * Currently sets UI elements to initial value and starts thread for UDP
	 * message receiving.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//update:15/02/2014 -> hide title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//update:15/02/2014 -> hide status bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);

		gearDisplay = (TextView) findViewById(R.id.gear);
		gearDisplay.setText("gear_S");
			
		rpmDisp = (TextView) findViewById(R.id.rpm);
		rpmDisp.setText("rpm_S");

		oilDisp = (TextView) findViewById(R.id.oil);
		oilDisp.setText("eot_S");
		
		batteryDisplay = (TextView) findViewById(R.id.battery);
		batteryDisplay.setText("vbat_S");
		
		lambdaDisplay = (TextView) findViewById(R.id.lambda);
		lambdaDisplay.setText("lam1_S");
		
		coolantDisplay = (TextView) findViewById(R.id.coolant);
		coolantDisplay.setText("ect1_S");
		
		UdpReceive sync = new UdpReceive(mainHandler);
		new Thread(sync).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
