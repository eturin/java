import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class VisitDir {
    public static void main(String[] args) throws IOException{
        for(String str:args){
            Path path= Paths.get(str);
            Files.walkFileTree(path,new SimpleFileVisitor<Path>(){
                String pref="";
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)  throws IOException {
                    if(dir.getFileName()!=null) {
                        System.out.println(pref + dir.getFileName());
                        pref += "\t";
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e)  throws IOException {
                    if(dir.getFileName()!=null)
                        pref=pref.substring(0,pref.length()-1);
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println(pref+file.getFileName());
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}
