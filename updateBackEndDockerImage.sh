#!/bin/bash

docker build . -t elrintowser/p3-backend
docker push elrintowser/p3-backend
kubectl apply -f "./kubectl yaml files/back-end-deployment.yml" -n p3-space
kubectl apply -f "./kubectl yaml files/back-end-service.yml" -n p3-space
kubectl rollout restart deployment back-end-deployment -n p3-space