# Vaccination centers - Collection of adverse events
A university project, build with Java 13, to handle Covid-19 vaccination adverse event.

### Build and Run with Maven
If you are using IntelliJ run these commands in the terminal by pressing <kbd>CTRL</kbd> + <kbd>ENTER</kbd>
```console
$ mvn clean:clean
$ mvn clean compile package install
```
Then add the postgresql dependencies (just downloaded by Maven) to Project modules from:
File > Project structure > Modules > Add library.

Then build project with <kbd>CTRL</kbd> + <kbd>F9</kbd>
```console
$ mvn exec:java
```

If you have problem with IntelliJ: remove 'target' folder and rebuild project

_(I'm not good with maven)_