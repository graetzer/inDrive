package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.drawable.Drawable;

public class QuotesFact implements Fact {
	private static Random mRnd = new Random();
	@SuppressWarnings("serial")
	private static ArrayList<String> mQuotes = new ArrayList<String>(){{
		add("The best and most beautiful things in the world cannot be seen or even touched - they must be felt with the heart - Helen Keller");
		add("It is during our darkest moments that we must focus to see the light - Aristotle Onassis");
		add("Don't judge each day by the harvest you reap but by the seeds that you plant - Louis Stevenson");
	}};
	
	private int mIndex = 0;

	@Override
	public String getFact() {
		return mQuotes.get(mIndex);
	}

	@Override
	public Drawable getIcon() {
		return null;
	}

	@Override
	public boolean ready() {
		return true;
	}

	@Override
	public void onDestroy() {
		mIndex = mRnd.nextInt(mQuotes.size());
	}

	@Override
	public void onActive() {
		
	}

}
