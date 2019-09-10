package eu.xgp.staffreport.spigot.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NMSUtils {
    public void sendTitle(Player p, String enumTitle, String text, int stay, int fadeIn, int fadeOut) {
        try {
            Class titleClass = getNMSClass("PacketPlayOutTitle");
            Class chatClass = getNMSClass("IChatBaseComponent");
            Class enumTitleAction = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0];
            Method chatMethod = chatClass.getDeclaredClasses()[0].getMethod("a", String.class);

            Object titleEnum = enumTitleAction.getField(enumTitle).get(null);
            Object titleChatComponent = chatMethod.invoke(null,"{\"text\": \"" + text + "\"}");

            Constructor<?> tConstructur = titleClass.getConstructor(enumTitleAction, chatClass, int.class, int.class, int.class);
            Object titlePacket = tConstructur.newInstance(titleEnum, titleChatComponent, stay, fadeIn, fadeOut);

            sendPacket(p, titlePacket);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(Player p, Object packet) {
        try {
            Object handle = p.getClass().getMethod("getHandle").invoke(p);
            Object conn = handle.getClass().getField("playerConnection").get(handle);
            conn.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(conn, packet);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private Class<?> getNMSClass(String name) {
        Class<?> nmsClass = null;
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            nmsClass = Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return nmsClass;
    }
}
