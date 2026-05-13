#!/usr/bin/env bash

print_usage() {
  >&2 printf "Usage: \n-s - send success message\n-f - send failure message\n\n"
}

while getopts 'sf' flag; do
  case "${flag}" in
    s) echo 'true' ;;
    f) echo 'false' ;;
    *) print_usage
       exit 1 ;;
  esac
done