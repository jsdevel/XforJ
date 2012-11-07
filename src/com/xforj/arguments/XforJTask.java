package com.xforj.arguments;

import org.apache.tools.ant.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import java.io.File;
import java.util.List;

public class XforJTask extends Task {

   private File inputfile=null;
   private File outputfile=null;
   private File outputdirectory=null;
   private File inputdirectory=null;
   private List<File> inputfiles=null;
   private boolean overwrite=false;
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
            outputdirectory,
            inputdirectory,
            inputfiles,
            overwrite,
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
   public void setOutputdirectory(File outputdirectory){
      this.outputdirectory=outputdirectory;
   }
   public void setInputdirectory(File inputdirectory){
      this.inputdirectory=inputdirectory;
   }
   public void addConfigured(FileSet files){
      Iterator<FileResource> iterator = files.iterator();
      while(iterator.hasNext()){
         if(inputfiles==null){
            inputfiles= new ArrayList<File>();
         }
         File next = iterator.next().getFile();
         inputfiles.add(next);
      }
   }
   public void setOverwrite(boolean overwrite){
      this.overwrite=overwrite;
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
