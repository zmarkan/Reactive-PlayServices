package com.zmarkan.rx.playservices.provider.location;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.google.android.gms.location.LocationRequest;

import org.mockito.ArgumentCaptor;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by njackson on 06/02/15.
 */
public class ObservableLocationProviderImplTest extends AndroidTestCase {

    private ObservableLocationFactory mockFactory;
    private ObservableLocationProvider sut;
    private Observable mockObservable;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().toString());

        mockFactory = mock(ObservableLocationFactory.class);
        mockObservable = mock(Observable.class);
        when(mockFactory.getObservable(any(LocationRequest.class))).thenReturn(mockObservable);

        sut = new ObservableLocationProviderImpl(mockFactory);
    }

    @SmallTest
    public void test_ProvideLocationUpdatesWithRequestCallsFactoryCreate() {
        LocationRequest request = new LocationRequest();
        sut.provideLocationUpdates(request);

        verify(mockFactory, times(1)).getObservable(request);
    }

    @SmallTest
    public void test_ProvideSingleLocationUpdatesWithRequestCallsFactoryCreate() {
        LocationRequest request = new LocationRequest();
        sut.provideSingleLocationUpdate(request);

        verify(mockFactory, times(1)).getFirstObservable(request);
    }

    @SmallTest
    public void test_ProvideLocationUpdatesCallsFactoryGetObservable() {
        ArgumentCaptor<LocationRequest> captor = ArgumentCaptor.forClass(LocationRequest.class);
        sut.provideLocationUpdates();

        verify(mockFactory, times(1)).getObservable(captor.capture());

        assertEquals(10000,captor.getValue().getInterval());
    }

    @SmallTest
    public void test_ProvideSingleLocationUpdatesCallsFactoryGetFirstObservable() {
        ArgumentCaptor<LocationRequest> captor = ArgumentCaptor.forClass(LocationRequest.class);
        sut.provideSingleLocationUpdate();

        verify(mockFactory, times(1)).getFirstObservable(captor.capture());
        assertEquals(10000,captor.getValue().getInterval());
    }
}
