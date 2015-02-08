package com.zmarkan.rx.playservices.provider.location;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import rx.Observable;


/**
 * Created by zan on 26/12/14.
 */
public interface ObservableLocationProvider {

    /**
     * Base request.
     * @return observable that starts/stops getting updates when unsubscribe
     * @param locationRequest the request with all parameters set
     * */
    public Observable<Location> provideLocationUpdates(LocationRequest locationRequest);

    /**
     * @param locationRequest the request with all parameters set
     * */
    public Observable<Location> provideSingleLocationUpdate(LocationRequest locationRequest);
    
    
    /**
     * Default request. Will provide fused location updates every 5-10 seconds
     * */
    public Observable<Location> provideLocationUpdates();

    
    public Observable<Location> provideSingleLocationUpdate();

}
