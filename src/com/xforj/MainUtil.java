/*
 * Copyright 2012 Joseph Spencer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xforj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

/**
 *
 * @author Joseph Spencer
 */
public class MainUtil {
   public static String arrayStringToString( String[] args ){
      StringBuilder argumentStringBuilder = new StringBuilder();
      for ( int i = 0; i < args.length; i++){
         argumentStringBuilder.append( args[i] );
      }
      return argumentStringBuilder.toString();
   }

   public static char[] getChars(File file)
      throws IOException
   {
      String str;

      FileReader reader = new FileReader(file);
      BufferedReader buffer = new BufferedReader(reader);
      String line;
      StringBuilder result = new StringBuilder();
      while( ( line = buffer.readLine() ) != null ) {
         result.append(line).append("\n");
      }
      str = result.toString();
      buffer.close();
      reader.close();

      return str.toCharArray();
   }
   public static void putString(File file, String contents ) throws IOException {
      FileWriter output = new FileWriter( file );
      output.write( contents, 0, contents.length() );
      output.close();
   }
   public static Properties getPropertiesFileFromProject(String path)
      throws IOException 
   {
      Properties propFile = new Properties();

      Class it = MainUtil.class.getClass();

      InputStream props = it.getResourceAsStream(path);

      propFile.load(props);

      return propFile;
   }
   public static String getResourceFileContents(String path){
      Class it = MainUtil.class.getClass();
      InputStream stream = it.getResourceAsStream(path);
      String contents = "";
      try{
         int available = stream.available();
         byte[] data = new byte[available];
         stream.read(data);
         contents = new String(data);
      } catch (IOException exc ){
         LOGGER.out("Could not read "+path);
      }
      return contents;
   }

   public static <T> void addArrayToArrayList(T[] array, ArrayList<T> arrayList){
      if ( array == null || arrayList == null)return;
      int startLength = array.length;
      for ( int i = 0; i < startLength; i++ ){
         arrayList.add(array[i]);
      }
   }
   public static <T> void addArrayListToArrayList(ArrayList<T> array, ArrayList<T> arrayList){
      Iterator<T> it = array.iterator();
      while(it.hasNext()){
         arrayList.add(it.next());
      }
   }

   public static <T> void mapKeysToBooleanInHashMap(T[] array, HashMap<T, Boolean> map, boolean boo ){
      int startLength = array.length;
      for ( int i = 0; i < startLength; i++ ){
         map.put(array[i], boo);
      }
   }

   public static ArrayList<File> getFilesInDirectory(File directory, FileFilter filter){
      return getFilesInDirectory(directory, new ArrayList<File>(), filter, false);
   }

   public static ArrayList<File> recursivelyGetFilesInDirectory(File directory, FileFilter filter){
      return getFilesInDirectory(directory, new ArrayList<File>(), filter, true);
   }

   public static ArrayList<File> getFilesInDirectory(
         File directory,
         FileFilter filter,
         boolean recursive
   ){
      return getFilesInDirectory(directory, new ArrayList<File>(), filter, recursive);
   }

   public static ArrayList<File> getFilesInDirectory(File directory, ArrayList<File> files, FileFilter filter, boolean recursive){
      if ( directory != null && directory.isDirectory() ){
         File[] listings = filter != null ? directory.listFiles(filter) : directory.listFiles();

         if ( listings != null ){
            for( int i = 0; i < listings.length; i++){
               File item = listings[i];
               if ( item.isDirectory() && recursive ){
                  getFilesInDirectory(item, files, filter, recursive);
               } else {
                  files.add(item);
               }
            }
         }
      }

      return files;
   }

   /**
    * Useful for handling user supplied paths to resources, which can be both
    * relative and absolute.
    * 
    * @param path
    * @return An absolute path.  The resulting path is not normalized, so ../ 
    * ./ is maintained.
    */
   public static String getPathToUse(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = System.getProperty("user.dir")+"/"+path;
      }
      return pathToUse;
   }

}
