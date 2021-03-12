#!/bin/bash

kubectl delete ingress quarkus-todo
kubectl delete service quarkus-todo
kubectl delete deployment quarkus-todo
kubectl delete configmap quarkus-todo-config