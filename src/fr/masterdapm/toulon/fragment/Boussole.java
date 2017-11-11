package fr.masterdapm.toulon.fragment;

import java.util.List;

import fr.masterdapm.toulon.R;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public class Boussole extends Activity {
	
	//La vue de notre boussole
	private CompassView compassView;
	
	//Le gestionnaire des capteurs
	private SensorManager sensorManager;
	//Notre capteur de la boussole numÈrique
	private Sensor sensor;
	
	//Notre listener sur le capteur de la boussole numérique
	private final SensorEventListener sensorListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			updateOrientation(event.values[SensorManager.DATA_X]);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_boussole);
        compassView = (CompassView)findViewById(R.id.compassView);
        //RécupÈration du gestionnaire de capteurs
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //Demander au gestionnaire de capteur de nous retourner les capteurs de type boussole
        List<Sensor> sensors =sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        //síil y a plusieurs capteurs de ce type on garde uniquement le premier
        if (sensors.size() > 0) {
        	sensor = sensors.get(0);
        }
    }
    
	//Mettre ‡ jour l'orientation
    protected void updateOrientation(float rotation) {
		compassView.setNorthOrientation(rotation);
	}

	@Override
    protected void onResume(){
    	super.onResume();
    	//Lier les événements de la boussole numérique au listener
    	sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		//Retirer le lien entre le listener et les événements de la boussole numérique
		sensorManager.unregisterListener(sensorListener);
	}
}