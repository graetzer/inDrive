package de.founderhack.indrive;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import de.founderhack.indrive.DSA.DSAListener;

public class MainActivity extends Activity implements DSAListener {

	
	private DSA mDSA;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDSA = new DSA(this);
		mDSA.startListening();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onSensorUpdate() {
		Log.d("Results", "Speed: " + mDSA.getSpeed() + "km/h");
	}

}
