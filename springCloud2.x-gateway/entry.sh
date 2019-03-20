#!/bin/bash


if [ -z $1 ];then
	echo 'must point want to run'
  exit 0
fi

PROJ= $1
pid = `ps -ef | grep $PROJ | awk '{print $2}'`

if [ -n "$pid"];then
	echo -e "close kill -9 $pid"
	kill -9 $pid
fi

nohup java -jar ./target/$PROD.jar > logs.log 2>&1 &
exit 0