package com.xforj.arguments;

import java.io.File;

public class XforJArguments {

   private File inputfile=null;
   private File outputfile=null;
   private boolean minifyhtml=true;
   private boolean assigntoglobal=true;
   private boolean stripnewlines=true;
   private boolean debug=false;
   private boolean warn=false;

   public XforJArguments(
      final File inputfile,
      final File outputfile,
      final boolean minifyhtml,
      final boolean assigntoglobal,
      final boolean stripnewlines,
      final boolean debug,
      final boolean warn
   ){
      if(inputfile!=null && inputfile.exists() && !inputfile.canWrite()) {
         throw new IllegalArgumentException("The following file may not be overwritten to: 'inputfile'.");
      }
      if(outputfile!=null && outputfile.exists() && !outputfile.canWrite()) {
         throw new IllegalArgumentException("The following file may not be overwritten to: 'outputfile'.");
      }
      if(inputfile==null) {
         throw new IllegalArgumentException("The following argument is required: '--input-file'.");
      }
      if(outputfile==null) {
         throw new IllegalArgumentException("The following argument is required: '--output-file'.");
      }
      this.inputfile=inputfile;
      this.outputfile=outputfile;
      this.minifyhtml=minifyhtml;
      this.assigntoglobal=assigntoglobal;
      this.stripnewlines=stripnewlines;
      this.debug=debug;
      this.warn=warn;
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
   public boolean getDebug(){
      return debug;
   }
   public boolean getWarn(){
      return warn;
   }
}
