package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.drawable.Drawable;

public class QuotesFact implements Fact {
	private static Random mRnd = new Random();
	@SuppressWarnings("serial")
	private static ArrayList<String> mQuotes = new ArrayList<String>(){{
		add("F�r Deinen zur�ckglegten Weg k�nntest Du 1394 Kit Kats a 45 Gramm essen.");
		add("W�rdest Du Schafswolle tanken, m�ssten wegen Dir schon 305 Schafe geschoren worden sein.");
		add("So f�hrt man Auto!\nDu k�nntest Dir von Deinem gesparten Geld ein neues Paar Turnschuhe bestellen ;-)");
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
