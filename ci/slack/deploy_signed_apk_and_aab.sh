#!/usr/bin/env bash

# fail if any commands fails
set -e

isSuccess=$(./ci/slack/options.sh $@)

USERNAME="Bitrise-Android"
ICON_EMOJI=''
TEXT=''

git fetch --tags
GIT_TAG=$(git describe --tags --abbrev=0)

if [ "$isSuccess" = "true" ]; then
  ICON_EMOJI=':google_play:'
  TEXT="*$GIT_TAG is available in Google Play Alpha channel*"
else
  ICON_EMOJI=':broken_heart:'
  TEXT="*Release of $GIT_TAG Candidate failed!*"
fi

source ./ci/slack/notify_slack.sh "$USERNAME" "$ICON_EMOJI" "$TEXT" "release" "$isSuccess"
