import java.lang.reflect.Constructor;

public class Main {
    public static void main(String[] args) throws Exception {
        Object o=ReflectionHelper.getObject("Test");
        System.out.println(o);
        if(ReflectionHelper.setField(o,"a",1));
            System.out.println(o);

        Constructor ctor=Class.forName("Test").getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        Object ob=ctor.newInstance(7);
        System.out.println(ob);

    }
}
