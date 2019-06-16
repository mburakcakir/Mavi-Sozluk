package com.burhangok.mavisozluk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper {

    public static final String VT_ADI = "mavi";
    public static final String TABLO_ADI = "sozluk";
    public static final String TABLO_ADI2 = "gecmis";
    public Context mcontext;


    public Veritabani(Context context) {
        super(context, VT_ADI, null, 2);
        this.mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlCumlesi = "CREATE TABLE IF NOT EXISTS " + TABLO_ADI + " (ID INTEGER PRIMARY KEY , English TEXT, Turkish TEXT" + ")";
        db.execSQL(sqlCumlesi);

        String sqlCumlesi2 = "CREATE TABLE IF NOT EXISTS " + TABLO_ADI2 + " (ID INTEGER PRIMARY KEY , Keyword TEXT" + ")";
        db.execSQL(sqlCumlesi2);

        try {
            veritabaniniKopyala(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion == 2) {
            String sqlCumlesi2 = "CREATE TABLE IF NOT EXISTS " + TABLO_ADI2 + " (ID INTEGER PRIMARY KEY , Keyword TEXT" + ")";
            db.execSQL(sqlCumlesi2);
        }

    }

    public boolean IsSavedHistory(String keyword) {
        if (!keyword.isEmpty()) {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT COUNT(*) FROM " + TABLO_ADI2 + " WHERE Keyword='" + keyword + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    return cursor.getInt(0) > 0;
                } while (cursor.moveToNext());
            }
            db.close();
        }
        return false;
    }

    public void GecmisEkle(String kkeyword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Keyword", kkeyword);

        db.insert(TABLO_ADI2, null, values);
        db.close();
    }

    public List<String> GecmisListele() {
        List<String> kelimeler = new ArrayList<String>();
        SQLiteDatabase vt = this.getWritableDatabase();

        String sqlCumlesi = "SELECT * FROM " + TABLO_ADI2 + " ORDER BY ID DESC";
        Cursor cursor = vt.rawQuery(sqlCumlesi, null);

        while (cursor.moveToNext()) {
            kelimeler.add(cursor.getString(1));
        }

        return kelimeler;
    }

    public List<Sozluk> kelimeleriGetir(String aranacak) {
        List<Sozluk> kelimeler = new ArrayList<Sozluk>();

        SQLiteDatabase vt = this.getWritableDatabase();

        String sqlCumlesi = "SELECT * FROM sozluk WHERE English LIKE '" + aranacak + "%' ORDER BY English ";
        Cursor cursor = vt.rawQuery(sqlCumlesi, null);

        while (cursor.moveToNext()) {

            Sozluk kelime = new Sozluk();
            kelime.setEnglish(cursor.getString(1));
            kelime.setTurkish(cursor.getString(2));
            kelimeler.add(kelime);
        }

        return kelimeler;

    }

    public void veritabaniniKopyala(SQLiteDatabase vt) throws IOException {

        InputStream insertsStream = mcontext.getResources().openRawResource(R.raw.sozluk);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            vt.execSQL(insertStmt);
        }
        insertReader.close();
    }

}
