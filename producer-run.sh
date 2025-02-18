#/bin/bash

java -javaagent:opentelemetry-javaagent.jar \
  -Dotel.traces.exporter=logging \
  -Dotel.metrics.exporter=logging \
  -Dotel.logs.exporter=logging \
  -jar producer/build/libs/producer.jar
