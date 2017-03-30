#!/bin/sh
### BEGIN INIT INFO
# Provides:         sysctl 
# Required-Start:
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Deault set swappness and min_free_kbytes
### END INIT INFO

if [ -e /proc/sys/vm/swappiness ]; then
	echo 0 > /proc/sys/vm/swappiness
fi

if [ -e /proc/sys/vm/min_free_kbytes ]; then
	echo 8192 > /proc/sys/vm/min_free_kbytes
fi
