package fr.masterdapm.toulon.util;


import java.util.List;
import fr.masterdapm.toulon.R;
import fr.masterdapm.toulon.fragment.CMesTrajets;
import fr.masterdapm.toulon.sql.Randonnee;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AdapterMesRandos extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Randonnee> mListRando;
	
	private static Boolean FLAG_BT_DELETE = false;
	public static int FLAG_ID_TO_DELETE;
	public static int ID_RANDO_TO_LOAD;
	public static Boolean LOAD_RANDO = false;
	//private RandoBDD accessBDD;
	
	public AdapterMesRandos(Context pcontext, List<Randonnee> pListRando) {
		
		mInflater = LayoutInflater.from(pcontext);
		mListRando = pListRando;
	}

	@Override
	public int getCount() {
		return mListRando.size();
	}

	@Override
	public Object getItem(int position) {
		return mListRando.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.l_listviewperso, null);
			holder = new ViewHolder();
			holder.tvNom = (TextView) convertView.findViewById(R.id.nomRandoLV);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.descRandoLV);
			holder.tvDuree = (TextView) convertView.findViewById(R.id.dureeRandoLV);
			holder.tvDiff = (TextView) convertView.findViewById(R.id.diffRandoLV);
			holder.tvDate = (TextView) convertView.findViewById(R.id.dateRandoLV);
			holder.tvId = (TextView) convertView.findViewById(R.id.idRandoLV);
			holder.btDelete = (Button) convertView.findViewById(R.id.btDelete);
			holder.btLoad = (Button) convertView.findViewById(R.id.btLoad);

			convertView.setTag(holder); 
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvNom.setText("Nom : "+mListRando.get(position).getNomRando());
		holder.tvDesc.setText("Description : "+mListRando.get(position).getDescRando());
		holder.tvDuree.setText("Durée : "+milliToDuree(mListRando.get(position).getDureeRando()));
		holder.tvDiff.setText("Difficulté : "+String.valueOf(mListRando.get(position).getDiffRando()));
		holder.tvDate.setText("Date : "+mListRando.get(position).getDateRando());
		holder.tvId.setText("Id : "+String.valueOf(mListRando.get(position).getIdRando()));
		holder.btDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				FLAG_BT_DELETE = true;
				FLAG_ID_TO_DELETE = mListRando.get(position).getIdRando();
				holder.tvNom.setTextColor(Color.RED);
				AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
				alert.setTitle("Effacer ?");
				alert.setIcon(android.R.drawable.ic_dialog_alert);
				alert.setMessage("Désirez vous vraiment effacer cet enregistrement ?");
				alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						CMesTrajets.accessBDD.open();
						CMesTrajets.accessBDD.removeRandoWithId(mListRando.get(position).getIdRando());
						CMesTrajets.accessBDD.close();
					}
				});// fin OUi
				alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						holder.tvNom.setTextColor(Color.BLACK);
						FLAG_BT_DELETE = false;
					}
				});// fin annuler
				alert.show();	

			}// fin on click
		});// fin setOnClick...

		holder.btLoad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (LOAD_RANDO){// == true 
					holder.tvNom.setTextColor(Color.BLACK);
					LOAD_RANDO = false;
				}else{
					holder.tvNom.setTextColor(Color.GREEN);
					LOAD_RANDO = true;
					ID_RANDO_TO_LOAD = mListRando.get(position).getIdRando();
				}

			}
		});
	return convertView;
	}
	
	public static long modulo(long m, long n) {
		long mod = m % n;
		return (mod < 0) ? mod + n : mod;
	}
	
	public static String milliToDuree(long dureeMilli) {
		long ms = modulo(dureeMilli, 1000);
		long s = modulo((dureeMilli / 1000), 60);
		long m = modulo((dureeMilli / 60000), 60);
		long h = dureeMilli / 3600000;
		return h + "h" + m + "m" + s + "s" ;//+ ms + "ms";
	}
	
	class ViewHolder {

		public TextView tvNom;
		public TextView tvDesc;
		public TextView tvDuree;
		public TextView tvDiff;
		public TextView tvDate;
		public TextView tvId;
		public Button btDelete;
		public Button btLoad;
	}
}
