import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ShowDir {
    public static void main(String[] args) throws IOException {
        for(String str:args){
            Path path=Paths.get(str);
            System.out.printf("Содержимое %20s:\n",str);

            if(Files.isDirectory(path)){
                for(Path p:Files.newDirectoryStream(path))
                    System.out.printf("%-100s %s\n",p.toString(),Files.isRegularFile(p));
            }

        }
    }
}
