package kaizen7.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Collections {

    /**
     * Builds a new List.
     * @param <E> The type of elements in this list.
     * @return A new List.
     */
    static <E> List<E> newList() {
        return new ArrayList<>();
    }

    static <E> List<E> cloneList(List<E> src) {
        return new ArrayList<>(src);
    }

    /**
     * Builds a new Map.
     * @param <K> The type of keys maintained by this map.
     * @param <V> The type of mapped values.
     * @return New Map object.
     */
    static <K, V> Map<K, V> newMap() {
        return new HashMap<>();
    }

    static <K, V> Map<K, V> cloneMap(Map<K, V> src) {
        return new HashMap<>(src);
    }

    /**
     * Builds a new Set.
     * @param <E> The type of elements maintained by this set.
     * @return New Set object.
     */
    static <E> Set<E> newSet() {
        return new HashSet<>();
    }

}
