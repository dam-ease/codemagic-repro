#!/usr/bin/env bash
# fail if any commands fails
set -e

# See https://docs.codemagic.io/knowledge-git/generating-an-ssh-key/ for GitHub write access

PROFILE_PATH="app/src/release/generated/baselineProfiles"

if [[ `git status $PROFILE_PATH --porcelain` ]]; then
  echo "Baseline profile changed - opening review PR"
else
  echo "No baseline profile changes - skipping PR creation"
  exit
fi

git add $PROFILE_PATH
git commit -m "Update baseline profile"

date=$(date +'%d_%m_%Y')
branch_name="baseline_profile_$date"
git checkout -b $branch_name

echo "${GITHUB_PRIVATE_SSH_KEY}" > /tmp/ssh_key
chmod 600 /tmp/ssh_key
eval `ssh-agent -s`
ssh-add /tmp/ssh_key

git remote add originssh git@github.com:DroidsOnRoids/ccc-android.git
git push originssh $branch_name

if [[ "$OSTYPE" == "linux-gnu"* ]]; then # Linux
  sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-key 23F3D4EA75716059
  sudo add-apt-repository -r https://cli.github.com/packages
  sudo apt update
  sudo apt install gh
elif [[ "$OSTYPE" == "darwin"* ]]; then # Mac OSX
  brew install gh
else
  echo "Unsupported OS type: $OSTYPE"
  exit 0
fi

gh auth login --with-token <<< $GITHUB_PERSONAL_ACCESS_TOKEN_FOR_PR
gh pr create --base master --head $branch_name --title "Update baseline profile" --body "Pull request created with scheduled workflow"
