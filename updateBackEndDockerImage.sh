#!/bin/bash

docker build . -t elrintowser/p3-backend:untested
docker push elrintowser/p3-backend:untested
kubectl delete deployment backend-deployment -n p3-space
kubectl apply -f /kubectl\ yml\ files/back-end-deployment.yml deployment -n p3-space
kubectl rollout restart deployment backend-deployment -n p3-space