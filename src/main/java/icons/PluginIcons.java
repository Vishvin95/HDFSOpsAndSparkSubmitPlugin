package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface PluginIcons {
    Icon folder = IconLoader.getIcon("/icons/folder.svg", PluginIcons.class);
    Icon file = IconLoader.getIcon("/icons/file.svg", PluginIcons.class);
    Icon hdfs = IconLoader.getIcon("/icons/webHdfs.svg", PluginIcons.class);
    Icon refresh = IconLoader.getIcon("/icons/refresh.svg", PluginIcons.class);
    Icon spark = IconLoader.getIcon("/icons/spark.svg", PluginIcons.class);
    Icon paste = IconLoader.getIcon("/icons/paste.svg", PluginIcons.class);
    Icon config = IconLoader.getIcon("/icons/config.svg", PluginIcons.class);
}
