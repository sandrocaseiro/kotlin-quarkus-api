# api-template project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

##Remarks

- Query with paging using fetch joins don't work because of the count query
- Custom authorization annotations aren't implemented as they should. The correct way is to create an extension,
  but is much more complicated
- Cucumber integration not working as it should, only simpler scenarios are runnning

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```
or with local profile:
```shell script
./gradlew quarkusLocal
```


## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `api-template-1.0.0-SNAPSHOT-runner.jar` file in the `/build` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar build/api-template-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/api-template-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.
