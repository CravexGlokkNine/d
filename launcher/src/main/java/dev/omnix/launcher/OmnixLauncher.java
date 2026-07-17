package dev.omnix.launcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimal launcher entry point for local MCP-Reborn development builds.
 */
public final class OmnixLauncher {
    private static final int DEFAULT_RAM_GB = 4;
    private static final int MIN_RAM_GB = 2;
    private static final int MAX_RAM_GB = 16;

    private OmnixLauncher() {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        LauncherOptions options = LauncherOptions.parse(args);
        validatePaths(options);

        List<String> command = new ArrayList<>();
        command.add(options.javaExecutable().toString());
        command.add("-Xmx" + options.ramGb() + "G");
        command.add("-jar");
        command.add(options.clientJar().toString());
        if (!options.server().isBlank()) {
            command.add("--server");
            command.add(options.server());
        }

        System.out.println("Launching Omnix Client with " + options.ramGb() + " GB RAM");
        Process process = new ProcessBuilder(command)
                .inheritIO()
                .start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("Minecraft exited with code " + exitCode);
        }
    }

    private static void validatePaths(LauncherOptions options) throws IOException {
        if (!Files.isRegularFile(options.javaExecutable())) {
            throw new IOException("Java executable not found: " + options.javaExecutable());
        }
        if (!Files.isRegularFile(options.clientJar())) {
            throw new IOException("Client jar not found: " + options.clientJar());
        }
    }

    private record LauncherOptions(Path javaExecutable, Path clientJar, int ramGb, String server) {
        private static LauncherOptions parse(String[] args) {
            Path javaExecutable = Path.of(System.getProperty("java.home"), "bin", isWindows() ? "java.exe" : "java");
            Path clientJar = Path.of("build", "libs", "omnix-client-1.20.1.jar");
            int ramGb = DEFAULT_RAM_GB;
            String server = "";

            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--java" -> javaExecutable = Path.of(readValue(args, ++i, "--java"));
                    case "--client-jar" -> clientJar = Path.of(readValue(args, ++i, "--client-jar"));
                    case "--ram-gb" -> ramGb = clampRam(Integer.parseInt(readValue(args, ++i, "--ram-gb")));
                    case "--server" -> server = readValue(args, ++i, "--server");
                    case "--help" -> {
                        printHelp();
                        System.exit(0);
                    }
                    default -> throw new IllegalArgumentException("Unknown option: " + args[i]);
                }
            }
            return new LauncherOptions(javaExecutable, clientJar, ramGb, server);
        }

        private static String readValue(String[] args, int index, String option) {
            if (index >= args.length || args[index].startsWith("--")) {
                throw new IllegalArgumentException("Missing value for " + option);
            }
            return args[index];
        }

        private static int clampRam(int ramGb) {
            return Math.max(MIN_RAM_GB, Math.min(MAX_RAM_GB, ramGb));
        }

        private static boolean isWindows() {
            return System.getProperty("os.name").toLowerCase().contains("windows");
        }

        private static void printHelp() {
            System.out.println("Omnix Launcher");
            System.out.println("  --java <path>        Java executable to use");
            System.out.println("  --client-jar <path>  Built Omnix client jar");
            System.out.println("  --ram-gb <2-16>      RAM allocation in GB");
            System.out.println("  --server <host>      Optional direct-join server host");
        }
    }
}
