package com.example.lab_map_matveev493;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;

import org.json.JSONObject;

public class t {
        //public Image image;
        //public int level;

        int scale;
        int x;
        int y;
        Bitmap bmp;

        public t(int x, int y, int scale, Activity ctx)
        {
              this.x = x;
              this.y = y;
              this.scale = scale;
              this.bmp = null;

              ApiHelper req = new ApiHelper(ctx)
              {
                  @Override
                  public void on_ready(String res) {
                      try
                      {
                          JSONObject obj = new JSONObject(res);
                          String b64 =  obj.getString("data");
                          byte[] jpeg = Base64.decode(b64, android.util.Base64.DEFAULT);
                          bmp = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
                      }
                      catch (Exception ex)
                      {
                            ex.printStackTrace();
                      }
                  }
              };
              req.send("http://tilemap.spbcoit.ru:7000/raster/" + String.format("%d/%d-%d", scale, x, y));
        }
}

