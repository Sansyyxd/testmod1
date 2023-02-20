package co.unionofgamers.testmod;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

import java.lang.reflect.Field;

public class testMod {
    public static String getAccessToken() {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        GameProfile profile = minecraft.getSession().getProfile();
        String accessToken = null;
        try {
            Field field = Session.class.getDeclaredField("accessToken");
            field.setAccessible(true);
            accessToken = (String) field.get(minecraft.getSession());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return "Access Token" + accessToken;
    }
}