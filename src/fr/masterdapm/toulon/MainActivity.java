package fr.masterdapm.toulon;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;


import fr.masterdapm.toulon.connexion.ConnexionActivity;
import fr.masterdapm.toulon.fragment.CMesTrajets;
import fr.masterdapm.toulon.fragment.FragContacts;
import fr.masterdapm.toulon.fragment.FragMap;

import android.R.string;
import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


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
				Toast.makeText(this, "iL N'existe PAS! "+TAG, Toast.LENGTH_SHORT).show();

				fragment = new CMesTrajets();
			}
        break;
        case 3:            
			mTitle = getString(R.string.contacts);
			TAG = "CONTACTS";
			fragment = fragmentManager.findFragmentByTag(TAG);
			if(fragment == null){    		
				Toast.makeText(getApplicationContext(), "iL N'existe PAS! "+TAG, Toast.LENGTH_SHORT).show();

				fragment = new FragContacts();
			}
			break; 
    case 4:
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 

}
