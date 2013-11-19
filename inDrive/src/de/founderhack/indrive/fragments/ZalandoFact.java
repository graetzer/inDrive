package de.founderhack.indrive.fragments;

import java.io.ByteArrayInputStream;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.AnalogClock;
import de.founderhack.indrive.DataAnalysis;
import de.founderhack.indrive.funfacts.Fact;

public class ZalandoFact implements Fact {
	
	private double mFuelCostPerLitre = 1.35;
	private String mCategory;
	private AsyncHttpClient mClient = new AsyncHttpClient();
	private Random mRnd = new Random();
	
	private static final String URL = "http://shop-catalog-api.zalando.net/search/www.zalando.de/%s?price=0-%.2f&page_size=10";
	
	private String mResult;
	private Drawable mIcon;
	
	@Override
	public String getFact() {
		// TODO Auto-generated method stub
		return mResult;
	}

	@Override
	public Drawable getIcon() {
		// TODO Auto-generated method stub
		return mIcon;
	}

	@Override
	public boolean ready() {
		return mResult != null;
	}

	@Override
	public void onDestroy() {
		mResult = null;
		mIcon = null;
	}

	@Override
	public void onActive() {
		// TODO Auto-generated method stub

	}
	
	public ZalandoFact(String category) {
		mCategory = category;
		getZalandoInfo();
	}
	
	private void getZalandoInfo() {
		
		DataAnalysis data = new DataAnalysis();
		long now = System.currentTimeMillis();
		final double fuel = data.getFuelConsumption(now-1000*60*60, now);
		
		String url = String.format(URL, mCategory, fuel*mFuelCostPerLitre);
		mClient.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					JSONArray results = response.getJSONObject("searchResults").getJSONArray("data");
					if (results.length() > 0) {
						JSONObject result = results.getJSONObject(mRnd.nextInt(results.length()));
						mResult = String.format("Sie haben %.2fL Sprit verbraucht, dafür hätten sie sich %s "+
						"bei Zalando kaufen können", fuel, result.getString("name"));
						
						mClient.get(result.getString("imageUrl"), new BinaryHttpResponseHandler(){
							public void onSuccess(byte[] binaryData) {
								if (binaryData != null) {									
									mIcon = Drawable.createFromStream(new ByteArrayInputStream(binaryData), "abc.jpg");
								}
							};
						});
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		    @Override
		    public void onSuccess(String response) {
		    	
		    	
		    	
		    }
		});

	}

}
