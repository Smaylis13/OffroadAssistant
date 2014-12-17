package fr.masterdapm.toulon.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
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
import fr.masterdapm.toulon.SmsReceiver;
import fr.masterdapm.toulon.item.DrawerItemCustomAdapter;
import fr.masterdapm.toulon.item.ObjectDrawerItem;
import fr.masterdapm.toulon.sql.PointRando;
import fr.masterdapm.toulon.sql.RandoBDD;
import fr.masterdapm.toulon.sql.Randonnee;
import fr.masterdapm.toulon.util.AdapterMesRandos;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
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
   // private double latitude ;
   // private double longitude ;
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

	//public static Boolean SMS_R_WAYPOINT = false;
	public static boolean SMS_R_WAYPOINT = false; 



	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		final View v = inflater.inflate(R.layout.carte_main, container, false);
		
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

		MapsInitializer.initialize(this.getActivity());
		//map.animateCamera(CameraUpdateFactory.zoomTo( 13 ));

/*===================================ON MY LOCATION CHANGED ===============================================*/
        map.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {			
			@Override
			public void onMyLocationChange(Location arg0) {
				// TODO Auto-generated method stub
				
				if(SMS_R_WAYPOINT){
					AlertDialog.Builder builderSingle = new AlertDialog.Builder(
	 	                    getActivity());

				    builderSingle.setIcon(R.drawable.send1);
				    builderSingle.setTitle("Message \"WayPoint\"");
				    String lMsgWayPoint = "Vous avez reçu un \"WayPoint\" de la part de "+ SmsReceiver.mPhoneNumber;
				    builderSingle.setMessage(lMsgWayPoint);
				    builderSingle.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							double lat = Double.parseDouble(SmsReceiver.mMsgWayPoint[1]);
							double lng = Double.parseDouble(SmsReceiver.mMsgWayPoint[2]);
							
							LatLng latLng = new LatLng(lat,lng);
							MarkerOptions marker = new MarkerOptions().position(latLng);
							
							marker.title(SmsReceiver.mMsgWayPoint[3]);
							marker.snippet(SmsReceiver.mMsgWayPoint[4]);
				
							
							map.addMarker(marker);
							Toast.makeText(getActivity(), "\"WayPoint\" ajouté avec succès!", Toast.LENGTH_SHORT).show();
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
					
				    SMS_R_WAYPOINT = false;
				}
				
				
			    map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(arg0
						.getLatitude(), arg0.getLongitude())));
				String typeItineraire = "itineraire";

		/*---------SI DÉMARRÉE ON AJOUTE LES LIGNE... -----------------*/
				if (getEtatRandonnee().equals("demarrée")) {
					ClickOnEffacer = false;// On remet la variable Effacer a false pour que je
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
		  //  latitude = arg0.getLatitude();
		    // Getting longitude of the current location
		   // longitude = arg0.getLongitude();

			}
		});
        
        /*================ON Click WAY POINT=============================*/
        
        map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(final Marker arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builderSingle = new AlertDialog.Builder(
		                    getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);

		            builderSingle.setIcon(R.drawable.configway);
		            builderSingle.setTitle("Configuration");
		            
		            List<ObjectDrawerItem> rowItems = new ArrayList<ObjectDrawerItem>();
		            
		            rowItems.add(new ObjectDrawerItem(R.drawable.send1,"Envoyer par sms"));
		            rowItems.add(new ObjectDrawerItem(R.drawable.suppway,"Supprimer"));
		            
		            DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(
		                    v.getContext(),
		                    R.layout.listview_item_row, rowItems);

		            builderSingle.setNegativeButton("Annuler",
		                    new DialogInterface.OnClickListener() {
		                        @Override
		                        public void onClick(DialogInterface dialog, int which) {
		                            dialog.dismiss();
		                        }
		                    });
		            /*********************ON CLICK SUPPRIMER/ENVOYER PAR SMS******************/
		            builderSingle.setAdapter(adapter,
		                    new DialogInterface.OnClickListener() {

		                        @Override
		                        public void onClick(DialogInterface dialog, int which) {
		                        	switch(which){
		                        	case 0:
		                        		double lat = arg0.getPosition().latitude;
		                        		double lng = arg0.getPosition().longitude;
		                        		String title = arg0.getTitle();
		                        		String desc = arg0.getSnippet();
		                        		String messageBody = SmsReceiver.EN_TETE_WAYPOINT 
		                        				+";"+lat+";"+lng+";"+title+";"+desc+";";
		                        		SmsManager smsManager = SmsManager.getDefault();
		                        		smsManager.sendTextMessage("+33651271872", null, messageBody, null, null);
		    							Toast.makeText(getActivity(), "\"WayPoint\" envoyé avec succès!", Toast.LENGTH_SHORT).show();
		                        		break;
		                        	case 1:
		                        		for (Entry<String, MarkerOptions> e : ensMarkerOpts.entrySet()) {
		                        		    String key = e.getKey();
		                        		    MarkerOptions value = e.getValue();
		                        		    if (value.getPosition().equals(arg0.getPosition())){
		                        		    	ensMarkerOpts.remove(key);
		                        		    }
		                        		}		                        		
		                        		arg0.remove();
		                        		break;
		                        	}

		                        }
		                    });
		            builderSingle.setInverseBackgroundForced(false);
		            builderSingle.show();
		        /*************************    
 				AlertDialog.Builder alert = new AlertDialog.Builder(
 	                    getActivity());
 		        LayoutInflater factory = LayoutInflater.from(getActivity().getBaseContext());
				// final View alertDialogView = factory.inflate(R.layout.listview_item_row, null);
				    String[] listContent = {"January","December"};
				   ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
				        android.R.layout.simple_list_item_1,  listContent);
				 
				   alert.setAdapter(adapter, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
	 		        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
	 		       // alert.setView(alertDialogView);
	 		        
	 				alert.setTitle("Envoyer un \"waypoint\"");
	 				final Button button = (Button) alertDialogView.findViewById(R.id.couleur1);
	 				final TextView input = (TextView) alertDialogView.findViewById(R.id.titre1);
	 				alert.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
	 	 				public void onClick(DialogInterface dialog, int whichButton) {

	 	 						 	 					
	 	 				  }
	 	 			});

	 				alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
	 	 				  public void onClick(DialogInterface dialog, int whichButton) {
	 	 				    // Canceled.
	 	 				  }
	 	 			});
	 	 				
	 	 			alert.setIcon(R.drawable.send1);
	 	 			alert.show();*/
			}
		});
        
		/*=====================ON LONG CLICK =================*/
       
     // on click long
        map.setOnMapLongClickListener(new OnMapLongClickListener() {
     	   int index_couleur=8;
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