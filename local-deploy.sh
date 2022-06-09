#!/bin/bash -e

echo "Build applicant service"
mvn clean install -DskipTests

echo "Build docker image"
docker build -t applicant-service .

echo "Build and start service"
docker-compose build
docker-compose up -d