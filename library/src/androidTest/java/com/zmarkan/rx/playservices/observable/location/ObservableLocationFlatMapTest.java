package com.zmarkan.rx.playservices.observable.location;

import android.location.Location;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.zmarkan.rx.playservices.observable.connection.ObservableConnection;
import com.zmarkan.rx.playservices.provider.location.ObservableLocationFlatMap;

import rx.Observable;

import static org.mockito.Mockito.mock;

/**
 * Created by njackson on 06/02/15.
 */
public class ObservableLocationFlatMapTest extends AndroidTestCase {

    private GoogleApiClient mockAPIClient;
    private LocationRequest locationReqest;
    private ObservableLocationFlatMap sut;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockAPIClient = mock(GoogleApiClient.class);
        locationReqest = new LocationRequest();
        sut = new ObservableLocationFlatMap(mockAPIClient,locationReqest);
    }

    @SmallTest
    public void test_returnsObservableLocation() {
        Observable<Location> loc = sut.call(ObservableConnection.CONNECTION_STATUS.CONNECTED);

        assertNotNull(loc);
    }
}
