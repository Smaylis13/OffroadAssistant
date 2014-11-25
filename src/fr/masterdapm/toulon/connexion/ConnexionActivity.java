package fr.masterdapm.toulon.connexion;

import fr.masterdapm.toulon.MainActivity;
import fr.masterdapm.toulon.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ConnexionActivity extends Activity {
	private Button logIn;
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
    }
}
