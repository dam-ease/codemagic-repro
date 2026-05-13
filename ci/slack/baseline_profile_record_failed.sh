#!/usr/bin/env bash

# fail if any commands fails
set -e

USERNAME="BASELINE PROFILE RECORD"
ICON_EMOJI=':pobudka:'
TEXT=':no_entry_sign: *Baseline profile recording failed!* :no_entry_sign:'

source ./ci/slack/notify_slack.sh "$USERNAME" "$ICON_EMOJI" "$TEXT" "alerts" "false"
