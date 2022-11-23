package com.example.lab_map_matveev493;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

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

    int[] levels = {16, 8, 4, 2, 1};
    int[] xtiles = {54, 108, 216, 432, 864};
    int[] ytiles = {27, 54, 108, 216, 432};

    int tile_wight = 100;
    int tile_height = 100;

    float offset_x = 0.0f;
    float offset_y = 0.0f;

    Paint p;

    int width;
    int height;

    Activity ctx;

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GREEN);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        width = canvas.getWidth();
        height = canvas.getHeight();

        canvas.drawColor(Color.rgb(255,255,255));

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
                //tile box
                //get tile position
                float x0 = x * tile_wight + (int)offset_x;
                float y0 = y * tile_height + (int)offset_y;
                float x1 = x0 + tile_wight;
                float y1 = y0 + tile_height;

                if (!rect_intersects_rect(x0, y0, x1, y1, screen_x0, screen_y0, screen_x1, screen_y1) == false) continue;

                t t = gett(x, y, levels[current_level_index]);
                if(t.bmp != null) canvas.drawBitmap(t.bmp, x0,  y0, p);

                canvas.drawRect(x0, y0, x1, y1, p);
                //canvas.drawText(String.valueOf(levels[current_level_index]) + ", " + String.valueOf(x) + ", " + String.valueOf(y));

            }
        }

        // download();
        // pending = false;

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
                invalidate();

                last_x = x;
                last_y = y;
                return true;

            case MotionEvent.ACTION_UP:
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



}
