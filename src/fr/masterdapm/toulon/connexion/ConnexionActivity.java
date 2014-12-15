package fr.masterdapm.toulon.connexion;

import fr.masterdapm.toulon.MainActivity;
import fr.masterdapm.toulon.R;
import fr.masterdapm.toulon.fragment.FragMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class ConnexionActivity extends Activity {
	
	private int idSelectionneTypeRando, idSelectionneVueRando;
	private RadioGroup rgChoixTypeRando, rgChoixVue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
		//TextView tvPseudo = (TextView)findViewById(R.id.tvReglagePseudo);
		EditText etPseudo = (EditText)findViewById(R.id.etReglagePseudo);
		final RadioGroup rgChoixTypeRando = (RadioGroup)findViewById(R.id.rgChoixTypeRando);
		final RadioGroup rgChoixVue = (RadioGroup)findViewById(R.id.rgChoixVue);
		Button btValidateChoice =(Button) findViewById(R.id.btValidateChoice);
		
		btValidateChoice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				idSelectionneTypeRando =  rgChoixTypeRando.getCheckedRadioButtonId();
				 idSelectionneVueRando =  rgChoixVue.getCheckedRadioButtonId();
				 switch (idSelectionneTypeRando) {
					case R.id.rb4x4:
						FragMap.TYPE_RANDO = "mode4x4";
						break;
					case R.id.rbPied:
						FragMap.TYPE_RANDO = "modePedestre";
						break;
					default:
						break;
					}
					
					switch (idSelectionneVueRando) {
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
			}
		});
		
		
		
	}
	
	
	/* Connexion Activity
	 * private Button logIn;
	private Button createAccount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        logIn = (Button) findViewById(R.id.connect);
        createAccount = (Button) findViewById(R.id.create_account);
        
        //Action sur le bouton connexion
        logIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
    }*/
}
