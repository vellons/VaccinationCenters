# Vaccination centers - Collection of adverse events
A university project, build with Java 13, to handle Covid-19 vaccination adverse event.

### Build and Run with Maven
If you are using IntelliJ run these commands in the terminal by pressing <kbd>CTRL</kbd> + <kbd>ENTER</kbd>
```console
mvn clean:clean
mvn clean compile package install
```
After compile, in the target folder you will find 3 jar file.

To  run the server with maven run
```console
mvn exec:java
```


#### In case of problem
If you have problem with IntelliJ: remove 'target' folder and rebuild project.

Add dependencies inside lib/ to IntelliJ:

File > Project structure > Modules > Add library.
