package com.zmarkan.observablelocation.observable;

import rx.functions.Action0;

/**
* Created by zan on 04/01/15.
*/
public class UnsubscribeAction implements Action0 {
    private Unsubscribable unsubscribable;

    public UnsubscribeAction(Unsubscribable unsubscribable) {
        this.unsubscribable = unsubscribable;
    }

    @Override
    public void call() {
        unsubscribable.unsubscribe();
    }
}
