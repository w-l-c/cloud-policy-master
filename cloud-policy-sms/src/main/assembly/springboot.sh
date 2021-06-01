#!/bin/bash

APP_NAME=${project.artifactId}-${project.version}

function stop(){
  PID=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
  if [ ${PID} ]; then
      echo 'Stop Process...'
      kill -15 $PID
  fi
  sleep 5
  PID=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
  if [ ${PID} ]; then
      echo 'Kill Process!'
      kill -9 $PID
  else
      echo 'Stop Success!'
  fi
}


function start(){
  java -version
  if [ $? = 0 ];then

    
    JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
    JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "

    nohup java $JAVA_OPTS $JAVA_MEM_OPTS -jar   ${APP_NAME}.jar 2>&1 &
    echo $ $?
    echo Start Success!
  else
      echo "java is not installed"
  fi

}


function status(){
   PID=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
    if [ ${PID} ]; then
        echo "$APP_NAME is running."
    else
        echo "$APP_NAME is downing."
    fi
}


case $1 in
 'start')
    start
    ;;
 'stop')
    stop
    ;;
 'status')
     status
     ;;
 *)
    echo "./springboot.sh start/stop/status"
    ;;

esac

exit 0