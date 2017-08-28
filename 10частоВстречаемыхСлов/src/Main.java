import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //формируем стрим из потока stdin
        (new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))).lines()
                //преобразуем к нижнему регистру и заменяем на пробелы все символы, кроме букв и цифр
                .map(s->s.toLowerCase().replaceAll("[^\\wа-яё]"," "))
                //делим поток на слова на основе пробельных символов
                .flatMap(s-> Arrays.stream(s.split("\\s")))
                //удаляем пустые слова
                .filter(s->s.length()!=0)
                //группируем по словам и подсчитываем количество повторов
                .collect(Collectors.groupingBy(s->s, Collectors.counting())).entrySet().stream()
                //упорядочиваем в порядке убывания частоты
                .sorted((x,y)->y.getValue().compareTo(x.getValue())==0 ? x.getKey().compareTo(y.getKey()) : y.getValue().compareTo(x.getValue()))
                //выделяем ключи
                .map(x->x.getKey())
                //оставляем только первые 10 слов
                .limit(10)
                //выводим на консоль
                .forEach(System.out::println);

        System.exit(0);
    }
}
