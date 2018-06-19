package com.yaki.mypetbook2.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yaki.mypetbook2.R;

import java.util.ArrayList;
import java.util.HashMap;

public class LostPetsActivity extends AppCompatActivity {
    PetListAdapter adapter;
    ListView lv;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pets);
        getChildsOnChange();
    }


    private void getChildsOnChange() {
        ref.child("pets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PetListItem> PetListItems=new ArrayList<>();
                int i=1;
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    HashMap hm = (HashMap) child.getValue();
                    PetListItem pli=new PetListItem();
                    pli.setName((String) hm.get("name"));
                    pli.setPropellant((String) hm.get("breed"));
                    pli.setImage((String)hm.get("imageEncoded"));
                    pli.setAge((String)hm.get("age"));
                    pli.setDescription((String)hm.get("description"));
                    pli.setOwnerEmail((String)hm.get("ownerEmail"));
                    pli.setOwnerPhoneNumber((String)hm.get("ownerPhoneNumber"));
                    PetListItems.add(pli);
                }
                lv= (ListView) findViewById(R.id.lvlp);
                adapter=new PetListAdapter(LostPetsActivity.this,PetListItems);
                lv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("","The read failed: " + databaseError.getCode());
            }
        });
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

