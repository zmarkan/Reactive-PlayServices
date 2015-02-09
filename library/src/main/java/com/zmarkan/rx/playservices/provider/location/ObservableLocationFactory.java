package com.zmarkan.rx.playservices.provider.location;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import rx.Observable;

/**
 * Created by njackson on 06/02/15.
 */
public interface ObservableLocationFactory  {
    public Observable<Location> getObservable(final LocationRequest locationRequest);
    public Observable<Location> getFirstObservable(final LocationRequest locationRequest);
}
