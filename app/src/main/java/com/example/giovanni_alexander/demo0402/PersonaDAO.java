package com.example.giovanni_alexander.demo0402;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giovanni_Alexander on 30/06/2017.
 */

public class PersonaDAO {
    private final String TABLE = "Persona";
    private final String COL_ID = "id";
    private final String COL_NOMBRE = "nombre";
    private final String COL_APELLIDO = "apellido";
    private final String COL_EDAD = "edad";
    private final String COL_DOCUMENTO = "documento";
    private final String COL_TELEFONO = "telefono";
    private final String COL_DISPONIBLE = "disponible";
    private Context context;

    public PersonaDAO(Context context) {
        this.context = context;
    }

    // Metodo para listar personas
    public List<Persona> listarPersonas() {
        List<Persona> lstPersona = new ArrayList<Persona>();
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            SQLiteDatabase sqLiteDatabase = dataBaseHelper.openDataBase();
            Cursor cursor = sqLiteDatabase.query(TABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    lstPersona.add(transformarCursorAPersona(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstPersona;
    }

    // Método para crear persona
    public long crearPersona(Persona p) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, p.getNombre());
        contentValues.put(COL_APELLIDO, p.getApellido());
        contentValues.put(COL_EDAD, p.getEdad());
        contentValues.put(COL_DOCUMENTO, p.getDocumento());
        contentValues.put(COL_TELEFONO, p.getTelefono());
        contentValues.put(COL_DISPONIBLE, p.isDisponible() ? 1 : 0);
        return new DataBaseHelper(context).openDataBase().insert(TABLE, null, contentValues);
    }

    // Método para actualizar persona
    public void actualizarPersona(Persona p) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, p.getNombre());
        contentValues.put(COL_APELLIDO, p.getApellido());
        contentValues.put(COL_EDAD, p.getEdad());
        contentValues.put(COL_DOCUMENTO, p.getDocumento());
        contentValues.put(COL_TELEFONO, p.getTelefono());
        contentValues.put(COL_DISPONIBLE, p.isDisponible() ? 1 : 0);

        new DataBaseHelper(context).openDataBase().update(TABLE, contentValues, COL_ID + "=?", new String[]{String.valueOf(p.getId())});
    }


    // Método para eliminar persona
    public void eliminarPersona(Persona p) {
        new DataBaseHelper(context).openDataBase().delete(TABLE, COL_ID + "=?", new String[]{String.valueOf(p.getId())});
    }


    // Transformar el cursor
    private Persona transformarCursorAPersona(Cursor cursor) {
        Persona p = new Persona();
        p.setId(cursor.isNull(cursor.getColumnIndex(COL_ID)) ? "" : cursor.getString(cursor.getColumnIndex(COL_ID)));
        p.setNombre(cursor.isNull(cursor.getColumnIndex(COL_NOMBRE)) ? "" : cursor.getString(cursor.getColumnIndex(COL_NOMBRE)));
        p.setApellido(cursor.isNull(cursor.getColumnIndex(COL_APELLIDO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_APELLIDO)));
        p.setEdad(cursor.isNull(cursor.getColumnIndex(COL_EDAD)) ? 0 : cursor.getInt(cursor.getColumnIndex(COL_EDAD)));
        p.setTelefono(cursor.isNull(cursor.getColumnIndex(COL_TELEFONO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_TELEFONO)));
        p.setDocumento(cursor.isNull(cursor.getColumnIndex(COL_DOCUMENTO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_DOCUMENTO)));
        p.setDisponible(cursor.isNull(cursor.getColumnIndex(COL_DISPONIBLE)) ? false : cursor.getInt(cursor.getColumnIndex(COL_DISPONIBLE)) > 0);
        return p;
    }
}
