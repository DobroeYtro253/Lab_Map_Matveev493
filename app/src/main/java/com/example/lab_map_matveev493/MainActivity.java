package com.example.lab_map_matveev493;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MapView mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mv = findViewById(R.id.mapView);

        mv.invalidate();
    }

    public void ZoomOut(View v)
    {


    }
    public void ZoomIn(View v)
    {


    }

}