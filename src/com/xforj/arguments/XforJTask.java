package com.xforj.arguments;

import org.apache.tools.ant.*;

import java.io.File;

public class XforJTask extends Task {

   private File inputfile=null;
   private File outputfile=null;
   private boolean minifyhtml=true;
   private boolean assigntoglobal=true;
   private boolean stripnewlines=true;
   @Override
   public void execute() throws BuildException {
      com.xforj.XforJ.startCompiling(new XforJArguments(
         inputfile,
         outputfile,
         minifyhtml,
         assigntoglobal,
         stripnewlines
      ));
   }

   public void setInputfile(File inputfile){
      this.inputfile=inputfile;
   }
   public void setOutputfile(File outputfile){
      this.outputfile=outputfile;
   }
   public void setMinifyhtml(boolean minifyhtml){
      this.minifyhtml=minifyhtml;
   }
   public void setAssigntoglobal(boolean assigntoglobal){
      this.assigntoglobal=assigntoglobal;
   }
   public void setStripnewlines(boolean stripnewlines){
      this.stripnewlines=stripnewlines;
   }
}
