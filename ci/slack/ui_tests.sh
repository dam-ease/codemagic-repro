#!/usr/bin/env bash

# fail if any commands fails
set -e

isSuccess=$(./ci/slack/options.sh $@)

USERNAME="UI TESTS"
ICON_EMOJI=''
TEXT=''

if [ "$isSuccess" = "true" ]; then
  ICON_EMOJI=':wow_krzysiu:'
  TEXT=':done-tick: *UI tests passed* :done-tick:'
else
  ICON_EMOJI=':dokadzmierzam:'
  TEXT=':alarmlight: *UI tests failed!* :alarmlight:'
fi

source ./ci/slack/notify_slack.sh "$USERNAME" "$ICON_EMOJI" "$TEXT" "alerts" "$isSuccess"
