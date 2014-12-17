package fr.masterdapm.toulon;

import fr.masterdapm.toulon.fragment.FragMap;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
	
	private final String ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";
	public final static String EN_TETE_WAYPOINT = "fr.masterdapm.toulon.WAYPOINT";
	public static String[] mMsgWayPoint;
	public static String mPhoneNumber;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION_RECEIVE_SMS)){
			Bundle bundle = intent.getExtras();
			 if (bundle != null){
				 Object[] pdus = (Object[]) bundle.get("pdus");
				 
				 final SmsMessage[] messages = new SmsMessage[pdus.length];
				 for (int i = 0; i < pdus.length; i++){
					 messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				 }
				 if (messages.length > -1){
					 mPhoneNumber = messages[0].getDisplayOriginatingAddress();
					 final String messageBody = messages[0].getMessageBody();
					 mMsgWayPoint = messageBody.split(";");
					 
					 if(mMsgWayPoint[0].equals(EN_TETE_WAYPOINT)){
						 FragMap.SMS_R_WAYPOINT = true;
					 }
					 //SmsManager smsManager = SmsManager.getDefault();
					// smsManager.sendTextMessage("+33651271872", null, phoneNumber+messageBody, null, null);
					 //Toast.makeText(context, "Expediteur : " + phoneNumber, Toast.LENGTH_LONG).show();
					 //Toast.makeText(context, "Message :" + messageBody, Toast.LENGTH_LONG).show();
				 
				 }
			 }
		}
	}
}
	/*// private final String ACTION_RECEIVE_SMS =
	// "android.provider.Telephony.SMS_RECEIVED";
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i("dosso","youl");

		Bundle bundle = intent.getExtras();
		// String string = intent.getStringExtra(MainActivity.RAID_SMS);
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");

			final SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			if (messages.length > -1) {
				// final String phoneNumber = "Expediteur : " +
				// messages[0].getDisplayOriginatingAddress();
				final String messageBody = "\nMessage : "
						+ messages[0].getMessageBody();

				Log.i("Test", messageBody);

			}
		}
	}

}*/
