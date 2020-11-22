#! /bin/bash

user=$1
web_ip=$2
server_path=$3
client_path=$4

echo "user: "${user} "web_ip: "${web_ip} "client_path: "${client_path} "server_path: "${server_path}

rsync -avzu --progress ${server_path} ${user}@${web_ip}:${client_path}
