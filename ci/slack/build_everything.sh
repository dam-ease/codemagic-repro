#!/usr/bin/env bash

# fail if any commands fails
set -e

isSuccess=$(./ci/slack/options.sh $@)

USERNAME="NIGHTLY"
ICON_EMOJI=''
TEXT=''

if [ "$isSuccess" = "true" ]; then
  ICON_EMOJI=':heart:'
  TEXT=':done: *Nightly build success!* :done:'
else
  ICON_EMOJI=':broken_heart:'
  TEXT=':alarmlight: *Nightly build failed!* :alarmlight:'
fi

source ./ci/slack/notify_slack.sh "$USERNAME" "$ICON_EMOJI" "$TEXT" "alerts" "$isSuccess"
