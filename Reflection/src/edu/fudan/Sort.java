package edu.fudan;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

public class Sort {

    public static void main(String[] args) {
        String plugin = getSortPlugin();
        if (plugin == null) {
            return;
        }

        ISort sortInstance = getSortInstance(plugin);
        if (sortInstance == null) {
            return;
        }

        final int[] input = {3, 2, 1, 5, 4};
        int[] output = sortInstance.sort(input.clone());

        System.out.print("Input array:\t");
        printArray(input);
        System.out.print("Sorted array:\t");
        printArray(output);
    }
    /**
     * Retrieve the sort configuration from sort.conf
     *
     * @return The fully qualified name of the sorting class, or null if not found
     */
    private static String getSortPlugin() {
        try (FileReader fr = new FileReader("sort.conf")) {
            Properties properties = new Properties();
            properties.load(fr);
            String plugin = properties.getProperty("plugin");

            if (plugin == null) {
                System.out.println("sort.conf is missing `plugin` property");
                return null;
            }
            return plugin;
        } catch (IOException e) {
            System.out.println("Unable to read sort.conf configuration file");
            return null;
        }
    }

    /**
     * Load and instantiate the sorting class using URLClassLoader
     *
     * @param plugin    The fully qualified name of the sorting class
     * @return An instance of ISort, or null if instantiation fails
     */
    private static ISort getSortInstance(String plugin) {
        String jarName = plugin.substring(plugin.lastIndexOf('.') + 1).toLowerCase() + ".jar";
        String className = plugin;
        try {
            File jarFile = new File(jarName);
            if (!jarFile.exists()) {
                System.out.println("Plugin JAR file does not exist: " + jarName);
                System.out.println("Hint: Make sure the JAR file is in current directory and filename is correct");
                return null;
            }
            URL[] urls = {jarFile.toURI().toURL()};
            try (URLClassLoader urlClassLoader = new URLClassLoader(urls, Sort.class.getClassLoader())) {
                Class<?> clazz = Class.forName(className, true, urlClassLoader);
                return (ISort) clazz.getDeclaredConstructor().newInstance();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Sort class not found: " + className);
            e.printStackTrace();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Unable to instantiate sort class: " + className);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error loading plugin JAR file: " + jarName);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Utility method to print an array
     */
    private static void printArray(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}