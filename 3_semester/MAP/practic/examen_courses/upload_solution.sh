#!/usr/bin/env bash
set -x

read -p "Codul de examen: " CODE_TOKEN

# zip
cd /home/coder
zip examen.zip examen -r

aws s3 --region eu-south-1  cp examen.zip s3://map-examen-practic/v2/$CODE_TOKEN.zip