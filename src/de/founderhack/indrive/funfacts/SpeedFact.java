package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Locale;

import de.founderhack.indrive.DataAnalysis;
import de.founderhack.indrive.R;
import android.graphics.drawable.Drawable;

public class SpeedFact implements Fact {

	@SuppressWarnings("serial")
	private static ArrayList<String> mQuotes = new ArrayList<String>(){{
		add("Sie sind in der letzten Stunde durschnittlich %d km/h gefahren. Sie sollten vorsichtig sein");
		add("Wussten sie das wenn sie, statt %d km/h zu fahren, 100 km/h schneller wären, das die Zeit für sie langsamer vergeht? (Jedenfalls sagt das Einstein)");
		add("Sie sind in der letzten Stunde durschnittlich %d km/h gefahren. Denken sie daran: Auf dem Tacho sitzt der Tod und erwartet ihr Kommando.");
		add("%d km/h ? Geschwindigkeit ist keine Hexerei. Radarkontrolle ist jedoch eine..");
		add("Durschnittlich %d km/h ? Das größte Problem der Schnecken ist ihre ungenügendes Tempo.");
		add("Sie fahren %d km/h. Bis zur unendlichkeit und noch viel weiter.");
	}};
	
	private int mIndex = 0;
	private String cache;

	@Override
	public String getFact() {
		return cache;
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
	public void onDestroy() {}

	@Override
	public void onActive() {
		String f = mQuotes.get(mIndex % mQuotes.size());
		mIndex++;
		long now = System.currentTimeMillis();
		cache = String.format(Locale.GERMAN, f, (int)DataAnalysis.getMeanSpeed(now-30*60*1000, now));
	}
	

}
