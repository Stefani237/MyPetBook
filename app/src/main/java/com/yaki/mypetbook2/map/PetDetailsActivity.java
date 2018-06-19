package com.yaki.mypetbook2.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaki.mypetbook2.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PetDetailsActivity extends AppCompatActivity {

    private Bitmap imageBitmap;
    private String imgString, age, desc, name, breed, ownerEmail, ownerPhoneNumber,lastSeenOn,lastSeenAt, className;
    private ImageView img;
    private TextView ageTxt, descTxt, nameTxt, breedTxt, contentTxt, lastSeenOnTxt, lastSeenAtTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        imgString = bundle.getString("pic");
        img= (ImageView)findViewById(R.id.petImageViewContects);
        try {
            imageBitmap = decodeFromFirebaseBase64(imgString);
            img.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        className = bundle.getString("className");
        age = bundle.getString("age");
        desc = bundle.getString("description");
        name = bundle.getString("name");
        breed = bundle.getString("breed");
        ownerEmail = bundle.getString("ownerEmail");
        ownerPhoneNumber = bundle.getString("ownerPhoneNumber");
        ageTxt = (TextView)findViewById(R.id.ageTextViewContent);
        descTxt = (TextView)findViewById(R.id.infoTextViewContent);
        nameTxt = (TextView)findViewById(R.id.nameTextViewContent);
        breedTxt = (TextView)findViewById(R.id.breedTextViewContent);
        contentTxt = (TextView)findViewById(R.id.contactTextViewContent);
        ageTxt.setText(age);
        descTxt.setText(desc);
        nameTxt.setText(name);
        breedTxt.setText(breed);
        contentTxt.setText(ownerEmail + " and " + ownerPhoneNumber);
        if(className.equals("LostPetsActivity")){
            lastSeenOnTxt = (TextView)findViewById(R.id.lastSeenOnTextViewContent);
            lastSeenAtTxt = (TextView)findViewById(R.id.lastSeenAtTextViewContent);
            lastSeenOnTxt.setVisibility(View.VISIBLE);
            lastSeenAtTxt.setVisibility(View.VISIBLE);
        }
    }


    public static Bitmap decodeFromFirebaseBase64(String base64image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(base64image, Base64.DEFAULT);
        BitmapFactory.Options options=new BitmapFactory.Options();
        Bitmap image = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length, options);
        image = Bitmap.createScaledBitmap(image, 800, 800, false);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        System.gc();
        return image;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
