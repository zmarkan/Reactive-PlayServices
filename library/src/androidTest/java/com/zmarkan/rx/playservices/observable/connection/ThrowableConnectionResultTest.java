package com.zmarkan.rx.playservices.observable.connection;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.google.android.gms.common.ConnectionResult;

import static org.mockito.Mockito.*;

/**
 * Created by njackson on 04/02/15.
 */
public class ThrowableConnectionResultTest extends AndroidTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().toString());
    }

    @SmallTest
    public void test_InitialiseSetsMessage() {
        ThrowableConnectionResult sut = new ThrowableConnectionResult("some description", getConnectionResult());

        assertEquals("some description", sut.getMessage());
    }

    @SmallTest
    public void test_InitialiseSetsConnection() {
        ConnectionResult result = getConnectionResult();
        ThrowableConnectionResult sut = new ThrowableConnectionResult("some description", result);

        assertEquals(result, sut.getResult());
    }

    private ConnectionResult getConnectionResult() {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,new Intent("STUFF"),0);
        return new ConnectionResult(0,pendingIntent);
    }

    private Context getMockContext() {
        return mock(Context.class);
    }
}
