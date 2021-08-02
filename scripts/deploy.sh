#!/bin/bash

API_BIN_DIR=/home/ubuntu/apps/member-api/bin
MAINTENANCE_FILE_PATH=${API_BIN_DIR}/maintenance

# create maintenance file
if [ ! -f "${MAINTENANCE_FILE_PATH}" ]; then
  touch ${MAINTENANCE_FILE_PATH}
fi

# set jar file path
JAR_FILE_PATH=/home/ubuntu/apps/member-api/archives/${1}

# shutdown
${API_BIN_DIR}/shutdown.sh

# rename
if [ -f "${JAR_FILE_PATH}" ]; then
  echo "change target path of member-api.jar"
  ln -sf ${JAR_FILE_PATH} /home/ubuntu/apps/member-api/bin/member-api.jar
fi

# startup
${API_BIN_DIR}/startup.sh

# wait for starting application
WAITING_SECONDS=30
echo "Waiting ${WAITING_SECONDS}s for starting application."
sleep ${WAITING_SECONDS}

# check health (TODO: spring boot actuator /health)
NUMBER_OF_PROCESSES=$(ps -ef | grep member-api | grep -v 'grep' | wc -l)
if [ "${NUMBER_OF_PROCESSES}" -ne 1 ]; then
  echo "Failed to deploy application. 'member-api' process is not found."
fi

# delete maintenance file
if [ -f "${MAINTENANCE_FILE_PATH}" ]; then
  rm ${MAINTENANCE_FILE_PATH}
fi
