package com.example.pigeonsmap_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class AdressesActivity extends AppCompatActivity {
    private ArrayList<EditText> addressBoxes;
    private ArrayList<Spinner> priorityBoxes;
    private ArrayList<Intent> intents;
    private ArrayList<Place> places;
    private Button addAddressButton;
    private Button doneButton;
    private boolean mLocationPermissionGranted = false;
    private String mStrLatLng = "";
    private EditText startEditText;
    private ScrollView sv;
    private LinearLayout layoutInScroll;

    private Switch isCircularSw;
    private Switch travelModeSw;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adresses);


        //setContentView(R.layout.main);


        LinearLayout layout = (LinearLayout) findViewById(R.id.oll);
        addAddressButton = (Button) findViewById(R.id.addr);
        doneButton = (Button) findViewById(R.id.done);
        startEditText = (EditText) findViewById(R.id.editTextStart);
        isCircularSw = (Switch) findViewById(R.id.switch2);
        travelModeSw = (Switch) findViewById(R.id.switch3);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        addressBoxes = new ArrayList<EditText>();
        priorityBoxes = new ArrayList<>();
        intents = new ArrayList<Intent>();
        sv = (ScrollView) findViewById(R.id.scroll);
        Context c = this;
        EditText et = new EditText(c);
        et.setFocusable(false);
        places = new ArrayList<>();
        startEditText.setFocusable(false);
        startEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(AdressesActivity.this);
                startActivityForResult(intent, 100);
                intents.add(intent);
            }
        });


        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(AdressesActivity.this);
                startActivityForResult(intent, 100);
                intents.add(intent);
            }
        });
        addressBoxes.add(startEditText);
        addressBoxes.add(et);
        Places.initialize(getApplicationContext(), "AIzaSyBQNaogf7a45cgWe4YcZcsp4IVIWdi78mg");
        layout.removeView(addAddressButton);
        layoutInScroll = findViewById(R.id.inScrollLinear);
        layoutInScroll.addView(addressBoxes.get(1));

        Spinner dropdown = new Spinner(c);
        String[] items = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        priorityBoxes.add(dropdown);

        layoutInScroll.addView(dropdown);

        layoutInScroll.addView(addAddressButton);
        //sv.addView(layoutInScroll);

        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et = new EditText(c);
                et.setFocusable(false);
                et.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                                Place.Field.LAT_LNG, Place.Field.NAME);
                        Intent intent2 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                                fieldList).build(AdressesActivity.this);
                        startActivityForResult(intent2, 100);
                        intents.add(intent2);
                    }
                });
                addressBoxes.add(et);
                layoutInScroll.addView(et);
                //Places.initialize(getApplicationContext(), "AIzaSyBQNaogf7a45cgWe4YcZcsp4IVIWdi78mg");

                layoutInScroll.removeView(addAddressButton);
                //layout.addView(addressBoxes.get(addressBoxes.size() - 1));


                Spinner dropdown = new Spinner(c);
                String[] items = new String[]{"1", "2", "3", "4", "5"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);
                priorityBoxes.add(dropdown);

                layoutInScroll.addView(dropdown);

                layoutInScroll.addView(addAddressButton);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check permission
                if (ActivityCompat.checkSelfPermission(AdressesActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(AdressesActivity.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
                getLocation();
                // construct strings
                String latLonStr = mStrLatLng;
                for (int i = 0; i < places.size(); i++) {
                    String sss = places.get(i).getLatLng().toString();
                    //System.out.println(sss);
                    latLonStr += sss.split("[\\(\\)]")[1];
                    if (i != places.size() - 1)
                        latLonStr += "|";
                }
                System.out.println("#############################");
                System.out.println(latLonStr);
                //System.out.println(latLonStr.length());


                String mode;
                if(travelModeSw.isChecked())
                    mode = "driving";
                else
                    mode = "walking";

                String apiUrl = String.format("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=%s&mode=%s&destinations=%s&key=AIzaSyBQNaogf7a45cgWe4YcZcsp4IVIWdi78mg&", latLonStr, mode, latLonStr);
                System.out.println(apiUrl.length());
                System.out.println(apiUrl);
                String json = "";
                // Get json
                try {
                    URL url = new URL(apiUrl);
                    Scanner sc = new Scanner(url.openStream());
                    StringBuffer sb = new StringBuffer();
                    while (sc.hasNext()) {
                        sb.append(sc.next());
                        //System.out.println(sc.next());
                    }
                    json = sb.toString();
                } catch (MalformedURLException e) {

                } catch (IOException e) {

                }

                System.out.println(json);
                DataModel dm = new DataModel(json);

                long[][] mat = dm.getDistanceMatrix();

                for (int i = 0; i < mat.length; i++) {
                    for (int j = 0; j < mat.length; j++) {
                        System.out.print(mat[i][j] + " ");
                    }
                    System.out.println();
                }
                boolean circular = isCircularSw.isChecked();
                // Calculate route
                ArrayList<Integer> best = new ArrayList<>();
                int bestTime = -1;

                ArrayList<Integer> priorities = new ArrayList<>();
                priorities.add(10);
                System.out.println( ((String) priorityBoxes.get(0).getSelectedItem()).length());
                System.out.println("..............................................");
                for(int i = 0; i < priorityBoxes.size(); i++)
                    priorities.add( Integer.parseInt(priorityBoxes.get(i).getSelectedItem().toString()));

                //for(int i = 0; i < priorityBoxes.size(); i ++)
                //    priorities.add(Integer.valueOf(priorityBoxes.get(i).getSelectedItem().toString()));

                int k = calculateK(places.size());

                // all combinations of route
                ArrayList<ArrayList<Integer>> allRoutes = calculateAllCombinations(places.size() - 1);


                ArrayList<ArrayList<Integer>> bestRoutes = new ArrayList<>();
                ArrayList<Integer> bestTimes = new ArrayList<>();

                for(int i = 0; i < allRoutes.size(); i ++ )
                {
                    ArrayList<Integer> currentRoute = allRoutes.get(i);

                    if(currentRoute.get(0) != 0)
                        continue;

                    int currentTime = calculateTime(currentRoute, mat, circular);

                    if( bestTimes.size() == 0) {
                        bestTimes.add(currentTime);
                        bestRoutes.add(currentRoute);
                    }
                    else {
                        for (int j = 0; j < bestTimes.size(); j++) {
                            if (currentTime < bestTimes.get(j)) {
                                if (j == 0 && bestTimes.size() == k )
                                    break;
                                else {
                                    bestTimes.add(j, currentTime);
                                    bestRoutes.add(j, currentRoute);
                                }
                                if (bestTimes.size() > k) {
                                    bestTimes.remove(0);
                                    bestRoutes.remove(0);
                                }
                            }
                        }


                    }
                }

                ArrayList<Integer> selected = null;
                int bestCoef = 0;
                for(int i = 0; i < bestRoutes.size(); i ++)
                {
                    int currentCoef = calculateCoef(bestRoutes.get(i), priorities);

                    if(selected == null || currentCoef < bestCoef)
                    {
                        selected = bestRoutes.get(i);
                        bestCoef = currentCoef;
                        continue;
                    }
                }

                if(circular)
                {
                    selected.add(0);
                    //places.add(places.get(0));
                }

                ArrayList<Place> tempPlaces = new ArrayList<>();

                for(int i = 0; i < selected.size(); i++)
                    tempPlaces.add(places.get(selected.get(i)));

                places = tempPlaces;

                // Go to map page
                Intent nintent = new Intent(getBaseContext(), MapActivity.class);
                nintent.putExtra("EXTRA_PLACES", places);
                startActivity(nintent);

                //startActivity(new Intent(AdressesActivity.this, MapActivity.class));
            }
        });

    }

    private int calculateK(int n)
    {
        if(n <= 7)
            return (n-1)*(n-1);
        return (n-1)*(n-1)*(n-1);
    }

    private int calculateCoef( ArrayList<Integer> route, ArrayList<Integer> priorities)
    {
        ArrayList<Integer> prioritiesWithRouteOrder = new ArrayList<>();

        for(int i = 0; i < priorities.size(); i ++)
            prioritiesWithRouteOrder.add(priorities.get(route.get(i)));

        ArrayList<Integer> sorted = new ArrayList<>();

        for(int i = 0; i < prioritiesWithRouteOrder.size(); i ++)
            sorted.add(prioritiesWithRouteOrder.get(i));

        Collections.sort(sorted);

        int coef = 0;
        for(int i = 0; i < sorted.size(); i ++)
        {
            int priority = prioritiesWithRouteOrder.get(i);
            for(int j = 0; j < sorted.size(); j ++)
            {
                if(sorted.get(j) == priority)
                {
                    int distance = j - i;
                    if(distance < 0)
                        distance *= -1;
                    coef += distance;
                    break;
                }
            }
        }

        return coef;
    }

    private ArrayList<ArrayList<Integer>> calculateAllCombinations(int n)
    {
        return foo(n);
    }

    static ArrayList<ArrayList<Integer>> fooHelper(ArrayList<Integer> nList){

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if(nList.size() == 1) {
            ArrayList<Integer> temp = new ArrayList<Integer>(nList);
            result.add(temp);
            return result;
        }


        for(int i:nList) {
            ArrayList<Integer> temp = new ArrayList<Integer>(nList);
            temp.remove(Integer.valueOf(i));

            ArrayList<ArrayList<Integer>> possibleExtensions  = fooHelper(temp);

            for(int j = 0; j < possibleExtensions.size(); ++j) {
                ArrayList<Integer> toBeAdded = new ArrayList<Integer>();
                toBeAdded.add(i);
                toBeAdded.addAll(possibleExtensions.get(j));
                result.add(toBeAdded);
            }
        }


        return result;
    }

    static ArrayList<ArrayList<Integer>> foo(int n) {
        ArrayList<Integer> whatWeHave = new ArrayList<Integer>();

        for(int i = 1; i <= n; ++i) { //initialization loop
            whatWeHave.add(i);
        }

        ArrayList<ArrayList<Integer>> result  = fooHelper(whatWeHave);

        for(int i = 0; i < result.size(); ++i) {
            result.get(i).add(0, 0);
        }

        return result;
    }

    private int calculateTime(ArrayList<Integer> route, long[][] mat, boolean circular)
    {
        int time = 0;
        for(int i = 1; i < route.size(); i ++ )
            time += mat[route.get(i-1)][route.get(i)];

        if(circular)
            time += mat[route.get(route.size()-1)][route.get(0)];
        return time;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                if (location != null) {
                    Geocoder geocoder = new Geocoder(AdressesActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        mStrLatLng = addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude() + "|";

                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){

            places.add(Autocomplete.getPlaceFromIntent(data));

            for(int i = 0; i < places.size(); i++)
            {
                //Place place = Autocomplete.getPlaceFromIntent(intents.get(i));
                addressBoxes.get(i).setText(places.get(i).getAddress());
            }
        }
    }
}