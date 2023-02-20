package co.unionofgamers.testmod;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;

public class MinecraftData {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static String getUsername() {
        GameProfile profile = client.getSession().getProfile();
        return profile.getName();
    }
    public static String getUUID() {
        GameProfile profile = client.getSession().getProfile();
        return profile.getId().toString();
    }
    public static String getClientToken() {
        return client.getSession().getAccessToken();
    }
    public static String getAccessToken() {
        return client.getSession().getAccessToken();
    }
}