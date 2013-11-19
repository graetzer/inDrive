package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Random;

import org.graetzer.witzmaschine.Witzmaschine;
import org.json.JSONArray;
import org.json.JSONException;

import com.loopj.android.http.JsonHttpResponseHandler;

import de.founderhack.indrive.R;

import android.graphics.drawable.Drawable;

public class JokeFact implements Fact {
	
	private ArrayList<String> mJokes;
	private Random mRnd = new Random();
	private int mIndex;

	@Override
	public String getFact() {
		// TODO Auto-generated method stub
		return mJokes.get(mIndex);
	}

	@Override
	public Drawable getIcon() {
		return null;
	}
	
	@Override
	public int getIconResource() {
		return R.drawable.witzicon;
	}

	@Override
	public boolean ready() {
		// TODO Auto-generated method stub
		return mJokes != null && mJokes.size() > 0;
	}

	@Override
	public void onDestroy() {
		mIndex = mRnd.nextInt(mJokes.size());
	}

	@Override
	public void onActive() {
	}
	
	public JokeFact() {
		loadJokes();
	}
	
	private void loadJokes() {
		
		int offset = 0;
		if (mJokes != null) {
			offset = mJokes.size();
		}
		
		Witzmaschine.loadFeaturedJokes(offset, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray response) {
				mJokes = new ArrayList<String>();
				for (int i = 0; i < Math.min(50, response.length()); i++) {
					try {
						String joke = response.getJSONObject(i).getString("content");
						if (joke.length() < 200)
							mJokes.add(joke);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}
