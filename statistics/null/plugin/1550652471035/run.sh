#!/bin/sh
cd `dirname $0`
parse_jvm_options() {
  if [ -f "$1" ]; then
    echo "`grep "^-" "$1" | tr '
' ' '`"
  fi
}

JVM_OPTIONS_FILE=jvm.options

RT_JAVA_OPTS="`parse_jvm_options "$JVM_OPTIONS_FILE"` $RT_JAVA_OPTS"
echo $RT_JAVA_OPTS
case $1 in
   start)
    nohup java $RT_JAVA_OPTS -jar pinpoint-plugin-generate-bootstrap-5.2.2.jar > pinpoint-plugin-generate.log &
    tail -f pinpoint-plugin-generate.log
     ;;
   stop)
    java -jar pinpoint-plugin-generate-bootstrap-5.2.2.jar  stop --shutdownLevel=9
     ;;
   restart)
    nohup java $RT_JAVA_OPTS -jar pinpoint-plugin-generate-bootstrap-5.2.2.jar restart --shutdownLevel=9 > pinpoint-plugin-generate.log &
    tail -f pinpoint-plugin-generate.log
    ;;
   *)
     echo
     echo "Usage:";
     echo "  pinpoint-plugin-generate keyword [value1 [value2]] ";
     echo "  ----------------------------------------------------------------";
     echo "  start                             -- Start pinpoint-plugin-generate";
     echo "  stop                              -- stop pinpoint-plugin-generate";
     echo "  restart                           -- Restart pinpoint-plugin-generate";
     echo "  ----------------------------------------------------------------";
     echo
     ;;
esac
