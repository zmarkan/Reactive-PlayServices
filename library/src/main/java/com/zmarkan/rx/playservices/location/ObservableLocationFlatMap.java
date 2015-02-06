package com.zmarkan.rx.playservices.location;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.zmarkan.rx.playservices.connection.ObservableConnection;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by njackson on 06/02/15.
 */
public class ObservableLocationFlatMap implements Func1<ObservableConnection.CONNECTION_STATUS, Observable<Location>> {

    private final GoogleApiClient mClient;
    private final LocationRequest mRequest;

    public ObservableLocationFlatMap(GoogleApiClient client, LocationRequest request) {
        mClient = client;
        mRequest = request;
    }

    @Override
    public Observable<Location> call(ObservableConnection.CONNECTION_STATUS connection_status) {
        return Observable.create(new ObservableLocation(mClient, LocationServices.FusedLocationApi, mRequest));
    }
}
