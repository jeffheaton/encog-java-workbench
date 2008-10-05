package org.encog.workbench.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExtensionFilter extends FileFilter {
    private String extensions[];

    private String description;

    public ExtensionFilter(String description, String extension) {
      this(description, new String[] { extension });
    }

    public ExtensionFilter(String description, String extensions[]) {
      this.description = description;
      this.extensions = (String[]) extensions.clone();
    }

    public boolean accept(File file) {
      if (file.isDirectory()) {
        return true;
      }
      int count = extensions.length;
      String path = file.getAbsolutePath();
      for (int i = 0; i < count; i++) {
        String ext = extensions[i];
        if (path.endsWith(ext)
            && (path.charAt(path.length() - ext.length()) == '.')) {
          return true;
        }
      }
      return false;
    }

    public String getDescription() {
      return (description == null ? extensions[0] : description);
    }
    
    public static String getExtension(File file)
    {
    	String filename = file.getName();
    	String ext = (filename.lastIndexOf(".")==-1)?null:filename.substring(filename.lastIndexOf(".")+1,filename.length());
    	return ext;
    }
  }
