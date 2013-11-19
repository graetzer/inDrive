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

	public List<DiagnosticValue> fuelReserve = new ArrayList<DiagnosticValue>(),
			temp = new ArrayList<DiagnosticValue>(),
			oilTemp = new ArrayList<DiagnosticValue>(),
			brightness = new ArrayList<DiagnosticValue>(),
			coolantTemp = new ArrayList<DiagnosticValue>(),
			rpm = new ArrayList<DiagnosticValue>(),
			speed = new ArrayList<DiagnosticValue>(),
			accelerationPedal = new ArrayList<DiagnosticValue>(),
			range = new ArrayList<DiagnosticValue>(),
			distance = new ArrayList<DiagnosticValue>();
	Random generator = new Random();

	private List<DiagnosticValue> getDummyList() {
		List<DiagnosticValue> dummyList = new ArrayList<DiagnosticValue>();
		for (int i = 1; i < 10; i++) {
			dummyList.add(new DiagnosticValue("dummy", generator.nextInt(),
					"dummy"));
		}
		return dummyList;
	}
	
	
	private void generateDummyData() {
		fuelReserve.clear();
		fuelReserve.add(new DiagnosticValue("dummy", 55f,"dummy",1*60000));
		fuelReserve.add(new DiagnosticValue("dummy", 51f,"dummy",2*60000));
		fuelReserve.add(new DiagnosticValue("dummy", 42f,"dummy",3*60000));
		fuelReserve.add(new DiagnosticValue("dummy", 39f,"dummy",4*60000));
		fuelReserve.add(new DiagnosticValue("dummy", 29f,"dummy",5*60000));
		
		distance.clear();
		distance.add(new DiagnosticValue("dummy", 10f,"dummy",1*60000));
		distance.add(new DiagnosticValue("dummy", 28f,"dummy",2*60000));
		distance.add(new DiagnosticValue("dummy", 45f,"dummy",3*60000));
		distance.add(new DiagnosticValue("dummy", 52f,"dummy",4*60000));
		distance.add(new DiagnosticValue("dummy", 60f,"dummy",5*60000));
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
		distance.addAll(getDummyList());
		generateDummyData();
	}

	public static DataBuffer getInstance(Context context) {
		if (instance == null) {
			instance = new DataBuffer(context.getApplicationContext());

		}
		return instance;
	}
}