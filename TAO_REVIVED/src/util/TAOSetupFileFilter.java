package util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class TAOSetupFileFilter extends FileFilter
{
    public static final String EXTENSION = "taos";
    
    public boolean accept(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf(46);
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return "taos".equals(ext) || f.isDirectory();
    }
    
    public String getDescription() {
        return "TAO Setup Files";
    }
}
