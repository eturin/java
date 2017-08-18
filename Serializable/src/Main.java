import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

class Animal implements Serializable {
    private final String name;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Animal) {
            return Objects.equals(name, ((Animal) obj).name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        //формируем массив экземпляров класса
        Scanner sc=new Scanner(System.in);
        System.out.print("Укажите количество элементов массива: ");
        int len=sc.nextInt();
        Animal[] m=new Animal[len];
        for(int i=0;i<len;++i)
            m[i]=new Animal("Птичка №"+(i+1));

        //серриализуем массив (записываем в файл)
        String strPath="e:\\data.my_format";
        try(FileOutputStream fos=new FileOutputStream(strPath);
            ObjectOutputStream ous=new ObjectOutputStream(fos)){
            //записываем размер массива
            ous.writeInt(m.length);
            //записываем экземпляры класса
            for(Animal t:m)
                ous.writeObject(t);
        }

        //зачитываем файл в массив байт
        byte[] data=Files.readAllBytes(Paths.get(strPath));
        Animal[] newM=deserializeAnimalArray(data);
        for(int i=0;i<newM.length;++i)
            System.out.println(newM[i]);

        System.exit(0);
    }

    public static Animal[] deserializeAnimalArray(byte[] data) {
        try(ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(data))) {
            int len = ois.readInt();
            Animal[] m = new Animal[len];
            for (int i = 0; i < len; ++i)
                try {
                    m[i] = (Animal) ois.readObject();
                } catch (ClassNotFoundException e) {
                    //подавляем исключение
                }

            return m;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
}
