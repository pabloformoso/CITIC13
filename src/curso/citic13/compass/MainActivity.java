package curso.citic13.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static SensorManager sensorManager;
	private CompassView compassView;
	private Sensor sensor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		compassView = new CompassView(this);
		setContentView(compassView);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
		if (sensor != null) {
			sensorManager.registerListener(mySensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
			Log.d("MAIN", "Sensor registered");
		} else {
		      Toast.makeText(this, "No se puede acceder a la orientaci—n", Toast.LENGTH_LONG).show();
		      finish();			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private SensorEventListener mySensorEventListener = new SensorEventListener() {

	    @Override
	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    }

	    @Override
	    public void onSensorChanged(SensorEvent event) {
	      float azimuth = event.values[0];
	      compassView.updateData(azimuth);
	    }
	};



	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (sensor != null) {
			sensorManager.unregisterListener(mySensorEventListener);
		}
	}
}