#!/bin/bash

export PGPASSWORD="postgres"
export port=8080

PGPASSWORD=$1
port=$2

test=$(psql -h localhost -t -U postgres  -c  "CREATE DATABASE sqlpresentation")
echo $test

mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=${port} - --spring.datasource.password=${PGPASSWORD}"
