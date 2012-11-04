package com.xforj.arguments;

import java.io.File;

public class XforJTerminal {
   private boolean __showHelpOnNoArgs=true;
   private File inputfile;
   private File outputfile;
   private boolean minifyhtml=true;
   private boolean assigntoglobal=true;
   private boolean stripnewlines=true;

   public XforJTerminal(String[] args) throws IllegalArgumentException {
      super();
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
            if(inputfile.exists() && !inputfile.canWrite()) {
               throw new IllegalArgumentException("The following file may not be overwritten to: '"+inputfile+"'.");
            }
            continue;
         }
         if("--output-file".equals(key)){
            String newPath = getPath(val);
            outputfile = new File(newPath);
            if(outputfile.exists() && !outputfile.canWrite()) {
               throw new IllegalArgumentException("The following file may not be overwritten to: '"+outputfile+"'.");
            }
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
      }
      if(i - len != 0){
         throw new IllegalArgumentException("An even number of arguments must be given.");
      }
            if(inputfile==null) {
               throw new IllegalArgumentException("The following argument is required: '--input-file'.");
            }
   }
   public File getInputfile(){
      return inputfile;
   }
   public File getOutputfile(){
      return outputfile;
   }
   public boolean getMinifyhtml(){
      return minifyhtml;
   }
   public boolean getAssigntoglobal(){
      return assigntoglobal;
   }
   public boolean getStripnewlines(){
      return stripnewlines;
   }
   private String getPath(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = System.getProperty("user.dir")+"/"+path;
      }
      return pathToUse;
   }
   public final boolean getBoolean(String bool){
      if(bool != null){
         String s = bool.toLowerCase();
         if("true".equals(bool) || "yes".equals(bool) || "1".equals(bool)){
            return true;
         }
      }
      return false;
   }
}
