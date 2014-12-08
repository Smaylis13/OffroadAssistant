package fr.masterdapm.toulon.fragment;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import fr.masterdapm.toulon.sql.CDBTrajet;
import fr.masterdapm.toulon.R;

import android.app.Fragment;
import android.app.ListActivity;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class CMesTrajets extends ListFragment{
	private CDBTrajet mDb;
	public static final LatLng[] TAB_TRAJET =null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list_trajet,container, false);
		

		
	    ArrayAdapter<LatLng> adapter = new ArrayAdapter<LatLng>(inflater.getContext(),
	    		android.R.layout.simple_list_item_1,TAB_TRAJET);
	    
	    /** Setting the list adapter for the ListFragment */
	    setListAdapter(adapter);

	return rootView;
}
	
    
    @Override
	public void onDestroy() {
    	mDb.close();
    	super.onDestroy();
    }


	
}


