#!/usr/bin/env bash

# fail if any commands fails
set -e

isSuccess=$(./ci/slack/options.sh $@)

USERNAME="BASELINE PROFILE BENCHMARKS"
ICON_EMOJI=''
TEXT=''

if [ "$isSuccess" = "true" ]; then
  ICON_EMOJI=':stonks:'
  TEXT=':piorun: *Baseline profile benchmarks passed!* :piorun:'
else
  ICON_EMOJI=':pobudka:'
  TEXT=':no_entry_sign: *Baseline profile benchmarks failed!* :no_entry_sign:'
fi

source ./ci/slack/notify_slack.sh "$USERNAME" "$ICON_EMOJI" "$TEXT" "alerts" "$isSuccess"
