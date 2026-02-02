using DiscordRPC;
using DiscordRPC.Logging;

var appId = Environment.GetEnvironmentVariable("DISCORD_APP_ID");
if (string.IsNullOrWhiteSpace(appId))
{
    Console.Error.WriteLine("Missing DISCORD_APP_ID env var. Set it to your Discord application ID.");
    Environment.Exit(1);
}

var startTime = DateTime.UtcNow;
var cts = new CancellationTokenSource();

Console.CancelKeyPress += (_, eventArgs) =>
{
    eventArgs.Cancel = true;
    cts.Cancel();
};

using var client = new DiscordRpcClient(appId)
{
    Logger = new ConsoleLogger { Level = LogLevel.Warning }
};

client.OnReady += (_, args) =>
{
    Console.WriteLine($"Connected to Discord as {args.User.Username}#{args.User.Discriminator}.");
};

client.OnError += (_, args) =>
{
    Console.Error.WriteLine($"Discord RPC error {args.Code}: {args.Message}");
};

client.Initialize();
client.SetPresence(new RichPresence
{
    Details = "Building a fast Discord app",
    State = "RPC enabled",
    Timestamps = new Timestamps(startTime),
    Assets = new Assets
    {
        LargeImageKey = "default",
        LargeImageText = "Discord RPC"
    }
});

Console.WriteLine("RPC is live. Press Ctrl+C to stop.");

try
{
    await Task.Delay(Timeout.InfiniteTimeSpan, cts.Token);
}
catch (OperationCanceledException)
{
}

client.ClearPresence();
client.Deinitialize();
