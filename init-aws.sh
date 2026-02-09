#!/bin/bash

# S3 버킷 생성
awslocal s3 mb s3://cms

# S3 버킷 정책 설정
export AWS_ACCESS_KEY_ID=cms
export AWS_SECRET_ACCESS_KEY=cms
export AWS_DEFAULT_REGION=ap-northeast-2