package com.yaki.mypetbook2.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.yaki.mypetbook2.R;

public class MainActivity extends AppCompatActivity {
    private Button mAdoption;
    private Button mBusinesses;
    private Button mLostPets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAdoptionActivity();
        startBusinessesActivity();
        startLostPetActivity();
    }


    public void startAdoptionActivity() {
        mAdoption =  (Button) findViewById(R.id.adoptionBtn);
        mAdoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdoptionActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startBusinessesActivity() {
        mBusinesses =  (Button) findViewById(R.id.businessesBtn);
        mBusinesses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startLostPetActivity() {
        mLostPets =  (Button) findViewById(R.id.lostPetBtn);
        mLostPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LostPetsActivity.class);
                startActivity(intent);
            }
        });
    }
}