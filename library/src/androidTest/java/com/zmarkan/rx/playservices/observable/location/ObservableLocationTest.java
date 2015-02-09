package com.zmarkan.rx.playservices.observable.location;

import android.location.Location;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import org.mockito.ArgumentCaptor;

import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by zan on 26/12/14.
 */
public class ObservableLocationTest extends AndroidTestCase {

    private static final String PROVIDER = "flp";
    private static final double LAT = 37.377166;
    private static final double LNG = -122.086966;
    private static final float ACCURACY = 3.0f;

    GoogleApiClient mockGoogleAPIClient;

    FusedLocationProviderApi mockLocationProvider;

    private ObservableLocation sut;
    private TestSubscriber<Location> testSubscriber;

    private ArgumentCaptor<LocationListener> locationListenerCaptor = ArgumentCaptor.forClass(LocationListener.class);
    private LocationRequest locationRequest;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setupMocks();

        locationRequest = buildLocationRequest();
        sut = new ObservableLocation(mockGoogleAPIClient, mockLocationProvider, locationRequest);

        testSubscriber = new TestSubscriber<>();
    }

    private void setupMocks() {
        mockGoogleAPIClient = mock(GoogleApiClient.class);
        mockLocationProvider = mock(FusedLocationProviderApi.class);
    }

    @SmallTest
    public void test_onSubscribeAddsLocationListener() {
        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockLocationProvider,times(1)).requestLocationUpdates(any(GoogleApiClient.class), any(LocationRequest.class), any(LocationListener.class));
    }

    @SmallTest
    public void test_callSubscribe_startsReceivingLocationUpdates(){
        sut.call(testSubscriber);
        verify(mockLocationProvider, times(1)).requestLocationUpdates(
                any(GoogleApiClient.class),
                any(LocationRequest.class),
                locationListenerCaptor.capture());
        locationListenerCaptor.getValue().onLocationChanged(createLocation(LAT, LNG, ACCURACY));

        assertEquals(1, testSubscriber.getOnNextEvents().size());
    }

    @SmallTest
    public void test_unsubscribeRemovesLocationListener() {
        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockLocationProvider,times(1)).removeLocationUpdates(any(GoogleApiClient.class), any(LocationListener.class));
    }

    private Location createLocation(double lat, double lng, float accuracy) {
        Location newLocation = new Location("O");//mock(Location.class, RETURNS_DEFAULTS);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
}