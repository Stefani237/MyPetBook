package com.yaki.mypetbook2.map;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.location.LocationListener;

/**
 * Created by יקי on 28/03/2017.
 */

public class GPSTracker extends Service implements LocationListener {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
