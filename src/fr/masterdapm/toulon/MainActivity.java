package fr.masterdapm.toulon;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.masterdapm.toulon.connexion.ConnexionActivity;
import fr.masterdapm.toulon.fragment.CMesTrajets;
import fr.masterdapm.toulon.fragment.FragContacts;
import fr.masterdapm.toulon.fragment.FragInvit;
import fr.masterdapm.toulon.fragment.FragMap;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private static CharSequence mTitle="Carte";
    
    // Pour la reception de sms
    public final static String RAID_SMS = "raid_app";
    
   
    // TAG
    private static String TAG="MAP"; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        //mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        switch (position+1) {
        case 1:
            mTitle = getString(R.string.carte);
            TAG= "CARTE";
			fragment = fragmentManager.findFragmentByTag(TAG);
            if(fragment == null){		
	            fragment = new FragMap();
            }
            break;
        case 2:
            mTitle = getString(R.string.trajet);
            TAG = "TRAJET";	
			fragment = fragmentManager.findFragmentByTag(TAG);
            if(fragment == null){    		
				fragment = new CMesTrajets();
			}
        break;
        case 3:            
			mTitle = getString(R.string.contacts);
			TAG = "CONTACTS";
			fragment = fragmentManager.findFragmentByTag(TAG);
			if(fragment == null){ 
				fragment = new FragContacts();
			}
			break;
			
        case 4:		
        	mTitle = getString(R.string.inviter);
            TAG= "INVITER";
			fragment = fragmentManager.findFragmentByTag(TAG);
            if(fragment == null){		
	            fragment = new FragInvit();
            }        
        	break;
        case 5:
		AlertDialog.Builder alert_box=new AlertDialog.Builder(this);
		alert_box.setIcon(R.drawable.deco);
		alert_box.setMessage("Are you sure you want to log out ?");
		alert_box.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Déconnexion...",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this, ConnexionActivity.class);
				startActivity(intent);
			}
		});
		alert_box.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
			}
		});
		alert_box.show();

		break;     
    }
        if (fragment != null){
            fragmentManager.beginTransaction()
            .replace(R.id.container, fragment,TAG)
            .commit();
        }       
    }
 
    

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent intent = new Intent(getApplicationContext(),ConnexionActivity.class);
			startActivity(intent);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    
    //////////////////////////////////////////////
    
    
    
    public void inviterParticipant(View v) {
    	
    	// Récupération du numéro de téléphone entré par l'organisateur du RAID
    	EditText editText = (EditText)findViewById(R.id.contact);
    	String str = editText.getText().toString();
    	SmsManager smsManager = SmsManager.getDefault();
    	// "0651271872"
    	smsManager.sendTextMessage("0651271872", null, "Salut", null, null);    	
    	
    	
    }
    
}
















//Fonction qui affiche la liste des contacts (en Log pour le moment )
/* public void displayContacts(View v) {
	     
	      ContentResolver cr = getContentResolver();
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
	                         Log.d("Résultat","Name: " + name + ", Phone No: " + phoneNo);
	                         }
	                    pCur.close();
	                }
	            }
	        }
	    }*/
