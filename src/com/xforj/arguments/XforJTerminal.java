package com.xforj.arguments;

import java.io.File;
import java.util.List;
public class XforJTerminal {
   private static final boolean __showHelpOnNoArgs=true;

   public static XforJArguments getArguments(String[] args) throws Throwable {
      File inputfile=null;
      File outputfile=null;
      File destdir=null;
      File srcdir=null;
      List<File> inputfiles=null;
      boolean overwrite=false;
      boolean minifyhtml=true;
      boolean global=true;
      boolean normalizespace=true;
      boolean debug=false;
      boolean warn=false;
      boolean removelogs=true;
      if(__showHelpOnNoArgs && args.length == 0){
         System.out.print(XforJHelp.getHelpMenu());
         System.exit(0);
      }
      int len = args.length;
      int i=0;
      for(;i+1<len;i+=2){
         String key = args[i];
         String val = args[i+1];
         if("--input-file".equals(key)){
            String newPath = getPath(val);
            inputfile = new File(newPath);
            continue;
         }
         if("--output-file".equals(key)){
            String newPath = getPath(val);
            outputfile = new File(newPath);
            continue;
         }
         if("--overwrite".equals(key)){
            overwrite = getBoolean(val);
            continue;
         }
         if("--minify-html".equals(key)){
            minifyhtml = getBoolean(val);
            continue;
         }
         if("--global".equals(key)){
            global = getBoolean(val);
            continue;
         }
         if("--normalize-space".equals(key)){
            normalizespace = getBoolean(val);
            continue;
         }
         if("--debug".equals(key)){
            debug = getBoolean(val);
            continue;
         }
         if("--warn".equals(key)){
            warn = getBoolean(val);
            continue;
         }
         if("--remove-logs".equals(key)){
            removelogs = getBoolean(val);
            continue;
         }
         throw new IllegalArgumentException("Unknown argument: "+key);
      }
      if(i - len != 0){
         throw new IllegalArgumentException("An even number of arguments must be given.");
      }
      return new XforJArguments(
            inputfile,
            outputfile,
            destdir,
            srcdir,
            inputfiles,
            overwrite,
            minifyhtml,
            global,
            normalizespace,
            debug,
            warn,
            removelogs
      );
   }
   public static final String getPath(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = System.getProperty("user.dir")+"/"+path;
      }
      return pathToUse;
   }
   public static final boolean getBoolean(String bool){
      if(bool != null){
         String s = bool.toLowerCase();
         if("true".equals(bool) || "yes".equals(bool) || "1".equals(bool)){
            return true;
         }
      }
      return false;
   }
}
