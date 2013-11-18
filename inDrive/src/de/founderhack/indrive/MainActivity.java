package de.founderhack.indrive;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import de.founderhack.indrive.DSA.DSAListener;
import de.founderhack.indrive.fragments.ChartsFragment;
import de.founderhack.indrive.fragments.WelcomeFragment;

public class MainActivity extends FragmentActivity implements DSAListener {
	
	private DSA mDSA;
	private DataBuffer dataBuffer;
	private TextToSpeech mTts;
	private ViewPager mViewPager;
	private FragmentManager mFragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dataBuffer = DataBuffer.getInstance(getApplicationContext());
		init();
		
		mDSA = new DSA(this);
		mDSA.startListening();
	}
	
	private void init(){
		mFragmentManager = getSupportFragmentManager();
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(new SectionsPagerAdapter(mFragmentManager));
		
		mTts = new TextToSpeech(this, new OnInitListener() {
			
			@Override
			public void onInit(int status) {
				mTts.speak("Herzlich willkommen!", TextToSpeech.QUEUE_FLUSH, null);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onSensorUpdate() {
		//TODO Do something with the updated vars
		Log.d("Results", "Speed: " + mDSA.getSpeed() + "km/h");
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position){
			case 0:
				return new WelcomeFragment();
				
			case 1:
				return new ChartsFragment();
				
			default:
				return new Fragment();
			}
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "willkommen";
			case 1:
				return "infoscreen";
			case 2:
				return "statistiken";
			case 3:
				return "achievements";
			}
			return null;
		}
	}

}