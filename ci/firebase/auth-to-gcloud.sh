set -ex

echo $GCLOUD_KEY_FILE | base64 --decode > ./firebase.json
gcloud auth activate-service-account --key-file=./firebase.json
gcloud --quiet config set project $FIREBASE_PROJECT_ID