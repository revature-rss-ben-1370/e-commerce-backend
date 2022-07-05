#!/bin/bash

docker build . -t elrintowser/p3-backend:untested
docker push elrintowser/p3-backend:untested
kubectl apply -f "./kubectl yaml files/back-end-deployment.yml" -n p3-space
kubectl apply -f "./kubectl yaml files/back-end-service.yml" -n p3-space
# kubectl rollout restart deployment backend-deployment -n p3-space