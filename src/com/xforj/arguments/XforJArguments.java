package com.xforj.arguments;

import java.io.File;

public class XforJArguments {

   private File inputfile=null;
   private File outputfile=null;
   private boolean minifyhtml=true;
   private boolean assigntoglobal=true;
   private boolean stripnewlines=true;

   public XforJArguments(
      final File inputfile,
      final File outputfile,
      final boolean minifyhtml,
      final boolean assigntoglobal,
      final boolean stripnewlines
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
      this.inputfile=inputfile;
      this.outputfile=outputfile;
      this.minifyhtml=minifyhtml;
      this.assigntoglobal=assigntoglobal;
      this.stripnewlines=stripnewlines;
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
}
