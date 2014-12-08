package fr.masterdapm.toulon.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import fr.masterdapm.toulon.R;


public class FragMap extends Fragment implements LocationListener{
	private MapView mapView;
	private GoogleMap map;
	private LocationManager locationManager;
	private Location location;
	private LatLng myPos;
	private boolean b=true;
    private double latitude ;
    private double longitude ;
    private Marker marker;
    private List<Marker> markers = new ArrayList<Marker>();

	// inside loop:
//	Marker marker = myMap.addMarker(new MarkerOptions().position(new LatLng(geo1Dub,geo2Dub)));
//	markers.add(marker);

	// after loop:
//	markers.size();



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.carte_main, container, false);
		
		// Gets the MapView from the XML layout and creates it
		mapView = (MapView) v.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		
		// Gets to GoogleMap from the MapView and does initialization stuff
		map = mapView.getMap();
		map.getUiSettings().setMyLocationButtonEnabled(true);//false
		map.setMyLocationEnabled(true);
		locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//Toast.makeText(getActivity().getApplicationContext(),"GPS (Y) ", Toast.LENGTH_SHORT).show();

        }
        // getting network status
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//Toast.makeText(getActivity().getApplicationContext(),"Network (Y) ", Toast.LENGTH_SHORT).show();
        }
       // location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        /*if (location != null) {
Toast.makeText(getActivity().getApplicationContext(),"GPS (Y) ", Toast.LENGTH_LONG).show();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }*/
		MapsInitializer.initialize(this.getActivity());
        //final MarkerOptions m = new MarkerOptions().title("My position");
		
        map.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
			
			@Override
			public void onMyLocationChange(Location arg0) {
				// TODO Auto-generated method stub
			    if(marker != null){
	                marker.remove();
	            }
 
		    // Getting latitude of the current location
		    latitude = arg0.getLatitude();
		    // Getting longitude of the current location
		    longitude = arg0.getLongitude();
		    // Creating a LatLng object for the current location
		    LatLng latLng = new LatLng(latitude, longitude);
		    marker = map.addMarker(new MarkerOptions().position(latLng));
		    // Showing the current location in Google Map
		   // map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		    // Zoom in the Google Map
		    //map.animateCamera(CameraUpdateFactory.zoomTo(16));
		    
		    if (arg0.distanceTo(location) > 1){
//Toast.makeText(getActivity().getApplicationContext(),"!"+arg0.distanceTo(location),Toast.LENGTH_LONG).show();
//Marker marker = map.addMarker(new MarkerOptions().position(latLng));
		    Polyline line = map.addPolyline(new PolylineOptions()
		     .add(new LatLng(location.getAltitude(),location.getLongitude()),
		    		 new LatLng(arg0.getAltitude(),arg0.getLongitude()))
		     .width(5)
		     .color(Color.RED));
//markers.add(marker);
location = map.getMyLocation();
		    }
			}
		});

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
					LatLng posActu = new LatLng(arg0.latitude, arg0.longitude);
					
					Marker marker=map.addMarker(new MarkerOptions()
			        .title(input.getText().toString())
			        .snippet(description.getText().toString())
			        .position(posActu)
			        .icon(BitmapDescriptorFactory.defaultMarker(tabcouleur2[index_couleur])));
					markers.add(marker);
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
	 @Override 
	 public void onLocationChanged(Location location) { 
Toast.makeText(getActivity().getApplicationContext(),"CHANGED (Y) ", Toast.LENGTH_SHORT).show();

	 int lat = (int) (location.getLatitude() * 1E6); 
	 
	 int lng = (int) (location.getLongitude() * 1E6); 
	 LatLng pos = new LatLng(lat, lng);
	map.addMarker(new MarkerOptions()
     .title("My position")
     .position(pos));

	 
	 } 

} 