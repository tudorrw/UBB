#!/usr/bin/env bash
set -x

AWS_INSTANCE_ID=`curl -s http://169.254.169.254/latest/meta-data/instance-id`
EC2_NAME=$(aws ec2 describe-tags --region eu-south-1 --filters "Name=resource-id,Values=$AWS_INSTANCE_ID" "Name=key,Values=Name" --output text | cut -f5)

# zip
cd /home/coder
zip examen.zip examen -r

aws s3 --region eu-south-1  cp examen.zip s3://map-examen-practic/$EC2_NAME.zip