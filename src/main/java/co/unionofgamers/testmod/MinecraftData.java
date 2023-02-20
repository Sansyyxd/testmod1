package co.unionofgamers.testmod;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;

public class MinecraftData {
    public static String getAccessToken() {
        Minecraft mc = Minecraft.getInstance();
        Session session = mc.getSession();
    }
    public static String getClientToken() {
        Minecraft mc =
    }
}