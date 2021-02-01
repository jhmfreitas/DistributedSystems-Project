# ForkExec

**Fork**: delicious menus for **Exec**utives

Distributed Systems 2018-2019, 2nd semester project

## Authors

Group T22

|    Nome		|	Número	|	Id				|	
|---------------|-----------|-------------------|
| João Freitas 	| 87671		| jhmfreitas		|
| Pedro Soares 	| 87693		| pedroamaralsoares	|


For each module, the README file must identify the lead developer and the contributors.
The leads should be evenly divided among the group members.


### Code identification

In all the source files (including POMs), please replace __CXX__ with your Campus: A (Alameda) or T (Tagus); and your group number with two digits.

This is important for code dependency management 
i.e. making sure that your code runs using the correct components and not someone else's.


## Getting Started

The overall system is composed of multiple services and clients.
The main service is the _hub_ service that is aided by the _pts_ service. 
There are also multiple _rst_ services, one for each participating restaurant.

See the project statement for a full description of the domain and the system.

## Instructions

**Test Hub:**

cd hub-ws

mvn compile exec:java

cd pts-ws

mvn compile exec:java

cd rst-ws

mvn compile exec:java

mvn compile exec:java -D ws.i=2


cd hub-ws-cli 

mvn verify 

**Test Restaurant:**

cd rst-ws

mvn compile exec:java

cd rst-ws-cli 

mvn verify

**Test Points:**

cd pts-ws

mvn compile exec:java

cd pts-ws-cli 

mvn verify

**Test CreditCard:**

cd cc-ws-cli

mvn verify

### Prerequisites

Java Developer Kit 8 is required running on Linux, Windows or Mac.
Maven 3 is also required.

To confirm that you have them installed, open a terminal and type:

```
javac -version

mvn -version
```


### Installing

To compile and install all modules:

```
mvn clean install -DskipTests
```

The tests are skipped because they require each server to be running.


## Built With

* [Maven](https://maven.apache.org/) - Build Tool and Dependency Management
* [JAX-WS](https://javaee.github.io/metro-jax-ws/) - SOAP Web Services implementation for Java



## Versioning

We use [SemVer](http://semver.org/) for versioning. 



