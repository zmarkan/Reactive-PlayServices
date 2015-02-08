package com.zmarkan.location;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.zmarkan.rx.playservices.connection.ObservableConnection;
import com.zmarkan.rx.playservices.location.ObservableLocation;
import com.zmarkan.rx.playservices.location.ObservableLocationFlatMap;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by njackson on 06/02/15.
 */
public class ObservableLocationFlatMapTest {

    private GoogleApiClient mockAPIClient;
    private LocationRequest locationReqest;
    private ObservableLocationFlatMap sut;

    @Before
    public void setup() {
        mockAPIClient = mock(GoogleApiClient.class);
        locationReqest = new LocationRequest();
        sut = new ObservableLocationFlatMap(mockAPIClient,locationReqest);
    }

    @Test
    public void test_returnsObservableLocation() {
        Observable<Location> loc = sut.call(ObservableConnection.CONNECTION_STATUS.CONNECTED);

        assertNotNull(loc);
    }
}
