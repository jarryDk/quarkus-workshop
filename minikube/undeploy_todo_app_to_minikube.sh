#!/bin/bash

kubectl delete service quarkus-todo
kubectl delete deployment quarkus-todo
kubectl delete configmap quarkus-todo-config