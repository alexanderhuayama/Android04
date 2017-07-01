package com.example.giovanni_alexander.demo0402;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Giovanni_Alexander on 30/06/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.example.giovanni_alexander.demo0402/databases/";
    private static String DB_NAME = "Demo11.sqlite";
    private SQLiteDatabase db;
    private final Context context;

    // COPIAR Y PEGAR ESTE METODO
    public DataBaseHelper(Context context) {
        // Conexto, Nombre de la base de datos, cursor, version de la base de datos
        super(context, DB_NAME, null, 1);
        this.context = context;
    }


    // COPIAR Y PEGAR ESTE METODO
    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if (dbExist){

        }else{
            this.getReadableDatabase();
            try {
                copyDataBase();
            }catch (IOException e){
                throw new Error("Error copying database");
            }
        }
    }

    // COPIAR Y PEGAR ESTE METODO
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try {
            String path = DB_PATH+DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        if (checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    // COPIAR Y PEGAR ESTE METODO
    private void copyDataBase() throws IOException{
        InputStream inputStream = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte buffer[] = new byte[1024];
        int length;
        while ((length=inputStream.read(buffer))>0){
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();;
        outputStream.close();
        inputStream.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException{
        if (db == null || !db.isOpen()){
            String path = DB_PATH+DB_NAME;
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return db;
    }

    @Override
    public synchronized void close() {
        if (db != null){
            db.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
