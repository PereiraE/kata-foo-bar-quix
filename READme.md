# Kata FOO BAR QUIX

## Repository's organisation

The repository is basically a multi-module maven project.

The first and most important one is `kata-lib` which contains the core of the 
algorithm implementation. The two other modules depends on this one.

The second one, `kata-api` contains a single route API exposing the Kata over
HTTP.

Finally, `kata-batch` contains the batch implementation.

## Usage

### Requirements

- Java 17 

### Compiling the project

You can compile everything by running the following command from the root of
the repository:

```sh
mvn clean package
```

### Running the API

After compiling the project, you can start the API from the root of the
repository by running:

```sh
java -jar kata-api/target/kata-api-0.0.1-SNAPSHOT.jar
```

The API will be exposed on `http://localhost:8080`. For convenience, a swagger
is available at `http://localhost:8080/swagger-ui/index.html`.


### Running the batch

After compiling the project, you can run the batch from the root of the
repository by running:

```sh
java -jar kata-batch/target/kata-batch-0.0.1-SNAPSHOT.jar
```

It will pick up the provided `input.txt` file (locate at the root of the
project) and store the result of the batch in an `output.txt` file.

## Tests

Each module contains unit tests and / or integration tests.