package de.founderhack.indrive;

import java.util.ArrayList;
import java.util.List;

import de.founderhack.indrive.dsa.DiagnosticValue;

public class DataAnalysis {
	private DataBuffer buffer = null;
	
	public DataAnalysis() {
		buffer = DataBuffer.getInstance(null);
	}
	
	private List<DiagnosticValue> condense(List<DiagnosticValue> vals, long timestamp, int maxResults) {
		maxResults = Math.min(maxResults, vals.size());
		if (maxResults == 0) return null;
		
		ArrayList<DiagnosticValue> results = new ArrayList<DiagnosticValue>(maxResults);
		int start = findNearestIndex(vals, timestamp);
		int steps = Math.min( (vals.size() - start)/maxResults, 1);
		for (int i = vals.size()-1; i > start; i -= steps) {
			results.add(vals.get(i));
		}
		
		return results;
	}
	
	private int findNearestIndex(List<DiagnosticValue> vals, long timestamp) {
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
	
	public double getFuelConsumption(long startTime, long endTime) {
		int start_index = findNearestIndex(buffer.fuelReserve, startTime);
		int end_index = findNearestIndex(buffer.fuelReserve, endTime);
		double fuelConsumption = buffer.fuelReserve.get(start_index).getValue()-buffer.fuelReserve.get(end_index).getValue();
		return fuelConsumption;	
	}
	
	public double getDistance(long startTime, long endTime) {
		int start_index = findNearestIndex(buffer.distance, startTime);
		int end_index = findNearestIndex(buffer.distance, endTime);
		double distance = buffer.distance.get(end_index).getValue()-buffer.distance.get(start_index).getValue();
		return distance;	
	}
	
	public double getMeanSpeed(long startTime, long endTime) {
		int start_index = findNearestIndex(buffer.distance, startTime);
		int end_index = findNearestIndex(buffer.distance, endTime);
		double distance = buffer.distance.get(end_index).getValue()-buffer.distance.get(start_index).getValue();
		double meanSpeed = distance / (endTime-startTime);
		return meanSpeed;	
	}
	
	
	
	
}
