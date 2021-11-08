# PLANitJ2Py

Java part of PLANit-Python interface. Allowing the Python component of PLANit to access the Java components.

This project solely exists to provide the bootstrap Java entry point for PLANit which is invoked from the Python side via a subprocess call to Java. We use [Py4J](www.py4j.org) to establish the connection. 

>More information on our integration of Py4J can be found in the [PLANitPy2J project](https.github.sydney.edu.au/planit/planitPy2J) including the necessary licensing acknowledgements.

For more information on PLANit such as the user the manual, licensing, installation, getting started, reference documentation, and more, please visit [www.goPLANit.org](http://www.goplanit.org)

## Development

### Maven parent

PLANit J2Py has the following PLANit specific dependencies (See pom.xml):

* planit-parentpom
* planit-core
* planit-utils
* planit-io
* planit-xml

Dependencies will be automatically downloaded from the PLANit website, (www.repository.goplanit.org)[http://repository.goplanit.org], or alternatively can be checked-out locally for local development. The shared PLANit Maven configuration can be found in planit-parent-pom which is defined as the parent pom of each PLANit repository.

> When developing on multiple PLANit projects locally, including the parent-pom; make sure you install the PLANitParentPom pom.xml before conducting a Maven build (in for example Eclipse), otherwise it resorts to the online repository rather then the local one.

### Maven deploy

Distribution management is setup via the parent pom such that Maven deploys this project to the PLANit online repository (also specified in the parent pom). To enable deployment ensure that you setup your credentials correctly in your settings.xml as otherwise the deployment will fail.

### Git Branching model

We adopt GitFlow as per https://nvie.com/posts/a-successful-git-branching-model/