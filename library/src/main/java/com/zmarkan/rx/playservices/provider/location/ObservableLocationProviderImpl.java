package com.zmarkan.rx.playservices.provider.location;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import rx.Observable;

/**
 * Created by zan on 26/12/14.
 */
public class ObservableLocationProviderImpl implements ObservableLocationProvider {

    private final ObservableLocationFactory mFactory;
    
    public ObservableLocationProviderImpl(ObservableLocationFactory factory) {
        mFactory = factory;
    }

    @Override
    public Observable<Location> provideLocationUpdates(final LocationRequest locationRequest) {
        return mFactory.getObservable(locationRequest);
    }

    @Override
    public Observable<Location> provideSingleLocationUpdate(LocationRequest locationRequest) {
        return mFactory.getFirstObservable(locationRequest);
    }

    @Override
    public Observable<Location> provideLocationUpdates() {
        return provideLocationUpdates(buildLocationRequest());
    }

    @Override
    public Observable<Location> provideSingleLocationUpdate() {
        return provideSingleLocationUpdate(buildLocationRequest());
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }


}
