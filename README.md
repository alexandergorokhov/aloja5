# Assumptions about the system.
Since no information has been provided following assumptions has been made:
1. This is a simulation, no real interaciton with File system is needed.
2. Only one file was specified in the challenge for unit testing. So no other was created by the author
3. Since no specification was provided about the persistence, the state of the applicacion is stored in a file.
4. Since the only message for duplicate entries available is "Directory already exists", the same is used for files.

# Prerequisite
Java 8 or higher.
Maven 3.3 or higher.
# Installation
From the root of the project run :
mvn clean install.
# Testing
The programs contains unit test as part of the code.
The program contains Jacoco plugin and coverage, branches, etc can be viewed in target/site.
Simulation test is ignored for maven build. Please ignore it or run all of them together.
# Start
From the root of the project :
java -jar target/cmd-processor-1.0-SNAPSHOT.jar


