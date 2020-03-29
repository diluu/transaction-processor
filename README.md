==============================================
#Summary
==============================================



==============================================
#Getting Started
==============================================

Setting up Databases:
----------------------

* Create new connection to XE database with administrator account
* Run database-alg/kwijtschelding-tablespacealg.sql to create tablespace and users

* Create new connection to XE database with administrator account
* Run database-mun/kwijtschelding-tablespacemun1.sql to create tablespace and users

Setting up Eclipse Maven Project:
----------------------------------
* Import project as existing maven projects into the workspace.
	- Select parent project directory as the Root Folder
	- Tick on child pom files as well
* Project facates:
	- database-alg: Liquibase module for alg datasource
	- database-mun: Liquibase module for municipality datasource
	- business : EJBModlue, Java, JPA
	- domain: EJBModule, Java, JPA
	- ear: EAR
	- util: Java, Utility Module
* Change validation level of error to info in persistence.xml message for "Jar file cannot be resolved"

Changing the maven version number:
----------------------
Change in the parent the version number and run the following maven command:
$ mvn -N -DgenerateBackupPoms=false versions:update-child-modules
	
Setting up Wildfly Deploy and Run:
-----------------------------------
* Setup Wildfly 10.0 server
	- Wildfly repository: https://devel.conxillium.com/gitlab/procura-kwijtscheldingen/kwijtschelding
* alg schema should be a wildlfly datasource and its jndi name should be equal to jndi name in persitence.xml 'alg' persitence units jndi name.
* Setup wildfly datasource according to the instructions provided in https://devel.conxillium.com/gitlab/conxillium/documentation/blob/master/80-work-instructions/Wildfly-Datasource-Configuration.docx
* Run mvn clean install on parent module (This will create the database tables and insert test data as well)
* add user "adminontw" using "add-user.bat" in the bin directory
* add the correct JAVA_HOME & JBOSS_HOME to the standalone.bat
	set JAVA_HOME=
	set JBOSS_HOME=
* choose the ports in node "socket-binding-group" in standalone.xml
	Check with netstat -a in CMD
* Start the Wildfly server
* Run mvn wildfly:deploy
* Navigate to http://localhost:8080/innen
* Login with default user (Username: gtadmin, Password: gtadmin)
* To undeploy run mvn wildfly:undeploy

==============================================
#Testing the Product
==============================================


==============================================
#Documentation Links
==============================================
* Draft design document: http://srv-270:8080/svn/actueel/documentatie/conxillium/30-architectuur-en-bouw/Technical%20Design/DraftDesign-EJBAssignment.docx
* Setting up Wildfly datasource: http://srv-270:8080/svn/actueel/documentatie/conxillium/80-werkinstructies/Wildfly-Datasource-Configuration.docx
* https://docs.jboss.org/author/display/WFLY10/Security+subsystem+configuration
* https://www.examsmyantra.com/article/119/javaee/form-based-login-authentication-in-javaee7-with-wildfly-and-mysql (contains an error in the <policy-module code="Database" flag="required">)
* https://mvnrepository.com/artifact/org.wildfly/wildfly-parent/10.1.0.Final
* https://mvnrepository.com/artifact/org.wildfly.bom/
* http://www.mastertheboss.com/jboss-frameworks/maven-tutorials/jboss-maven/using-wildfly-boms

==============================================
#Todo's
==============================================
* Load testing using JMeter
* Background jobs that does not execute a query
* ORM.xml
* on enter in a search field execute default button (search) - Do this for the screens with out search filterable grids
* On click label in RemissionCrudView focus on Input field
* Reports table is now not used, the report is now hardCoded and not calculated using tax year
* Link jasper report datasource dynamically
* Dropdown options extending all types of values
* Notification stack instead on top of each other 
* Abstract MyView at the end scroll to top button :D
* Security:
	- block login after x wrong attempts
	- persit login attemps (succesfull/unsuccesfull with ip)
	- block by ip, allow to add IP by email (also only for x days or x logins)
	- no plain passwords (datasource, user, jasper):
		- no plain passwords in deltaspike propperties
		- no plain passwords in liquibase patches
* Forgot password
* Different language in 2 tabs, still work after page refresh / same for dataset 
* Also support PostgreSQL (Reppido alreday uses the same base structure with PostgreSQL) maybe make it a option in ALG datasets
* reuse wildfly sub system http://stackoverflow.com/questions/38688392/slf4j-logging-with-jboss-wildfly-10
* Each application own log file own error file.
* Now we use Hibernate 5 version supplied from Wildfly, update the wildfly module with latstest 5 version and update parent pom (5.2.10.Final)
* Refactor and move the hash value generation from PaswordHasher class, since it is not only used in passwords.
* Migrate to wildfly 12
* Migrate to Vaadin 10
* Replace CheckBoxRenderer with ComponentColumn
* Refactor all the invoice creation/write off creation methods to use a common class. Now there are 3 write off invoice (Bankstatement/Kwijtschelding/Invoice import)
* Unit tests for 6.06,6.07, 6.08 backend logic
* Override entity class todo methods to return String.valueOf(id) to make it easy to find errors in db opertations
* Remember the last sorting order on table component for a User (improvement which peter suggested)
* Add a button in RemissionCRUDView to resend active blocked record to Heffen. (This will be needed if Heffen or the connection was not working at the moment of remission update)
* Remove all viritin UI components and use only Vaadin components
* Replace the Kwijtschelding details jasper report with a document created with document micro service
* Get rid of no longer used REMISSION_STATES table and releated entities

==============================================
#Tips and Tricks
==============================================
* when it is needed to create multiple municipality schemas create the database according to the database setup instructions
	- Change database-mun/kwijtschelding-tablespacemun1.sql accordingly to create tablespaces and users
	- Change liquibase-maven-plugin configuration parameters in database-mun module pom.xml
	- run mvn clean install on databse-mun module
* Creating Maven EJB project: http://www.famvdploeg.com/blog/2008/08/building-ejb3-applications-with-maven-2/
* ojdbc library need to be added as a global module in Wildfly to avoid ClassNotFoundException in municipality datasource creation
* alg schema should be a wildlfly datasource and its jndi name should be equal to jndi name in persitence.xml persitence units jndi name.
* analysing the JVM with visualVM add JMX connection service:jmx:http-remoting-jmx://192.168.118.132:9990 source:http://www.mastertheboss.com/jboss-server/wildfly-8/monitoring-wildfly-using-visualvm
* If you want to create a new user, you can hash the password with the PasswordHasherTest#testHashPassword method

==============================================
#Naming Conventions
==============================================
* Database naming conventions:
	- Table names and column names are in upper case.
	- Id columns are indicated with a prefix for each table (eg: USR_ID, SUB_ID)
	- Foreign keys are started as FK_ and have a name representing related tables and the column
	- Primary keys are as PK_<TableName>
	- Unique keys are started as UK_ and have a name representing the unique entity
	- Many to Many relationships are represented in tables with names like <Table1>_<Table2>
	- Sequences starts as SEQ_<SequenceName>
	- Enum types starts as CHK_<EnumName>
	
* Java naming conventions are the standard Java naming conventions with below specific rules
* Data Access Objects
	- Interface name like GenericDao, UserDao
	- Implementation name like GenericDaoBean, UserDaoBean
* Remote EJBs
	- Remote interface like UserEJBRemote, SettingEJBRemote
	- Implementation class like UserEJB, SettingEJB
* Entities
	- Entity class like User, Dataset
	- Interface holding string constants like UserTable, DatasetTable

* Multilanguage support
	- Backend:
		- For each multilingual error message from backend add a Message key in nl.procura.kwijtschelding.util.constants.MessageKeys and add a numeric language code
		- Add message in each language file with the numeric code
		- In backend throw a new CustomAppException with userMessage as the messageKey in MessageKeys interface
	- Frontend:
		- For each multilingual text from the ui add a MSG_<DescriptiveText> key in nl.procura.kwijtschelding.web2.multilanguage.ITranslate
		- Add a message in each language file with the MSG_<DescriptiveText> as key
		- Use the translateByKey method with the MSG_<DescriptiveText> string (and implement the ITranslate interface in the UI class where you need it if this hasn't been done yet)


==============================================
#Project Rules
==============================================
* In the pom always try to create a unique namespace (so when their company poc ever releases a library it isn't a problem)
	- groupId: nl.procura.kwijtschelding
	- artifactId: parent
* Give maven plugins a version number, so when the pom get executed, always the same plugin is used
* Mention in the parent a Distribution Node  
* Mention in the parent a source management Node  
* Use dependency management and plugin management in the parent.
* Use property "project.build.sourceEncoding" in the pom to handle resources as UTF-8 resources.
* Use the same order of maven nodes in all the POM files.

* For database columns of type Date and Timestamp always use java.util.Date in entitiy class
 and annotate as  @Temporal(TemporalType.TIMESTAMP) according to db data type

* Annotate unique columns with @Column(unique = true) in entity class

* AbstractEntity introduced to support common features

* Use unique names as groupId and artifact id for java packages

==============================================
#Domain Rules
==============================================

* When we talk about a date range it's always from(-inclusive) to(-exclusive). (I.e. When we retrieve the correct Conditions based on their start/end date for a new RemissionRequest (with a start date), we do the following check: startDateCondition <= startDateRemission AND (endDateCondition == null OR endDateCondition > startDateRemission. Note the `<=` in the first part of the check, and `>` in the second part of the check)

==============================================
#UI/View Rules
==============================================

[See the separate README.md in the web-parent UI project.](web-parent/README.md)

==============================================
#Software stack
==============================================


## Deltaspike
http://deltaspike.apache.org/documentation/configuration.html
We now only use Deltaspike for property injection. 
Keep in mind within an EAR when the EJB module has a properties file the web also inherits those properties but can override them in it's own properties file.

Why?
The goal of the DeltaSpike configuration mechanism is to make it obsolete to touch released binaries for changing the configuration of your project. 
All values which are needed in your code (but should not be hardcoded as static final constants) can be maintained via DeltaSpike’s own configuration mechanism in a very flexible and powerful way.

private static final can only be injected using protected static final int MAX_RESULTS = ConfigResolver.resolve("com.conxillium.ejbmultitenancy.business.dao.MAX_RESULTS").as(Integer.class).withDefault(500).getValue();
not using @Inject it seems.

ConfigSources Provided by Default
By default there are implementations for the following configuration sources (listed in the lookup order):
System properties (deltaspike_ordinal = 400)
Environment properties (deltaspike_ordinal = 300)
JNDI values (deltaspike_ordinal = 200, the base name is "java:comp/env/deltaspike/")
Properties file values (apache-deltaspike.properties) (deltaspike_ordinal = 100, default filename is "META-INF/apache-deltaspike.properties")

Define:
```java
  @Inject
  @ConfigProperty(name = "db.port", defaultValue = "testBusiness")
  private String test;
```
Usage:
```java
    LOGGER.info("test {}", this.test);
```

Define:
```java
  private final TypedResolver<String> test2 = ConfigResolver.resolve("db.port2").as(String.class).withDefault("test2")
      .logChanges(true).cacheFor(TimeUnit.SECONDS, 30);
```
Usage:
```java
    LOGGER.info("test2 {}", this.test2.getValue());
```

If a properties file is changed and the file should be reloaded execute the following line of code:
```java
ConfigResolver.freeConfigSources();
```

## SLF4J
```java
private static final Logger LOGGER = LoggerFactory.getLogger(DatasetServlet.class);
```

Example usage:
```java
LOGGER.info("User has role 'admin' {}", this.ejbContext.isCallerInRole("admin"));
```
Exception logging:
```java
final String msg = MessageFormat.format("Error loading operation, Redirecting to {0} {1}", RequestPaths.INDEX_PAGE, e.getMessage());
LOGGER.error(msg, e);
```

## Liquibase
We use Liquibase in database version management
Documentation: http://www.liquibase.org/documentation/index.html

Change Log file:
The root of all Liquibase changes is the databaseChangeLog file.

Change Set:
The changeSet tag is what you use to group database changes/refactorings together.
Each changeSet tag is uniquely identified by the combination of the “id” tag, the “author” tag, and the changelog file classpath name

```xml
 <changeSet id="1" author="Thudani" logicalFilePath="src/main/resources/changelog_alg.xml">
 </changeSet>
```

Refactorings:
createTable, addColumn, addDefaultValue, addForeignKeyConstraint, addPrimaryKey, addUniqueConstraint,...

Compile time database update:
Liquibase can be used to update database on compile time as well as on run time
We have two liqubase modules for two schema types (ALG and Municipality) with two change log files
It is needed to configure liquibase-maven-plugin with the path for a change log file and a database connection
Then in the update goal of the liquibase-maven-plugin it will checks for any updates in the change log file and will update the database

Run time database update:
We use runtime database updates to check updates during server startup for ALG schema and during first access to municipality schemas
```java
final ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
final JdbcConnection jdbcConnection = new JdbcConnection(connection);
final Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
final Liquibase liquiBase = new Liquibase(this.changeLogFile, resourceAccessor, new JdbcConnection(connection));
liquiBase.update(new Contexts());
```

Skipping liquibase on a maven build
install -Dliquibase.skip=true

patch folder structure & filenames:
Because we don't want a folder that will contain (in a few years) thousands of files we will seperate them using the following structure

Folder:Year
SubFolder:Month

In 2016 we had a different structure but that one was not handy, because of our GIT branch structure.

For example:
2017/01/changelog_data.xml

We can use changelog_data.xml for small fixes like adding settings or inserts.
We can use changelog_tables.xml for mutating tables.
We can should use remote_subjects_data.xml when in a f_remote_subjects branch.

## Test Checklist

* UTF-8 message - try to save with submitter with only one the firstname filled. The message should start with Eén
* UTF-8 save person with ëéúüíö€ in the name, also check in results table.
* UTF-8 Dropdown in the Remission screen has é in one of the dropdown options
* Save without submitter
* Save with submitter
* Export WORD
* Filter using all fields
* Export
* Import
* Jasper report UTF-8 (BSN example:234884885) Test URL:https://venray-test.procura.nl/heffen/rest/getSubject/234884885

## Known errors:

"{\"WFLYCTL0080: Failed services\" => {\"jboss.persistenceunit.\\\"ear.ear/business.jar#mun\\\"\" => \"org.jboss.msc.service.StartException in service jboss.persistenceunit.\\\"ear.ear/business.jar#mun\\\": org.hibernate.AnnotationException: Use of @OneToMany or @ManyToMany targeting an unmapped class: nl.procura.kwijtschelding.domain.entity.mun.remission.RemissionRequest.blockedRows[nl.procura.kwijtschelding.domain.entity.blocked.Blocked]
    Caused by: org.hibernate.AnnotationException: Use of @OneToMany or @ManyToMany targeting an unmapped class: nl.procura.kwijtschelding.domain.entity.mun.remission.RemissionRequest.blockedRows[nl.procura.kwijtschelding.domain.entity.blocked.Blocked]\"}}"

Because nl.procura.kwijtschelding.domain.entity.blocked.Blocked is not mentioned in the persistence.xml

## Run Book
Preconditions
* Oracle database schema ALG (with all rights also to add / remove / alter sequences/triggers/tables) + Dutch locale and timezone
* Oracle database schema MUN1 (with all rights also to add / remove / alter sequences/triggers/tables) + Dutch locale and timezone
* JRE 8 <-- set this as JAVA_HOME
* Jasper server 

Install:
* Install Wildfly / Setup Wildfly 10.0 server
	Wildfly repository: http://srv-824.hosting.local/conxillium/wildfly/tree/F_kwijtschelding
	alg schema should be a wildlfly datasource 
	and its jndi name should be equal to jndi name in persitence.xml 'alg' persitence units jndi name.
	Setup wildfly datasource according to the instructions provided in http://srv-270:8080/svn/actueel/documentatie/conxillium/80-werkinstructies/Wildfly-Datasource-Configuration.docx

* install Jasper reports for kwijtschelding
	(http://srv-824.hosting.local/procura/documentation/tree/master/kwijtschelding/70-basic-files/Kwijtschelding-Rapportages)
	This can be done by using the GTI

* install Wildfly as a windows service:
	- Copy \wildfly-10.0.0.Final\docs\contrib\scripts\service to \wildfly-10.0.0.Final\bin\
	- wildfly-10.0.0.Final\bin\service>service.bat install /loglevel INFO /user adminontw /password $PASSWORD
	If you want to install WildFly as a service in standalone mode simple issue from the command prompt:

	service install
Now you can use the Windows Services tab in order to manage the service start/stop. As an alternative you can use the service command to perform basic service management (start/stop/restart). Example:


	service restart

Installing WildFly in domain mode requires that you specify some additional settings such as the Domain controller (default 127.0.0.1 on port 9990) and the host name we are going to start (default “master”).


	service install /controller localhost:9990 /host master
	
* 7zip portable
* place the ear in wildfly-10GIT\standalone\deployments
* change the properties 
	wildfly-10GIT\standalone\deployments\ear.ear\business.jar\META-INF\apache-deltaspike.properties
	wildfly-10GIT\standalone\deployments\ear.ear\web2-ui-F_calculations-SNAPSHOT.war\META-INF\apache-deltaspike.properties
* Boot by running standalone.bat or run the service
* fill in the right credentials for mun in the ALG schema in the table Datasets
	For example:--update DATASETS set URL='jdbc:oracle:thin:@srv-261.hosting.local:1521:tst12a', USERNAME='KWIJTSCHELDING_DF1', PASSWORD='KWIJTSCHELDING_DF1' where DS_ID=1;
* Update some settings like jasper endpoint and credentials
* login with gtadmin
*Check if the windows taskplanner has a kill all java job at 23:30
if so add the start Wildfly service to the start task at 06:30


==============================================
#Unit and Integration Tests
==============================================

##Arquillian

* In order to run the Business UnitTests in an in-memory database with Arquillian, you'll have to add the following VM arguments to a JUnit Run Configuration which will also enable logging:  

    -Dlog4j.configuration=log4j.xml -Djava.util.logging.manager="org.jboss.logmanager.LogManager" -Dlog4j.debug=true

* In order to run the Business UnitTests in a real database, you'll have to add the following VM arguments to a JUnit Run Configuration (replace MUN1/ALG with your own database):

     -Dlog4j.configuration=log4j.xml -Djava.util.logging.manager="org.jboss.logmanager.LogManager" -Dlog4j.debug=true -Dpersistence.file.to.use=persistence-oracle.xml -Dmun.javax.persistence.jdbc.url=jdbc:oracle:thin:@localhost:1521:XE -Dmun.javax.persistence.jdbc.user=KWIJTSCHELDING_MUN1 -Dmun.javax.persistence.jdbc.password=KWIJTSCHELDING_MUN1 -Dalg.javax.persistence.jdbc.url=jdbc:oracle:thin:@localhost:1521:XE -Dalg.javax.persistence.jdbc.user=KWIJTSCHELDING_ALG -Dalg.javax.persistence.jdbc.password=KWIJTSCHELDING_ALG