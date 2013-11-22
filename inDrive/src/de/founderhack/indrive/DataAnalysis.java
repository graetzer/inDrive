package de.founderhack.indrive;

import java.util.ArrayList;
import java.util.List;

import de.dsa.hackathon2013.lib.FuelType;
import de.founderhack.indrive.dsa.DiagnosticValue;

public class DataAnalysis {
	private DataAnalysis(){};
	
	public static List<DiagnosticValue> condense(List<DiagnosticValue> vals, long timestamp, int maxResults) {
		maxResults = Math.min(maxResults, vals.size());
		if (maxResults == 0) return null;
		
		ArrayList<DiagnosticValue> results = new ArrayList<DiagnosticValue>(maxResults);
		int start = findNearestIndex(vals, timestamp);
		int steps = Math.min( (vals.size() - start)/maxResults, 1);
		for (int i = start; i < vals.size(); i += steps) {
			results.add(vals.get(i));
		}
		
		return results;
	}
	
	public static int findNearestIndex(List<DiagnosticValue> vals, long timestamp) {
		long minDiff = Math.abs(vals.get(0).getTime() - timestamp) ;
		int index = 0;
		for (int i = 1; i < vals.size(); i++) {
			long diff = Math.abs(vals.get(i).getTime() - timestamp);
			if (diff < minDiff) {
				index = i;
			}
		}
		return index;
	}
	
	public static double getFuelConsumption() {
		DataBuffer buffer = DataBuffer.getInstance();
		double fuelConsumption = buffer.fuelReserve.get(0).getValue()-buffer.fuelReserve.get(buffer.fuelReserve.size()-1).getValue();
		return fuelConsumption;	
	}
	
	public static double getFuelConsumption(long startTime, long endTime) {
		DataBuffer buffer = DataBuffer.getInstance();
		int start_index = findNearestIndex(buffer.fuelReserve, startTime);
		int end_index = findNearestIndex(buffer.fuelReserve, endTime);
		double fuelConsumption = buffer.fuelReserve.get(start_index).getValue()-buffer.fuelReserve.get(end_index).getValue();
		return fuelConsumption;	
	}
	
	public static double getDistance(long startTime, long endTime) {
		DataBuffer buffer = DataBuffer.getInstance();
		int start_index = findNearestIndex(buffer.distance, startTime);
		int end_index = findNearestIndex(buffer.distance, endTime);
		double distance = buffer.distance.get(end_index).getValue()-buffer.distance.get(start_index).getValue();
		return distance;	
	}
	
	public static double getMeanSpeed(long startTime, long endTime) {
		DataBuffer buffer = DataBuffer.getInstance();
		int start_index = findNearestIndex(buffer.distance, startTime);
		int end_index = findNearestIndex(buffer.distance, endTime);
		double distance = buffer.distance.get(end_index).getValue()-buffer.distance.get(start_index).getValue();
		double meanSpeed = distance / (endTime-startTime);
		return meanSpeed;	
	}
	
	public static double getMoney() {
		double money = fuelPricePerLitre(FuelType.GASOLINE)*getFuelConsumption();
		return money;
	}
	
	public static double getKitKat() {	
		double kitKat = 1.68*getMoney();
		return kitKat;	
	}
	
	public static double fuelPricePerLitre(FuelType type) {
		return 1.5;// TODO fetch this somewhere
	}
	
	// Compare money spend on this timespan, with the one the day before 
	public static double moneySaved(long timespan, FuelType type) {
		long now = System.currentTimeMillis();
		long day = 24*60*60*1000;
		double currentCons = getFuelConsumption(now, now-timespan);
		double lastCons = getFuelConsumption(now-day, now-timespan-day);
		return (currentCons - lastCons)*fuelPricePerLitre(type);
	}
	
}
