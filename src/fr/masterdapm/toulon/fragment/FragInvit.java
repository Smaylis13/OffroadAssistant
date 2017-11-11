package fr.masterdapm.toulon.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.masterdapm.toulon.R;



public class FragInvit extends Fragment { 

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
				View rootView = inflater.inflate(R.layout.fragment_invit,container, false);
				/* cf MainActivity */
				return rootView;
		}
}
