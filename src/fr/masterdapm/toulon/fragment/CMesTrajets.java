package fr.masterdapm.toulon.fragment;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import fr.masterdapm.toulon.sql.PointRando;
import fr.masterdapm.toulon.sql.RandoBDD;
import fr.masterdapm.toulon.sql.Randonnee;
import fr.masterdapm.toulon.util.AdapterMesRandos;
import fr.masterdapm.toulon.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CMesTrajets extends ListFragment{
		private ArrayList<Randonnee> listRando = new ArrayList<Randonnee>();
		private Randonnee r;
		private TextView tvRefresh, tv;
		public static RandoBDD accessBDD; 


		@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.l_mesrandos,container, false);

			
			accessBDD = new RandoBDD(getActivity().getApplicationContext());
			accessBDD.open();

			listRando = accessBDD.getAllRandos();
			accessBDD.close();

			final ListView listV = (ListView) v.findViewById(android.R.id.list);
			
			
			//TEXT POUR INFORMER
			//tvRefresh = (TextView) v.findViewById(R.id.TVrefresh);
			//tvRefresh.setTextColor(Color.GREEN);
			if (listRando != null){
				AdapterMesRandos ad = new AdapterMesRandos(v.getContext(), listRando);
				listV.setAdapter(ad);	
			}

			 // BUTTON REFRESH
			Button btn = (Button) v.findViewById(R.id.btRefresh);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//callOnResume();
					//mode("paysage");
					
					//onDetach();
					//onAttach(getActivity());
					Toast.makeText(v.getContext(), "onFFResume()",
							Toast.LENGTH_SHORT).show();
				}
			});
			return v;
			
			
		}
		
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			//if (AdapterMesRandos.FLAG_BT_DELETE) {
				//Log.i("getitem",String.valueOf(ad.getItem(AdapterMesRandos.FLAG_ID_TO_DELETE).toString()));
				/*accessBDD.open();
				accessBDD.removeRandoWithId(AdapterMesRandos.FLAG_ID_TO_DELETE);
				accessBDD.close();
				AdapterMesRandos.FLAG_BT_DELETE = false;
				*/// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				//mode("portrait");
			//}
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			Toast.makeText(getActivity().getBaseContext(), "click on item", Toast.LENGTH_SHORT)
					.show();
		}

		/*public void mode(String type) {
			if (type == "paysage") {
				getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else {
				getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		}*/

		public void callOnResume() {
			onResume();
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
		//	if (AdapterMesRandos.FLAG_BT_DELETE) {
				// Log.i("getitem",String.valueOf(ad.getItem(AdapterMesRandos.FLAG_ID_TO_DELETE).toString()));
				accessBDD.open();
			//	accessBDD.removeRandoWithId(AdapterMesRandos.FLAG_ID_TO_DELETE);
				accessBDD.close();
				//AdapterMesRandos.FLAG_BT_DELETE = false;
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				//mode("portrait");
		//	}
		}
		
}


