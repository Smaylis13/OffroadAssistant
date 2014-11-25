package fr.masterdapm.toulon.item;

import java.util.List;

import fr.masterdapm.toulon.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {
	 
    Context mContext;
    int layoutResourceId;
    List<ObjectDrawerItem> data = null;
 
    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, List<ObjectDrawerItem> data) {
 
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ObjectDrawerItem rowItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item_row, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textViewName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
                 
        holder.txtTitle.setText(rowItem.getName());
        holder.imageView.setImageResource(rowItem.getIcon());
         
        return convertView;
    }

 
}