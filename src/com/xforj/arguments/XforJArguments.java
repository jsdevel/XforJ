package com.xforj.arguments;

import java.io.File;
import java.util.List;
public class XforJArguments {

   private File inputfile=null;
   private File outputfile=null;
   private File outputdirectory=null;
   private File inputdirectory=null;
   private List<File> inputfiles=null;
   private boolean overwrite=false;
   private boolean minifyhtml=true;
   private boolean global=true;
   private boolean normalizespace=true;
   private boolean debug=false;
   private boolean warn=false;

   public XforJArguments(
      final File inputfile,
      final File outputfile,
      final File outputdirectory,
      final File inputdirectory,
      final List<File> inputfiles,
      final boolean overwrite,
      final boolean minifyhtml,
      final boolean global,
      final boolean normalizespace,
      final boolean debug,
      final boolean warn
   ){
      if(inputfile!=null && inputfile.exists() && !inputfile.canWrite()) {
         throw new IllegalArgumentException("The following file may not be overwritten to: 'inputfile'.");
      }
      if(outputfile!=null && outputfile.exists() && !outputfile.canWrite()) {
         throw new IllegalArgumentException("The following file may not be overwritten to: 'outputfile'.");
      }
      if(outputdirectory != null && !outputdirectory.isDirectory()) {
         throw new IllegalArgumentException("Directory doesn't exist :'"+outputdirectory+"'.  Given by argument 'outputdirectory'.");
      }
      if(inputdirectory != null && !inputdirectory.isDirectory()) {
         throw new IllegalArgumentException("Directory doesn't exist :'"+inputdirectory+"'.  Given by argument 'inputdirectory'.");
      }
      this.inputfile=inputfile;
      this.outputfile=outputfile;
      this.outputdirectory=outputdirectory;
      this.inputdirectory=inputdirectory;
      this.inputfiles=inputfiles;
      this.overwrite=overwrite;
      this.minifyhtml=minifyhtml;
      this.global=global;
      this.normalizespace=normalizespace;
      this.debug=debug;
      this.warn=warn;
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
   public File getOutputdirectory(){
      return outputdirectory;
   }
   public boolean hasOutputdirectory(){
      return outputdirectory!=null;
   }
   public File getInputdirectory(){
      return inputdirectory;
   }
   public boolean hasInputdirectory(){
      return inputdirectory!=null;
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
}
