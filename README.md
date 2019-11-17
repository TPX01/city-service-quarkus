# Quarkus Sample

This code example demonstrates the key features and strenghts of Quarkus. Got some improvements in mind?
Create a pull request ;)

## Work in progress :muscle:
* Add FlyWay

## Features :sparkles:
* Contract first OpenAPI generation (framework: OpenAPI Maven Codegen)
* Architecture testing to validate package boundaries (framework: ArchUnit)
* JPA repository interface (framework: Panache)
* Unit integration test (framework: RestAssured)
* Mapping between layer boundaries (framework: MapStruct)
* Rest service consumption
* SpanID used for apiError handling
* .. So much more :)

## Getting started :running:

### Intellij
1. 'Add new configuration > Maven'
2. You can add following command line
 `compile quarkus:dev -DZIPCODE_SERVICE_URL=http://localhost:8080 -DJAEGER_HOST_PORT=localhost`
 
Alternatively you can also start quarkus using the quarkus maven plugin by executing 'quarkus:dev"

### Using the jar
Simply run this command from the project root folder:
```
java -DZIPCODE_SERVICE_URL=http://localhost:8080 -DJAEGER_HOST_PORT=localhost -jar target/*-runner.jar
```

### Maven
You can also easily run it using this maven command:
```
   mvn quarkus:dev -Dthorntail.properties.zipcode-service.url=http://localhost:8080
```

## Demo time :eyes:
### Endpoints

* A rest service is exposed that you can test (HTTP GET) which should return a list of cities for the country that you specified.

  ⟶ http://localhost:8080/countries/BE/cities

* Metrics are also provided (Prometheus). Navigate to following URL to consume this information

  ⟶ http://localhost:8080/metrics

* Application health status is available on a dedicated endpoint. This can be extended with custom applicative health checks.

  ⟶ http://localhost:8080/health
  
  ⟶ http://localhost:8080/health/live
  
  ⟶ http://localhost:8080/health/ready

* REST contract defined by service can be consulted on dedicated endpoint. Useful for service catalog in the future but can be viewed in editor.swagger.io for now.

  ⟶ http://localhost:8080/openapi
  
  ⟶ http://localhost:8080/swagger-ui (only in dev mode)

=== Exception handling

We've embedded apiError handling in this service so that the consumer does not get any stacktraces

* In case the user did not specify the correct input value we throw a validation error:

  ⟶ http://localhost:8080/countries/BEL/cities

* If an apiError occurs, like a null pointer apiError, we catch it and throw an internal server error to the consumer.

  ⟶ http://localhost:8080/countries/NULL/cities

## Docker :rocket:

### Build 
There are two options to build your docker image for Quarkus. Either we use a JVM build or we build it natively. The native build takes a big hit on your machine (don't say I warned you).

#### JVM

This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode

Before building the docker image run:

`$ mvn package`

Then, build the image with:

`$ docker build -f Dockerfile.jvm -t city-service-quarkus .`

Then run the container using:

`$ docker run -i --rm -p 8080:8080 -e ZIPCODE_SERVICE_URL=http://localhost:8080 city-service-quarkus`

#### Native

This Dockerfile is used in order to build a container that runs the Quarkus application in native (no JVM) mode

Before building the docker image run:

`$ mvn package -Pgenerate-api,native -Dquarkus.native.container-build=true`

Creating these native images takes up a lot of compute power. To avoid it going OOM you can add following configuration to the maven command: `-Dnative-image.xmx=5g`

Then, build the image with:

`$ docker build -f Dockerfile.native -t city-service-quarkus-native .`

Then run the container using:

`$ docker run -i --rm -p 8080:8080 city-service-quarkus-native`

### Deploy :scream:

#### Docker-compose

Navigate to the repository folder and type:

`docker-compose up -d`

Everything will be configured for you. To stop all containers use following command:

`docker-compose down`

#### Docker run

Then run the container using:
* JVM build ⟶ `$ docker run -i --rm -p 8080:8080 -e ZIPCODE_SERVICE_URL=http://localhost:8080 city-service-quarkus`
* Native build ⟶ `$ docker run -i --rm -p 8080:8080 -e ZIPCODE_SERVICE_URL=http://localhost:8080 city-service-quarkus-native`

Jaeger can be started via Docker as follows:

```
docker run \
    --rm \
    -p5775:5775/udp \
    -p6831:6831/udp \
    -p6832:6832/udp \
    -p5778:5778 \
    -p16686:16686 \
    -p14268:14268 \
    --name=jaeger \
    jaegertracing/all-in-one:latest
```

Or, if you do not have docker available on your machine you can also start it using the provided binary.
Download it on: https://www.jaegertracing.io/download/#binaries

Execute jaeger in cmd:
```
PATH_HERE_TO_BINARY_HERE/jaeger-all-in-one.exe
```
### Endpoints
* Jaeger will be available at http://localhost:16686
* Your application is configured to be available at http://localhost:8080

### Logs

Whenever an operation is called you'll see in the logs that span id is communicated with Jaeger:

```
2019-09-08 21:46:17,909 INFO  [io.jaegertracing.internal.reporters.LoggingReporter] (default task-1) Span reported: e4b3509a94e1ffc6:e4b3509a94e1ffc6:0:1 - GET:CountriesApi.findCitiesByCountryCode
```

You can search in jaeger under the service name 'city-service'. Each time a service call is done on one of the endpoints you should see a trace in Jaeger.