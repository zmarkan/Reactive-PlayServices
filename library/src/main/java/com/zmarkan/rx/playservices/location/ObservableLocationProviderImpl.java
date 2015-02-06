package com.zmarkan.rx.playservices.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import rx.Observable;

/**
 * Created by zan on 26/12/14.
 */
public class ObservableLocationProviderImpl implements ObservableLocationProvider {

    private Context mContext;
    
    public ObservableLocationProviderImpl(Context context){
        mContext = context;
    }
    
    @Override
    public Observable<Location> provideLocationUpdates(LocationRequest locationRequest) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .build();
        Observable.OnSubscribe<Location> locationUpdatesObservable = 
                new LocationUpdatesObservable(googleApiClient, LocationServices.FusedLocationApi);
        return Observable.create(locationUpdatesObservable);
    }

    @Override
    public Observable<Location> provideSingleLocationUpdate(LocationRequest locationRequest) {
        return provideLocationUpdates(locationRequest).first();
    }

    @Override
    public Observable<Location> provideLocationUpdates() {
        return provideLocationUpdates(createDefaultLocationRequest());
    }

    @Override
    public Observable<Location> provideSingleLocationUpdate() {
        return provideSingleLocationUpdate(createDefaultLocationRequest());
    }

    private LocationRequest createDefaultLocationRequest(){
        return LocationRequest.create().
                setInterval(10000).
                setFastestInterval(5000).
                setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}
