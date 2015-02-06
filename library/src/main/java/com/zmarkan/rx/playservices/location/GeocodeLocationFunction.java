package com.zmarkan.rx.playservices.location;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;

import rx.exceptions.OnErrorThrowable;
import rx.functions.Func1;

/**
 * Created by zan on 06/02/15.
 */
public class GeocodeLocationFunction implements Func1<Location, List<Address>>{
    
    private final Geocoder mGeocoder;
    
    public GeocodeLocationFunction(Geocoder geocoder){
        mGeocoder = geocoder;
    }

    @Override
    public List<Address> call(Location location)  {
        try {
            return mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
        } catch (IOException e) {
            throw OnErrorThrowable.from(new GeocodingThrowable(e));
        }
    }
}
