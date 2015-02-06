package com.zmarkan.rx.playservices.location;

import java.io.IOException;

/**
 * Created by zan on 06/02/15.
 */
public class GeocodingThrowable extends Throwable {
    IOException mException;
    
    public GeocodingThrowable(IOException e){
        super(e.getMessage());
        mException = e;
    }
}
