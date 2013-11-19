package de.founderhack.indrive.funfacts;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.drawable.Drawable;
import de.founderhack.indrive.DataAnalysis;
import de.founderhack.indrive.DataBuffer;

public class FuelConsumptionFact implements Fact {
	private static Random mRnd = new Random();
	private static ArrayList<String> mFuel = new ArrayList<String>();
	private DataAnalysis analysis = new DataAnalysis();
	private int mIndex = 0;
	private DataBuffer buffer = DataBuffer.getInstance(null);

	@Override
	public String getFact() {
		return mFuel.get(mIndex);
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
		
	}

	@Override
	public void onActive() {
		mFuel.clear();
		mFuel.add(String.format("Sie haben bisher %.2f Liter Sprit verbraucht", buffer.fuelReserve.get(buffer.fuelReserve.size()-1).getValue()-buffer.fuelReserve.get(0).getValue()));
		mFuel.add(String.format("Sie haben in den letzten 10 Minuten %.2f Liter Sprit verbraucht",analysis.getFuelConsumption(System.currentTimeMillis()-600000, System.currentTimeMillis())));
		mIndex = mRnd.nextInt(mFuel.size());
	}
	

}