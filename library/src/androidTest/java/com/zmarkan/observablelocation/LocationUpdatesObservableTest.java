package com.zmarkan.observablelocation;

import android.location.Location;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by zan on 26/12/14.
 */
public class LocationUpdatesObservableTest extends InstrumentationTestCase {

    private static final String PROVIDER = "flp";
    private static final double LAT = 37.377166;
    private static final double LNG = -122.086966;
    private static final float ACCURACY = 3.0f;

    GoogleApiClient mockGoogleAPIClient;

    FusedLocationProviderApi mockLocationProvider;

    private LocationUpdatesObservable sut;
    private TestSubscriber<Location> testSubscriber;

    private ArgumentCaptor<LocationUpdatesObservable.APIConnectionCallbacks> connectionCallbackCaptor;
    private ArgumentCaptor<LocationListener> locationListenerCaptor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setupMocks();

        sut = new LocationUpdatesObservable(mockGoogleAPIClient, mockLocationProvider);

        testSubscriber = new TestSubscriber<>();

        connectionCallbackCaptor =
                ArgumentCaptor.forClass(LocationUpdatesObservable.APIConnectionCallbacks.class);
        locationListenerCaptor =
                ArgumentCaptor.forClass(LocationListener.class);
    }

    private void setupMocks() {
        mockGoogleAPIClient = mock(GoogleApiClient.class);
        mockLocationProvider = mock(FusedLocationProviderApi.class);
    }

    @SmallTest
    public void test_callSubscribe_startsReceivingLocationUpdates(){
        sut.call(testSubscriber);
        verify(mockGoogleAPIClient).registerConnectionCallbacks(
                connectionCallbackCaptor.capture());
        connectionCallbackCaptor.getValue().onConnected(null);
        verify(mockLocationProvider).requestLocationUpdates(
                any(GoogleApiClient.class),
                any(LocationRequest.class),
                locationListenerCaptor.capture());
        locationListenerCaptor.getValue().onLocationChanged(createLocation(LAT, LNG, ACCURACY));

        assertEquals(1, testSubscriber.getOnNextEvents().size());
    }

    @SmallTest
    public void test_callSubscribe_completeAfterFirstUpdate(){
        sut.call(testSubscriber);
        verify(mockGoogleAPIClient).registerConnectionCallbacks(
                connectionCallbackCaptor.capture());
        connectionCallbackCaptor.getValue().onConnected(null);
        verify(mockLocationProvider).requestLocationUpdates(
                any(GoogleApiClient.class),
                any(LocationRequest.class),
                locationListenerCaptor.capture());

        locationListenerCaptor.getValue().onLocationChanged(createLocation(LAT, LNG, ACCURACY));

        assertEquals(1, testSubscriber.getOnCompletedEvents().size());
    }

    @SmallTest
    public void test_callSubscribeWhenPlayServicesErrors_callsOnError(){
        ConnectionResult failedConnection = new ConnectionResult(ConnectionResult.DEVELOPER_ERROR, null);

        sut.call(testSubscriber);
        verify(mockGoogleAPIClient).registerConnectionFailedListener(connectionCallbackCaptor.capture());
        connectionCallbackCaptor.getValue().onConnectionFailed(failedConnection);

        assertEquals(1, testSubscriber.getOnErrorEvents().size());
    }

    @SmallTest
    public void test_unsubscribeUnregistersWhenGoogleApiConnected() {
        when(mockGoogleAPIClient.isConnected()).thenReturn(true);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockLocationProvider,times(1)).removeLocationUpdates(any(GoogleApiClient.class), any(LocationListener.class));
    }

    @SmallTest
    public void test_unsubscribeDisconnectsWhenGoogleApiConnected() {
        when(mockGoogleAPIClient.isConnected()).thenReturn(true);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockGoogleAPIClient,times(1)).disconnect();
    }

    @SmallTest
    public void test_unsubscribeUnregistersWhenGoogleApiConnecting() {
        when(mockGoogleAPIClient.isConnecting()).thenReturn(true);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockLocationProvider,times(1)).removeLocationUpdates(any(GoogleApiClient.class), any(LocationListener.class));
    }


    @SmallTest
    public void test_unsubscribeDisconnectsWhenGoogleApiConnecting() {
        when(mockGoogleAPIClient.isConnecting()).thenReturn(true);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockGoogleAPIClient,times(1)).disconnect();
    }

    @SmallTest
    public void test_unsubscribeDoesNotUnregistersWhenGoogleApiNotConnectedOrConnecting() {
        when(mockGoogleAPIClient.isConnected()).thenReturn(false);
        when(mockGoogleAPIClient.isConnecting()).thenReturn(false);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockLocationProvider,times(0)).removeLocationUpdates(any(GoogleApiClient.class), any(LocationListener.class));
    }

    @SmallTest
    public void test_unsubscribeDoesNotDisconnectsWhenGoogleApiNotConnected() {
        when(mockGoogleAPIClient.isConnected()).thenReturn(false);
        when(mockGoogleAPIClient.isConnecting()).thenReturn(false);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockGoogleAPIClient,times(0)).disconnect();
    }

    public Location createLocation(double lat, double lng, float accuracy) {
        Location newLocation = new Location(PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }
}
