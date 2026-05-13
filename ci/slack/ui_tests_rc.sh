#!/usr/bin/env bash

# fail if any commands fails
set -e

isSuccess=$(./ci/slack/options.sh $@)

USERNAME="UI TESTS"

if [ "$isSuccess" = "true" ]; then
  source ./ci/slack/notify_slack.sh "$USERNAME" ":wow_krzysiu:" ":done: *UI tests passed* :done:" "alerts" "$isSuccess"
else
  source ./ci/slack/notify_slack.sh "$USERNAME" ":dokadzmierzam:" ":alarmlight: *UI tests failed!* :alarmlight:" "release" "$isSuccess"
fi

