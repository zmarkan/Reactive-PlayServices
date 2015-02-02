package com.zmarkan.observablelocation;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.zmarkan.observablelocation.observable.Unsubscribable;
import com.zmarkan.observablelocation.observable.UnsubscribeAction;

import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Created by zan on 04/01/15.
 */
public class UnsubscribeActionTest extends AndroidTestCase {
    
    @SmallTest
    public void test_unsubscribe_callsUnsubscribe(){
        
        Unsubscribable mockUnsubscribable = Mockito.mock(Unsubscribable.class);
        
        UnsubscribeAction sut = new UnsubscribeAction(mockUnsubscribable);
        sut.call();
        
        Mockito.verify(mockUnsubscribable, Mockito.only()).unsubscribe();
    }
}
