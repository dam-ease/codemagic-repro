#!/usr/bin/env bash

# fail if any commands fails
set -e

FILE_PATH=$1

if [ "$FILE_PATH" = "" ]; then
  echo "Missing file path"
  exit 1
fi

JSON=$(
  cat <<EOF
{
    "client_id": "$HUAWEI_CONNECT_API_CLIENT_ID",
    "client_secret": "$HUAWEI_CONNECT_API_CLIENT_SECRET"
}
EOF
)

echo "$JSON" >> $FILE_PATH