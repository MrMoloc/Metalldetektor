package fillmore.metalldetektor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor magneticSensor;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(!sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).isEmpty()) {
            magneticSensor = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
        } else {
            textView.setText("Kein Magnetiksensor installiert.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] mag = event.values;
        double betrag = Math.sqrt(mag[0] * mag[0] + mag[1] * mag[1] + mag[2] * mag[2]);
        progressBar.setMax(300);
        progressBar.setProgress((int) betrag);
        textView.setText(String.valueOf(betrag));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
