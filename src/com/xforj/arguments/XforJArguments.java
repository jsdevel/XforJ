package com.xforj.arguments;

import java.io.File;
import java.util.List;
public class XforJArguments {

   private File inputfile=null;
   private File outputfile=null;
   private File destdir=null;
   private File srcdir=null;
   private List<File> inputfiles=null;
   private boolean overwrite=false;
   private boolean minifyhtml=true;
   private boolean global=true;
   private boolean normalizespace=true;
   private boolean debug=false;
   private boolean warn=false;
   private boolean removelogs=true;

   public XforJArguments(
      final File inputfile,
      final File outputfile,
      final File destdir,
      final File srcdir,
      final List<File> inputfiles,
      final boolean overwrite,
      final boolean minifyhtml,
      final boolean global,
      final boolean normalizespace,
      final boolean debug,
      final boolean warn,
      final boolean removelogs
   ) throws Throwable {
      if(inputfile!=null && inputfile.exists() && !inputfile.canWrite()) {
         throw new IllegalArgumentException("The following file may not be overwritten to: 'inputfile'.");
      }
      if(outputfile!=null && outputfile.exists() && !outputfile.canWrite()) {
         throw new IllegalArgumentException("The following file may not be overwritten to: 'outputfile'.");
      }
      if(destdir != null){
         if(!destdir.exists() || !destdir.isDirectory()){
            destdir.mkdirs();
         }
         if(!(destdir.exists() && destdir.isDirectory())){
            throw new IllegalArgumentException("Directory doesn't exist :'"+destdir+"'.  Given by argument 'destdir'.");
         }
      }
      if(srcdir != null){
         if(!srcdir.exists() || !srcdir.isDirectory()){
            srcdir.mkdirs();
         }
         if(!(srcdir.exists() && srcdir.isDirectory())){
            throw new IllegalArgumentException("Directory doesn't exist :'"+srcdir+"'.  Given by argument 'srcdir'.");
         }
      }
      this.inputfile=inputfile;
      this.outputfile=outputfile;
      this.destdir=destdir;
      this.srcdir=srcdir;
      this.inputfiles=inputfiles;
      this.overwrite=overwrite;
      this.minifyhtml=minifyhtml;
      this.global=global;
      this.normalizespace=normalizespace;
      this.debug=debug;
      this.warn=warn;
      this.removelogs=removelogs;
   }

   public File getInputfile(){
      return inputfile;
   }
   public boolean hasInputfile(){
      return inputfile!=null;
   }
   public File getOutputfile(){
      return outputfile;
   }
   public boolean hasOutputfile(){
      return outputfile!=null;
   }
   public File getDestdir(){
      return destdir;
   }
   public boolean hasDestdir(){
      return destdir!=null;
   }
   public File getSrcdir(){
      return srcdir;
   }
   public boolean hasSrcdir(){
      return srcdir!=null;
   }
   public List<File> getInputfiles(){
      return inputfiles;
   }
   public boolean hasInputfiles(){
      return inputfiles!=null;
   }
   public boolean getOverwrite(){
      return overwrite;
   }
   public boolean hasOverwrite(){
      return overwrite;
   }
   public boolean getMinifyhtml(){
      return minifyhtml;
   }
   public boolean hasMinifyhtml(){
      return minifyhtml;
   }
   public boolean getGlobal(){
      return global;
   }
   public boolean hasGlobal(){
      return global;
   }
   public boolean getNormalizespace(){
      return normalizespace;
   }
   public boolean hasNormalizespace(){
      return normalizespace;
   }
   public boolean getDebug(){
      return debug;
   }
   public boolean hasDebug(){
      return debug;
   }
   public boolean getWarn(){
      return warn;
   }
   public boolean hasWarn(){
      return warn;
   }
   public boolean getRemovelogs(){
      return removelogs;
   }
   public boolean hasRemovelogs(){
      return removelogs;
   }
}
