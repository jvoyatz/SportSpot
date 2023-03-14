# SportSpot
The application is an upcoming sport events viewer which allows users to check sport events about to start in the near future.

---

## Basic stuff

+ Endpoint: 
  [https://618d3aa7fe09aa001744060a.mockapi.io/api/sports] (https://618d3aa7fe09aa001744060a.mockapi.io/api/sports)
+ Functionalities:
  1. Events should scroll horizontally, each row representing a different sport.
  2. Users should be allowed to mark an event as a favourite and favourites should
    appear first in their respective row. You do not need to persist them after the
    application is killed, marking them volatilely as favourites for a single run is enough.
  3. Each event must display a countdown to its starting time.
  4. Users should be able to collapse and expand events per sport.
+ MinSDK: 21

## Implementation Notes

### App architecture
Used a clean approach impl, following the Dependency Inversion Principle as well as
i tried to avoid building a monolith app. How? Provided extra modules for the main app's
feature, same for its corresponding data layer (for this feature).
Of course, this does not work a well for a small app like this one.

    HOME:-- data: sport_events --> datasources:net, datasources:db
   /     \- presentation:home
APP -- core etc.

 + **Domain Layer**
    * `domain`: contains the use cases and the business logic needed in this app
    * `model`: contains the models that needed (and used) across the app
 + **Data Layer**
   + sport_events dir
     + `repo layer`
     + `source-db`
     + `source-net`
 + **Presentation (or UI) Layer**
   + `home` screen layer
 + **Core Layer** contains common functionality used throughout the project
   + `db` layer
   + `network` layer
   + `testing` layer
   + `di`
   + `common` 
 + `app` layer


### Other Notes
+ Used Dependency Injection with Dagger Hilt
+ Navigation Component (even if it was needed based on the assignment, but we need for sure a nav_graph in case of multiple modules)
+ Repository pattern for data fetching, using two datasources (api/db) and providing them through constructor injection
+ Retrofit for networking, added two `safeApiCall` and an enhanced ApiResponse class (didn't have time to provide my own Retrofit adapter)
+ Used Room-DB for in memory cache. (In case my time was not limited, i 've thought to provide offline support for the app until the net is back on? and i would delete the data in app start and i would use soumething line Syncinitializer, anyway. )
+ Using flows for providing using streams
+ Wrapping tasks into flows, see ResultData
+ Using MVI arch for handling the data provided to the ui. That means that UiState sealed classes exist in the code as well as a single point of taking care of user actions
+ Used ListAdapters for the recyclerviews, supporting multiple view types in the first. Tried to config the adapters to 'play together' meaning sharing the same pool that i ran into some problems, so i commented out. For sure i would like to discuss that with you.
+ Tweaked as much as i coould my layouts and my constraints in order to avoid flickering effects etc
+ I provided some unit tests and a few instrumented tests in the lower layers of the code. Didn't have actually time to provide tests for viewmodel. As an approach i would say, that since i can play with fake or mock dependencies, it is easy to provide tests for the viewmodel as well as for the use cases. In the end a pretty integration test (meaning testing two or more components together) could be written. I would mix mocking with fakes (mocking for datasources), fakes for the rest.
+ I ve written test using the mock server dep.
+ Supporting process death using savedStateHandle and flows.

*Thanks for making me challenge myself, i m looking forward to hearing from you*


### Libraries or concepts used

+ Build Configuration
  + gradle scripts as kotlin dsl scripts
    [check this](https://developer.android.com/studio/build/migrate-to-kts) as a quick reference
  + version catalog for better dependency management
  + [gitignore](https://github.com/github/gitignore/blob/main/Android.gitignore)
 


  
