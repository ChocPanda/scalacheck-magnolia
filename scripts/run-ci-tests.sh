#!/bin/bash

# Run tests and create report
sbt clean coverage test
sbt coverageReport
sbt coverageAggregate
# sbt codacyCoverage