package com.zmarkan.rx.playservices.connection;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;

import org.junit.Test;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by njackson on 04/02/15.
 */
public class ThrowableConnectionResultTest {

    @Test
    public void test_InitialiseSetsMessage() {
        ThrowableConnectionResult sut = new ThrowableConnectionResult("some description", getConnectionResult());

        assertEquals("some description", sut.getMessage());
    }

    @Test
    public void test_InitialiseSetsConnection() {
        ConnectionResult result = getConnectionResult();
        ThrowableConnectionResult sut = new ThrowableConnectionResult("some description", result);

        assertEquals(result, sut.getResult());
    }

    private ConnectionResult getConnectionResult() {
        PendingIntent pendingIntent = mock(PendingIntent.class);
        return new ConnectionResult(0,pendingIntent);
    }

    private Context getMockContext() {
        return mock(Context.class);
    }
}
