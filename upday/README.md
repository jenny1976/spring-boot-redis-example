## A little News-Article RESTful Service

### Quick APIs Overview

HttpMethod | path | description
------------ | ------------- | --------------
PUT | /articles | create an article
POST | /articles/{id} | update an article
DELETE | /articles/{id} | delete an article
GET | /articles/{id} | find one article
GET | /articles/author/{authorId} | find all articles of one author
GET | /articles/keyword/{keyword} | find all articles with a keyword
GET | /articles/date/{from}/{to} | find all articles out of a date range


### The Technologies
I used a lot of libraries that i haven't worked with before, never the less my coding-style should be clear :smile: Also Git was new for me, but i think it's not a problem.

Here is the tech base of this project: maven 3, java 8, latest spring-boot, spring-mvc and REDIS key-value-store. For a test coverage report i used the [jacoco-maven-plugin](http://eclemma.org/jacoco/trunk/doc/index.html). It generates nice html-pages and also integrates perfectly into netbeans.
There is also a postman project available for a quick-start [here](https://github.com/jenny1976/java-test/blob/master/upday/doc/news-API.json.postman_collection).


### Getting started
if you want to run the application, change into the webservice-folder where you can find the pom.xml and execute the spring-boot 
```bash
console:/some/path/upday/webservice$ mvn sprint-boot:run
```
or install via maven (`mvn clean install`) and start the *.jar that resides in the target-folder
```bash 
console:/..../$ java -jar target/news-api-1.0-SNAPSHOT.jar
```
