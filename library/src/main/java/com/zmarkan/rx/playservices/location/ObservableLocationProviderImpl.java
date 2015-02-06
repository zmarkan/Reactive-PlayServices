package com.zmarkan.rx.playservices.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.zmarkan.rx.playservices.connection.ObservableConnection;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zan on 26/12/14.
 */
public class ObservableLocationProviderImpl implements ObservableLocationProvider {

    private Context mContext;
    
    public ObservableLocationProviderImpl(Context context){
        mContext = context;
    }
    
    @Override
    public Observable<Location> provideLocationUpdates(final LocationRequest locationRequest) {
        final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .build();
        return Observable.create(new ObservableConnection(googleApiClient)).filter(new Func1<ObservableConnection.CONNECTION_STATUS, Boolean>() {
            @Override
            public Boolean call(ObservableConnection.CONNECTION_STATUS connection_status) {
                return connection_status.compareTo(ObservableConnection.CONNECTION_STATUS.CONNECTED) == 0;
            }
        }).flatMap(
                new Func1() {
                    @Override
                    public Observable<Location> call(Object o) {
                        return Observable.create(new ObservableLocation(googleApiClient, LocationServices.FusedLocationApi, locationRequest));
                    }
                }
        );
    }

    @Override
    public Observable<Location> provideSingleLocationUpdate(LocationRequest locationRequest) {
        return provideLocationUpdates(locationRequest).first();
    }

    @Override
    public Observable<Location> provideLocationUpdates() {
        return provideLocationUpdates(buildLocationRequest());
    }

    @Override
    public Observable<Location> provideSingleLocationUpdate() {
        return provideSingleLocationUpdate(buildLocationRequest()).first();
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
}
