package fr.masterdapm.toulon;

import android.app.ActionBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.masterdapm.toulon.connexion.ConnexionActivity;
import fr.masterdapm.toulon.fragment.CMesTrajets;
import fr.masterdapm.toulon.fragment.FragParticipant;
import fr.masterdapm.toulon.fragment.FragInvit;
import fr.masterdapm.toulon.fragment.FragMap;
import fr.masterdapm.toulon.receiver.SmsReceiver;


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
    
    // TAG
    private static String TAG="MAP"; 
    private final int PICK_CONTACT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

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
				fragment = new FragParticipant();
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
 
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void inviterParticipant(View v) {
    	try {
	    	// Récupération du numéro de téléphone entré par l'organisateur du RAID
	    	EditText editText = (EditText)findViewById(R.id.contact);
	    	String str = editText.getText().toString();
	    	
	    	FragParticipant.NUM_TEL = str;
	    	
	    	SmsManager smsManager = SmsManager.getDefault();
	    	smsManager.sendTextMessage(str, null,
	    			SmsReceiver.EN_TETE_INVITATION + ";", null, null);
			LayoutInflater inflater = getLayoutInflater();
			View toastRoot = null;
			// The actual toast generated here.
			Toast toast = new Toast(getApplicationContext());
	        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
			toast.setDuration(Toast.LENGTH_SHORT);
	        toastRoot = inflater.inflate(R.layout.activity_together, null);
			toast.setView(toastRoot);
			toast.show();
    	}catch(Exception e){
    		Toast.makeText(getApplicationContext(),"Mauvais numéro !", Toast.LENGTH_SHORT).show();
    	}

    }
	 public void readcontact(View v){
	  try {
		   Intent intent = new Intent(Intent.ACTION_PICK);
		   intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
		   startActivityForResult(intent, PICK_CONTACT); 
	  } catch (Exception e) {
	          e.printStackTrace();
	    }
	}

	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	    super.onActivityResult(reqCode, resultCode, data);
	
	    switch (reqCode) {
	      case (PICK_CONTACT) :
	        if (resultCode == Activity.RESULT_OK) {
	            Uri contactData = data.getData();
	              Cursor c =  managedQuery(contactData, null, null, null, null);
	              startManagingCursor(c);
	              if (c.moveToFirst()) {
	                  String name = c.getString(0);
	             // Récupération du numéro de téléphone entré
	            	EditText editText = (EditText)findViewById(R.id.contact);
	            	String phoneNumber=" ";
	            	String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
	                Cursor phones = getContentResolver()
	                		.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
	                				ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
	                if (phones.moveToNext()){
	                  phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	              	editText.setText(phoneNumber );
	              	Toast.makeText(this, "Invitation prête à être envoyé à "+name, Toast.LENGTH_LONG).show();
	                }else{
	                    Toast.makeText(this, "Numéro non éxistant!", Toast.LENGTH_LONG).show();
	                	editText.setText("XXXXXX");
	                }
	                phones.close();
	               }
	         }
	       break;
	    }
	}  
}
