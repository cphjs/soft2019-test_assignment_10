#!/bin/sh

url=$APP_URL
ip="${url%:*}"
ip=${ip#*//}
port=${url##*:}

count=1
until nc -q 1 -w 1 $ip $port; do
    echo "Waiting for app to start"
    sleep 1
    count=$((count+1))
    if [ "$count" -eq 100 ]; then 
        break;
    fi
done

mvn -Dtest=cphb.OpenAccountTest -DAPP_URL=${APP_URL} test
