package com.example.prueba2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 03-Jan-17.
 */
public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Model> ModelArrayList;

    public CustomAdapter(Context context, ArrayList<Model> ModelArrayList) {

        this.context = context;
        this.ModelArrayList = ModelArrayList;
    }

    public void remove(int position) {
        ModelArrayList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return ModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.items, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.tv);

            convertView.setTag(holder);

            holder.tvname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    holder.tvname.setBackgroundResource(R.color.colorPrimary);
                }
            });

        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(ModelArrayList.get(position).getName());
        //Asigno color azul a los primeros 20 items
        if (position < 20) {
            holder.tvname.setBackgroundResource(R.color.azul);
        }
        else
        {
            holder.tvname.setBackgroundResource(R.color.colorPrimary);
        }
        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        private ImageView iv;

    }

}