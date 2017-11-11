package fr.masterdapm.toulon.receiver;

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
	public final static String EN_TETE_INVITATION = "fr.masterdapm.toulon.INVITATION";
	public final static String EN_TETE_INVITATION_ACCEPTEE = "fr.masterdapm.toulon.INVITATION_ACCEPTEE";
	public final static String G_EN_TETE_POSES = "raid_pos";

	public static String[] sMsg;// contient ceci ==> {en_tete, etc...}
	public static String sPhoneNumber;

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
				 //Message vide ??
				 if (messages.length > -1){
					 sPhoneNumber = messages[0].getDisplayOriginatingAddress();
					 final String messageBody = messages[0].getMessageBody();
					 //Table msg contient en tete + msg
					 sMsg = messageBody.split(";");
					 /**
					  * Si c'est un Waypoint
					  */
					 if(sMsg[0].equals(EN_TETE_WAYPOINT)){
						 this.abortBroadcast();
						 if(sMsg.length < 4){
							 sMsg[4]="";
						 }
						 FragMap.SMS_R_WAYPOINT = true;
					 }
					 /**
					  * Si c'est une invitation
					  */
					 if(sMsg[0].equals(EN_TETE_INVITATION)){
						 this.abortBroadcast();
						 FragMap.SMS_R_INVITATION = true;
					 }
					 /**
					  * Si l'invitation est acceptée
					  */
					 if(sMsg[0].equals(EN_TETE_INVITATION_ACCEPTEE)){
						 this. abortBroadcast();
						 FragMap.SMS_R_INVITATION_ACCEPTEE = true;
					 }
					 /**
					  * Si c'est une position d'un participant
					  */
                     if(sMsg[0].equals(G_EN_TETE_POSES)){
                    	 this. abortBroadcast();
						 FragMap.SMS_R_POSE = true;
					 }
				 }
			 }
		}
	}
}
