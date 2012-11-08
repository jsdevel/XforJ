package com.xforj.arguments;

public class XforJHelp {
   public static String getHelpMenu(){
return "TITLE\n   XforJ A Javascript Template Compiler \n\nCOPYRIGHT\n   © 2012 Joseph Spencer \n\nLICENSE\n\n   http://www.apache.org/licenses/LICENSE-2.0 \n\nDESCRIPTION\n   XforJ combines the elegance of \n   javascript and the power of XSL \n   elements to produce compiled javascript \n   templates. You compose templates in a \n   hybird grammar and compile that down to \n   javascript. \n\n   Visit http://www.xforj.com for more \n   info. \n\nEXAMPLE\n   java -jar XforJ.x.x.x.jar --input-file \n   /my/template/file.html \n   --output-directory \n   /my/template/output.js \n\nARGUMENTS\n   OPTIONAL\n      --input-file\n         A pre-compiled XforJ template file. \n\n         This is used in conjunction with the \n         --output-file argument, or the \n         --output-directory argument. \n\n         When --output-file is specified, this \n         will be the target of your input file. \n\n         When --output-directory is used without \n         --output-file, the target file will \n         have the same name as the input file, \n         and will reside in the location \n         specified by --output-directory. \n\n      --output-file\n         The file where the compiled template \n         will be placed. \n\n      destdir\n         [ANT PROPERTY]\n         The output directory for files given by \n         --input-file, or inputfiles(ant). \n         Directory structure is copied from \n         inputfiles. \n\n      srcdir\n         [ANT PROPERTY]\n         The source directory for files found \n         using inputfiles(ant). This argument is \n         required when using nested filesets, in \n         order to preserve directory structure. \n\n      inputfiles\n         [NESTED ANT TASK]\n         The input files to compile. When using \n         this option, you must provide both the \n         --output-directory and \n         --input-directory arguments, or this \n         task has no effect. \n\n      --overwrite\n         Whether or not files should be \n         overriden. If not specified, any \n         attempt to overwrite an existing file \n         will be rejected, and a message \n         displayed to the output. \n\n      --minify-html\n         When enabled, space is removed before \n         and after all '<>'. \n\n      --global\n         When enabled, all templates are \n         assigned to the global object. When \n         disabled, a new object is returned from \n         the entire XforJ program. In this \n         scenario, a mechanism such as eval must \n         be used to capture the returned object \n         to gain access to the defined \n         templates. \n\n      --normalize-space\n         Replace all white space with a single \n         space ' '. \n\n      --debug\n         Turns on debugging. \n\n      --warn\n         Turns on run time warnings. \n\n";   }
}
