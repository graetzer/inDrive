package de.founderhack.indrive;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.founderhack.indrive.dsa.DiagnosticValue;

public class DataBuffer {
	private static DataBuffer instance = null;
	private Context context = null;
	
	public List<DiagnosticValue> 
			fuelReserve = new ArrayList<DiagnosticValue>(),
			temp = new ArrayList<DiagnosticValue>(),
			oilTemp = new ArrayList<DiagnosticValue>(),
			brightness = new ArrayList<DiagnosticValue>(),
			coolantTemp = new ArrayList<DiagnosticValue>(),
			rpm = new ArrayList<DiagnosticValue>(),
			speed = new ArrayList<DiagnosticValue>(),
			accelerationPedal = new ArrayList<DiagnosticValue>(),
			range = new ArrayList<DiagnosticValue>(),
			distance = new ArrayList<DiagnosticValue>();
	
	private DataBuffer(Context ctx) {
		context = ctx;
	}
	
	 public static DataBuffer getInstance(Context context) {
	        if (instance == null) {
	            instance = new DataBuffer(context.getApplicationContext());           
	        }
	        return instance;
	 }
}