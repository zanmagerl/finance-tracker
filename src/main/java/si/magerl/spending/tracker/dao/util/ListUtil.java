package si.magerl.spending.tracker.dao.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtil {

    public static <T> List<T> append(List<T> list, T... args) {
        return Stream.concat(list.stream(), Stream.of(args)).toList();
    }

    public static <T> String join(List<T> list, String delimiter) {
        return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }
}
