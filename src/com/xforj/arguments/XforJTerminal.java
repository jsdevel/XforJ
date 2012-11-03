package com.xforj.arguments;

import java.io.File;

public class XforJTerminal {

   private File inputfile;
   private File outputfile;

   public XforJTerminal(String[] args) throws IllegalArgumentException {
      super();
      int len = args.length;
      int i=0;
      for(;i+1<len;i+=2){
         String key = args[i];
         String val = args[i+1];
         if("--input-file".equals(key)){
            String newPath = __getPath(val);
            inputfile = new File(newPath);
            continue;
         }
         if("--output-file".equals(key)){
            String newPath = __getPath(val);
            outputfile = new File(newPath);
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
   private String __getPath(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = System.getProperty("user.dir")+"/"+path;
      }
      return pathToUse;
   }
}