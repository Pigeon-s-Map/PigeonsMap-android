package com.example.pigeonsmap_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataModel {
    private long[][] distanceMatrix;
    private final int vehicleNumber = 1;
    private final int depot = 0;

    public DataModel( String json) {
        // Parse JSON and construct distanceMatrix
        try{
            JSONObject jObject = new JSONObject(json);
            String rowsStr = jObject.getString("rows");
            JSONArray rowsArr = new JSONArray(rowsStr);
            distanceMatrix = new long[rowsArr.length()][rowsArr.length()];
            for(int i = 0; i < rowsArr.length(); i++)
            {
                JSONObject row = (JSONObject) rowsArr.get(i);
                JSONArray columns = new JSONArray(row.getString("elements"));
                for(int j = 0; j < rowsArr.length(); j ++)
                {
                    JSONObject currentCol = (JSONObject) columns.get(j);
                    String durationStr = currentCol.getString("duration");
                    JSONObject durObj = new JSONObject(durationStr);
                    long duration = Long.parseLong(durObj.getString("value"));
                    distanceMatrix[i][j] = duration;
                }
            }
        }
        catch(JSONException e)
        {

        }

    }

    public long[][] getDistanceMatrix()
    {
        return distanceMatrix;
    }

}
