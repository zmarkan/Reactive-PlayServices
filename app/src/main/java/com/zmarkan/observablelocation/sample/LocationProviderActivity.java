package com.zmarkan.observablelocation.sample;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zmarkan.rx.playservices.provider.location.ObservableLocationFactoryImpl;
import com.zmarkan.rx.playservices.provider.location.ObservableLocationProvider;
import com.zmarkan.rx.playservices.provider.location.ObservableLocationProviderImpl;

import java.util.Date;

import observablelocation.zmarkan.com.observablelocation.R;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class LocationProviderActivity extends Activity {
    
    Button getUpdatesButton;
    Button unsubscribeButton;
    Button getSingleUpdateButton;
    
    TextView latitudeTextView;
    TextView longitudeTextView;
    TextView lastUpdatedTextView;
    
    private Subscription locationUpdatesSubscription;
    private ObservableLocationProvider observableLocationProvider;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        observableLocationProvider = new ObservableLocationProviderImpl(new ObservableLocationFactoryImpl(this));
        
        getUpdatesButton = (Button) findViewById(R.id.button_get_updates);
        unsubscribeButton = (Button) findViewById(R.id.button_unsubscribe);
        getSingleUpdateButton = (Button) findViewById(R.id.button_get_single_update);
        latitudeTextView = (TextView) findViewById(R.id.textview_latitude);
        longitudeTextView = (TextView) findViewById(R.id.textview_longitude);
        lastUpdatedTextView = (TextView) findViewById(R.id.textview_last_updated);
        
        
        getUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationUpdatesSubscription = 
                        observableLocationProvider.provideLocationUpdates()
                                .subscribe(
                                        new LocationUpdatedAction(),
                                        new LocationErrorAction(),
                                        new LocationUpdatesCompleteAction());
            }
        });
        
        unsubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locationUpdatesSubscription != null){
                    locationUpdatesSubscription.unsubscribe();
                }
            }
        });
        
        getSingleUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationUpdatesSubscription =
                        observableLocationProvider.provideSingleLocationUpdate()
                                .subscribe(
                                        new LocationUpdatedAction(),
                                        new LocationErrorAction(),
                                        new LocationUpdatesCompleteAction());
            }
        });
    }
    
    class LocationUpdatedAction implements Action1<Location>{
        @Override
        public void call(Location location) {
            Log.d(LocationProviderActivity.this.getClass().getSimpleName(), "location");

            String lastUpdated = "Last updated: " + new Date().getTime()/1000;
            longitudeTextView.setText(String.valueOf(location.getLongitude()));
            latitudeTextView.setText(String.valueOf(location.getLatitude()));
            lastUpdatedTextView.setText(lastUpdated);
        }
    }
    
    class LocationErrorAction implements Action1<Throwable>{
        @Override
        public void call(Throwable throwable) {
            String lastError = "Error: " + new Date().getTime()/1000;
            lastUpdatedTextView.setText(lastError);
            Log.d(LocationProviderActivity.this.getClass().getSimpleName(), throwable.getMessage());
        }
    }
    
    class LocationUpdatesCompleteAction implements Action0{
        @Override
        public void call() {
            Log.d(LocationProviderActivity.this.getClass().getSimpleName(), "completed");
            String lastCompleted = "Completed: " + new Date().getTime()/1000;
            lastUpdatedTextView.setText(lastCompleted);
        }
    }
}
