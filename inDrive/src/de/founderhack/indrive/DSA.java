package de.founderhack.indrive;

import android.util.Log;
import de.dsa.hackathon2013.DiagnosticNames;
import de.founderhack.indrive.dsa.DiagnosticValue;
import de.founderhack.indrive.dsa.ReadVehicleValuesTask;
import de.founderhack.indrive.dsa.ReadVehicleValuesTask.VehicleValuesReaderTaskCallbacks;

public class DSA implements VehicleValuesReaderTaskCallbacks{

	private DSAListener mListener;
	private DataBuffer dataBuffer;
	private boolean debug = true;
	
	//Current values
	private float fuelReserve, temp, oilTemp, brightness, coolantTemp, rpm, speed, accelerationPedal, range;
	private boolean updating;
	
	public interface DSAListener{
		public void onSensorUpdate();
	}
	
	public DSA(DSAListener list){
		mListener = list;
		dataBuffer = DataBuffer.getInstance(null);
	}
	
	public void stopListening(){
		updating = false;
	}
	
	public void startListening(){
		if(updating) return;
		
		updating = true;
		getCarValues();
	}
	
	private void getCarValues(){
		ReadVehicleValuesTask mTask = new ReadVehicleValuesTask(this);
		mTask.execute("Measurements");
	}

	@Override
	public void onPreExecute() { }

	@Override
	public void onProgressUpdate(Object value) {
		// TODO save vars
		if(value instanceof DiagnosticValue){
			//Set Vars
			setValue((DiagnosticValue) value);
		}else{
			//Debug
			Log.d("DSA", (String) value);
		}
	}

	@Override
	public void onCancelled() {	}

	@Override
	public void onPostExecute() { 
		if(mListener != null){
			mListener.onSensorUpdate();
		}else{
			updating = false;
		}
		
		if(updating){
			getCarValues();
		}
	}
	
	private void setValue(DiagnosticValue val){
		if(val.getName().equals(DiagnosticNames.FUEL_RESERVE)){
			fuelReserve = val.getValue();
			if (!debug)
				dataBuffer.fuelReserve.add(val);
		}else if(val.getName().equals(DiagnosticNames.OUTER_TEMPERATURE)){
			temp = val.getValue();
			if (!debug)
				dataBuffer.temp.add(val);
		}else if(val.getName().equals(DiagnosticNames.OIL_TEMPERATURE)){
			oilTemp = val.getValue();
			if (!debug)
				dataBuffer.oilTemp.add(val);
		}else if(val.getName().equals(DiagnosticNames.PHOTO_TRANSISTOR)){
			brightness = val.getValue();
			if (!debug)
				dataBuffer.brightness.add(val);
		}else if(val.getName().equals(DiagnosticNames.COOLANT_TEMPERATURE)){
			coolantTemp = val.getValue();
			if (!debug)
				dataBuffer.coolantTemp.add(val);
		}else if(val.getName().equals(DiagnosticNames.ENGINE_RPM)){
			rpm = val.getValue();
			if (!debug)
				dataBuffer.rpm.add(val);
		}else if(val.getName().equals(DiagnosticNames.SPEED_SENSOR)){
			speed = val.getValue();
			if (!debug)
				dataBuffer.speed.add(val);
		}else if(val.getName().equals(DiagnosticNames.ACCELERATOR_POSITION)){
			accelerationPedal = val.getValue();
			if (!debug)
				dataBuffer.accelerationPedal.add(val);
		}else if(val.getName().equals(DiagnosticNames.RANGE)){
			range = val.getValue();
			if (!debug)
				dataBuffer.range.add(val);
		}
	}
	
	public float getFuelReserve(){
		return fuelReserve;
	}

	public float getTemp() {
		return temp;
	}

	public float getOilTemp() {
		return oilTemp;
	}

	public float getBrightness() {
		return brightness;
	}

	public float getCoolantTemp() {
		return coolantTemp;
	}

	public float getRpm() {
		return rpm;
	}

	public float getSpeed() {
		return speed;
	}

	public float getAccelerationPedal() {
		return accelerationPedal;
	}

	public float getRange() {
		return range;
	}
	
}
