package util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class TAOGameFileFilter extends FileFilter
{
    public static final String EXTENSION = "taog";
    
    public boolean accept(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf(46);
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return "taog".equals(ext) || f.isDirectory();
    }
    
    public String getDescription() {
        return "TAO Game Files";
    }
}
