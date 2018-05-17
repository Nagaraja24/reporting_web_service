# reporting_web_service

This application extract the data from the csv files and find out the additional metrics and stores in to in memory h2 database.
This application exposes four end points:

* `/test` Test for application status
* `/savedata` Extracts data from csv files present in the resources and stores in to database 
* `/getAllReports` Returns the list of all reports
* `/getReport/{month}/{site}` Returns report by month and site


Build
---

To build reporting_web_service, you need [Maven](http://maven.apache.org/). Just call `mvn clean verify` from the command-line, and you're done. Alternatively, you can use any IDE with Maven support.


Deploy
---
Once you are build the project, you will get a `report-service-0.0.1-SNAPSHOT.jar` in the target folder.
Just run the jar with following command `java -jar report-service-0.0.1-SNAPSHOT.jar`.
And the application will be listening in the port `8090`.

The base URL for the application is `http://localhost:8090/reports`.
The H2 database console can be accessed at `http://localhost:8090/h2`
User Name: `sa`
Password: Not required
And click on `connect` to take in to database part.
