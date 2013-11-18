package de.founderhack.indrive;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.founderhack.indrive.dsa.DiagnosticValue;

public class DataBuffer {
	private static DataBuffer instance = null;
	private Context context = null;
	public List<DiagnosticValue> fuelReserve = new ArrayList<DiagnosticValue>();
	public List<DiagnosticValue> temp = new ArrayList<DiagnosticValue>();
	public List<DiagnosticValue> oilTemp = new ArrayList<DiagnosticValue>();
	public List<DiagnosticValue> brightness = new ArrayList<DiagnosticValue>();
	public List<DiagnosticValue> coolantTemp = new ArrayList<DiagnosticValue>();
	public List<DiagnosticValue> rpm = new ArrayList<DiagnosticValue>();
	public List<DiagnosticValue> speed = new ArrayList<DiagnosticValue>();
	public List<DiagnosticValue> accelerationPedal = new ArrayList<DiagnosticValue>();
	public List<DiagnosticValue> range = new ArrayList<DiagnosticValue>();
	
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