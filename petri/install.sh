#!/bin/bash
mvn clean package assembly:single
mkdir bin/
mv target/petri-1.0-SNAPSHOT-jar-with-dependencies.jar bin/petric.jar
