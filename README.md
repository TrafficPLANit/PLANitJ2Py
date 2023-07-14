# PLANitJ2Py

![Master Branch](https://github.com/TrafficPLANit/PLANit/actions/workflows/maven_master.yml/badge.svg?branch=master)
![Develop Branch](https://github.com/TrafficPLANit/PLANit/actions/workflows/maven_develop.yml/badge.svg?branch=develop)

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

### Maven deploy

Distribution management is set up via the parent pom such that Maven deploys this project to the PLANit online repository (also specified in the parent pom). To enable deployment ensure that you setup your credentials correctly in your settings.xml as otherwise the deployment will fail.

### Git Branching model

We adopt GitFlow as per https://nvie.com/posts/a-successful-git-branching-model/