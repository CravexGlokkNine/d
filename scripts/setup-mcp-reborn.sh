#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
MCP_DIR="$ROOT_DIR/mcp-reborn"
JARS_DIR="$ROOT_DIR/jars"

if [[ ! -d "$MCP_DIR/.git" ]]; then
  cat >&2 <<MSG
MCP-Reborn checkout not found at $MCP_DIR.
Clone MCP-Reborn into ./mcp-reborn before running this script.
MSG
  exit 1
fi

for jar in client.jar server.jar; do
  if [[ ! -f "$JARS_DIR/$jar" ]]; then
    echo "Missing $JARS_DIR/$jar. Add the official Minecraft 1.20.1 $jar file." >&2
    exit 1
  fi
done

cd "$MCP_DIR"
./gradlew setup
