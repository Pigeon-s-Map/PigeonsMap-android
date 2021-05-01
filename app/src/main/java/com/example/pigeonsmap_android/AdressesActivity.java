package com.example.pigeonsmap_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.SortedMap;

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

                // TODO
                boolean circular = isCircularSw.isChecked();
                // Calculate route
                ArrayList<Integer> best = new ArrayList<>();
                int bestTime = -1;

                ArrayList<Integer> priorities = new ArrayList<>();

                for(int i = 0; i < priorityBoxes.size(); i ++)
                    priorities.add(Integer.valueOf(priorityBoxes.get(i).getSelectedItem().toString()));


                // TODO
                int k = 10;

                ArrayList<ArrayList<Integer>> bestRoutes = new ArrayList<>();
                ArrayList<Integer> routeTimes = new ArrayList<>();
                for(int i = 0; i < mat.length; i ++)
                {

                }

                if(circular)
                    places.add(places.get(0));
                // Go to map page
                Intent nintent = new Intent(getBaseContext(), MapActivity.class);
                nintent.putExtra("EXTRA_PLACES", places);
                startActivity(nintent);

                //startActivity(new Intent(AdressesActivity.this, MapActivity.class));
            }
        });

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