package tinyjvm.structure;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Daniel
 */
public class FilePathManager {
    
    public String rootPath;
    
    public FilePathManager(File mainFile){
        this.rootPath = getRootFromFile(mainFile);
    }
    
    public String getRootPath(){
        return rootPath;
    }
    
    private String getRootFromFile(File file){
        String tmpRootPath = "";
        String [] splitPath = file.getParentFile().getAbsolutePath().split(Pattern.quote(File.separator));
        for(String partPath : splitPath){
            tmpRootPath = tmpRootPath.concat(partPath + "\\");
            System.out.println("part: " + partPath);
            if(partPath.equals("classes")) return tmpRootPath;
        }
        return null;
    }
    
    public String getAbsolutePath(String relativePath){
        relativePath = relativePath.replaceAll("/", Matcher.quoteReplacement("\\"));
        return rootPath + relativePath + ".class";
    }
}
