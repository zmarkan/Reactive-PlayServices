package com.zmarkan.rx.playservices.observable.connection;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by njackson on 02/02/15.
 */
public class ObservableConnection implements Observable.OnSubscribe<ObservableConnection.CONNECTION_STATUS>, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "GooglePlayConnectionObservable";

    public enum CONNECTION_STATUS {
        CONNECTED,
        SUSPENDED
    }

    private final ObservableConnection thisClass = this;
    private final GoogleApiClient googleAPIClient;
    private Subscriber<? super CONNECTION_STATUS> observer;

    public ObservableConnection(GoogleApiClient client) {
        this.googleAPIClient = client;
    }

    @Override
    public void call(Subscriber<? super CONNECTION_STATUS> subscriber) {
        observer = subscriber;
        googleAPIClient.registerConnectionCallbacks(this);
        googleAPIClient.registerConnectionFailedListener(this);
        googleAPIClient.connect();

        UnsubscribeAction unsubscribeAction = new UnsubscribeAction();
        observer.add(Subscriptions.create(unsubscribeAction));
    }

    @Override
    public void onConnected(Bundle bundle) {
        observer.onNext(CONNECTION_STATUS.CONNECTED);
    }

    @Override
    public void onConnectionSuspended(int i) {
        observer.onNext(CONNECTION_STATUS.SUSPENDED);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        observer.onError(new ThrowableConnectionResult(connectionResult.toString(), connectionResult));
    }

    class UnsubscribeAction implements Action0 {
        @Override
        public void call() {
            if (googleAPIClient.isConnected() || googleAPIClient.isConnecting()) {
                googleAPIClient.disconnect();
            }
            googleAPIClient.unregisterConnectionFailedListener(thisClass);
            googleAPIClient.unregisterConnectionCallbacks(thisClass);
        }
    }

}
