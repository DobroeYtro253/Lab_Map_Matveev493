package com.example.lab_map_matveev493;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static String api;
    public static DB base;
    MapView mv;
    public static Spinner sp;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        base = new DB(this, "setting.db", null, 1);
        api = base.getApi();
        Settings.cbc = Integer.parseInt(MainActivity.base.getColorCoastline());
        Settings.cbr =  Integer.parseInt(MainActivity.base.getColorRiver());
        Settings.cbro =  Integer.parseInt(MainActivity.base.getColorRoad());
        Settings.cbrr =  Integer.parseInt(MainActivity.base.getColorRailroad());
        String time = base.getCacheTime();
        String t = base.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            date = formatter.parse(t);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            if (time.indexOf('d') != -1)
            {

                String time2 = time.substring(0, 1);
                instance.add(Calendar.DAY_OF_MONTH, Integer.parseInt(time2));
                Date newDate = instance.getTime();
                Calendar calendar = Calendar.getInstance();
                Date nowDate = calendar.getTime();
                if(newDate.getTime() < nowDate.getTime()){base.clearCache();}
            }  else
            {
                if (time.indexOf('m') != -1)
                {
                    String time2 = time.substring(0, 1);
                    instance.add(Calendar.MINUTE, Integer.parseInt(time2));
                    Date newDate = instance.getTime();
                    Calendar calendar = Calendar.getInstance();
                    Date nowDate = calendar.getTime();
                    if(newDate.getTime() < nowDate.getTime()){base.clearCache();}
                } else
                {
                    if(time.indexOf('s') != -1)
                    {
                        String time2 = time.substring(0, 1);
                        instance.add(Calendar.SECOND, Integer.parseInt(time2));
                        Date newDate = instance.getTime();
                        Calendar calendar = Calendar.getInstance();
                        Date nowDate = calendar.getTime();
                        if(newDate.getTime() < nowDate.getTime()){base.clearCache();}
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



        mv = findViewById(R.id.mapView);
        mv.ctx = this;
        mv.callOnClick();
        mv.offset_x = Float.valueOf(base.getLastPositionX());
        mv.offset_y = Float.valueOf(base.getLastPositionY());
        mv.current_level_index = Integer.parseInt(base.getLastPositionLevel());

        mv.invalidate();
        sp = findViewById(R.id.spinnerType);

    }

    public void settingMenu(View v)
    {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }
    public void ZoomOut(View v)
    {
        if (mv.current_level_index == 0) return;
        mv.current_level_index --;

        mv.offset_x += mv.width  / 2.0f;
        mv.offset_y += mv.height / 2.0f;

        mv.offset_x /=2.0f;
        mv.offset_y /=2.0f;

        mv.invalidate();

    }
    public void ZoomIn(View v)
    {
        if (mv.current_level_index == 5) return;
        mv.current_level_index ++;

        mv.offset_x *=2.0f;
        mv.offset_y *=2.0f;

        mv.offset_x -= mv.width  / 2.0f;
        mv.offset_y -= mv.height / 2.0f;

        //mv.offset_x *=2.5f;
        //mv.offset_y *=1.5f;


        mv.invalidate();
    }


}