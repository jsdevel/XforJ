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
   private boolean useexternal=false;
   private File outputlibrary=null;
   private boolean escapexss=true;
   @Override
   public void execute() throws BuildException {
      try {
         com.xforj.XforJ.startCompiling(new XforJArguments(
            inputfile,
            outputfile,
            destdir,
            srcdir,
            inputfiles,
            overwrite,
            minifyhtml,
            global,
            normalizespace,
            debug,
            warn,
            removelogs,
            useexternal,
            outputlibrary,
            escapexss
         ));
      } catch (Throwable exc) {
         throw new BuildException(exc.getMessage());
      }
   }

   public void setInputfile(File inputfile){
      this.inputfile=inputfile;
   }
   public void setOutputfile(File outputfile){
      this.outputfile=outputfile;
   }
   public void setDestdir(File destdir){
      this.destdir=destdir;
   }
   public void setSrcdir(File srcdir){
      this.srcdir=srcdir;
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
   public void setGlobal(boolean global){
      this.global=global;
   }
   public void setNormalizespace(boolean normalizespace){
      this.normalizespace=normalizespace;
   }
   public void setDebug(boolean debug){
      this.debug=debug;
   }
   public void setWarn(boolean warn){
      this.warn=warn;
   }
   public void setRemovelogs(boolean removelogs){
      this.removelogs=removelogs;
   }
   public void setUseexternal(boolean useexternal){
      this.useexternal=useexternal;
   }
   public void setOutputlibrary(File outputlibrary){
      this.outputlibrary=outputlibrary;
   }
   public void setEscapexss(boolean escapexss){
      this.escapexss=escapexss;
   }
}
