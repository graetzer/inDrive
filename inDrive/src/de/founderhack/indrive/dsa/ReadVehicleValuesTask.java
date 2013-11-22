/************************************************************************
 *                                                                      *
 *  DDDD     SSSS    AAA        Daten- und Systemtechnik Aachen GmbH    *
 *  D   D   SS      A   A       Pascalstrasse 28                        *
 *  D   D    SSS    AAAAA       52076 Aachen-Oberforstbach, Germany     *
 *  D   D      SS   A   A       Telefon: +49 (0)2408 / 9492-0           *
 *  DDDD    SSSS    A   A       Telefax: +49 (0)2408 / 9492-92          *
 *                                                                      *
 *                                                                      *
 *  (c) Copyright by DSA - all rights reserved                          *
 *                                                                      *
 ************************************************************************
 *
 * Initial Creation:
 *    Author      Ada Lezama
 *    Created on  21.10.2013
 *
 ************************************************************************/
package de.founderhack.indrive.dsa;

import java.io.File;

import android.os.AsyncTask;
import android.os.Environment;
import de.dsa.hackathon2013.DiagnosticNames;
import de.dsa.hackathon2013.VehicleDiagnosisApi;
import de.dsa.hackathon2013.lib.IVehicleDiagnosisApi;
import de.dsa.hackathon2013.lib.MirrorMovingDirection;
import de.dsa.hackathon2013.lib.WindowMovingDirection;
import de.dsa.smartdiag.interpreter.provider.SmartDiagDataProviderFactory;
import de.dsa.smartdiag.vciapi.util.VciConstants;

/**
 * A task that performs the reading operation as a background task and proxies
 * progress updates and results back to the Diagnostics Activity.
 *
 * Note that we need to check if the callbacks are null in each method in case
 * they are invoked after the Activity's onDestroy() method has been called.
 */
public class ReadVehicleValuesTask extends AsyncTask<String, Object, Void> {
	
	 public static interface VehicleValuesReaderTaskCallbacks {
	        /**
	         * Callback method to perform operations before the task is executed.
	         */
	        void onPreExecute();

	        /**
	         * Callback method to perform operations during the execution of the
	         * task and to report the progress which can be then shown in an UI
	         * element.
	         *
	         * @param value a diagnostic value that has been read from the vehicle
	         */
	        void onProgressUpdate(Object value);

	        /**
	         * Callback method to perform the operations required when the task has
	         * been cancelled.
	         */
	        void onCancelled();

	        /**
	         * Callback method to perform operations one the task has been
	         * completed.
	         */
	        void onPostExecute();
	}
	 
    /** Directory where the vehicle information is stored in the device. */
    private static final String DIRECTORY_VEHICLE_DATA = "/DSA/vehicle_data";

    /** The object that reacts to the output of the task. In this case will be DSADiagnostcisActivity. */
    private VehicleValuesReaderTaskCallbacks mCallbacks;

    /** The object that provides the API to perform vehicle communication. */
    private IVehicleDiagnosisApi mVehicleCommunication;

    /**
     * Creates a new instance of this task with an object to which it will
     * report the progress updates and the final output.
     *
     * @param pCallbacks
     *            The object to which the progress and output is reported
     */
    public ReadVehicleValuesTask(VehicleValuesReaderTaskCallbacks pCallbacks) {
        mCallbacks = pCallbacks;
        configureVehicleCommunication();
    }

    /**
     * Setup the vehicle data for the Passat vehicle.
     * This method will:
     * 1. Read from the external storage DSA directory the data corresponding
     * to the vehicle.
     * 2. Setup the vehicle communication API to access the control and readings methods.
     */
    private void configureVehicleCommunication() {
        if (mVehicleCommunication == null) {
            // Initialise Vehicle Data from the information stored in the device
            File rootDir = Environment.getExternalStorageDirectory();
            SmartDiagDataProviderFactory.getProvider().setVehicleDatas(rootDir + DIRECTORY_VEHICLE_DATA + "/Passat");

            // Read vehicle data and create the communication API
            mVehicleCommunication = new VehicleDiagnosisApi();
        }
    }

    /**
     * Set the callback object to which the task must notify about changes
     * during execution.
     *
     * @param pCallbacks
     *            the callback object
     */
    public void setTaskCallback(VehicleValuesReaderTaskCallbacks pCallbacks) {
        mCallbacks = pCallbacks;
    }

    @Override
    protected void onPreExecute() {
        // invoked on the UI thread before the task is executed. This step is
        // normally used to setup the task, for instance by showing a progress
        // bar in the user interface.
        if (mCallbacks != null) {
            mCallbacks.onPreExecute();
        }
    }

    @Override
    protected Void doInBackground(String... pOperation) {
        String operation = pOperation[0];

        // Configure the IP Address of the WDI. Must be performed before any
        // operation since it could have been changed between operations.
        setIpAddress();

        if (operation.equals("Measurements")) {
            // Read the complete list of measurement values
            readMeasurementValues();
        } else {
            // Execute the control command selected by the user
            executeControl(operation);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... value) {
        // Invoked on the UI thread after a call to publishProgress(Progress...).
        // The timing of the execution is undefined. This method is used to
        // display any form of progress in the user interface while the background
        // computation is still executing. For instance, it can be used to animate a
        // progress bar or show logs in a text field.
        if (mCallbacks != null && value.length > 0) {
            mCallbacks.onProgressUpdate(value[0]);
        }
    }

    @Override
    protected void onCancelled() {
        if (mCallbacks != null) {
            mCallbacks.onCancelled();
        }
    }

    @Override
    protected void onPostExecute(Void ignore) {
        if (mCallbacks != null) {
            mCallbacks.onPostExecute();
        }
    }

    /**
     * Set the IP Address of the WDI before performing any operation
     * on the vehicle.
     *
     * Default is set to 192.168.126.2, since it is the default WDI
     */
    private void setIpAddress() {
    	
    	//Use the standard IP
    	VciConstants.getInstance().setVCI5Ip("192.168.126.2");
    	
//        if (mCallbacks instanceof Activity) {
//            Activity activity = (Activity)mCallbacks;
//            // Get from preferences stored in the settings dialog
//            //final SharedPreferences mPreferenceSettings = activity.getSharedPreferences(DSADiagnosticsActivity.SETTINGS_PREFERENCES, 0);
//            //String storedAddress = mPreferenceSettings.getString(DSADiagnosticsActivity.IPADDRESS_PREFERENCE, "192.168.126.2");
//            VciConstants.getInstance().setVCI5Ip("192.168.126.2");
//        }
    }

    /**
     * Execute a control command in the vehicle. These operations will not return any
     * value but will execute an action in the vehicle.
     *
     * @param pAction the action to be performed
     */
    private void executeControl(String pAction) {
        if (pAction.equals(DiagnosticNames.MIRROR_UP)) {
            onProgressUpdate("Moving Mirror Up");
            mVehicleCommunication.moveDriverSideMirror(MirrorMovingDirection.UP);
        } else if (pAction.equals(DiagnosticNames.MIRROR_DOWN)) {
            onProgressUpdate("Moving Mirror Down");
            mVehicleCommunication.moveDriverSideMirror(MirrorMovingDirection.DOWN);
        } else if (pAction.equals(DiagnosticNames.MIRROR_LEFT)) {
            onProgressUpdate("Moving Mirror Left");
            mVehicleCommunication.moveDriverSideMirror(MirrorMovingDirection.LEFT);
        } else if (pAction.equals(DiagnosticNames.MIRROR_RIGHT)) {
            onProgressUpdate("Moving Mirror Right");
            mVehicleCommunication.moveDriverSideMirror(MirrorMovingDirection.RIGHT);
        } else if (pAction.equals(DiagnosticNames.WINDOW_UP)) {
            onProgressUpdate("Moving Window Up");
            mVehicleCommunication.moveDriverWindow(WindowMovingDirection.UP);
        } else if (pAction.equals(DiagnosticNames.WINDOW_DOWN)) {
            onProgressUpdate("Moving Window Down");
            mVehicleCommunication.moveDriverWindow(WindowMovingDirection.DOWN);
        } else if (pAction.equals(DiagnosticNames.DOOR_LOCK)) {
            onProgressUpdate("Lock Door");
            mVehicleCommunication.setDriverSideDoor(true);
        } else if (pAction.equals(DiagnosticNames.DOOR_UNLOCK)) {
            onProgressUpdate("Unlock Door");
            mVehicleCommunication.setDriverSideDoor(false);
        }
    }

    /**
     * Read the measurement values from the vehicle all at once.
     */
    private void readMeasurementValues() {
//        onProgressUpdate("Reading: " + DiagnosticNames.CLOCK);
//        String clockValue = mVehicleCommunication.readClock().toString();
//        onProgressUpdate(new DiagnosticValue(DiagnosticNames.CLOCK, clockValue, ""));

        onProgressUpdate("Reading: " + DiagnosticNames.FUEL_RESERVE);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.FUEL_RESERVE, mVehicleCommunication.readFuelTankReserve(), "l"));

        onProgressUpdate("Reading: " + DiagnosticNames.FUELTANK_NEEDLE_POSITION);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.FUELTANK_NEEDLE_POSITION, mVehicleCommunication.readFuelTankNeedlePosition(), "�"));

        onProgressUpdate("Reading: " + DiagnosticNames.OUTER_TEMPERATURE);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.OUTER_TEMPERATURE, mVehicleCommunication.readEnvironmentTemperature(), "�C"));

        onProgressUpdate("Reading: " + DiagnosticNames.OIL_TEMPERATURE);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.OIL_TEMPERATURE, mVehicleCommunication.readEngineOilTemperature(), "�C"));

        onProgressUpdate("Reading: " + DiagnosticNames.PHOTO_TRANSISTOR);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.PHOTO_TRANSISTOR, mVehicleCommunication.readEnvironmentelLightValue(), "%"));

        onProgressUpdate("Reading: " + DiagnosticNames.COOLANT_TEMPERATURE);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.COOLANT_TEMPERATURE, mVehicleCommunication.readEngineCoolantTemperature(), "�C"));

        onProgressUpdate("Reading: " + DiagnosticNames.ENGINE_RPM);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.ENGINE_RPM, mVehicleCommunication.readEngineRpm(), "/min"));

        onProgressUpdate("Reading: " + DiagnosticNames.SPEED_SENSOR);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.SPEED_SENSOR, mVehicleCommunication.readVehicleSpeed(), "km/h"));

        onProgressUpdate("Reading: " + DiagnosticNames.ACCELERATOR_POSITION);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.ACCELERATOR_POSITION, mVehicleCommunication.readAcceleratorPedalPosition(), "%"));

        onProgressUpdate("Reading: " + DiagnosticNames.RANGE);
        onProgressUpdate(new DiagnosticValue(DiagnosticNames.RANGE, mVehicleCommunication.readRange(), "km"));

//        onProgressUpdate("Reading: " + DiagnosticNames.DOOR_STATE);
//        String doorState = mVehicleCommunication.readDoorStates().toString();
//        onProgressUpdate(new DiagnosticValue(DiagnosticNames.DOOR_STATE, doorState, ""));

//        onProgressUpdate("Reading: " + DiagnosticNames.VEHICLE_ID_NUMBER);
//        String vehicleIdNumber = mVehicleCommunication.readVehicleIdentificationNumber();
//        onProgressUpdate(new DiagnosticValue(DiagnosticNames.VEHICLE_ID_NUMBER, vehicleIdNumber, ""));
    }
}