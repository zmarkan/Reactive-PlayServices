package com.zmarkan.rx.playservices.observable.connection;

import com.google.android.gms.common.ConnectionResult;

/**
 * Created by njackson on 04/02/15.
 */
class ThrowableConnectionResult extends Throwable {
    ConnectionResult mResult;

    ConnectionResult getResult() {
        return mResult;
    }

    ThrowableConnectionResult(String description, ConnectionResult result) {
        super(description);
        this.mResult = result;
    }
}