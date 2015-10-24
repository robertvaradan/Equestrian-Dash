package com.colonelhedgehog.equestriandash.utilities;

import org.bukkit.Bukkit;

import java.lang.reflect.Method;

/**
 * Created by ColonelHedgehog on 12/25/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class Reflection
{
    // http://bukkit.org/threads/basic-reflection-tutorial.329127
    public static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException
    {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        return Class.forName(name);
    }

    public static Class<?> getOBC(String nmsClassString) throws ClassNotFoundException
    {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "org.bukkit.craftbukkit." + version + nmsClassString;
        return Class.forName(name);
    }


    public static Method[] getNMSMethod(Class<?> c)
    {
        return c.getDeclaredMethods();
    }

    // Some other stuff
}

