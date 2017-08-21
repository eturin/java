/*Для получения заголовочного файла dll_Main.h нужно скомпилировать класс и выполнить команду:
        javah -jni dll.Main
  Для получения JAR архива:
        java -jar my.jar
 */

package dll;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private int cnt;
    private int[] m={1,2,3,4,5};
    public Main(int cnt){
        this.cnt=cnt;

    }

    @Override
    public String toString() {
        return "cnt = "+cnt;
    }

    private native void add7();
    private native void addVall(int val);
    private native int addToM(int val);

    public static void main(String[] args) throws IOException {
        Main o=new Main(1);
        System.out.println(o);

        o.add7();
        System.out.println(o);

        o.addVall(14);
        System.out.println(o);

        int sum=o.addToM(2);
        System.out.println("sum = "+sum+" m:");
        for(int k:o.m)
            System.out.println("\t->"+k);

        //Files.delete(Paths.get(strPath));
    }

    /*static {
        //положить полученную dll в текущий каталог
        System.loadLibrary("myDLL");
    }*/
    private static String strPath="myDLL.dll";
    static {
        try {
            Path path = Paths.get(strPath);
            if (Files.exists(path) && Files.isRegularFile(path)) {
                //загрузка из текущего каталога
                System.load(path.toAbsolutePath().toString());
                strPath="";
            }else {
                //копируем файл из архива JAR во временную директорию
                Path tmpPath = Files.createTempFile(path.getFileName().toString(), ".dll");
                if (!Files.exists(tmpPath))
                    throw new FileNotFoundException("Не удается создать временный файл " + tmpPath);
                try (InputStream is = Main.class.getResourceAsStream("/" + strPath);
                     OutputStream os = new FileOutputStream(tmpPath.toString())) {
                    byte[] buf = new byte[2048];
                    int cnt;
                    while ((cnt = is.read(buf)) != -1)
                        os.write(buf, 0, cnt);
                }
                strPath=tmpPath.toString();
                System.load(strPath);
            }
        }catch (IOException e){
            System.err.println(e);
        }
    }

}
