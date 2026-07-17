# Omnix Client

**Tagline:** Native. Unfiltered. Unmatched.

Omnix Client is a Minecraft 1.20.1 MCP-Reborn project scaffold for a native client build focused on legitimate visual customization, HUD overlays, performance controls, and developer rebuildability.

## Supported Development Baseline

- Minecraft version: 1.20.1
- Framework: MCP-Reborn, based on MCPConfig and ForgeGradle
- Java: JDK 17
- IDE: IntelliJ IDEA with Gradle import

## Repository Layout

- `settings.gradle` and `build.gradle`: root Gradle scaffold for MCP-Reborn validation and launcher distribution tasks.
- `launcher/`: Java 17 launcher application that starts a built Omnix client jar with configurable RAM and optional direct server join.
- `scripts/setup-mcp-reborn.sh`: helper script that validates local jars and runs MCP-Reborn setup.
- `jars/`: local-only directory for official Minecraft 1.20.1 `client.jar` and `server.jar`.
- `mcp-reborn/`: local-only MCP-Reborn checkout.
- `patches/`: workspace for reviewed MCP source patches.
- `config/omnix/`: default Omnix configuration.

## Build Workflow

1. Clone MCP-Reborn into `./mcp-reborn`.
2. Place the official Minecraft 1.20.1 `client.jar` and `server.jar` files in `./jars/`.
3. Run `./scripts/setup-mcp-reborn.sh` or `./gradlew setupMcpReborn` to decompile editable sources.
4. Apply Omnix source patches and configuration files.
5. Run `./gradlew runclient` for local testing.
6. Run `./gradlew build` to build the launcher distribution and copy default Omnix config.

## Launcher

The launcher module is a minimal Java 17 application. It accepts these options:

```text
--java <path>        Java executable to use
--client-jar <path>  Built Omnix client jar
--ram-gb <2-16>      RAM allocation in GB
--server <host>      Optional direct-join server host
```

Example:

```bash
./gradlew :launcher:run --args="--client-jar build/libs/omnix-client-1.20.1.jar --ram-gb 4"
```

## Safety and Fair-Play Scope

This repository intentionally excludes functionality that would provide unfair multiplayer advantages, bypass server enforcement, spoof credentials, or evade anti-cheat systems. Omnix Client development should remain limited to features that are appropriate for local play, accessibility, visual preferences, performance tuning, and transparent HUD overlays.

Excluded feature categories include:

- Extended reach, hitbox expansion, cooldown bypasses, velocity suppression, or combat automation for multiplayer use.
- Anti-cheat evasion, packet spoofing, scan bypasses, or stealth toggles.
- Account token caching, session spoofing, impersonation, or premium-server nickname overrides.
- Any feature intended to deceive servers or other players.

## Planned Compliant Features

### Visuals

- No-fog toggle for local visual preference where permitted.
- Transparent chat background option.
- Low-fire visual option.
- Brightness override.
- Hurt-camera and red-flash visibility toggle.

### HUD

- Coordinates display.
- Ping display.
- Armor durability display.
- Potion timers.
- CPS display for self-observation only.

### Performance

- Render distance override.
- Entity distance override.
- Force garbage collection button.
- Conservative rendering optimizations that do not alter gameplay semantics.

### Profile Management

Omnix may support local display profiles and configuration presets. It must not store, replay, spoof, or override access tokens or authenticated account identities.

## Configuration

Default user-facing settings live in `config/omnix/omnix-settings.json`. The schema is intentionally limited to legitimate visual, HUD, and performance preferences.

## Keybinds

See [`KEYBINDS.txt`](KEYBINDS.txt) for the default keybind cheat sheet.
