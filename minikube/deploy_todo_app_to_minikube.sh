#!/bin/bash

source config.sh

if [ -d "tmp" ] ; then
    rm -rf tmp
fi
mkdir tmp
cp minikube-quarkus-todo-app.yaml tmp/minikube-quarkus-todo-app.yaml

sed 's/VERSION/'$VERSION'/' tmp/minikube-quarkus-todo-app.yaml > tmp/minikube-quarkus-todo-app-VERSION.yaml
rm tmp/minikube-quarkus-todo-app.yaml

sed 's/REGISTRY_IP/'$REGISTRY_IP'/' tmp/minikube-quarkus-todo-app-VERSION.yaml > tmp/minikube-quarkus-todo-app-REGISTRY_IP.yaml
rm tmp/minikube-quarkus-todo-app-VERSION.yaml

sed 's/QUARKUS_BUILD/'$QUARKUS_BUILD'/' tmp/minikube-quarkus-todo-app-REGISTRY_IP.yaml > tmp/minikube-quarkus-todo-app-$VERSION.yaml
rm tmp/minikube-quarkus-todo-app-REGISTRY_IP.yaml

if [ "X" != "X$CLEAN" ]; then
    kubectl delete ingress quarkus-todo
    kubectl delete service quarkus-todo
    kubectl delete deployment quarkus-todo
    kubectl delete configmap quarkus-todo-config
fi

kubectl create -f tmp/minikube-quarkus-todo-app-$VERSION.yaml
rm -rf tmp

ENDPOINT=$(minikube service quarkus-todo --url)

echo ""
echo "Endpoint for todo-app is : $ENDPOINT"
echo ""
echo "Endpoint for todos in todo-app :"
echo "$ENDPOINT/todos"
