#!/usr/bin/env bash
set -e
set -o pipefail
set -x

CONFIG_PATH="$CM_BUILD_DIR/app/src/debug/res/xml/network_security_config.xml"

cat > "$CONFIG_PATH" <<EOL
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
  <debug-overrides>
    <trust-anchors>
      <!-- Trust user added CAs while debuggable only -->
      <certificates src="user" />
      <certificates src="system" />
    </trust-anchors>
  </debug-overrides>

  <base-config cleartextTrafficPermitted="true">
    <trust-anchors>
      <certificates src="system" />
      <certificates src="user" />
    </trust-anchors>
  </base-config>
</network-security-config>
EOL

echo "The network_security_config.xml file has been replace."