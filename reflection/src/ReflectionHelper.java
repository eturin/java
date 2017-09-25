import java.lang.reflect.Field;

public class ReflectionHelper {
    //инстанцируем экземпляр класса по строковому имени
    public static Object getObject(String strClassName) throws Exception{
        return Class.forName(strClassName).newInstance();
    }

    //меняем свойства экземпляра класса по имени
    public static boolean setField(Object o,String strField, String strVal){
        boolean isOk=true;
        try{
            //получаем поле
            Field field=o.getClass().getDeclaredField(strField);
            //делаем его доступным
            field.setAccessible(true);
            //устанавливаем новое значение
            Class<?> type=field.getType();
            if(type.equals(String.class))
                field.set(o,strVal);
            else if(type.equals(Integer.class) || type.equals(int.class))
                field.set(o,Integer.parseInt(strVal));
            else if(type.equals(Double.class) || type.equals(double.class))
                field.set(o,Double.parseDouble(strVal));
            //и т.д.
            field.setAccessible(false);
        }catch (NoSuchFieldException e){
            //у экземпляра нет такого свойства
            isOk=false;
            e.printStackTrace();
        }catch (IllegalAccessException e){
            //не удалось установить новое значение свойства
            isOk=false;
            e.printStackTrace();
        }


        return isOk;
    }
    public static boolean setField(Object o,String strField, Object newVal){
        boolean isOk=true;
        try{
            //получаем поле
            Field field=o.getClass().getDeclaredField(strField);
            //делаем его доступным
            field.setAccessible(true);
            //устанавливаем новое значение
            field.set(o,newVal);
            field.setAccessible(false);
        }catch (NoSuchFieldException e){
            //у экземпляра нет такого свойства
            isOk=false;
        }catch (IllegalAccessException e){
            //не удалось установить новое значение свойства
            isOk=false;
        }

        return isOk;
    }
}
