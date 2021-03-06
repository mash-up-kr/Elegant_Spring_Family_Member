#!/bin/bash

# stop embedded tomcat
PROCESSES=$(ps -ef | grep java | grep jar | grep member-api)

SPLIT=(${PROCESSES})
PID=${SPLIT[1]}

if [ -n "$PID" ]; then
  kill $PID
fi
