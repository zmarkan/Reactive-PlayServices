package com.zmarkan.location;

import com.google.android.gms.location.LocationRequest;
import com.zmarkan.rx.playservices.location.ObservableLocationFactory;
import com.zmarkan.rx.playservices.location.ObservableLocationProvider;
import com.zmarkan.rx.playservices.location.ObservableLocationProviderImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by njackson on 06/02/15.
 */
public class ObservableLocationProviderImplTest {

    private ObservableLocationFactory mockFactory;
    private ObservableLocationProvider sut;
    private Observable mockObservable;

    @Before
    public void setUp() {
        mockFactory = mock(ObservableLocationFactory.class);
        mockObservable = mock(Observable.class);
        when(mockFactory.getObservable(any(LocationRequest.class))).thenReturn(mockObservable);

        sut = new ObservableLocationProviderImpl(mockFactory);
    }

    @Test
    public void test_ProvideLocationUpdatesWithRequestCallsFactoryCreate() {
        LocationRequest request = new LocationRequest();
        sut.provideLocationUpdates(request);

        verify(mockFactory, times(1)).getObservable(request);
    }

    @Test
    public void test_ProvideSingleLocationUpdatesWithRequestCallsFactoryCreate() {
        LocationRequest request = new LocationRequest();
        sut.provideSingleLocationUpdate(request);

        verify(mockFactory, times(1)).getFirstObservable(request);
    }

    @Test
    public void test_ProvideLocationUpdatesCallsFactoryGetObservable() {
        ArgumentCaptor<LocationRequest> captor = ArgumentCaptor.forClass(LocationRequest.class);
        sut.provideLocationUpdates();

        verify(mockFactory, times(1)).getObservable(captor.capture());

        assertEquals(10000,captor.getValue().getInterval());
    }

    @Test
    public void test_ProvideSingleLocationUpdatesCallsFactoryGetFirstObservable() {
        ArgumentCaptor<LocationRequest> captor = ArgumentCaptor.forClass(LocationRequest.class);
        sut.provideSingleLocationUpdate();

        verify(mockFactory, times(1)).getFirstObservable(captor.capture());
        assertEquals(10000,captor.getValue().getInterval());
    }
}
