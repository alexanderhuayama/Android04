package com.example.giovanni_alexander.demo0402;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_ADD = 1;
    private static final int REQ_CODE_UPDATE = 2;
    private Button btnAdd;
    TextView tvPersonas;
    List<Persona> lstPersona;
    private AdapterListView adapterListView;
    ListView lvPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creación de views
        //tvPersonas = (TextView) findViewById(R.id.tvPersonas);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        lvPersonas = (ListView)findViewById(R.id.lvPersonas);

        //////////////////////////
        adapterListView = new AdapterListView(this);
        lvPersonas.setAdapter(adapterListView);

        // seteando eventos a los botones
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, REQ_CODE_ADD);
            }
        });

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            dataBaseHelper.createDataBase();
            cargarLista();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //////////////////////////////////
        lvPersonas.setOnItemClickListener(adaptadorOnItemClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_ADD){
            if (resultCode ==RESULT_OK){
                cargarLista();
            }
        }

        if (requestCode == REQ_CODE_UPDATE){
            if (resultCode ==RESULT_OK){
                cargarLista();
            }
        }
    }

    private void cargarLista() {
        //tvPersonas.setText("");
        lvPersonas = (ListView)findViewById(R.id.lvPersonas);
        adapterListView = new AdapterListView(MainActivity.this);
        lvPersonas.setAdapter(adapterListView);
        lstPersona = new PersonaDAO(MainActivity.this).listarPersonas();
        //adapterListView.addAll(lstPersona);
        for (Persona p : lstPersona){
            adapterListView.add(p);
        }


        /*
        for (Persona p : lstPersona) {
            tvPersonas.append("Id : " + p.getId() + "\n");
            tvPersonas.append("Nombre : " + p.getNombre() + "\n");
            tvPersonas.append("Apellido : " + p.getApellido() + "\n");
            tvPersonas.append("Edad : " + p.getEdad() + "\n");
            tvPersonas.append("Documento : " + p.getDocumento() + "\n");
            tvPersonas.append("Telefono : " + p.getTelefono() + "\n");
            tvPersonas.append("Disponible : " + p.isDisponible() + "\n" + "\n");
        }
        */
    }

    private final AdapterView.OnItemClickListener adaptadorOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, int position, long id) {
            final CharSequence dialogItem[] = {"Editar", "Eliminar"};
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0){
                        TextView tvId = (TextView)view.findViewById(R.id.tvId),
                                tvName = (TextView)view.findViewById(R.id.tvName),
                                tvLastName = (TextView)view.findViewById(R.id.tvLastName),
                                tvAge = (TextView)view.findViewById(R.id.tvAge),
                                tvDocument = (TextView)view.findViewById(R.id.tvDocument),
                                tvPhone = (TextView)view.findViewById(R.id.tvPhone),
                                tvDisponible = (TextView)view.findViewById(R.id.tvDisponible);
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        intent.putExtra("id", tvId.getText().toString());
                        intent.putExtra("name", tvName.getText().toString());
                        intent.putExtra("lastname", tvLastName.getText().toString());
                        intent.putExtra("age", tvAge.getText().toString());
                        intent.putExtra("document", tvDocument.getText().toString());
                        intent.putExtra("phone", tvPhone.getText().toString());
                        intent.putExtra("disponible", tvDisponible.getText().toString());
                        startActivityForResult(intent, REQ_CODE_UPDATE);
                    }
                    if (which == 1){
                        TextView tvId = (TextView)view.findViewById(R.id.tvId);
                        Persona p = new Persona();
                        p.setId(tvId.getText().toString());
                        new PersonaDAO(MainActivity.this).eliminarPersona(p);
                        cargarLista();
                    }
                }
            }).show();
        }
    };
}
