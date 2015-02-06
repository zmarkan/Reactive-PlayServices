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
    public Observable<Location> provideLocationUpdates(LocationRequest locationRequest) {
        final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .build();
        return Observable.create(new ObservableConnection(googleApiClient)).filter(new Func1<ObservableConnection.CONNECTION_STATUS, Boolean>() {
            @Override
            public Boolean call(ObservableConnection.CONNECTION_STATUS connection_status) {
                return connection_status.compareTo(ObservableConnection.CONNECTION_STATUS.CONNECTED) == 0;;
            }
        }).flatMap(
                new Func1() {
                    @Override
                    public Observable<Location> call(Object o) {
                        return Observable.create(new LocationUpdatesObservable(googleApiClient, LocationServices.FusedLocationApi));
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
        return provideLocationUpdates(createDefaultLocationRequest());
    }

    @Override
    public Observable<Location> provideSingleLocationUpdate() {
        return provideSingleLocationUpdate(createDefaultLocationRequest());
    }
}
