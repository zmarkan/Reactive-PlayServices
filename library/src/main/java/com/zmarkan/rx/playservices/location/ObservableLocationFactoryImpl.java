package com.zmarkan.rx.playservices.location;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.zmarkan.rx.playservices.connection.ObservableConnection;

import rx.Observable;

/**
 * Created by njackson on 06/02/15.
 */
public class ObservableLocationFactoryImpl implements ObservableLocationFactory{

    private final Context mContext;
    private final GoogleApiClient mGoogleApiClient;

    public ObservableLocationFactoryImpl(Context context) {
        mContext = context;
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public Observable<Location> getObservable(final LocationRequest locationRequest) {
        return Observable.create(new ObservableConnection(mGoogleApiClient))
                .filter(new ConnectionStatusFilter())
                .flatMap(new ObservableLocationFlatMap(mGoogleApiClient, locationRequest));
    }

    @Override
    public Observable<Location> getFirstObservable(LocationRequest locationRequest) {
        return getObservable(locationRequest).first();
    }
}
