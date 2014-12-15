package fr.masterdapm.toulon.fragment;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import fr.masterdapm.toulon.R;
import fr.masterdapm.toulon.sql.RandoBDD;
public class FragContacts extends Fragment {

	private GoogleMap map;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_main,container, false);
	   // RandoBDD saveRando = new RandoBDD(getActivity());
	  //  saveRando.open();
	   // map =   ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
	    //map =((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
	    return rootView;
	}


}