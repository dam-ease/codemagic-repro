#!/usr/bin/env bash

USERNAME=$1
ICON_EMOJI=$2
TEXT=$3
CHANNEL_NAME=$4
IS_SUCCESS=$5

#https://docs.codemagic.io/knowledge-others/slack-api-integration/

# Local run test data
# SLACK_ALERTS_WEBHOOK=https://hooks.slack.com/services/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
# SLACK_CCC_ANDROID_RELEASE_ALERT_WEBHOOK=https://hooks.slack.com/services/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
# CM_BRANCH="master"
# CM_PROJECT_ID=6932d50af4bfa1dbff9d90ee
# CM_BUILD_ID=6935e1906c27566d24cd4bcf
# BUILD_NUMBER=75

case "${CHANNEL_NAME}" in
  'release') CHANNEL=$SLACK_CCC_ANDROID_RELEASE_ALERT_WEBHOOK ;;
  'alerts') CHANNEL=$SLACK_ALERTS_WEBHOOK ;;
  *) print_usage
     exit 1 ;;
esac

if [ "$IS_SUCCESS" = "true" ]; then
   COLOR="#4FA833"
else
   COLOR="#ff0000"
fi

# Step code
BUILD_URL=https://codemagic.io/app/$CM_PROJECT_ID/build/$CM_BUILD_ID
NOW="$(date +'%d/%m/%Y  %H:%M')"

if [ "$CM_TAG" = "" ]; then
  TRIGGER_HEAD_TITLE="*Branch*"
else
  TRIGGER_HEAD_TITLE="*Tag*"
fi

if [ "$CM_TAG" = "" ]; then
  TRIGGER_HEAD_VALUE="$CM_BRANCH"
else
  TRIGGER_HEAD_VALUE="$CM_TAG"
fi

if [ -n "$CM_TAG" ] && [ "$CHANNEL_NAME" = "release" ]; then
  SHOULD_SHOW_APK_BOTTON=$IS_SUCCESS
else
  SHOULD_SHOW_APK_BOTTON="false"
fi

if [ "$SHOULD_SHOW_APK_BOTTON" = "true" ]; then
  BUTTONS_JSON=$(
  cat <<EOF
{
  "type": "button",
  "text": {
    "type": "plain_text",
    "text": "Download APK :package:",
    "emoji": true
  },
  "value": "apk_download",
  "url": "https://codemagic.io/dashboard/b476bba8-7a6f-4c13-8f00-7913c693b3e6?"
}
EOF
)
else
  BUTTONS_JSON=$(
  cat <<EOF
{
  "type": "button",
  "text": {
    "type": "plain_text",
    "text": "View Build :hammer_and_wrench:",
    "emoji": true
  },
  "value": "build_url",
  "url": "$BUILD_URL"
}
EOF
)
fi

JSON=$(
  cat <<EOF
{
  "username": "$USERNAME",
  "icon_emoji": "$ICON_EMOJI",
  "text": "Step result",
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "$TEXT",
      }
    }
  ],
  "attachments": [
    {
      "color": "$COLOR",
      "blocks": [
        {
          "type": "section",
          "fields": [
            {
              "type": "mrkdwn",
              "text": "*App*"
            },
            {
              "type": "mrkdwn",
              "text": "$TRIGGER_HEAD_TITLE"
            },
            {
              "type": "plain_text",
              "text": "ccc-android"
            },
            {
              "type": "plain_text",
              "text": "$TRIGGER_HEAD_VALUE"
            }
          ]
        },
        {
          "type": "section",
          "fields": [
            {
              "type": "mrkdwn",
              "text": "*Workflow*"
            },
            {
              "type": "mrkdwn",
              "text": "*Build no.*"
            },
            {
              "type": "plain_text",
              "text": "$CM_WORKFLOW_NAME"
            },
            {
              "type": "plain_text",
              "text": "$BUILD_NUMBER"
            }
          ]
        },
        {
          "type": "context",
          "elements": [
            {
              "type": "image",
              "image_url": "https://codemagic.io/media/landing/press-kit/png/star-gradient.png",
              "alt_text": "images"
            },
            {
              "type": "mrkdwn",
              "text": "Codemagic   $NOW"
            }
          ]
        },
        {
          "type": "actions",
          "elements": [
            $BUTTONS_JSON
          ]
        }
      ]
    }
  ]
}
EOF
)

curl -d "$JSON" -H "Content-type: application/json" -X POST $CHANNEL
