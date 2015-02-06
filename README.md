Reactive Play Services for Android
==================================

A library that streamlines Google Play Services interaction using RxJava, returning Observables.

###NOTE: this is pretty much still Readme-Driven-Development, so this readme will serve as a basis to libraries' implementation. Any not yet implemented features will be specified.

##Motivation
Play Services are awesome addition to Android, but their APIs are ugly. We're trying to present them in a nicer format for easier usage using RxJava.

##APIs supported
- g+ Sign in
- location
- google fit

##Usage examples

- Location:

```
LocationProvider locationProvider = new LocationProviderImpl(googlePlayApi, locationManager);

Observable<Geolocation> = locationProvider.getLocationUpdates() //get fast updates until canceled
locationProvider.getSingleAccurateUpdate()
locationProvider.getLastKnowLocation()

```

TODO: pretty much everything
