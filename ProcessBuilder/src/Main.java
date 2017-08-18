import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        if(args.length>0){
            /*Если путь к файлу не абсолютный, то программа будет искаться на основе текущего каталога.
            * Пример: C:\Windows\System32\cmd.exe*/
            ProcessBuilder pb=new ProcessBuilder(args);
            pb.directory(new File("e:\\"))
                    //stdout процесса направлять в stdout родителя
                    .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                    .redirectError(ProcessBuilder.Redirect.INHERIT);

            //запускаем процесс и отправляем ему команды, которые попадут в наш stdout
            Process pr=pb.start();
            PrintWriter pw=new PrintWriter(pr.getOutputStream(),true);//не забываем делать flush()
            pw.println("dir");
            pw.println("exit");

            pr.waitFor();
        }
    }
}
