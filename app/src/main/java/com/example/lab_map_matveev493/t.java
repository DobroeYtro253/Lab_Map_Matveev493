package com.example.lab_map_matveev493;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class t {
        //public Image image;
        //public int level;

        int scale;
        int x;
        int y;
        Bitmap bmp;
        String b64;

        public t(int x, int y, int scale, Activity ctx)
        {
              this.x = x;
              this.y = y;
              this.scale = scale;
              this.bmp = null;
                try
                {
                    b64 = MainActivity.base.getCache(String.valueOf(x), String.valueOf(y), String.valueOf(scale));
                    byte[] jpeg = Base64.decode(b64, Base64.DEFAULT);
                    bmp = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
                }catch (Exception ex)
                {}
                if (bmp != null)
                {
                    return;
                }

              ApiHelper req = new ApiHelper(ctx)
              {
                  @RequiresApi(api = Build.VERSION_CODES.O)
                  @Override
                  public void on_ready(String res) {
                      try
                      {
                          JSONObject obj = new JSONObject(res);
                          b64 =  obj.getString("data");
                          Calendar calendar = Calendar.getInstance();
                          SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                          MainActivity.base.addCache(String.valueOf(x), String.valueOf(y), String.valueOf(scale), b64, formatter.format(calendar.getTime()));
                          byte[] jpeg = Base64.decode(b64, Base64.DEFAULT);
                          bmp = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
                      }
                      catch (Exception ex)
                      {
                            ex.printStackTrace();
                      }
                  }
              };
              req.send(MainActivity.api + "/raster/" + String.format("%d/%d-%d", scale, x, y));
        }
}

