# Space Demo

A simple demonstration for querying a list of SpaceX Launches and displaying a detailed view. This is mostly a demonstration of core functionality and architecture so the scope of features is limited.

Launch List                       |  Launch Details
:--------------------------------:|:-------------------------:
![Launch List](images/list.png)   |  ![Launch Details](images/details.png)

## Functionality

- Get a list of launches
- Sort by launch date or mission name
- Filter by Successful and Unsuccessful launches
- Network error handling with user retry

## Architecture

The demo app is implemented using a combination of clean architecture and an MVVM presentation layer in Kotlin.

### Clean Architecture

The clean architecture allows for a good separation of concerns between querying and storing data, processing of data, and presenting data into 3 distinct layers. Known as the data, domain, and presentation layers, each layer only communicates directly with one other. This helps to prevent changes in the data layer from affecting the presentation layer and vice versa. 

### Data Layer

The Data Layer of the application is responsible for data access and storage with local and remote resources. By design, the data layer contains all logic responsible for querying and saving data within the app. The data layer provides functionality to the domain layer through the use of data repositories. Each repository provides generic access to a data source while abstracting away the implementation details. Therefore, a SpaceX and NASA service could be provided through the same repository interface without exposing differences in their APIs.

### Domain Layer

The Domain layer of the app serves as the intermediary between the data and the presentation layer. It is responsible for interaction with the data layer and providing information to the presentation layer through the use of use cases. These Use Cases are small reusable pieces of business logic that can be used by themselves, or through composition, to provide greater functionality to the app.


### Presentation Layer & MVVM

The presentation layer contains the logic required for navigation and displaying data throughout the app. Each Activity and Fragment has an accompanying ViewModel that interacts with the domain layer to retrieve and then format data for display. Once formatted, the data is then presented to the view which can then place the data in the relevant layout. This allows for a separation between the querying of data and the layout of the data within the app. Events raised in the view are passed to the ViewModel to update the backing data.


## Assumptions & Notes

- Due to the size of the API results. Not all fields are mapped through the data and domain layer to assist in development time
- All fields are treated optional unless required for functionality
- UI Design is optimized for phones
- Assumed two calls required for launch details screen
- Assumed single network call for all launches