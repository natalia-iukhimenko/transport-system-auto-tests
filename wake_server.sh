#!/bin/bash

URL="https://transport-system-backend.onrender.com/api/auth/signin"
DATA='{"username":"ts_wakener", "password":"ts_wakener"}'
HEADERS=(-H "Content-Type: application/json")

while true; do
  echo "Sending request to server..."
  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "${HEADERS[@]}" -d "$DATA" "$URL")
  if [[ $? -eq 0 ]]; then
    echo "Got response from server. Exiting."
    break
  else
    echo "No response from server yet. Retrying in 10 seconds..."
    sleep 10
  fi
done
