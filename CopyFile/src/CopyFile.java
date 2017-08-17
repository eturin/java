import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {
    private static int LEN=100;
    public static void main(String[] args) throws IOException{
        if(args.length!=2)
            System.out.println("Используйте CopyFile источник назначение");
        else {
            try (FileInputStream fis = new FileInputStream(args[0]);
                 FileOutputStream fos = new FileOutputStream(args[1])) {
                byte[] mb = new byte[LEN];
                int cnt;
                while ((cnt = fis.read(mb)) != -1)
                    fos.write(mb, 0, cnt);
            }
            System.out.println("Скоприрован файл "+args[0]);
        }

        System.exit(0);
    }
}
