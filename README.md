# Backend coding challenge UP42- Overview
This application aka Feature Service currently supports two GET endpoints and reads feature data from JSON file.

The application has four distinct layers after the refactoring and step-by-step layer implementation was being done.
## Steps followed for refactoring & API implementation:
1. The project structure was modified to separate concerns and give individual responsibilities to each layer as per the above diagram. Missing layers were added and thereby extended later during the course of implementation.
2. ### Data Layer:
      - The internal model objects from FeatureController class were moved to the data layer. This layer was not completely refactored as there was a plan to add DTOs while the web layer would be developed so it was a TODO.
      - FeatureController would use the reference of the model objects for now
3. ### Data Source Layer:
      - The purpose of this layer is to interact with Data repository and in this case we would be using a JSON file as the source of data.
      - FeatureDataSource interface was created initially with one method for retrieving all features and then another abstract method was added for retrieving a single feature by ID.
      - The above interface was implemented by FeatureFile DataSource repository with the responsibility of populating a local Allfeatures cache on JVM startup at the time of instantiation of this repository bean.
      - File reading, Feature retrieval and JSON parsing logic was moved from the Feature Controller to Feature Util class with custom exceptions added for JSON parsing and File not found errors.
      -	FeatureFileDataSource repository would in-turn use utility methods from FileUtil for retrieving features
      -	Junit tests were concurrently added too while the repository layer was being developed.
4. ### Service Layer:
      -	The purpose of this layer is to have any kind of business logic. Since the existing getFeatures endpoint was straightforward it largely skipped most of the business logic but as the service grows and more endpoints are added this layer would be heavily loaded with business logic.
      -	Feature service was implemented which would be interacting with the datasource (file datasource in our case as Database is not present).
      -	This service would have two APIs, one for getting all features and the other one for getting the quicklook image for a requested feature Id.
      -	Junit tests using mockK were concurrently added too while the service layer was being developed.
5. ### Web Layer:
      - This layer serves as the entry point for a REST service and has the two API endpoints mapped to controller methods.
      - MockMvc and rest-assured is used to unit test & perform integration testing of the controller endpoints.
      - Swagger was added to describe and tag the API endpoints.
      - Aspect programming is used to debug the request and response data that flow through the endpoints and also to log any underlying errors.
   
6. Finally, I decided to refactor the Data Layer and use Feature object of org.geojson library that will store all the properties for each feature read in the cache and use a Feature DTO to send requested data to the client.

## Feature retrieval mechanism:
1.	The FeatureFile DataSource has the core logic of this service as it is responsible to load all the features from the supplied JSON file.
2.	The loaded features will have all the properties once the JSON parsing and reading is successfully completed using Utility helper methods.
3.	The requested feature properties can be then read from the local cache for each feature and passed to the client request for GetAllFeatures.
4.	The idea of storing all properties in the local cache helped in implementing the get quicklook endpoint as it was a separate property.
5.	Furthermore when other properties are needed it can be done easily by querying the local cache.

## Further improvements that can be done in the service:
1.	Authentication & Authorization needs to be implemented.
2.	Pagination & Filtering capability can be added to GetAllFeatures endpoint.
3.	Database can be configured for storing Features metadata. Feature Data source can be extended for CRUD operations providing a new repository implementation.
4.	There should be a circuit breaker for long pending requests from client if server is not responding for a certain pre-determined amount of time.
5.	Monitoring Dashboard like Grafana can also be added to read specs pushed to Prometheus using Springboot actuator.

## How to run
There are two ways to start a service: with Gradle or with Docker.

### Docker
To start with Docker you need to execute the command:
```docker-compose up``` from the root directory.

### Gradle
To start with Gradle you need to execute the command:
```gradlew bootRun``` from the root directory.

When the service is up, it is available with URL http://localhost:8080/api/features

## Swagger:
Swagger endpoint:
http://localhost:8080/swagger-ui.html
