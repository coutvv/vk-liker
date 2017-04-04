package ru.coutvv.vkliker.test;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author coutvv
 */
public class TestMapMerge {

    private Map<Long, String> mergeAccounts(Map<Long, ?> profiles, Map<Long, ?> groups) {
        Map<Long, String> result = new HashMap<>();
        Stream.of(profiles, groups)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .forEach((entry) -> {
                    result.put(Math.abs(entry.getKey()), entry.getValue().toString());
                });
        return result;
    }

    private Map<Long, String> merge(Map<Long, ?> a, Map<Long, ?> b) {
        return Stream.of(a, b)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().toString()));
    }


    @Test
    public void merge() {
        Map<Long, String> m1 = ImmutableMap.of(123L, "fuck", 23L, "nope");
        Map<Long, String> m2 = ImmutableMap.of(12233L, "sadf", 223L, "yes");
        System.out.println(merge(m1, m2));
    }
}
