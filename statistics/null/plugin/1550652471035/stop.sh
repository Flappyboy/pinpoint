#!/bin/sh
cd `dirname $0`
java -jar pinpoint-plugin-generate-bootstrap-5.2.2.jar stop --shutdownLevel=9
