Reactive Play Services for Android
==================================

Library for Android that encapsulates Google Play Services interaction using RxJava, returning Observables.

###NOTE: this is pretty much still Readme-Driven-Development, so this readme will serve as a basis to libraries' implementation. Any not yet implemented features will be specified.

##Usage examples

```
LocationProvider locationProvider = new LocationProviderImpl(googlePlayApi, locationManager);

Observable<Geolocation> = locationProvider.getLocationUpdates() //get fast updates until canceled
locationProvider.getSingleAccurateUpdate()
locationProvider.getLastKnowLocation()

```

TODO: pretty much everything
