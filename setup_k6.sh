#!/bin/bash
set -ex

sudo yum update
sudo yum install dirmngr --install-recommends
sudo yum update
sudo yum install k6