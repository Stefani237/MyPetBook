package com.yaki.mypetbook2.map;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.yaki.mypetbook2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private LoginButton mLoginButton;
    private TextView mTextView;
    private CheckBox mCheckBox;
    private NewPetFragment mNewPetFragment;
    private CallbackManager mCallbackManager;
    private boolean visitedAddNewPet = false;
    private Button mGoToBtn;
    private AccessToken accessToken;
    private ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        mGoToBtn = (Button)findViewById(R.id.goToBtn);
        mLoginButton = (LoginButton)findViewById(R.id.login_button);
        mTextView = (TextView)findViewById(R.id.Hello);
        mCheckBox = (CheckBox)findViewById(R.id.addPet);
        mCheckBox.setVisibility(View.INVISIBLE);
        mCallbackManager = CallbackManager.Factory.create();
        profilePictureView = (ProfilePictureView) findViewById(R.id.friendProfilePicture);
        startMainActivity();
        addNewPet();
        goToMenu();
        mLoginButton.setReadPermissions("public_profile");
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            profilePictureView.setProfileId(profile2.getId());
                            TextView tv = (TextView)findViewById(R.id.Hello);
                            tv.setText("Hello "+ profile2.getFirstName() + "!");
                            Log.v("facebook - profile", profile2.getFirstName());
                            mProfileTracker.stopTracking();
                            isLoggedIn();
                        }
                    };
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());
                }
            }
            @Override
            public void onCancel() {
                isLoggedIn();
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    public void startMainActivity() {
        accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null)
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void addNewPet() {
        mGoToBtn.setVisibility(View.INVISIBLE);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                visitedAddNewPet = true;
                FragmentManager newPetManager = getSupportFragmentManager();
                FragmentTransaction newPetTransaction = newPetManager.beginTransaction();
                if(mCheckBox.isChecked()){
                    mNewPetFragment = new NewPetFragment();
                    newPetTransaction.add(R.id.fragment_container, mNewPetFragment);
                    newPetTransaction.addToBackStack(null);
                    mGoToBtn.setTranslationZ(-100);
                    newPetTransaction.commit();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
        if(visitedAddNewPet)
            mNewPetFragment.onActivityResult(requestCode, resultCode, data);
    }

    public void goToMenu(){
        mGoToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void isLoggedIn() {
        accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null) {
            mGoToBtn.setVisibility(View.VISIBLE);
            profilePictureView.setVisibility(View.VISIBLE);
            mCheckBox.setVisibility(View.VISIBLE);
            getPImage();
        }
        else{
            mGoToBtn.setVisibility(View.INVISIBLE);
            profilePictureView.setVisibility(View.INVISIBLE);
            mCheckBox.setVisibility(View.INVISIBLE);
        }
    }

    public void getPImage(){
        accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null) {
            Profile profile = Profile.getCurrentProfile();
            profilePictureView.setProfileId(profile.getId());
            TextView tv = (TextView)findViewById(R.id.Hello);
            tv.setText("Hello "+ profile.getFirstName() + "!");
        }
    }

    public String getUserFBId(){
        Profile profile = Profile.getCurrentProfile();
        return profile.getId();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPImage();
        isLoggedIn();
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
