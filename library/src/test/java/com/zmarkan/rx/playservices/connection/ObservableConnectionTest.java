package com.zmarkan.rx.playservices.connection;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by zan on 26/12/14.
 */
public class ObservableConnectionTest {

    private static final String PROVIDER = "flp";
    private static final double LAT = 37.377166;
    private static final double LNG = -122.086966;
    private static final float ACCURACY = 3.0f;

    GoogleApiClient mockGoogleAPIClient;

    FusedLocationProviderApi mockLocationProvider;

    private ObservableConnection sut;
    private TestSubscriber<ObservableConnection.CONNECTION_STATUS> testSubscriber;

    @Before
    public void setUp() throws Exception {
        setupMocks();

        sut = new ObservableConnection(mockGoogleAPIClient);

        testSubscriber = new TestSubscriber<>();
    }

    private void setupMocks() {
        mockGoogleAPIClient = mock(GoogleApiClient.class);
        mockLocationProvider = mock(FusedLocationProviderApi.class);
    }

    @Test
    public void test_unsubscribeDisconnectsWhenGoogleApiConnected() {
        when(mockGoogleAPIClient.isConnected()).thenReturn(true);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockGoogleAPIClient,times(1)).disconnect();
    }

    @Test
    public void test_unsubscribeDisconnectsWhenGoogleApiConnecting() {
        when(mockGoogleAPIClient.isConnecting()).thenReturn(true);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockGoogleAPIClient,times(1)).disconnect();
    }

    @Test
    public void test_unsubscribeDoesNotUnregistersWhenGoogleApiNotConnectedOrConnecting() {
        when(mockGoogleAPIClient.isConnected()).thenReturn(false);
        when(mockGoogleAPIClient.isConnecting()).thenReturn(false);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockLocationProvider,times(0)).removeLocationUpdates(any(GoogleApiClient.class), any(LocationListener.class));
    }

    @Test
    public void test_unsubscribeDoesNotDisconnectsWhenGoogleApiNotConnected() {
        when(mockGoogleAPIClient.isConnected()).thenReturn(false);
        when(mockGoogleAPIClient.isConnecting()).thenReturn(false);

        sut.call(testSubscriber);
        testSubscriber.unsubscribe();

        verify(mockGoogleAPIClient,times(0)).disconnect();
    }
}