package com.zmarkan.location;

import com.zmarkan.rx.playservices.connection.ObservableConnection;
import com.zmarkan.rx.playservices.location.ConnectionStatusFilter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by njackson on 06/02/15.
 */
public class ConnectionStatusFilterTest {

    private ConnectionStatusFilter sut;

    @Before
    public void setup() {
        sut = new ConnectionStatusFilter();
    }

    @Test
    public void test_returnsTrueWhenConnectionStatusConnected() {
        assertEquals(true, sut.call(ObservableConnection.CONNECTION_STATUS.CONNECTED));
    }

    @Test
    public void test_returnsFalseWhenNOTConnectionStatusConnected() {
        assertEquals(false, sut.call(ObservableConnection.CONNECTION_STATUS.SUSPENDED));
    }

}
