package de.founderhack.indrive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
	Random generator = new Random();
	
	private List<DiagnosticValue> getDummyList() {		
		List<DiagnosticValue> dummyList =  new ArrayList<DiagnosticValue>();
		for (int i=1;i<10;i++) {
			dummyList.add(new DiagnosticValue("dummy", generator.nextInt(), "dummy"));
		}
		return dummyList;
	}
	
	private DataBuffer(Context ctx) {
		context = ctx;
		fuelReserve.addAll(getDummyList());
		temp.addAll(getDummyList());
		oilTemp.addAll(getDummyList());
		brightness.addAll(getDummyList());
		coolantTemp.addAll(getDummyList());
		rpm.addAll(getDummyList());
		speed.addAll(getDummyList());
		accelerationPedal.addAll(getDummyList());
		range.addAll(getDummyList());
	}
	
	 public static DataBuffer getInstance(Context context) {
	        if (instance == null) {
	            instance = new DataBuffer(context.getApplicationContext()); 
	            
	        }
	        return instance;
	 }
}