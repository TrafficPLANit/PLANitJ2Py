# PLANitJ2Py
Java part of PLANit Java/Python interface

This project solely exists to provide the bootstrap Java entry point for PLANit which is invoked from the Python side via a subprocess call to Java. We use [Py4J](www.py4j.org) to establish the connection. 

>More information on our integration of Py4J can be found in the [PLANitPy2J project](https.github.sydney.edu.au/planit/planitPy2J) including the necessary licensing acknowledgements.

## Maven parent

Projects need to be built from Maven before they can be run. The common maven configuration can be found in the PLANitAll project which acts as the parent for this project's pom.xml.

> Make sure you install the PLANitAll pom.xml before conducting a maven build (in Eclipse) on this project, otherwise it cannot find the references dependencies, plugins, and other resources.

