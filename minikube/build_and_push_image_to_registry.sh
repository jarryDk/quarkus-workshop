#!/bin/bash

source config.sh

cd ../todo-app-$VERSION

if [ "native" == "$QUARKUS_BUILD" ]; then

    ./mvnw clean package -Pnative -Dquarkus.native.container-build=true

    podman build -f src/main/docker/Dockerfile.native -t quarkus/native/todo-app:$VERSION .
    podman push quarkus/native/todo-app:$VERSION $REGISTRY_IP:5000/quarkus/native/todo-app:$VERSION
   
fi

if [ "jvm" == "$QUARKUS_BUILD" ]; then

    ./mvnw clean package

    podman build -f src/main/docker/Dockerfile.jvm -t quarkus/jvm/todo-app:$VERSION .
    podman push quarkus/jvm/todo-app:$VERSION $REGISTRY_IP:5000/quarkus/jvm/todo-app:$VERSION
   
fi

