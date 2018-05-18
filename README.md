# reporting_web_service

This application extract the data from the csv files [CSVFiles](https://github.com/Nagaraja24/reporting_web_service/tree/master/docs/csvfiles) and find out the additional metrics and stores in to in memory h2 database.
This application exposes four end points:

* `GET - /test` Test for application status
* `PUT - /reports` Extracts data from csv files present in the resources and stores in to database 
* `GET - /reports` Returns the list of all reports
* `GET - /reports/{month}/{site}` Returns report by month and site


Build
---

To build reporting_web_service, you need [Maven](http://maven.apache.org/). Just call `mvn clean verify` from the command-line, and you're done. Alternatively, you can use any IDE with Maven support.


Deploy
---
Once you are build the project, you will get a `report-service-0.0.1-SNAPSHOT.jar` in the target folder.
Just run the jar with following command `java -jar report-service-0.0.1-SNAPSHOT.jar`.
And the application will be listening in the port `8090`.

The base URL for the application is `http://localhost:8090/reportservice`.
The H2 database console can be accessed at `http://localhost:8090/h2`
User Name: `sa`
Password: Not required
And click on `connect` to take in to database part.

CSV Files 
---
You can find csvfiles here [CSVFiles](https://github.com/Nagaraja24/reporting_web_service/tree/master/docs/csvfiles).

User Guide
---
You can find the userguide here [UserGuide](https://github.com/Nagaraja24/reporting_web_service/tree/master/docs/userguide).
