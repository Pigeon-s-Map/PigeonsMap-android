package com.example.pigeonsmap_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class AdressesActivity extends AppCompatActivity {
    private ArrayList<EditText> addressBoxes;
    private Button addAddressButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adresses);

        LinearLayout layout = (LinearLayout) findViewById(R.id.oll);
        addAddressButton = (Button) findViewById(R.id.addr);
        addressBoxes = new ArrayList<EditText>();
        Context c = this;
        addressBoxes.add(new EditText(this));

        layout.removeView(addAddressButton);
        layout.addView(addressBoxes.get(0));
        layout.addView(addAddressButton);

        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressBoxes.add(new EditText(c));
                layout.removeView(addAddressButton);
                layout.addView(addressBoxes.get(addressBoxes.size()-1));
                layout.addView(addAddressButton);
            }
        });

    }


}