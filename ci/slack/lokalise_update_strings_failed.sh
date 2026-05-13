#!/usr/bin/env bash

# fail if any commands fails
set -e

USERNAME="Bitrise-Android"
ICON_EMOJI=':broken_heart:'
TEXT='*Lokalise translations update failed!*'

source ./ci/slack/notify_slack.sh "$USERNAME" "$ICON_EMOJI" "$TEXT" "alerts" "#ff0000"
