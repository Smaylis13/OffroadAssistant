package fr.masterdapm.toulon.fragment;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.masterdapm.toulon.R;
public class FragParticipant extends Fragment {

	// Participant
	public static String PseudoParticipant = "";
	public static String Nom_Dans_Sim = "";
	public static String NUM_TEL = "";
	public static List<String> sParticipants = new ArrayList<String>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
	    View v = inflater.inflate(R.layout.fragment_participant,container, false);
	
	    
	    // récupération du pseudo de la personne DANS LA CARTE SIM
	   /* ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                  String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                  String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                  if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                     Cursor pCur = cr.query(
                               ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                               null,
                               ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                               new String[]{id}, null);
                     while (pCur.moveToNext()) {
                         String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                         if (phoneNo == NUM_TEL){
                        	 Log.d("TEST","ça a marché !!");
                        	 Nom_Dans_Sim = name;
                         }
                     }
                    pCur.close();     
                  }
            }
        }*/
	    
	    
	    int[] tabParticipant = {R.id.participant1}; //,R.id.participant2,R.id.participant3,R.id.participant4};
	   	
	    	TextView textView = (TextView)v.findViewById(R.id.participant1);
		    PseudoParticipant = PseudoParticipant+" "+Nom_Dans_Sim;
	    	textView.setText(PseudoParticipant);

	    return v;
        }
}