package com.zmarkan.rx.playservices.location;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by zan on 26/12/14.
 */
public class LocationUpdatesObservable implements Observable.OnSubscribe<Location> {

    final FusedLocationProviderApi mLocationProviderAPI;
    final GoogleApiClient mGoogleAPIClient;
    final LocationRequest mLocationRequest;

    private Subscriber<? super Location> observer;
    private LocationListener mLocationListener;
    
    public LocationUpdatesObservable(GoogleApiClient googleAPIClient, FusedLocationProviderApi locationProviderAPI){
        this.mLocationProviderAPI = locationProviderAPI;
        this.mGoogleAPIClient = googleAPIClient;
        mLocationRequest = buildLocationRequest();
    }

    public LocationUpdatesObservable(GoogleApiClient googleAPIClient, FusedLocationProviderApi locationProviderAPI, LocationRequest request){
        this.mLocationProviderAPI = locationProviderAPI;
        this.mGoogleAPIClient = googleAPIClient;
        this.mLocationRequest = request;
    }

    @Override
    public void call(Subscriber<? super Location> subscriber) {
        observer = subscriber;

        setupLocationClient();

        UnsubscribeAction unsubscribeAction = new UnsubscribeAction();
        observer.add(Subscriptions.create(unsubscribeAction));
    }

    protected void setupLocationClient() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                observer.onNext(location);
            }
        };
        mLocationProviderAPI.requestLocationUpdates(mGoogleAPIClient, mLocationRequest, mLocationListener);
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    class UnsubscribeAction implements Action0 {
        @Override
        public void call() {
            mLocationProviderAPI.removeLocationUpdates(mGoogleAPIClient, mLocationListener);
        }
    }
}