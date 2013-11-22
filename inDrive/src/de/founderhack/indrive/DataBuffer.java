package de.founderhack.indrive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.founderhack.indrive.dsa.DiagnosticValue;

public class DataBuffer {
	private static DataBuffer instance = null;

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
		fuelReserve.add(new DiagnosticValue("dummy", 55f,"l",System.currentTimeMillis()-18*60000));
		fuelReserve.add(new DiagnosticValue("dummy", 51f,"l",System.currentTimeMillis()-12*60000));
		fuelReserve.add(new DiagnosticValue("dummy", 42f,"l",System.currentTimeMillis()-6*60000));
		fuelReserve.add(new DiagnosticValue("dummy", 39f,"l",System.currentTimeMillis()-3*60000));
		fuelReserve.add(new DiagnosticValue("dummy", 29f,"l",System.currentTimeMillis()-1*60000));
		
		distance.clear();
		distance.add(new DiagnosticValue("dummy", 10f,"km",System.currentTimeMillis()-18*60000));
		distance.add(new DiagnosticValue("dummy", 28f,"km",System.currentTimeMillis()-12*60000));
		distance.add(new DiagnosticValue("dummy", 45f,"km",System.currentTimeMillis()-6*60000));
		distance.add(new DiagnosticValue("dummy", 52f,"km",System.currentTimeMillis()-3*60000));
		distance.add(new DiagnosticValue("dummy", 60f,"km",System.currentTimeMillis()-1*60000));
	}

	private DataBuffer() {
		
		// Provide some data for a start
		temp.addAll(getDummyList());
		oilTemp.addAll(getDummyList());
		brightness.addAll(getDummyList());
		coolantTemp.addAll(getDummyList());
		rpm.addAll(getDummyList());
		speed.addAll(getDummyList());
		accelerationPedal.addAll(getDummyList());
		range.addAll(getDummyList());
		generateDummyData();
	}

	public static DataBuffer getInstance() {
		if (instance == null) {
			instance = new DataBuffer();
		}
		return instance;
	}
}