package de.founderhack.indrive.funfacts;

import de.founderhack.indrive.DataAnalysis;
import de.founderhack.indrive.R;
import android.graphics.drawable.Drawable;

public class KitKatFact implements Fact {
	private String output = null;
	private DataAnalysis analysis = new DataAnalysis();

	@Override
	public String getFact() {
		return output;
	}

	@Override
	public Drawable getIcon() {
		return null;
	}
	
	@Override
	public int getIconResource() {
		return R.drawable.kitkat;
	}

	@Override
	public boolean ready() {
		return true;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActive() {
		output = String.format("Für Deinen zurückglegten Weg könntest Du %d Kit Kats a 45 Gramm essen.", (int)analysis.getKitKat());
		
	}

}
