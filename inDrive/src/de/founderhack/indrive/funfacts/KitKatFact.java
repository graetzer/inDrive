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
		output = String.format("Wenn du statt mit dem Auto zu fahren gelaufen wärest, "
	+"hättest du ohne schlechtes Gewissen %d Kit Kats a 45 Gramm essen können.", (int)analysis.getKitKat());
		
	}

}
