package fr.masterdapm.toulon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.masterdapm.toulon.fragment.FragMap;
import fr.masterdapm.toulon.item.DrawerItemCustomAdapter;
import fr.masterdapm.toulon.item.ObjectDrawerItem;
import fr.masterdapm.toulon.sql.Randonnee;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;



/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    
    public static final String[] names = new String[] { "Carte",
        "Mes trajets", "Contacts", "Inviter" , "Déconnexion" };

    public static final Integer[] icons = { R.drawable.carte,
        R.drawable.trajet, R.drawable.contacts, R.drawable.add70, R.drawable.deconnexion };
    
    private static MenuItem enregistrer;
    private static MenuItem effacer ;
    private static MenuItem play_pause;
    private View alertDialogView, alertDialogSaveView;
    private AlertDialog ad, adSave;
    private Randonnee rando;
    private String nomRandoSave ;
	private String descRandoSave ;
	private long chronoA, chronoB;
	private int niveauDiff = 5;
	private EditText etNomRando, etDescRando;
	private Calendar calendar;


    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        List<ObjectDrawerItem> rowItems = new ArrayList<ObjectDrawerItem>();
        
        for(int i=0; i < names.length ; i++){
	        ObjectDrawerItem item1 = new ObjectDrawerItem(icons[i],names[i]);
	        rowItems.add(item1);
        }
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(
                getActionBar().getThemedContext(),
                R.layout.listview_item_row, rowItems);
        
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    @SuppressLint("NewApi") 
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
        enregistrer = menu.findItem(R.id.enregistrer);
        play_pause  = menu.findItem(R.id.play_pause);
        effacer     = menu.findItem(R.id.effacer);        
    }
    

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
   		// The Custom Toast Layout Imported here
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View toastRoot = null;
		// The actual toast generated here.
		Toast toast = new Toast(getActivity().getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
				
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        
        if (item.getItemId() == R.id.play_pause) {
        	if(item.getTitle().equals("Play")){

                // Call toast.xml file for toast layout

                toastRoot = inflater.inflate(R.layout.play_toast, null);
        		toast.setView(toastRoot);
        		toast.show();
				if (FragMap.getEtatRandonnee().equals("nonDemarrée")) {
					FragMap.setEtatRandonnee("demarrée");
				}
				FragMap.setEtatRandonnee("demarrée");

				calendar = Calendar.getInstance();
				chronoA = calendar.getTimeInMillis();

				enregistrer.setEnabled(true);
				effacer.setEnabled(true);


	        	play_pause.setIcon(getResources().getDrawable(R.drawable.pause7));
	        	play_pause.setTitle("Pause");
        	}else{
                // Call toast.xml file for toast layout

				FragMap.EtatBouton = "play";
				FragMap.setEtatRandonnee("enPause");
				calendar = Calendar.getInstance();
				chronoB = calendar.getTimeInMillis();
				// timeMS = calendar.getTimeInMillis();
				FragMap.dureeRando += (chronoB - chronoA);
        		FragMap.setEtatRandonnee("demarrée");
                toastRoot = inflater.inflate(R.layout.pause_toast, null);
        		toast.setView(toastRoot);
        		toast.show();
	        	play_pause.setIcon(getResources().getDrawable(R.drawable.play1));
	        	play_pause.setTitle("Play");
        	}

        	
        	//Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }
    	if (item.getItemId() == R.id.enregistrer){
    		/*
    		 * ==================BOITE DE DIALOGUE (SAUVEGARDE
    		 * RANDONNEE)=========================
    		 */
    		AlertDialog.Builder adb2 = new AlertDialog.Builder(getActivity());
    		LayoutInflater factory = LayoutInflater.from(getActivity().getBaseContext());
    		alertDialogSaveView = factory.inflate(R.layout.l_saverando, null);
    		adb2.setView(alertDialogSaveView);
    		adb2.setTitle("Sauvegarde de la Randonnée");
    		adb2.setIcon(R.drawable.save28);
    		adb2.setPositiveButton("Sauvegarder",
    				new DialogInterface.OnClickListener() {

    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						/* Recuperation des editText */
    						etNomRando = (EditText) alertDialogSaveView
    								.findViewById(R.id.etNomRando);
    						etDescRando = (EditText) alertDialogSaveView
    								.findViewById(R.id.etDescRando);

    						nomRandoSave = etNomRando.getText().toString();
    						descRandoSave = etDescRando.getText().toString();
    						/*
    						 * rgDifficulte = (RadioGroup)
    						 * findViewById(R.id.rgDifficulte); idSelectionne =
    						 * rgDifficulte.getCheckedRadioButtonId(); switch
    						 * (idSelectionne) { case R.id.dif1: niveauDiff = 1;
    						 * break; case R.id.dif2: niveauDiff = 2; break; case
    						 * R.id.dif3: niveauDiff = 3; break; case R.id.dif4:
    						 * niveauDiff = 4; break; case R.id.dif5: niveauDiff =
    						 * 5; break; default: break; }
    						 */
    						// Log.i("checked ",
    						// String.valueOf(rgDifficulte.getCheckedRadioButtonId()));
    						// rbDifficulte = (RadioButton)
    						// findViewById(idSelectionne);
    						// niveauDiff =
    						// Integer.parseInt(rbDifficulte.getText().toString());
    						// Log.i("niveau diff :",rbDifficulte.getText().toString());
    						calendar = Calendar.getInstance();
    						FragMap.dateDuJour = calendar.getTime();
    						SimpleDateFormat sdf = new SimpleDateFormat(
    								"dd-MM-yyyy 'à' HH:mm:ss");
    						FragMap.ddj = sdf.format(FragMap.dateDuJour);
    						FragMap.save = true;
    						FragMap.REFRESH_LIST_RANDO = true;
    						
    			    		if(play_pause.getTitle().equals("Pause")){
    				        	item.setIcon(getResources().getDrawable(R.drawable.play1));
    				        	item.setTitle("Play");
    			    		}
    						enregistrer.setEnabled(false);
    						effacer.setEnabled(false);
    						FragMap.setEtatRandonnee("nonDemarrée");
    						
    					}
    				});

    		adb2.setNegativeButton("Annuler",
    				new DialogInterface.OnClickListener() {

    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						FragMap.save = false;
    					}
    				});
    		adSave = adb2.create();

    		adSave.setOnDismissListener(new OnDismissListener() {

    			@Override
    			public void onDismiss(DialogInterface dialog) {
    				// TODO Auto-generated method stub
    				if (FragMap.save == true) {
    					FragMap.saveRando.open();
    					// idRandoActuelle= RandoBDD.generateurId("rando");
    					rando = new Randonnee(FragMap.idRandoActuelle, nomRandoSave,
    							descRandoSave, FragMap.dureeRando, FragMap.ddj, niveauDiff,
    							FragMap.mesPoints);
    					Toast.makeText(getActivity().getBaseContext(),
    							"Votre randonnée a été sauvegardée avec succés.",
    							Toast.LENGTH_LONG).show();
    					FragMap.saveRando.insertRando(rando);
    					FragMap.saveRando.close();
    					FragMap.save = false;
    					FragMap.idRandoActuelle++; // Pour pouvoir sauvegarder d'autre rando
    										// sans redemarrer l'appli (à cause du
    										// PrimaryKey de la bdd)
    				}
    			}
    		});
    		
			adSave.show();

    		

			return true;
    	}
    	if (item.getItemId() == R.id.effacer){
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
	        //On instancie notre layout en tant que View
	        LayoutInflater factory = LayoutInflater.from(getActivity().getBaseContext());
	        final View alertDialogView = factory.inflate(
	        		android.R.layout.select_dialog_multichoice, null);
    		
			alert.setTitle("Effacer ?");
			alert.setIcon(android.R.drawable.ic_dialog_alert);
			alert.setMessage("Voulez vous vraiment effacer l'enregistrement en cours ?");
			alert.setPositiveButton("Oui", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
		    		if(play_pause.getTitle().equals("Pause")){
			        	play_pause.setIcon(getResources().getDrawable(R.drawable.play1));
			        	play_pause.setTitle("Play");
		    		}
					FragMap.map.clear();
					FragMap.ClickOnEffacer = true;
					enregistrer.setEnabled(false);
					effacer.setEnabled(false);
					FragMap.setEtatRandonnee("nonDemarrée");
				}
			});
			alert.setNegativeButton("Annuler", null);
			alert.show();	


			return true;
    	}

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

}

