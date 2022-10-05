#!/bin/bash

docker build . -t martindelatorre/p3-backend
docker push martindelatorre/p3-backend
kubectl apply -f "./resources/back-end-deployment.yml" -n p3-space
kubectl apply -f "./resources/back-end-service.yml" -n p3-space
kubectl rollout restart deployment back-end-deployment -n p3-space
