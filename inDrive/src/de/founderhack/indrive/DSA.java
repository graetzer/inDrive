package de.founderhack.indrive;

import android.util.Log;
import de.dsa.hackathon2013.DiagnosticNames;
import de.founderhack.indrive.dsa.DiagnosticValue;
import de.founderhack.indrive.dsa.ReadVehicleValuesTask;
import de.founderhack.indrive.dsa.ReadVehicleValuesTask.VehicleValuesReaderTaskCallbacks;

/**
 * Constantly poll the car for new values and write them into the buffer
 * If we are in debug mode, provide fake values
 * 
 * @author simon
 *
 */
public class DSA implements VehicleValuesReaderTaskCallbacks{

	private DSAListener mListener;
	private DataBuffer dataBuffer;
	private boolean debug = true;
	
	//Current values
	//private float fuelReserve, temp, oilTemp, brightness, coolantTemp, rpm, speed, accelerationPedal, range;
	private boolean updating;
	
	public interface DSAListener{
		public void onSensorUpdate();
	}
	
	public DSA(DSAListener list){
		mListener = list;
		dataBuffer = DataBuffer.getInstance();
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
	
	private void setValue(DiagnosticValue val) {
		if (debug) {
			// Provide some relevant data
			dataBuffer.temp.add(new DiagnosticValue(DiagnosticNames.OUTER_TEMPERATURE, 20, "C°"));
			dataBuffer.speed.add(new DiagnosticValue(DiagnosticNames.SPEED_SENSOR, 180, "km/h"));
			
			if (dataBuffer.fuelReserve.size() > 0) {
				DiagnosticValue last = dataBuffer.fuelReserve.get(dataBuffer.fuelReserve.size()-1);
				dataBuffer.fuelReserve.add(new DiagnosticValue(DiagnosticNames.FUEL_RESERVE, last.getValue()-0.1f, "l"));
			}
			return;
		}
		
		
		if(val.getName().equals(DiagnosticNames.FUEL_RESERVE)){
			dataBuffer.fuelReserve.add(val);
		}else if(val.getName().equals(DiagnosticNames.OUTER_TEMPERATURE)){
			dataBuffer.temp.add(val);
		}else if(val.getName().equals(DiagnosticNames.OIL_TEMPERATURE)){
			dataBuffer.oilTemp.add(val);
		}else if(val.getName().equals(DiagnosticNames.PHOTO_TRANSISTOR)){
			dataBuffer.brightness.add(val);
		}else if(val.getName().equals(DiagnosticNames.COOLANT_TEMPERATURE)){
			dataBuffer.coolantTemp.add(val);
		}else if(val.getName().equals(DiagnosticNames.ENGINE_RPM)){
			dataBuffer.rpm.add(val);
		}else if(val.getName().equals(DiagnosticNames.SPEED_SENSOR)){
			dataBuffer.speed.add(val);
		}else if(val.getName().equals(DiagnosticNames.ACCELERATOR_POSITION)){
				dataBuffer.accelerationPedal.add(val);
		}else if(val.getName().equals(DiagnosticNames.RANGE)){
				dataBuffer.range.add(val);
		}
	}
	
}
