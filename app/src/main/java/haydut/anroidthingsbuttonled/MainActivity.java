package haydut.anroidthingsbuttonled;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;
public class MainActivity extends Activity {
    //RasberryPi LED Pin
    //https://developer.android.com/things/hardware/raspberrypi-io.html
    private static final String LED = "BCM14";
    //Debug TAG
    private  static final String LogTag = "LED";
    private Gpio mLedGpio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button BTNLed = (Button) findViewById(R.id.button);
        final PeripheralManagerService service = new PeripheralManagerService();
        try {
            mLedGpio = service.openGpio(LED);
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            Log.d(LogTag,e.toString());
        }
        BTNLed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean LedStatus = mLedGpio.getValue();
                    Log.d(LogTag,String.valueOf(LedStatus));
                    //Set Led Status
                    mLedGpio.setValue(!LedStatus);
                } catch (IOException e) {
                    Log.d(LogTag,e.toString());
                }

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mLedGpio.close();
        } catch (IOException e) {
            Log.d(LogTag,e.toString());
        } finally {
            mLedGpio = null;
        }
    }
}
