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
nohup java $RT_JAVA_OPTS -jar pinpoint-plugin-generate-bootstrap-5.2.2.jar > pinpoint-plugin-generate.log &
tail -f pinpoint-plugin-generate.log
