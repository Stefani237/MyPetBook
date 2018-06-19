package com.yaki.mypetbook2.map;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.provider.MediaStore;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.util.Base64;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.Toast;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.yaki.mypetbook2.R;
        import com.yaki.mypetbook2.map.Logic.Pet;

        import java.io.ByteArrayOutputStream;

public class NewPetFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private DatabaseReference mDatabase;
    private Pet mPet;
    private StorageReference mStorageRef;
    private View view;
    private Spinner mSpinner;
    private Button mAddPetbtn;
    private ArrayAdapter<CharSequence> adapter;
    private String mType;
    private EditText mBreedEditText, mNameEditText, mAgeEditText, mDescEditText, mPhoneEditText, mEmailEditText;
    private ImageView mPetImage;
    private String mImageEncoded;
    private LoginActivity activity;
    public NewPetFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_new_pet_fragment, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        setSpinner();
        setEditText();
        insertPetImage();
        addNewPetToDB();
        return view;
    }

    private void setSpinner()
    {
        mSpinner = (Spinner) view.findViewById(R.id.catOrDog);
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.petTypesArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }
    private void setEditText()
    {
        mBreedEditText = (EditText)view.findViewById(R.id.typeEditText);
        mBreedEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBreedEditText.setText("");
            }
        });
        mNameEditText = (EditText)view.findViewById(R.id.nameEditText);
        mNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNameEditText.setText("");
            }
        });
        mAgeEditText = (EditText)view.findViewById(R.id.ageEditText);
        mAgeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeEditText.setText("");
            }
        });
        mDescEditText = (EditText)view.findViewById(R.id.descriptionEditText);
        mDescEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDescEditText.setText("");

            }
        });
        mEmailEditText = (EditText)view.findViewById(R.id.emailEditText);
        mEmailEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmailEditText.setText("");
            }
        });
        mPhoneEditText = (EditText)view.findViewById(R.id.phoneNumberEditText);
        mPhoneEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneEditText.setText("");

            }
        });
    }

    public void insertPetImage(){
        mPetImage = (ImageView)view.findViewById(R.id.petImageView);
            mPetImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPetImage.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        mImageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    public void addNewPetToDB(){
        mAddPetbtn = (Button)view.findViewById(R.id.addPetBtn);
        mAddPetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mType = mSpinner.getSelectedItem().toString();
                activity = (LoginActivity) getActivity();
                if( mBreedEditText.getText().toString()!= "" && mNameEditText.getText().toString() != "" && mAgeEditText.getText().toString() != "") {
                    mPet = new Pet(activity.getUserFBId(), mType, mBreedEditText.getText().toString(), mNameEditText.getText().toString(), mAgeEditText.getText().toString(), mDescEditText.getText().toString(), mImageEncoded, mEmailEditText.getText().toString(), mPhoneEditText.getText().toString());
                    writeNewUser(mPet);
                    Toast.makeText(getActivity(),"Your pet was added successfully!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"You must fill all the fields!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void writeNewUser(Pet pet) {
        mDatabase.child("pets").push().setValue(pet);
    }
}
