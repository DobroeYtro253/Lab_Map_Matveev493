package com.example.lab_map_matveev493;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import org.json.JSONArray;

import java.util.ArrayList;

public class MapView extends SurfaceView {

    ArrayList <t> tiles = new ArrayList<t>();


    t gett(int x, int y, int scale)
    {
        for (int i = 0; i < tiles.size(); i++)
        {
            t t = tiles.get(i);
            if (t.x == x && t.y == y && t.scale == scale) return t;
        }
        t nt = new t(x, y, scale, ctx);
        tiles.add(nt);


        return nt;
    }

    float last_x;
    float last_y;


    int current_level_index = 0;

    int[] levels = new int[] {16, 8, 4, 2, 1};
    int[] xtiles = new int[] {54, 108, 216, 432, 864};
    int[] ytiles = new int[] {27, 54, 108, 216, 432};
    double[] dpp = {
            360.0 / (86400 / 16),
            360.0 / (86400 / 8),
            360.0 / (86400 / 4),
            360.0 / (86400 / 2),
            360.0 / (86400 / 1)
    };
    //Shape[] shapes = {};
    public static ArrayList<Shape> shapes = new ArrayList<Shape>();
    Shape p;

    int tile_wight = 100;
    int tile_height = 100;

    float offset_x = 0.0f;
    float offset_y = 0.0f;

    Paint paint, paint2;

    int width;
    int height;

    int lock = 0;

    Activity ctx;

    double lat0, lon0;
    double lat1, lon1;

    Bitmap bmp;

    String overlay = "";

    public void update_viewport()
    {
        lat0 = -offset_x * dpp[current_level_index] - 180.0;
        lon0 = 90.0 + offset_y * dpp[current_level_index];
        lat1 = lat0 + width * dpp[current_level_index];
        lon1 = lon0 - height * dpp[current_level_index];
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);

        setWillNotDraw(false);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int act = event.getAction();
        switch (act)
        {
            case MotionEvent.ACTION_DOWN:
                last_x = event.getX();
                last_y = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();

                float dx = x - last_x;
                float dy = y - last_y;

                offset_x += dx;
                offset_y += dy;
                update_viewport();
                invalidate();

                last_x = x;
                last_y = y;
                return true;

            case MotionEvent.ACTION_UP:
                overlay = MainActivity.sp.getSelectedItem().toString();
                shapes.clear();
                paint2 = new Paint();
                paint2.setStyle(Paint.Style.STROKE);
                switch (overlay)
                {
                    case "coastline": paint2.setColor(Settings.cbc); break;
                    case "river": paint2.setColor(Settings.cbr); break;
                    case "road": paint2.setColor(Settings.cbro); break;
                    case "railroad": paint2.setColor(Settings.cbrr); break;
                    case "nothing": paint2.setColor(Color.argb(0, 1, 1, 1)); break;

                }

                MainActivity.base.setLastPosition(offset_x, offset_y, current_level_index);
                ApiHelper req = new ApiHelper(ctx)
                {
                    @Override
                    public void on_ready(String res) {
                        try
                        {
                            JSONArray arr = new JSONArray(res);
                            shapes.add(new Shape(arr));
                            p = shapes.get(0);
                            lock = 1;
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                };
                String reqS = "http://tilemap.spbcoit.ru:7000/" + overlay + "/" + levels[current_level_index] + "?lat0="
                        + lat0 + "&lon0=" + lon0 + "&lat1=" + lat1 + "&lon1=" + lon1;
                req.send(reqS);

                return true;
        }
        return false;
    }

    boolean rect_intersects_rect(float ax0,float ay0,float ax1,float ay1,float bx0,float by0,float bx1,float by1)
    {
        if (ax1 < bx0) return false;
        if (ax0 > bx1) return false;
        if (ay1 < by0) return false;
        if (ay0 > by1) return false;
        return true;
    }

    public float map(double x,  float x0, float x1, float a, float b)
    {
        float t = ((float) x - x0) / (x1 - x0);
        return a + (b - a) * t;
    }
    @Override
    protected void onDraw(Canvas canvas)
    {

        width = canvas.getWidth();
        height = canvas.getHeight();

        canvas.drawColor(Color.WHITE);

        int screen_x0 = 0;
        int screen_y0 = 0;
        int screen_x1 = canvas.getWidth() - 1;
        int screen_y1 = canvas.getHeight() - 1;

        int w = xtiles[current_level_index];
        int h = ytiles[current_level_index];

        for (int y = 0; y < h; y++)
        {
            for (int x = 0; x < w; x++)
            {
                int x0 = x * tile_wight + (int)offset_x;
                int y0 = y * tile_height + (int)offset_y;
                int x1 = x0 + tile_wight;
                int y1 = y0 + tile_height;

                if (!rect_intersects_rect(screen_x0, screen_y0, screen_x1, screen_y1, x0, y0, x1, y1)) continue;

                t t = gett(x, y, levels[current_level_index]);
                if(t.bmp != null) canvas.drawBitmap(t.bmp, x0,  y0, paint);

               //canvas.drawRect(x0, y0, x1, y1, paint);
               //canvas.drawText(String.valueOf(levels[current_level_index]) + ", " + String.valueOf(x) + ", " + String.valueOf(y), x0, y0 , paint);

            }
        }
        if(MainActivity.sp.getSelectedItem().toString().equals("nothing"))
        {

        }

        if (lock == 1)
        {

            try
            {
                for (int si = 0; si < p.shx.size(); si++)
                {

                    float px0 = 0, py0 = 0;
                    for (int pi = 0; pi < p.shx.get(si).size(); pi++) {
                        float px1 = map((double) p.shx.get(si).get(pi), (float) lat0, (float) lat1, 0, width);
                        float py1 = map((double) p.shy.get(si).get(pi), (float) lon0, (float) lon1, 0, height);
                        // float py1 = map(p.y.get(pi).floatValue(), (float) lon0, (float) lon1, 0, height);
                        if (pi == 0) {
                        } else {
                            canvas.drawLine(px0, py0, px1, py1, paint2);
                        }
                        px0 = px1;
                        py0 = py1;


                    }
                }
            }
            catch (Exception ex)
            {
                return;
            }

        }


    }





}
