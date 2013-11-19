package org.graetzer.witzmaschine;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by simon on 14.06.13.
 */
public class Witzmaschine {
	private static final String TAG = "Witzmaschine";
	private static final String RATED_FILE = "rated";
	private static final String BASE_URL = "http://witzmaschine1.appspot.com/";

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static JSONArray ratedJokes;
	private static JSONArray mCategories;

	public static void loadFeaturedJokes(int offset,
			JsonHttpResponseHandler arrayHandler) {
		RequestParams params = new RequestParams();
		params.put("offset", String.valueOf(offset));
		client.get(getAbsoluteUrl("joke/featured"), params, arrayHandler);
	}

	public static void loadCategoryNames(final JsonHttpResponseHandler arrayHandler) {
		Assert.assertNotNull(arrayHandler);
		
		if (mCategories != null && mCategories.length() > 0) {
			arrayHandler.onSuccess(mCategories);
			return;
		}
		
		client.get(getAbsoluteUrl("category/all"), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray array) {
				mCategories = array;
				arrayHandler.onSuccess(array);
			}
			@Override
			public void onFailure(Throwable t, String s) {
				arrayHandler.onFailure(t, s);
			}
		});
	}

	public static void loadCategoryJokes(String name, int offset,
			JsonHttpResponseHandler arrayHandler) {
		RequestParams params = new RequestParams();
		params.put("offset", String.valueOf(offset));
		
		client.get(getAbsoluteUrl("category/" + Uri.encode(name)), params, arrayHandler);
	}

	public static void loadAuthorJokes(String author, int offset,
			JsonHttpResponseHandler arrayHandler) {
		RequestParams params = new RequestParams();
		params.put("offset", String.valueOf(offset));
		client.get(getAbsoluteUrl("author/" + Uri.encode(author)), params, arrayHandler);
	}

	public static void rateJoke(Context ctx, final JSONObject joke, final int rating) {
		final Context appCtx = ctx.getApplicationContext();

		try {
			joke.put("rating", rating);
			
			String key = joke.getString("key");
			RequestParams params = new RequestParams();
			params.put("rating", String.valueOf(rating));
			
			client.get(getAbsoluteUrl("joke/rate/" + Uri.encode(key)), params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String code) {
							addJoke(appCtx, joke);
						}

						@Override
						public void onFailure(Throwable t, String msg) {
							Log.e("Witzmaschine",
									"Failed to rate joke: " + msg, t);
						}
					});
		} catch (JSONException e) {
			Log.e(TAG, "Error rating a joke", e);
		}
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

	private static void loadJokes(Context ctx) {
		if (ratedJokes == null) {
			try {
				String content = IOUtils.getContentsOfFile(ctx, RATED_FILE);
				if (content != null)
					ratedJokes = (JSONArray) new JSONTokener(content)
							.nextValue();
			} catch (JSONException e) {
				Log.e(TAG, "Can't parse rated jokes file", e);
			}
			if (ratedJokes == null)
				ratedJokes = new JSONArray();
		}
	}

	private static void addJoke(final Context ctx, JSONObject joke) {
		loadJokes(ctx);
		ratedJokes.put(joke);

		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					String content = ratedJokes.toString();
					IOUtils.writeStringToFile(ctx, RATED_FILE, content);
				} catch (Exception e) {
					Log.e(TAG, "Can't write rated jokes file", e);
				}
			}
		};
		t.start();
	}

	public static boolean isRatedJoke(Context ctx, JSONObject joke) {
		loadJokes(ctx);

		try {
			for (int i = 0; i < ratedJokes.length(); i++) {
				JSONObject j = ratedJokes.getJSONObject(i);
				if (j.getString("key").equals(joke.getString("key")))
					return true;
			}
		} catch (JSONException e) {
			Log.e(TAG, "Can't parse rated jokes file", e);
		}
		return false;
	}
	
	public static JSONArray getRatedJokes(Context ctx) {
		loadJokes(ctx);
		return ratedJokes;
	}
}
