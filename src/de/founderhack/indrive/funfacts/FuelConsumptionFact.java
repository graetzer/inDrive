package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.graphics.drawable.Drawable;
import de.founderhack.indrive.DataAnalysis;
import de.founderhack.indrive.DataBuffer;

public class FuelConsumptionFact implements Fact {
	private static Random mRnd = new Random();
	private static ArrayList<String> mFuel = new ArrayList<String>();
	private int mIndex = 0;
	private DataBuffer buffer = DataBuffer.getInstance();

	@Override
	public String getFact() {
		return mFuel.get(mIndex);
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
		mFuel.clear();
		mFuel.add(String.format(Locale.GERMAN, "Sie haben bisher insgesamt %.2f Liter Sprit verbraucht", 
				buffer.fuelReserve.get(0).getValue()-buffer.fuelReserve.get(buffer.fuelReserve.size()-1).getValue()));
		mFuel.add(String.format(Locale.GERMAN, "Sie haben in den letzten 20 Minuten %.2f Liter Sprit verbraucht", 
				DataAnalysis.getFuelConsumption(System.currentTimeMillis()-1200000, System.currentTimeMillis())));
		mIndex = mRnd.nextInt(mFuel.size());
	}
	

}
