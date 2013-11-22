package de.founderhack.indrive.funfacts;

import java.util.ArrayList;

import de.founderhack.indrive.R;
import android.graphics.drawable.Drawable;

public class SpeedFact implements Fact {

	@SuppressWarnings("serial")
	private static ArrayList<String> mQuotes = new ArrayList<String>(){{
		add("Sie sind auf den letzten 100km durschnittlich 180km/h gefahren. Sie sollten vorsichtig sein");
		add("Wussten sie das wenn sie 100 km/h schneller fahren, das die Zeit für sie langsamer vergeht? (Jedenfalls sagt das Einstein)");
		add("Sie sind auf den letzten 100km durschnittlich 180km/h gefahren. Denken sie daran: Auf dem Tacho sitzt der Tod und erwartet ihr Kommando.");
		add("180 km/h ? Geschwindigkeit ist keine Hexerei. Radarkontrolle ist jedoch eine..");
		add("Durschnittlich 180 km/h ? Das größte Problem der Schnecken ist ihre ungenügendes Tempo.");
		add("Sie fahren 180 km/h. Bis zur unendlichkeit und noch viel weiter.");
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
