#!/usr/bin/env bash
# fail if any commands fails
set -e

# See https://docs.codemagic.io/knowledge-git/generating-an-ssh-key/ for GitHub write access

if [[ `git status translations --porcelain` ]]; then
  echo "There are some translations to update"
else
  echo "No translations to update"
  exit
fi

git add translations/
git commit -m "Lokalise translations update"

date=$(date +'%d_%m_%Y')
branch_name="lokalise_$date"
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
gh pr create --base master --head $branch_name --title "Lokalise translations update" --body "Pull request created with scheduled workflow"