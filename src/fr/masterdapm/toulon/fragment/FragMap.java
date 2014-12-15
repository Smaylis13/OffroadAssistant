package fr.masterdapm.toulon.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import fr.masterdapm.toulon.R;
import fr.masterdapm.toulon.sql.PointRando;
import fr.masterdapm.toulon.sql.RandoBDD;
import fr.masterdapm.toulon.sql.Randonnee;
import fr.masterdapm.toulon.util.AdapterMesRandos;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class FragMap extends Fragment implements LocationListener{
	private MapView mapView;
	public static GoogleMap map;
	private LocationManager locationManager;
    private double latitude ;
    private double longitude ;
    public static List<LatLng>  lListPoints=  new ArrayList<LatLng>();
    public static RandoBDD saveRando;
    private int idSelectionne;
	public static int idRandoActuelle;
    private Polyline mline;
    public static Boolean ClickOnEffacer = false;
	private PolylineOptions polyOpts;
	private HashMap<String, MarkerOptions> ensMarkerOpts;
	public static List<PointRando> mesPoints = new ArrayList<PointRando>();
	private View alertDialogView, alertDialogSaveView;
	// Création de l'AlertDialog
	private AlertDialog.Builder adb, adb2;
	// public String textWP;
	public Boolean placerWP = false;
	public AlertDialog ad, adSave;
	public static int chiffre = 0;
	private Button btPlay, btStop, btNew;
	private TextView tvTest;
	private static String EtatRandonnee = "nonDemarrée";
	public static String EtatBouton = "play";
	//public static String textWP;
	public static String ddj;
	public static long dureeRando = 0, chronoA, chronoB;
	public static Date dateDuJour;
	private EditText etNomRando, etDescRando;
	private RadioGroup rgDifficulte;
	private RadioButton rbDifficulte;
	private int niveauDiff = 5;
	public static Boolean save = false;
	private Randonnee rando;
	private String nomRandoSave, descRandoSave;
	public static Boolean REFRESH_LIST_RANDO = false;

	public static String TYPE_RANDO = "";
	public static String VUE_RANDO = "";





	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.carte_main, container, false);
		
		/* ===============DETERMINATION DE L'ID DE LA RANDO================ */
		saveRando = new RandoBDD(getActivity().getApplicationContext());
		saveRando.open();
		idRandoActuelle = RandoBDD.generateurId("rando");
		Log.i("idRANDOACTUEL", String.valueOf(idRandoActuelle));
		saveRando.close();
		
		// Gets the MapView from the XML layout and creates it
		mapView = (MapView) v.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		// Gets to GoogleMap from the MapView and does initialization stuff
		map = mapView.getMap();
		map.getUiSettings().setMyLocationButtonEnabled(true);//false
		map.setMyLocationEnabled(true);
		
		/**
		 * TYPE DE MAP
		 */
		if (VUE_RANDO.equals("SatView")) {
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			
		} else if (VUE_RANDO.equals("StreetView")) {
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		} else if (VUE_RANDO.equals("TerrainView")){
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		} 
		/*
		 * ====================CHARGEMENT DE LA MAP (Si c'est le choix de
		 * l'user)================
		 */
		if (AdapterMesRandos.LOAD_RANDO) {
			chargerRando(AdapterMesRandos.ID_RANDO_TO_LOAD);
			AdapterMesRandos.LOAD_RANDO = false;
		}
		if ((savedInstanceState != null)) {
			Toast.makeText(getActivity().getApplicationContext(), "ON saved:!! ", Toast.LENGTH_SHORT).show();
			ClickOnEffacer = savedInstanceState.getBoolean("New");
			if (ClickOnEffacer == false) {
				polyOpts = savedInstanceState.getParcelable("polylineOptions");

				ensMarkerOpts = (HashMap<String, MarkerOptions>) savedInstanceState
						.getSerializable("monEnsWP");
				Set<Map.Entry<String, MarkerOptions>> ensembleWP = ensMarkerOpts
						.entrySet();
				for (Map.Entry<String, MarkerOptions> m : ensembleWP) {
					map.addMarker(m.getValue());
				}
			}
		} else {
				polyOpts = new PolylineOptions();
				ensMarkerOpts = new HashMap<String, MarkerOptions>();
		}
		
		

/*-----------------------------------------------------------------------------------------------*/
		/*locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//Toast.makeText(getActivity().getApplicationContext(),"GPS (Y) ", Toast.LENGTH_SHORT).show();

        }
        // getting network status
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//Toast.makeText(getActivity().getApplicationContext(),"Network (Y) ", Toast.LENGTH_SHORT).show();
        }*/
       // mlocation= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      /*  mlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (mlocation != null) {
Toast.makeText(getActivity().getApplicationContext(),
		mlocation.getLatitude()+", "+mlocation.getLongitude(), Toast.LENGTH_LONG).show();
        }*/
        //mlocation = map.getMyLocation();
		MapsInitializer.initialize(this.getActivity());
		//map.animateCamera(CameraUpdateFactory.zoomTo( 13 ));


        map.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {			
			@Override
			public void onMyLocationChange(Location arg0) {
				// TODO Auto-generated method stub

			    map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(arg0
						.getLatitude(), arg0.getLongitude())));
				String typeItineraire = "itineraire";

		/*---------SI DÉMARRÉE ON AJOUTE LES LIGNE... -----------------*/
				if (getEtatRandonnee().equals("demarrée")) {
					//ClickOnEffacer = false;// On remet la variable Effacer a false pour que je
										// sache que c une nouvelle randonnée
					LatLng coor = new LatLng(arg0.getLatitude(),
							arg0.getLongitude());
					polyOpts.add(coor);
					if (mline != null) {
						mline.remove();
						mline = null;
					}
					mline = map.addPolyline(polyOpts);
					/*
					 * Je rajoute les points dans ma liste pour pouvoir la sauvegarder
					 * dans la BDD
					 */
					PointRando pointBDD = new PointRando(coor.latitude, coor.longitude,
							typeItineraire, idRandoActuelle, " ","",0);
					mesPoints.add(pointBDD);
				}
		    // Getting latitude of the current location
		    latitude = arg0.getLatitude();
		    // Getting longitude of the current location
		    longitude = arg0.getLongitude();
		    // Creating a LatLng object for the current location
		   // LatLng latLng = new LatLng(latitude, longitude);
		    //marker = map.addMarker(new MarkerOptions().position(latLng));
		    // Showing the current location in Google Map
		   // map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		    // Zoom in the Google Map
		    //map.animateCamera(CameraUpdateFactory.zoomTo(16));
		  /*  if (arg0.distanceTo(mlocation) > 1){
//Toast.makeText(getActivity().getApplicationContext(),"!"+arg0.distanceTo(location),Toast.LENGTH_LONG).show();
//Marker marker = map.addMarker(new MarkerOptions().position(latLng));
		    LatLng lastLatLng = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
		    Polyline line = map.addPolyline(new PolylineOptions()
		     .add(new LatLng(mlocation.getLatitude(),mlocation.getLongitude()),
		    		 new LatLng(latitude, longitude))
		     .width(20)
		     .color(Color.RED));
//markers.add(marker);
			mlocation = map.getMyLocation();
		    }*/	    
		    
		    
			}
		});

		/*=====================ON LONG CLICK =================*/
       
     // on click long
        map.setOnMapLongClickListener(new OnMapLongClickListener() {
     	   int index_couleur=1;
            final float[] tabcouleur2 ={
 								   BitmapDescriptorFactory.HUE_AZURE,	            
 								   BitmapDescriptorFactory.HUE_BLUE,	            
 								   BitmapDescriptorFactory.HUE_CYAN,
 								   BitmapDescriptorFactory.HUE_YELLOW,
 								   BitmapDescriptorFactory.HUE_GREEN,	      
 								   BitmapDescriptorFactory.HUE_MAGENTA,
 								   BitmapDescriptorFactory.HUE_ORANGE,
 								   BitmapDescriptorFactory.HUE_RED, 
 								   BitmapDescriptorFactory.HUE_ROSE,
 								   BitmapDescriptorFactory.HUE_VIOLET,
 								   };
 			@Override
 			public void onMapLongClick(final LatLng arg0) {
 				// TODO Auto-generated method stub
 				AlertDialog.Builder alert = new AlertDialog.Builder(
 	                    getActivity());
 		        //On instancie notre layout en tant que View
 		        LayoutInflater factory = LayoutInflater.from(getActivity().getBaseContext());
 		        final View alertDialogView = factory.inflate(R.layout.alert_dialog, null);
 		 
 		        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
 		        alert.setView(alertDialogView);
 		        
 				alert.setTitle("Ajouter un \"waypoint\"");
 				final Button button = (Button) alertDialogView.findViewById(R.id.couleur1);
 				final TextView input = (TextView) alertDialogView.findViewById(R.id.titre1);
 				final TextView description = (TextView) alertDialogView.findViewById(R.id.description1);
 				button.setOnClickListener(new OnClickListener() {					
 					@Override
 					public void onClick(View v) {
 						// TODO Auto-generated method stub
 						AlertDialog.Builder builderSingle = new AlertDialog.Builder(
 			                    getActivity());

                         final int[] tabCouleur = {
                         		Color.parseColor("#007FFF"),
                         		Color.BLUE,
                         		Color.CYAN,
                         		Color.YELLOW,
                         		Color.GREEN,
                         		Color.MAGENTA,
                         		Color.parseColor("#ED7F10"),
                         		Color.RED,
                         		Color.parseColor("#FD6C9E"),
                         		Color.parseColor("#660099"),
                         };
 			            builderSingle.setIcon(R.drawable.color4);
 			            builderSingle.setTitle("Sélectionner une couleur");
 			            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
 			                    getActivity(),
 			                    android.R.layout.select_dialog_singlechoice);
 			            arrayAdapter.add("Azur");
 			            arrayAdapter.add("Bleu");
 			            arrayAdapter.add("Cyan");
 			            arrayAdapter.add("Jaune");
 			            arrayAdapter.add("Vert");
 			            arrayAdapter.add("Magenta");
 			            arrayAdapter.add("Orange");
 			            arrayAdapter.add("Rouge");
 			            arrayAdapter.add("Rose");
 			            arrayAdapter.add("Violet");

 			            builderSingle.setNegativeButton("Annuler",
 			                    new DialogInterface.OnClickListener() {
 			                        @Override
 			                        public void onClick(DialogInterface dialog, int which) {
 			                            dialog.dismiss();
 			                        }
 			                    });

 			            builderSingle.setAdapter(arrayAdapter,
 			                    new DialogInterface.OnClickListener() {

 			                        @Override
 			                        public void onClick(DialogInterface dialog, int which) {
 			                        	index_couleur = which;
 			                            button.setTextColor(tabCouleur[which]);
 	
 			                        }
 			                    });
 			            builderSingle.show();
 					}
 				});
 				
 				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
 				public void onClick(DialogInterface dialog, int whichButton) {
 				 // String value = input.getText().toString();
 				  // Do something with value!
 					LatLng point = new LatLng(arg0.latitude, arg0.longitude);
 					
 					map.addMarker(new MarkerOptions()
 			        .title(input.getText().toString())
 			        .snippet(description.getText().toString())
 			        .position(point)
 			        .icon(BitmapDescriptorFactory.defaultMarker(tabcouleur2[index_couleur])));
 					
					ensMarkerOpts.put(
							String.valueOf(chiffre),
							new MarkerOptions()
							.title(input.getText().toString())
							   .snippet(description.getText().toString())
 			        .position(point)
 			        .icon(BitmapDescriptorFactory.defaultMarker(tabcouleur2[index_couleur])));
					chiffre++;;
					/*
					 * Je rajoute les points dans ma liste pour pouvoir
					 * la sauvegarder dans la BDD
					 */
					String typeWP = "waypoint";
					PointRando pointBDD = new PointRando(
							point.latitude, point.longitude, 
							typeWP,
							idRandoActuelle, input.getText().toString(),
							description.getText().toString(),
							tabcouleur2[index_couleur]);
					mesPoints.add(pointBDD);

 					
 				  }
 				});

 				alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
 				  public void onClick(DialogInterface dialog, int whichButton) {
 				    // Canceled.
 				  }
 				});
 				
 				alert.setIcon(R.drawable.adding);
 				
 				alert.show();
 				}
 			});
       
       /*======================FIN ON LONG CLICK MAP==========*/
		// Updates the location and zoom of the MapView
/*		LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
	   
		Criteria criteria = new Criteria();
	    Location location = locationManager
	    		.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
	    double latitude  =location.getLatitude();
	    double longitude =location.getLongitude();

		cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), zoom);
		map.animateCamera(cameraUpdate);*/
		
		return v;
	}
	
	

	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
	/*	Toast.makeText(getActivity().getApplicationContext(), "ON RES:!! ", Toast.LENGTH_SHORT).show();
		if (VUE_RANDO.equals("SatView")) {
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		} else if (VUE_RANDO.equals("StreetView")) {
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		} else {
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		}
		*/
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//mapView.onDestroy();
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}
	
	 @Override 
	 public void onLocationChanged(Location location) { 
/*Toast.makeText(getActivity().getApplicationContext(),"CHANGED (Y) ", Toast.LENGTH_SHORT).show();

	 int lat = (int) (location.getLatitude() * 1E6); 
	 
	 int lng = (int) (location.getLongitude() * 1E6); 
	 LatLng pos = new LatLng(lat, lng);
	map.addMarker(new MarkerOptions()
     .title("My position")
     .position(pos));*/

	 
	 }

	 
		public void chargerRando(int id) {
			map.clear();
			saveRando.open();
			Randonnee rand = saveRando.getRandoWithId(id);
			Toast.makeText(getActivity().getBaseContext(), String.valueOf(rand.getIdRando()),
					Toast.LENGTH_SHORT).show();
			List<PointRando> mesPoints = rand.getMesPoints();
			String typePoint;
			PolylineOptions polyOpts = new PolylineOptions();

			for (PointRando pr : mesPoints) {
				typePoint = pr.getTypePoint();
				LatLng pts = new LatLng(pr.getLatitude(), pr.getLongitude());
				Log.i("*************CHARGER RANDO ", typePoint);
				if (typePoint.equals("waypoint")) {
					// Toast.makeText(getBaseContext(),
					// "Je suis dans la boucle point", Toast.LENGTH_SHORT).show();
					Log.i("*************CHARGER RANDO TEXT WP", pr.getTextWP());
					map.addMarker(new MarkerOptions().title(pr.getTextWP())
							.position(pts)
							.snippet(pr.getDescription())
							.icon(BitmapDescriptorFactory.defaultMarker(pr.getCouleur()))
							);
					
				} else if (typePoint.equals("itineraire")) {
					// Toast.makeText(getBaseContext(),
					// "Je suis dans la boucle itineraire",
					// Toast.LENGTH_SHORT).show();
					polyOpts.add(pts);
					if (mline != null) {
						mline.remove();
						mline = null;
					}
					mline = map.addPolyline(polyOpts);
				}
			}
			saveRando.close();
		}
		public static long modulo(long m, long n) {
			long mod = m % n;
			return (mod < 0) ? mod + n : mod;
		}

		public static String milliToDuree(long dureeMilli) {
			long ms = modulo(dureeMilli, 1000);
			long s = modulo((dureeMilli / 1000), 60);
			long m = modulo((dureeMilli / 60000), 60);
			long h = dureeMilli / 3600000;
			return h + "h" + m + "m" + s + "s" + ms + "ms";
		}



		public static String getEtatRandonnee() {
			return EtatRandonnee;
		}



		public static void setEtatRandonnee(String etatRandonnee) {
			EtatRandonnee = etatRandonnee;
		}
		@Override
		public void onSaveInstanceState(Bundle outState) {
			// TODO Auto-generated method stub
			outState.putParcelable("polylineOptions", polyOpts);
			outState.putSerializable("monEnsWP", ensMarkerOpts);
			outState.putString("saveEtatBouton", EtatBouton);
			outState.putString("saveEtatRando", EtatRandonnee);
			outState.putLong("dureeRando", dureeRando);
			outState.putBoolean("New", ClickOnEffacer);
			super.onSaveInstanceState(outState);
		}
}