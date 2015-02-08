package com.zmarkan.rx.playservices.location;

import com.zmarkan.rx.playservices.connection.ObservableConnection;

import rx.functions.Func1;

public class ConnectionStatusFilter implements Func1<ObservableConnection.CONNECTION_STATUS, Boolean> {
    @Override
    public Boolean call(ObservableConnection.CONNECTION_STATUS connection_status) {
        return connection_status.compareTo(ObservableConnection.CONNECTION_STATUS.CONNECTED) == 0;
    }
}