#!/bin/bash

HOME=/home/ubuntu
API_DIR=${HOME}/apps/member-api
BIN_DIR=${API_DIR}/bin
LOG_DIR=${API_DIR}/logs
SCOUTER_AGENT_DIR=${HOME}/apps/scouter/agent.java

# check parameters and initialize JAR_FILE_PATH
if [ "$#" -ne 1 ]; then
  echo "usage: startup.sh [jar file path to execute. (default: /home/ubuntu/apps/member-api/bin/member-api.jar)]"
  JAR_FILE_PATH=${BIN_DIR}/member-api.jar
else
  JAR_FILE_PATH=${1}
fi

# move
cd ${BIN_DIR}

# SCOUTER
JAVA_OPTS="${JAVA_OPTS} -javaagent:${SCOUTER_AGENT_DIR}/scouter.agent.jar"
JAVA_OPTS="${JAVA_OPTS} -Dscouter.config=${SCOUTER_AGENT_DIR}/conf/scouter.conf"

# REMOTE DEBUGGING
JAVA_OPTS="${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005"

# SPRING
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=develop"
JAVA_OPTS="${JAVA_OPTS} -Dlogging.file.path=${LOG_DIR}"
JAVA_OPTS="${JAVA_OPTS} -Dserver.tomcat.accesslog.enabled=true"
JAVA_OPTS="${JAVA_OPTS} -Dserver.tomcat.basedir=${API_DIR}"

# GC
#JAVA_OPTS="${JAVA_OPTS} -verbose:gc"
#JAVA_OPTS="${JAVA_OPTS} -Xloggc:${LOG_DIR}/gc_%p.log"
#JAVA_OPTS="${JAVA_OPTS} -XX:+HeapDumpOnOutOfMemoryError"
#JAVA_OPTS="${JAVA_OPTS} -XX:+PrintGCDetails"
#JAVA_OPTS="${JAVA_OPTS} -XX:+PrintGCDateStamps"

# HEAP
JAVA_OPTS="${JAVA_OPTS} -Xmx256m"

# start
nohup java ${JAVA_OPTS} -jar ${JAR_FILE_PATH} >/dev/null 2>&1 &
