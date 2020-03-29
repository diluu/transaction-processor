==============================================================
#Technical Task for Processing Future Transactions
==============================================================
*  My solution is build with Java8 and Spring Boot 2.2.6
*  Maven is used as the build tool

*  docker-compose.yml is used to create the database using latest postgres image
*  database initialiation scripts are placed in 'dbinit' directory

*  The spring boot application (transaction-processor) is build in docker-compose using the Dockerfile
*  log files from spring boot is mapped to 'logs' directory with docker volumes

*  Sample daily summary report 'Ouput.csv' and a sample log file can be found in submission directory

==============================================================
#Instruction to build and run the application
==============================================================
*  run.sh file does three steps
    - package the spring boot prokect
	- build it with docker-compose
	- run docker-compose to up database and the spring boot project in embedded tomcat

* It requires that java, maven and docker are installed on your enviornment and MAVEN_HOME and JAVA_HOME added to the PATH enviornment variable

* When the database is up you can see the below log line on bash console
	postgres_1         | 2020-03-29 06:31:21.380 UTC [1] LOG:  database system is ready to accept connections

* When spring boot application is up you can see the below log lines
    trans_processor_1  | 2020-03-29 06:31:43.433  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
    trans_processor_1  | 2020-03-29 06:31:43.448  INFO 1 --- [           main] c.t.TransactionProcessorApplication      : Started TransactionProcessorApplication in 31.206 seconds (JVM running for 35.629)

* Navigate to 'http://localhost:8080/transactions'


=============================================================
#Assumptions Made
=============================================================
*  Transaction amount was calculated as (Long quantity - Short quantity) as given in the requirement specification

============================================================
#Enhancements and known issues
============================================================
* Due to the limited time the UI is done as plain HTML with JQuery only used for date picker
* Security features such as storing plain passwords in docker-compose files are not addressed
* Eventhough, Kafka can be used as messaging framework to instert data with producer and consumer, due to limited experience and time did not done that part


