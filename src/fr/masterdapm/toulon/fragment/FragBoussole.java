package fr.masterdapm.toulon.fragment;

import fr.masterdapm.toulon.R;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragBoussole extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_invit,container, false);
		
    	Intent intent = new Intent(getActivity().getApplicationContext(),Boussole.class);
		startActivity(intent);
		   
		return v;
	}


}
