android-observable-location
===========================

Library for Android that encapsulates location retrieval using RxJava, returning Observables.

Note there is already a reactive location library but doesn't work with the latest play services, and doesn't have any tests. I intend to make it a production-ready library. 

###NOTE: this is pretty much still Readme-Driven-Development, so this readme will serve as a basis to libraries' implementation. Any not yet implemented features will be specified.

##Usage examples

```
LocationProvider locationProvider = new LocationProviderImpl(googlePlayApi, locationManager);

Observable<Geolocation> = locationProvider.getLocationUpdates() //get fast updates until canceled
locationProvider.getSingleAccurateUpdate()
locationProvider.getLastKnowLocation()

```

plus geofencing, reverse geocode, etc... everything the Google play location library gives us.

