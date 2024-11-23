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
        String[] config = getSortConfig();
        if (config == null) {
            return;
        }
        String jarName = config[0];
        String className = config[1];

        ISort sortInstance = getSortInstance(jarName, className);
        if (sortInstance == null) {
            return;
        }

        int[] input = {3, 2, 1, 5, 4};
        int[] output = sortInstance.sort(input);
        for (int i : output) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    /**
     * Retrieve the sort configuration from sort.conf
     *
     * @return Array containing the JAR filename and the class name, or null if not found
     */
    private static String[] getSortConfig() {
        try (FileReader fr = new FileReader("sort.conf")) {
            Properties properties = new Properties();
            properties.load(fr);
            String jarName = properties.getProperty("sortJar");
            String className = properties.getProperty("sortClass");
            if (jarName == null || className == null) {
                System.out.println("sort.conf is missing `sortJar` or `sortClass` property");
                return null;
            }
            return new String[]{jarName, className};
        } catch (IOException e) {
            System.out.println("Unable to read sort.conf configuration file");
            return null;
        }
    }

    /**
     * Load and instantiate the sorting class using URLClassLoader
     *
     * @param jarName    The name of the JAR file containing the sorting class
     * @param className  The fully qualified name of the sorting class
     * @return An instance of ISort, or null if instantiation fails
     */
    private static ISort getSortInstance(String jarName, String className) {
        try {
            File jarFile = new File(jarName);
            if (!jarFile.exists()) {
                System.out.println("Plugin JAR file does not exist: " + jarName);
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
}