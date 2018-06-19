package com.yaki.mypetbook2.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaki.mypetbook2.R;
import com.yaki.mypetbook2.map.Logic.Pet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PetListAdapter extends BaseAdapter {
    private Context c;
    private ArrayList<PetListItem> PetListItems;
    private Bitmap imageBitmap;
    public PetListAdapter(Context c, ArrayList<PetListItem> PetListItems) {
        this.c = c;
        this.PetListItems = PetListItems;

    }
    @Override
    public int getCount() {
        return PetListItems.size();
    }
    @Override
    public Object getItem(int i) {
        return PetListItems.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.activity_pet_list_item,viewGroup,false);
        }
        final PetListItem pli= (PetListItem) this.getItem(i);
        final ImageView img= (ImageView) view.findViewById(R.id.petImage);
        try {
            imageBitmap = decodeFromFirebaseBase64(pli.getImage());
            img.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);
        TextView propTxt= (TextView) view.findViewById(R.id.propellantTxt);
        nameTxt.setText(pli.getName());
        propTxt.setText(pli.getPropellant());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, PetDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("pic", pli.getImage());
                bundle.putString("name", pli.getName());
                bundle.putString("breed", pli.getPropellant());
                bundle.putString("age", pli.getAge());
                bundle.putString("description",pli.getDescription());
                bundle.putString("ownerEmail",pli.getOwnerEmail());
                bundle.putString("ownerPhoneNumber",pli.getOwnerPhoneNumber());
                bundle.putString("className", c.getClass().getSimpleName());
                intent.putExtra("bundle", bundle);
                c.startActivity(intent);
            }
        });
        return view;
    }

    public static Bitmap decodeFromFirebaseBase64(String base64image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(base64image, Base64.DEFAULT);
        BitmapFactory.Options options=new BitmapFactory.Options();
        Bitmap image = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length, options);
        image = Bitmap.createScaledBitmap(image, 460, 460, false);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        System.gc();
        return image;
    }
}
