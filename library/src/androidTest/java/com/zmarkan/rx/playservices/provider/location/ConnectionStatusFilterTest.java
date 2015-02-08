package com.zmarkan.rx.playservices.provider.location;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.zmarkan.rx.playservices.observable.connection.ObservableConnection;

/**
 * Created by njackson on 06/02/15.
 */
public class ConnectionStatusFilterTest extends AndroidTestCase {

    private ConnectionStatusFilter sut;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        sut = new ConnectionStatusFilter();
    }

    @SmallTest
    public void test_returnsTrueWhenConnectionStatusConnected() {
        assertTrue(sut.call(ObservableConnection.CONNECTION_STATUS.CONNECTED));
    }

    @SmallTest
    public void test_returnsFalseWhenNOTConnectionStatusConnected() {
        assertFalse(sut.call(ObservableConnection.CONNECTION_STATUS.SUSPENDED));
    }

}
