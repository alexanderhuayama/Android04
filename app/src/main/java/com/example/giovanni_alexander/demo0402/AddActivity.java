package com.example.giovanni_alexander.demo0402;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Giovanni_Alexander on 30/06/2017.
 */

public class AddActivity extends AppCompatActivity {
    private EditText etId, etName, etLastName, etAge, etDocument, etPhone, etDisponible;
    private Button btnCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etId = (EditText) findViewById(R.id.etId);
        etId.setEnabled(false);
        etId.setFocusable(false);

        etName = (EditText) findViewById(R.id.etName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etAge = (EditText) findViewById(R.id.etAge);
        etDocument = (EditText) findViewById(R.id.etDocument);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etDisponible = (EditText) findViewById(R.id.etDisponible);
        btnCreate = (Button) findViewById(R.id.btnCreate);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            etId.setText((String) bundle.get("id"));
            etName.setText((String) bundle.get("name"));
            etLastName.setText((String) bundle.get("lastname"));
            etAge.setText((String) bundle.get("age"));
            etDocument.setText((String) bundle.get("document"));
            etPhone.setText((String) bundle.get("phone"));
            etDisponible.setText((String) bundle.get("disponible"));
            btnCreate.setText("Actualizar");
        }

        // setear evento al boton
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    Persona p = new Persona();
                    p.setId(etId.getText().toString());
                    p.setNombre(etName.getText().toString());
                    p.setApellido(etLastName.getText().toString());
                    p.setEdad(Integer.parseInt(etAge.getText().toString()));
                    p.setDocumento(etDocument.getText().toString());
                    p.setTelefono(etPhone.getText().toString());
                    p.setDisponible(Boolean.parseBoolean(etDisponible.getText().toString()));

                    if (btnCreate.getText().toString().toUpperCase().equals("ACTUALIZAR")) {
                        new PersonaDAO(AddActivity.this).actualizarPersona(p);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        long filasAfectadas = new PersonaDAO(AddActivity.this).crearPersona(p);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
    }

    private boolean validar() {
        boolean result = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setTitle("ADVERTENCIA");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        /*if (etId.getText().toString().equals("") || etId.getText().toString() == null) {
            showAlertDialog(builder, "Ingresar el ID");
            result = false;
        } else */if (etName.getText().toString().equals("") || etName.getText().toString() == null) {
            showAlertDialog(builder, "Ingresar el Nombre");
            result = false;
        } else if (etLastName.getText().toString().equals("") || etLastName.getText().toString() == null) {
            showAlertDialog(builder, "Ingresar el Apellido");
            result = false;
        } else if (etAge.getText().toString().equals("") || etAge.getText().toString() == null) {
            showAlertDialog(builder, "Ingresar la Edad");
            result = false;
        } else if (etDocument.getText().toString().equals("") || etDocument.getText().toString() == null) {
            showAlertDialog(builder, "Ingresar el Nro de Documento");
            result = false;
        } else if (etPhone.getText().toString().equals("") || etPhone.getText().toString() == null) {
            showAlertDialog(builder, "Ingresar el Nro de Celular");
            result = false;
        } else if (etDisponible.getText().toString().equals("") || etDisponible.getText().toString() == null) {
            showAlertDialog(builder, "Ingresar la Disponibilidad");
            result = false;
        }
        return result;
    }

    private void showAlertDialog(AlertDialog.Builder builder, String message) {
        builder.setMessage(message);
        builder.show();
    }
}
