package cn.elytra.gtnh.cutcorners.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Utils {

    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(Collection<T> collection) {
        if (collection instanceof List<?> list) {
            return (List<T>) list;
        }
        return new ArrayList<>(collection);
    }

}
