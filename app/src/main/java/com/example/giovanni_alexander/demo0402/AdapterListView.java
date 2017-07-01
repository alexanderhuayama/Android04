package com.example.giovanni_alexander.demo0402;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Giovanni_Alexander on 30/06/2017.
 */

public class AdapterListView extends ArrayAdapter<Persona> {

    public AdapterListView(Context context) {
        super(context, 0, new ArrayList<Persona>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }

        TextView tvId, tvName, tvLastName, tvDocument, tvPhone, tvDisponible, tvAge;

        tvId = (TextView) convertView.findViewById(R.id.tvId);
        tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvLastName = (TextView) convertView.findViewById(R.id.tvLastName);
        tvDocument = (TextView) convertView.findViewById(R.id.tvDocument);
        tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
        tvDisponible = (TextView) convertView.findViewById(R.id.tvDisponible);
        tvAge = (TextView) convertView.findViewById(R.id.tvAge);

        Persona p = getItem(position);

        tvId.setText(p.getId());
        tvName.setText(p.getNombre());
        tvLastName.setText(p.getApellido());
        tvDocument.setText(p.getDocumento());
        tvPhone.setText(p.getTelefono());
        tvDisponible.setText(String.valueOf(p.isDisponible()));
        tvAge.setText(String.valueOf(p.getEdad()));

        return convertView;
    }
}
