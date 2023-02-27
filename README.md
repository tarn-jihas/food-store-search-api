# Food Store Search API

This API provides two endpoints for searching food stores based on various criteria, using data stored in an Elasticsearch engine. The API includes two endpoints: `searchNearestStores` and `searchByPartialNameOrAddress`.

[POSTMAN COLLECTION JSON](https://api.jsonserve.com/uMFtdB)

## Requirements

To run the API locally, you need to have the following installed:

- Docker
- Docker Compose
- Java 11

## Getting Started

To get started with the API, follow these steps:

1. Clone this repository to your local machine.
2. In the root directory of the project, run `./mvnw dependency:resolve` to resolve dependecies.
3. In the root directory of the project, run `./mvnw package` to build the jar file.
4. In the root directory of the project, run `docker-compose up` to start the Elasticsearch instance and the API instance. The instances will be able to communicate with each other.
5. To access the API, navigate to `http://localhost:8080`.
6. The API provides two endpoints:
   - `/searchNearestStores`: Expects 2 required arguments, `latitude` and `longitude`, and two optional parameters `distance` and `batchSize`. It returns stores that are within a radius of the provided `distance` (in kilometers). The default `batchSize` is 10, and the default `distance` is 1.
   - `/searchByPartialNameOrAddress`: Expects an inputParam `string`, which is required, and an optional `batchSize`.
7. To stop the instances, run `docker-compose down`.

NOTE: IF elasticsearch is returning an error about the vm.max_map_count=262144:
Windows: in powershell:
1. wsl -d docker-desktop
2. sysctl -w vm.max_map_count=262144

If docker for some reason fails, running the app manually as outlined below should work fine.

If you want to use a standalone Elasticsearch instance, download the version 7.15 from the [Elasticsearch website](https://www.elastic.co/downloads/past-releases/elasticsearch-7-15-0)
Run the following command in the extracted bin folder: `./elasticsearch`

To run the project with the local elasticsearch instance use the following command from the project's root dir after the elasticsaerch instance is up: 
1. `./mvnw dependency:resolve`
2. `./mvnw spring-boot:run`


## Libraries

The application uses the following libraries:

- Spring Boot version 2.7.8
- opencsv version 5.7
- ElasticSearch 7.15.0

## Data

The data used by the API is loaded from a provided CSV file. File used here is [CSV](https://drive.google.com/file/d/1S2GbGliw6JhLAdukY2t0zPfzqurFOvjh/view?usp=sharing)
