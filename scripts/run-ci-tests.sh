#!/bin/bash

# Run tests and create report
sbt ++$TRAVIS_SCALA_VERSION clean coverage test
sbt coverageReport
sbt coverageAggregate
bash <(curl -Ls https://coverage.codacy.com/get.sh)