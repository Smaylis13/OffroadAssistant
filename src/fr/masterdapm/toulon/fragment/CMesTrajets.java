package fr.masterdapm.toulon.fragment;

import java.util.ArrayList;

import fr.masterdapm.toulon.sql.RandoBDD;
import fr.masterdapm.toulon.sql.Randonnee;
import fr.masterdapm.toulon.util.AdapterMesRandos;
import fr.masterdapm.toulon.R;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CMesTrajets extends ListFragment{
		private ArrayList<Randonnee> listRando = new ArrayList<Randonnee>();
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
					Toast.makeText(v.getContext(), "REFRESH",
							Toast.LENGTH_SHORT).show();
				}
			});
			return v;	
		}
		
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			Toast.makeText(getActivity().getBaseContext(), "click on item", Toast.LENGTH_SHORT)
					.show();
		}
		public void callOnResume() {
			onResume();
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
		}
		
}


