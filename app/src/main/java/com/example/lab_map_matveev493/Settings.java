package com.example.lab_map_matveev493;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {


    EditText api, cache;
    Button bc, br, bro, brr;
    public static int cbc, cbr, cbro, cbrr;
    Activity ctx = this;
    final String[] colorsArray = {"red", "green", "blue", "purple", "black"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        cache = findViewById(R.id.editTextCache);
        api = findViewById(R.id.editTextApi);
        bc = findViewById(R.id.buttonCoastline);
        br = findViewById(R.id.buttonRiver);
        bro = findViewById(R.id.buttonRoad);
        brr = findViewById(R.id.buttonRailroad);
        bc.setBackgroundColor(cbc);
        br.setBackgroundColor(cbr);
        bro.setBackgroundColor(cbro);
        brr.setBackgroundColor(cbrr);
        api.setText(MainActivity.base.getApi());
        cache.setText(MainActivity.base.getCacheTime());
    }
    public void Save(View v)
    {
        MainActivity.base.setDefaultSetting(api.getText().toString(),  String.valueOf(cbc), String.valueOf(cbr), String.valueOf(cbro), String.valueOf(cbrr), cache.getText().toString());
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void CLose(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void Clear(View v)
    {
        MainActivity.base.clearCache();
    }

    public void Coastline(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Select color")
                .setItems(colorsArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (colorsArray[which])
                        {
                            case "red": cbc = Color.RED; break;
                            case "green": cbc = Color.GREEN; break;
                            case "blue": cbc = Color.BLUE; break;
                            case "purple": cbc = Color.rgb(128, 0, 128); break;
                            case "black": cbc = Color.BLACK; break;
                        }
                        bc.setBackgroundColor(cbc);
                    }
                });

       AlertDialog alert = builder.create();
       alert.show();
    }
    public void River(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Select color")
                .setItems(colorsArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (colorsArray[which])
                        {
                            case "red": cbr = Color.RED; break;
                            case "green": cbr = Color.GREEN; break;
                            case "blue": cbr = Color.BLUE; break;
                            case "purple": cbr = Color.rgb(128, 0, 128); break;
                            case "black": cbr = Color.BLACK; break;
                        }
                        br.setBackgroundColor(cbr);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void Road(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select color")
                .setItems(colorsArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (colorsArray[which])
                        {
                            case "red": cbro = Color.RED; break;
                            case "green": cbro = Color.GREEN; break;
                            case "blue": cbro = Color.BLUE; break;
                            case "purple": cbro = Color.rgb(128, 0, 128); break;
                            case "black": cbro = Color.BLACK; break;
                        }
                        bro.setBackgroundColor(cbro);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void Railroad(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select color")
                .setItems(colorsArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (colorsArray[which])
                        {
                            case "red": cbrr = Color.RED; break;
                            case "green": cbrr = Color.GREEN; break;
                            case "blue": cbrr = Color.BLUE; break;
                            case "purple": cbrr = Color.rgb(128, 0, 128); break;
                            case "black": cbrr = Color.BLACK; break;
                        }
                        brr.setBackgroundColor(cbrr);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}