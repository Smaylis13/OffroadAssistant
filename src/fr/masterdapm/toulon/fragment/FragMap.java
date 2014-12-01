package fr.masterdapm.toulon.fragment;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;

import fr.masterdapm.toulon.MainActivity;
import fr.masterdapm.toulon.R;
import fr.masterdapm.toulon.R.color;
import fr.masterdapm.toulon.couleur.ColorPickerDialog;
import fr.masterdapm.toulon.couleur.ColorPickerDialog.OnColorChangedListener;


public class FragMap extends Fragment {
	private MapView mapView;
	private GoogleMap map;
	private Location mCurrentLocation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.carte_main, container, false);
		
		// Gets the MapView from the XML layout and creates it
		mapView = (MapView) v.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		// Gets to GoogleMap from the MapView and does initialization stuff
		map = mapView.getMap();
		map.getUiSettings().setMyLocationButtonEnabled(false);
		map.setMyLocationEnabled(true);
       LatLng sydney = new LatLng(-33.867, 151.206);

        
		
		MapsInitializer.initialize(this.getActivity());
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
		map.addMarker(new MarkerOptions()
        .title("Sydney")
        .position(sydney));
	/*	
		LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

		Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		double latitude  =location.getLatitude();
		double longitude =location.getLongitude();
		LatLng posActu = new LatLng(latitude, longitude);
		map.addMarker(new MarkerOptions()
        .title("Moi")
        .position(posActu));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(posActu, 12));
		*/
       map.setOnMapLongClickListener(new OnMapLongClickListener() {
    	   private int index_couleur=1;
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
				button.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder builderSingle = new AlertDialog.Builder(
			                    getActivity());
						
                        final int[] tabCouleur = {
                        		Color.WHITE,
                        		Color.BLUE,
                        		Color.GRAY,
                        		Color.YELLOW,
                        		Color.parseColor("#582900"),
                        		Color.BLACK,
                        		Color.parseColor("#ED7F10"),
                        		Color.MAGENTA,
                        		Color.RED,
                        		Color.GREEN,
                        		Color.parseColor("#660099"),
                        };
			            builderSingle.setIcon(R.drawable.color4);
			            builderSingle.setTitle("Sélectionner une couleur");
			            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
			                    getActivity(),
			                    android.R.layout.select_dialog_singlechoice);
			            arrayAdapter.add("Blanc");
			            arrayAdapter.add("Bleu");
			            arrayAdapter.add("Gris");
			            arrayAdapter.add("Jaune");
			            arrayAdapter.add("Marron");
			            arrayAdapter.add("Noir");
			            arrayAdapter.add("Orange");
			            arrayAdapter.add("Rose");
			            arrayAdapter.add("Rouge");
			            arrayAdapter.add("Vert");
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
				  //String value = input.getText().toString();
				  // Do something with value!
					LatLng posActu = new LatLng(arg0.latitude, arg0.longitude);
					map.addMarker(new MarkerOptions()
			        .title("Moi")
			        .position(posActu));
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
	//	zoom = map.getCameraPosition().zoom;
		//Toast.makeText(getActivity().getApplicationContext(), zoom+"! ", Toast.LENGTH_SHORT).show();

		super.onResume();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}
} 