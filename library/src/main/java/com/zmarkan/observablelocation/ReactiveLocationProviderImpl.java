package com.zmarkan.observablelocation;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import rx.Observable;

/**
 * Created by zan on 26/12/14.
 */
public class ReactiveLocationProviderImpl implements ReactiveLocationProvider {

    @Override
    public Observable<Location> provideLocationUpdates(LocationRequest locationRequest) {

        Observable.OnSubscribe<Location> locationUpdatesObservable = new LocationUpdatesObservable();



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

    private LocationRequest createDefaultLocationRequest(){
        return LocationRequest.create().
                setInterval(10000).
                setFastestInterval(5000).
                setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}
