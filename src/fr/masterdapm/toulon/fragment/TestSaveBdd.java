package fr.masterdapm.toulon.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import fr.masterdapm.toulon.sql.*;

public class TestSaveBdd extends Activity {
	/*private EditText etNom, etDesc, etDiff;
	private TextView tvResult;
	private Button bt;
	private RandoBDD randoBDD;
	private List<PointRando> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.l_testsaverando);

		etNom = (EditText) findViewById(R.id.et1TestSave);
		etDesc = (EditText) findViewById(R.id.et2TestSave);
		etDiff = (EditText) findViewById(R.id.et3TestSave);
		tvResult = (TextView) findViewById(R.id.tv4TestSave);

		bt = (Button) findViewById(R.id.btTestSave);

		randoBDD = new RandoBDD(this);

		list = new ArrayList<PointRando>();
		double d1 = 12.34567;
		double d2 = 567.89;
		double d3 = 25.834567;
		double d4 = 123.567;
		PointRando p1 = new PointRando(d1, d2, "waypoint");
		PointRando p2 = new PointRando(d3, d4, "itineraire");
	//	list.add(p1);
		//list.add(p2);

		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				randoBDD.open();
				long duree = 12345;
				Randonnee r = new Randonnee(RandoBDD.generateurId("rando"),etNom.getText().toString(), etDesc
						.getText().toString(), duree, "13-12-2014",Integer.parseInt(etDiff
						.getText().toString()),list);
				randoBDD.insertRando(r);

				Randonnee resultRando = randoBDD.getRandoWithNom(etNom.getText().toString());
				tvResult.setText(resultRando.toString());
			}
		});
		

	}*/
}
