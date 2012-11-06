package com.xforj.arguments;

import org.apache.tools.ant.*;

import java.io.File;

public class XforJTask extends Task {

   private File inputfile=null;
   private File outputfile=null;
   private boolean minifyhtml=true;
   private boolean assigntoglobal=true;
   private boolean stripnewlines=true;
   private boolean debug=false;
   private boolean warn=false;
   @Override
   public void execute() throws BuildException {
      try {
         com.xforj.XforJ.startCompiling(new XforJArguments(
            inputfile,
            outputfile,
            minifyhtml,
            assigntoglobal,
            stripnewlines,
            debug,
            warn
         ));
      } catch (Exception exc) {
         throw new BuildException(exc.getMessage());
      }
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
   public void setDebug(boolean debug){
      this.debug=debug;
   }
   public void setWarn(boolean warn){
      this.warn=warn;
   }
}
