package com.example.tomek.myapplication;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static String DATABASE_NAME = "database";
    public final static String DATABASE_PATH = "/data/data/com.example.tomek.myapplication/databases/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream in = getResources().openRawResource(R.raw.database);
        OutputStream out = null;
        try {
            out = new FileOutputStream(DATABASE_PATH+DATABASE_NAME);
            byte[] buff = new byte[1024];
            int read = 0;

            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                SQLiteDatabase sql = SQLiteDatabase.openDatabase(DATABASE_PATH+DATABASE_NAME,null, SQLiteDatabase.OPEN_READWRITE);
                Cursor c = sql.rawQuery("SELECT * from hejka;",null);
                if (c.moveToFirst()){
                    do {
                        // Passing values
                        String imie = c.getString(0);
                        String nazwisko = c.getString(1);
                        Toast.makeText(getApplicationContext(),"Imie: "+imie+" nazwisko: "+nazwisko,Toast.LENGTH_LONG).show();
                        // Do something Here with values
                    } while(c.moveToNext());
                }
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
