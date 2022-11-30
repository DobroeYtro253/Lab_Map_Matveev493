package com.example.lab_map_matveev493;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Shape extends JSONArray {
    ArrayList<ArrayList> shx = new ArrayList<ArrayList>();
    ArrayList<ArrayList> shy = new ArrayList<ArrayList>();
    ArrayList<Double> x = new ArrayList<Double>();
    ArrayList<Double> y = new ArrayList<Double>();

    public Shape(JSONArray j) throws JSONException {
        for (int i = 0; i < j.length(); i++)
        {
            x = new ArrayList<Double>();
            y = new ArrayList<Double>();
            for (int k = 0; k < j.getJSONArray(i).length(); k++)
            {
                try {
                    this.x.add(j.getJSONArray(i).getJSONObject(k).getDouble("x"));
                    this.y.add(j.getJSONArray(i).getJSONObject(k).getDouble("y"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            shx.add(x);
            shy.add(y);

        }
    }

}
