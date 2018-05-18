# Step by step guide on running the application

## Step 1- Build:
To build reporting_web_service, you need [Maven](http://maven.apache.org/). Just call `mvn clean verify` from the command-line, and you're done. Alternatively, you can use any IDE with Maven support.

## Step 2 - Deploy:

Once you are build the project, you will get find a `report-service-0.0.1-SNAPSHOT.jar` in the target folder.

Place the jar file along with the `application.yml`.

Provide the two csv file paths in the `application.yml` as shown in the below

 *jan:
    filepath: `D:\Nagaraj\Crealytics\2018_01_report.csv(replace your file path)`

 *feb:
    filepath: `D:\Nagaraj\Crealytics\2018_02_report.csv(replace your file path)`

Run jar by command `java -jar report-service-0.0.1-SNAPSHOT.jar`.
And the application will be listening in the port `8090`.

The base URL for the application is `http://localhost:8090/reportservice`.
The H2 database console can be accessed at `http://localhost:8090/h2`
User Name: `sa`
Password: Not required
  
## Step 3 - Test End Points:

End Point 1 (Test Application):HTTP Method: `GET` - http://localhost:8090/reportservice/test
---
* To test whether application is up and running
* Expected Response: `Service is Up`

End Point 2 (Load CSV data in to database) : HTTP Method: `PUT` - http://localhost:8090/reportservice/reports
Note: Please use postman or RestClient for testing PUT methods
---
* To load the csv data in to database -- Note: Please make sure you are that you are invoking once as it leads to 	duplication of data in database
* Expected Response: `Data loaded in to database successfully from CSV files`
* Check the H2 DB console to verify whether data is uplaoded successfully
* Console: `http://localhost:8090/h2`
* User Name: `sa`
* Password: Not Required

End Point 3 (Get all reports): HTTP Method: `GET` - http://localhost:8090/reportservice/reports
---
* Returns all the list of reports
* Returns empty array `[]` if you invoke before loading csv data or no data present in the database

End Point 4(Get report by month and site): HTTP Method: `GET` - http://localhost:8090/reportservice/reports/{month}/{site}
--
* Returns the single report based on the month and site input
* Returns empty object `{}` if nothing matches with passed values
* Valid values for the month January (not case sensitive) : `1, jan, january`
* Valid values for the month February (not case sensitive) : `2, feb, february`
* Use following mappings for passing site
	* desktop_web: "desktop web"
   	* mobile_web: "mobile web"
    * android: "android"
    * iOS: "iOS"
 * Example 1: http://localhost:8090/reportservice/reports/jan/mobile_web
 * Example 2: http://localhost:8090/reportservice/reports/1/mobile_web
 * Example 3: http://localhost:8090/reportservice/reports/feb/ios
