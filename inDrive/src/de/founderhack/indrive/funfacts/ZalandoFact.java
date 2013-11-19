package de.founderhack.indrive.funfacts;

import java.io.ByteArrayInputStream;
import java.util.Locale;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import android.graphics.drawable.Drawable;
import de.founderhack.indrive.DataAnalysis;

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
		return mResult;
	}

	@Override
	public Drawable getIcon() {
		// TODO Auto-generated method stub
		return mIcon;
	}

	@Override
	public boolean ready() {
		return mResult != null && mIcon != null;
	}

	@Override
	public void onDestroy() {
		mResult = null;
		mIcon = null;
		getZalandoInfo();
	}

	@Override
	public void onActive() {
	}
	
	public ZalandoFact(String category) {
		mCategory = category;
		mClient.addHeader("Accept", "application/json");
		//mClient.setTimeout(100000);
		getZalandoInfo();
	}
	
	private void getZalandoInfo() {
		
		DataAnalysis data = new DataAnalysis();
		long now = System.currentTimeMillis();
		final double fuel = Math.abs(data.getFuelConsumption(now-1000*60*60, now));
		final double cost = fuel*mFuelCostPerLitre;
		
		String url = String.format(Locale.ENGLISH, URL, mCategory, cost);
		
		mClient.get(url, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject obj = new JSONObject(response);
					JSONArray results = obj.getJSONObject("searchResults").getJSONArray("data");
					if (results.length() > 0) {
						JSONObject result = results.getJSONObject(mRnd.nextInt(results.length()));
						mResult = String.format("Sie haben %.2fL Sprit im Wert von %.2f€ verbraucht, dafür hätten sie sich \"%s\" "+
						"bei Zalando kaufen können", fuel, cost, result.getString("name"));
						
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
		});

	}

}
