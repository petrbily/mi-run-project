package tinyjvm.structure;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Daniel
 */
public class FilePathManager {
    
    private static FilePathManager instance;
    
    public String rootPath;
    
    private FilePathManager(){
        rootPath = "/";
    }
    
    //Singleton
    public static FilePathManager getInstance() {
         if (instance == null) {
             instance = new FilePathManager();
         }
         return instance;
     }
    
    public void setRootPath(File mainFile){
        this.rootPath = getRootFromFile(mainFile);
    }
    
    public String getClassIdentifikator(String path){
        String classIdentifikator = "";
        String [] splitPath = path.split(Pattern.quote(File.separator));
        for (int i = splitPath.length - 1; i > 1; i--) {
            if(splitPath[i].equals("class")) continue;
            classIdentifikator = splitPath[i].concat(classIdentifikator);
            if(splitPath[i - 1].equals("classes")){
                return classIdentifikator.replaceAll(".class", "");
            }
            else classIdentifikator = "/".concat(classIdentifikator);
        }
        return null;
    }
    
    public String getRootPath(){
        return rootPath;
    }
    
    private String getRootFromFile(File file){
        String tmpRootPath = "";
        String [] splitPath = file.getParentFile().getAbsolutePath().split(Pattern.quote(File.separator));
        for(String partPath : splitPath){
            tmpRootPath = tmpRootPath.concat(partPath + "\\");
            if(partPath.equals("classes")) return tmpRootPath;
        }
        return null;
    }
    
    public String getAbsolutePath(String relativePath){
        //If the class file is from java lang make relative path to this class file
        relativePath = relativePath.replaceAll("/", Matcher.quoteReplacement("\\"));
        if(relativePath.contains("java\\lang\\")){
            return relativePath + ".class";
        }
        return rootPath + relativePath + ".class";
    }
}
