#!/usr/bin/env sh

/usr/bin/java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xmx256m -Xss512k -XX:MetaspaceSize=100m -jar /apps/rssReader-1.0.0.jar