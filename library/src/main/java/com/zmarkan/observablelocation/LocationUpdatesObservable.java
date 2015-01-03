package com.zmarkan.observablelocation;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by zan on 26/12/14.
 */
public class LocationUpdatesObservable implements Observable.OnSubscribe<Location> {

    final FusedLocationProviderApi locationProviderAPI;
    final GoogleApiClient googleAPIClient;

    private Subscriber<? super Location> observer;
    private LocationListener locationListener;
    
    public LocationUpdatesObservable(GoogleApiClient googleAPIClient, FusedLocationProviderApi locationProviderAPI){
        this.googleAPIClient = googleAPIClient;
        this.locationProviderAPI = locationProviderAPI;
    }

    @Override
    public void call(Subscriber<? super Location> subscriber) {
        APIConnectionCallbacks callbacks = new APIConnectionCallbacks();
        observer = subscriber;
        googleAPIClient.registerConnectionCallbacks(callbacks);
        googleAPIClient.registerConnectionFailedListener(callbacks);
        googleAPIClient.connect();

        UnsubscribeAction unsubscribeAction = new UnsubscribeAction();
        observer.add(Subscriptions.create(unsubscribeAction));
    }

    protected void onLocationClientReady() {
        LocationRequest locationRequest = buildLocationRequest();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                observer.onNext(location);
                observer.onCompleted();
            }
        };
        locationProviderAPI.requestLocationUpdates(googleAPIClient, locationRequest, locationListener);
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1);
        return locationRequest;
    }

    class APIConnectionCallbacks implements
            GoogleApiClient.OnConnectionFailedListener,
            GoogleApiClient.ConnectionCallbacks {

        @Override
        public void onConnected(Bundle bundle) {
            onLocationClientReady();
        }

        @Override
        public void onConnectionSuspended(int i) {
            observer.onCompleted();
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            observer.onError(new Throwable(connectionResult.toString()));
        }
    }

    class UnsubscribeAction implements Action0 {
        @Override
        public void call() {
            if (googleAPIClient.isConnected() || googleAPIClient.isConnecting()) {
                if (googleAPIClient.isConnected()) {
                    locationProviderAPI.removeLocationUpdates(googleAPIClient, locationListener);
                    googleAPIClient.disconnect();
                }
            }
        }
    }
}