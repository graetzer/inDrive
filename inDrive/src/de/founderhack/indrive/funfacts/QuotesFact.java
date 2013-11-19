package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.drawable.Drawable;

public class QuotesFact implements Fact {

	@SuppressWarnings("serial")
	private static ArrayList<String> mQuotes = new ArrayList<String>(){{
		add("Würdest Du Schafswolle tanken, müssten wegen Dir schon 305 Schafe geschoren worden sein.");
		add("Ein Tag hat 24 Stunden.\nEine Palette Bier 24 Dosen.\nDas kann kein Zufall sein. ");
		add("Ich bin nicht faul, ich bin im Energiesparmodus.");
		add("Fischers Fritze fischt frische Fische. Fischt Fischers Fritze frische Fische?\nSag's mir.");
		add("Ich bin der Stoff,\naus dem die Träume sind.");
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
	public int getIconResource() {
		return 0;
	}

	@Override
	public boolean ready() {
		return true;
	}

	@Override
	public void onDestroy() {
		
	}

	@Override
	public void onActive() {
		Random mRnd = new Random();
		mIndex = mRnd.nextInt(mQuotes.size());
	}

}
