package com.zmarkan.location;

import android.location.Geocoder;
import android.location.Location;

import com.zmarkan.rx.playservices.location.GeocodeLocationFunction;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import rx.exceptions.OnErrorThrowable;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by zan on 06/02/15.
 */
public class GeocodeLocationFunctionTest {
    
    Geocoder mockGeocoder;
    Location mockLocation;
    
    private GeocodeLocationFunction sut;
    
    @Before
    public void setUp() throws Exception{
        mockGeocoder = mock(Geocoder.class);
        mockLocation = mock(Location.class, RETURNS_DEFAULTS);
        
        sut = new GeocodeLocationFunction(mockGeocoder);
    }
    
    @Test
    public void test_callGeocoderToReturnListOfAddresses() throws IOException {
       
        sut.call(mockLocation);
        verify(mockGeocoder, times(1)).getFromLocation(anyDouble(), anyDouble(), anyInt());
    }
    
    @Test(expected = OnErrorThrowable.class)
    public void test_callWhenExceptionIsThrownThrowOnErrorThrowable() throws IOException {
        
        when(mockGeocoder.getFromLocation(anyDouble(), anyDouble(), anyInt())).thenThrow(new IOException());
        sut.call(mockLocation);
    }
}
