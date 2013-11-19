package de.founderhack.indrive.funfacts;

import java.util.ArrayList;

import de.founderhack.indrive.R;
import android.graphics.drawable.Drawable;

public class SpeedFact implements Fact {

	private static ArrayList<String> mQuotes = new ArrayList<String>(){{
		add("Sie sind auf den letzten 100km durschnittlich 180km/h gefahren. Sie sollten vorsichtig sein");
		add("Wissen sie das wenn sie 30 km/h schneller fahren, das die Zeit für sie langsamer vergeht?");
		add("Ich bin nicht faul, ich bin im Energiesparmodus.");
		add("Fischers Fritze fischt frische Fische. Fischt Fischers Fritze frische Fische?\nSag's mir.");
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
		return R.drawable.tacho;
	}

	@Override
	public boolean ready() {
		return true;
	}

	@Override
	public void onDestroy() {
		mIndex++;
	}

	@Override
	public void onActive() {
	}
	

}
