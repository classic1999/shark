package org.enhydra.shark.toolagent;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Use the static <code>load</code> method to load classes that implement tool
 * agents from jars located in a plugin directory. The name of the directory
 * defaults to "plugins". A different name may be given using the
 * ToolAgentPluginDir property in Shark's configuration file (conf/Shark.conf).
 * <br><br>
 * <b>NOTE: The plugin dir must not be in the classpath if you want the tool
 * agent class files to be reloadable. Otherwise they will be loaded by the
 * default class loader which does not provide a way to reload classes Note also
 * that whenever the default tool agent loads a secondary tool agent the
 * implementing class of the latter will be loaded anew. This fact may cause an
 * impact on the performance of your system. You may want to decide to put the
 * jar files holding your tool agent classes into the lib subdir which is in the
 * classpath.</b>
 *
 * @author Dirk Hoffmann, H+BEDV (www.antivir.de)
 */
public class ToolAgentLoader {

    /**
     * Load tool agent classes from plugin directory.
     *
     * @param cus
     *            callback utilities used to query the ToolAgentPluginDir
     *            property
     * @param name
     *            name of the class
     * @return the tool agent class
     * @throws ClassNotFoundException
     *             if tool agent class could not be found in the plugin
     *             directory
     * @throws MalformedURLException
     *             if the value of the ToolAgentPluginDir property results in an
     *             invalid URL
     */
    public static Class load(CallbackUtilities cus, String name)
            throws ClassNotFoundException, MalformedURLException {

        // The place where tool agent plugins will be found
        File toolAgentPluginDir = new File(cus.getProperty(
                "ToolAgentPluginDir", "plugins"));

        // We will look for jar and property files.
        FilenameFilter jarAndPropFiles = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar") || name.endsWith(".properties");
            }
        };

        File[] files = toolAgentPluginDir.listFiles(jarAndPropFiles);

        // Convert filenames to urls and create an alternate class path to be
        // used with the URLClassLoader
        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; ++i) {
            //urls[i] = files[i].toURL();
            // ,-----------------^^^^^^^
            // `-- depends on the user.dir property which may be corrupted.

            // Therefore we construct the URL using string methods.
            urls[i] = new URL("file:" + files[i].getPath());
        }

        // Create the URLClassLoader. Provide urls as search path. Provide the
        // class loader that loaded this class as the parent class loader (the
        // one that uses CLASSPATH). This is necessary to allow tool agents to
        // use statically loaded classes. This requires classes that are to be
        // loaded dynamically to be not in the CLASSPATH.
        ClassLoader cl = new URLClassLoader(urls, ToolAgentLoader.class
                .getClassLoader());

        // Finally load class
        return cl.loadClass(name);
    }
}