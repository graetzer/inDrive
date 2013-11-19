package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.drawable.Drawable;

public class QuotesFact implements Fact {
	private static Random mRnd = new Random();
	@SuppressWarnings("serial")
	private static ArrayList<String> mQuotes = new ArrayList<String>(){{
		add("Für Deinen zurückglegten Weg könntest Du 1394 Kit Kats a 45 Gramm essen.");
		add("Würdest Du Schafswolle tanken, müssten wegen Dir schon 305 Schafe geschoren worden sein.");
		add("So fährt man Auto!\nDu könntest Dir von Deinem gesparten Geld ein neues Paar Turnschuhe bestellen ;-)");
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
