package fr.masterdapm.toulon.connexion;

import fr.masterdapm.toulon.MainActivity;
import fr.masterdapm.toulon.R;
import fr.masterdapm.toulon.fragment.FragMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ConnexionActivity extends Activity {
	
	public static String sPseudo;
	private int mIdSelectionneVueRando;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
		
		final RadioGroup rgChoixVue = (RadioGroup)findViewById(R.id.rgChoixVue);
		Button btValidateChoice =(Button) findViewById(R.id.btValidateChoice);
		
		btValidateChoice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText etPseudo = (EditText)findViewById(R.id.etReglagePseudo);
				sPseudo = etPseudo.getText().toString();
				if(!(sPseudo.isEmpty())){
					
					mIdSelectionneVueRando =  rgChoixVue.getCheckedRadioButtonId();
						
						switch (mIdSelectionneVueRando) {
						case R.id.rbSatView:
							FragMap.VUE_RANDO = "SatView";
							break;
						case R.id.rbStreetView:
							FragMap.VUE_RANDO = "StreetView";
							break;
						case R.id.rbTraficView:
							FragMap.VUE_RANDO = "TerrainView";
							break;
						default:
							break;
						}
						Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
						startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(),
							"Please enter your user name !",
							Toast.LENGTH_LONG)
							.show();
				}
			}
		});	
	}
}
