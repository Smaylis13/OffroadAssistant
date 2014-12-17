package fr.masterdapm.toulon;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

public class SmsActivity extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			 String message = "Souhaitez-vous accepter un \"WayPoint\" de la part de : ";
				AlertDialog.Builder builderSingle = new AlertDialog.Builder(
			            this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

			    builderSingle.setIcon(R.drawable.send1);
			    builderSingle.setTitle("Message");
			    builderSingle.setMessage("message");
			    builderSingle.show();
	 }

}
