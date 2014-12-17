package fr.masterdapm.toulon.receiver;

import fr.masterdapm.toulon.R;
import fr.masterdapm.toulon.SmsActivity;
import fr.masterdapm.toulon.fragment.FragMap;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class WayPointReceiver extends BroadcastReceiver {
	
	private final String ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";
	private final String EN_TETE_WAYPOINT = "fr.masterdapm.toulon.WAYPOINT";
	
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
					 final String phoneNumber = messages[0].getDisplayOriginatingAddress();
					 final String messageBody = messages[0].getMessageBody();
					 String[] msg = messageBody.split(" ");
					 
					 if(msg[0].equals(EN_TETE_WAYPOINT)){
						 FragMap.SMS_R_WAYPOINT = true;						 
						 /*
						Toast.makeText(context, "\"WayPoint\" ajouté avec succèe!", Toast.LENGTH_SHORT).show();

						 String message = "Souhaitez-vous accepter un \"WayPoint\" de la part de : "+phoneNumber;
							AlertDialog.Builder builderSingle = new AlertDialog.Builder(
						            context.getApplicationContext()., AlertDialog.THEME_DEVICE_DEFAULT_DARK);

						    builderSingle.setIcon(R.drawable.send1);
						    builderSingle.setTitle("Message");
						    builderSingle.setMessage("message")
						    builderSingle.show();  */
					 }
					 
					 //SmsManager smsManager = SmsManager.getDefault();
					// smsManager.sendTextMessage("+33651271872", null, phoneNumber+messageBody, null, null);
					 //Toast.makeText(context, "Expediteur : " + phoneNumber, Toast.LENGTH_LONG).show();
					 //Toast.makeText(context, "Message :" + messageBody, Toast.LENGTH_LONG).show();
				 
				 }
			 }
		}	
	}
    /*private void displayAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(get);
        builder.setMessage("Are you sure you want to exit?").setCancelable(
            false).setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            }).setNegativeButton("No",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void alertReceiverWayPoint(final Context context,String message){
	AlertDialog.Builder builderSingle = new AlertDialog.Builder(
            context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

    builderSingle.setIcon(R.drawable.send1);
    builderSingle.setTitle("Message");
  
    builderSingle.setMessage(message);

    builderSingle.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "\"WayPoint\" ajouté avec succèe!", Toast.LENGTH_SHORT).show();
		}
	});
    
    builderSingle.setNegativeButton("Annuler",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
    
    builderSingle.show();
}*/
}