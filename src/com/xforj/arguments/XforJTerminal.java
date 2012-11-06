package com.xforj.arguments;

import java.io.File;

public class XforJTerminal {
   private static final boolean __showHelpOnNoArgs=true;

   public static XforJArguments getArguments(String[] args) throws IllegalArgumentException {
      File inputfile=null;
      File outputfile=null;
      boolean minifyhtml=true;
      boolean assigntoglobal=true;
      boolean stripnewlines=true;
      boolean debug=false;
      boolean warn=false;
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
         if("--minify-html".equals(key)){
            minifyhtml = getBoolean(val);
            continue;
         }
         if("--assign-to-global".equals(key)){
            assigntoglobal = getBoolean(val);
            continue;
         }
         if("--strip-new-lines".equals(key)){
            stripnewlines = getBoolean(val);
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
      }
      if(i - len != 0){
         throw new IllegalArgumentException("An even number of arguments must be given.");
      }
      return new XforJArguments(
            inputfile,
            outputfile,
            minifyhtml,
            assigntoglobal,
            stripnewlines,
            debug,
            warn
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
