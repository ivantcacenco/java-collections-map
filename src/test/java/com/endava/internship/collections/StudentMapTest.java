package com.endava.internship.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentMapTest {
    StudentMap map;

    @BeforeEach
    void beforeEach() {
        map = new StudentMap();
    }

    @Test
    void testSize() {
        Assertions.assertEquals(0,map.size());

        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");

        map.put(studentA, 11);
        map.put(studentB, 26);
        Assertions.assertEquals(2,map.size());

        map.remove(studentB);
        Assertions.assertEquals(1,map.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(map.isEmpty());

        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        map.put(studentA, 11);
        assertFalse(map.isEmpty());

        map.remove(studentA);
        assertTrue(map.isEmpty());
    }

    @Test
    void testContainsKey() {
        Student student1 = new Student("xxx", LocalDate.parse("1989-11-14"), "");
        Student student2 = new Student("xxasdx", LocalDate.parse("1989-11-14"), "");
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        String str = "";
        map.put(student1, 1);
        map.put(student2,2);

        assertAll(
                () -> assertTrue(map.containsKey(student2)),
                () -> assertFalse(map.containsKey(studentA)),
                () -> assertFalse(map.containsKey(str))
        );
    }

    @Test
    void testContainsValue() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        String str = "";
        map.put(studentA, 11);

        assertAll(
                () -> assertTrue(map.containsValue(11)),
                () -> assertFalse(map.containsValue(12)),
                () -> assertFalse(map.containsValue(str))
        );
    }

    @Test
    void testGet() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");
        String str = "";
        map.put(studentA, 11);
        map.put(studentB, 26);

        assertAll(
                () -> assertEquals(11, map.get(studentA)),
                () -> assertEquals(26, map.get(studentB)),
                () -> assertNull(map.get(str))
        );
    }

    @Test
    void testPut() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");

        assertAll(
                () -> assertNull(map.put(studentA, 11)),
                () -> assertEquals(11,map.put(studentA,12)),
                () -> assertEquals(1, map.size()),
                () -> assertTrue(map.containsKey(studentA)),
                () -> assertTrue(map.containsValue(12)),
                () -> assertEquals(12, map.get(studentA)),
                () -> assertThat(map.keySet()).contains(studentA),
                () -> assertThat(map.values()).contains(12)
        );
    }

    @Test
    void testAddObjectsWithSameHashCode() {
        Student student1 = new Student("xxx", LocalDate.parse("1989-11-14"), "");
        Student student2 = new Student("xxasdx", LocalDate.parse("1989-11-14"), "");

        map.put(student1, 1);
        map.put(student2, 2);

        assertAll(
                () -> assertEquals(1, map.get(student1)),
                () -> assertEquals(2, map.get(student2))
        );
    }

    @Test
    void testRemove() {
        Student student1 = new Student("xxx", LocalDate.parse("1989-11-14"), "");
        Student student2 = new Student("xxasdx", LocalDate.parse("1989-11-14"), "");
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        String str = "";

        map.put(student1, 1);
        map.put(student2, 2);

        assertAll(
                () -> assertEquals(2, map.size()),
                () -> assertNull(map.remove(str)),
                () -> assertNull(map.remove(studentA)),
                () -> assertEquals(2, map.remove(student2)),
                () -> assertEquals(1, map.size())
        );
    }

    @Test
    void testPutAll() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");
        HashMap<Student, Integer> hashMap = new HashMap<>();
        hashMap.put(studentA, 11);
        hashMap.put(studentB, 26);

        assertAll(
                () -> assertEquals(0, map.size()),
                () -> map.putAll(hashMap),
                () -> assertEquals(2, map.size()),
                () -> assertTrue(map.containsKey(studentA)),
                () -> assertTrue(map.containsValue(26))
        );
    }

    @Test
    void testClear() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");
        map.put(studentA, 11);
        map.put(studentB, 26);

        assertAll(
                () -> assertEquals(2, map.size()),
                () -> map.clear(),
                () -> assertEquals(0, map.size()),
                () -> assertTrue(map.isEmpty()),
                () -> assertNull(map.get(studentA)),
                () -> assertFalse(map.containsKey(studentB))
        );
    }

    @Test
    void testKeySet() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");
        Student studentC = new Student("ccc ccc", LocalDate.parse("1997-09-20"), "");

        map.put(studentA, 11);
        map.put(studentB, 26);

        Set<Student> keySet = map.keySet();

        assertAll(
                () -> assertEquals(map.size(), keySet.size()),
                () -> assertTrue(keySet.contains(studentB)),
                () -> assertTrue(keySet.remove(studentB)),
                () -> assertFalse(keySet.contains(studentC)),
                () -> assertFalse(keySet.remove(studentC)),
                () -> assertEquals(1,keySet.size()),
                keySet::clear,
                () -> assertEquals(map.size(), keySet.size())
        );
    }

    @Test
    void testKeySetIterator() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");

        map.put(studentA, 11);
        map.put(studentB, 26);

        Set<Student> keySet = map.keySet();
        Iterator<Student> it = keySet.iterator();

        assertTrue(it.hasNext());
        while (it.hasNext()){
            it.next();
            it.remove();
        }
        assertAll(
                () -> assertFalse(map.containsKey(studentA)),
                () -> assertFalse(map.containsValue(26)),
                () -> assertEquals(0,map.size()),
                () -> assertFalse(it.hasNext())
        );
    }

    @Test
    void testValues() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");

        map.put(studentA, 11);
        map.put(studentB, 26);

        Collection<Integer> values = map.values();

        assertAll(
                () -> assertEquals(map.size(), values.size()),
                () -> assertTrue(values.contains(26)),
                () -> assertTrue(values.remove(26)),
                () -> assertFalse(values.contains(30)),
                () -> assertFalse(values.remove(30)),
                () -> assertEquals(1,values.size()),
                values::clear,
                () -> assertEquals(map.size(), values.size())
        );
    }

    @Test
    void testValuesIterator() {
        Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");

        map.put(studentA, 11);
        map.put(studentB, 26);

        Collection<Integer> values = map.values();
        Iterator<Integer> it = values.iterator();

        assertTrue(it.hasNext());
        while (it.hasNext()){
            it.next();
            it.remove();
        }
        assertAll(
                () -> assertFalse(map.containsKey(studentA)),
                () -> assertFalse(map.containsValue(26)),
                () -> assertEquals(0,map.size()),
                () -> assertFalse(it.hasNext())
        );
    }
}