# Discord RPC App (C#)

Fast, minimal C# console app that connects to Discord Rich Presence (RPC) using the `DiscordRPC` NuGet package.

## Requirements
- .NET 8 SDK
- A Discord application ID (from the Discord Developer Portal)

## Run
```bash
export DISCORD_APP_ID="your_app_id"
dotnet run --project DiscordRpcApp
```

## Notes
- The example sets a simple presence with timestamps and assets.
- Update `Assets` in `Program.cs` to match the image keys configured in your Discord application.
