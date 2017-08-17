import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ShowFile {
    public static void main(String[] args) throws IOException {
        for(String str:args){
            if(!Files.exists(Paths.get(str)))
                System.out.println("Файл " + str + " не найден!");
            else {
                System.out.println("\nСодержимое файла " + str + ":");
                try(FileInputStream fis=new FileInputStream(str)) {
                    byte[] mb=new byte[100];
                    int cnt=0;
                    while ((cnt=fis.read(mb))!=-1)
                        System.out.write(mb,0,cnt);
                }
            }

        }
    }
}
