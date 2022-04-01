package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentMapTest {
    StudentMap map;

    Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
    Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");
    Student studentC = new Student("ccc ccc", LocalDate.parse("1997-09-20"), "");
    Student student1 = new Student("xxx", LocalDate.parse("1989-11-14"), "");
    Student student2 = new Student("xxasdx", LocalDate.parse("1989-11-14"), "");
    String str = "";

    @BeforeEach
    void beforeEach() {
        map = new StudentMap();
    }

    @Test
    void testEqualsAndHashCode() {
        StudentMap map1 = new StudentMap(0);
        StudentMap map2 = new StudentMap(11);

        assertAll(
                () -> assertTrue(map.equals(map1) && map1.equals(map)),
                () -> assertEquals(map.hashCode(), map1.hashCode()),
                () -> assertFalse(map.equals(map2) && map2.equals(map)),
                () -> assertNotEquals(map.hashCode(), map2.hashCode())
        );
    }

    @Test
    void testPairEqualsAndHashCode() {
        var studentA1 = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        var studentA2 = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        var studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");
        var str = "";

        var pair1 = new StudentMap.Pair<>(studentA1, 11, null);
        var pair2 = new StudentMap.Pair<>(studentA2, 11, null);
        var pair3 = new StudentMap.Pair<>(studentB, 26, null);

        assertAll(
                () -> assertTrue(pair1.equals(pair2) && pair2.equals(pair1)),
                () -> assertEquals(pair1.hashCode(), pair2.hashCode()),
                () -> assertFalse(pair1.equals(pair3) && pair3.equals(pair1)),
                () -> assertNotEquals(pair1.hashCode(), pair3.hashCode()),
                () -> assertEquals(pair1, pair1),
                () -> assertNotEquals(pair1, str)
        );
    }

    @Test
    void testPairToString() {
        var studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        var pair1 = new StudentMap.Pair<>(studentA, 11, null);

        assertEquals(pair1.toString(), studentA + "\nAge: " + 11);
    }

    @Test
    void testStudentCompareTo() {
        var studentA1 = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
        var studentA2 = new Student("aaa aaa", LocalDate.parse("2011-09-30"), "");

        assertAll(
                () -> assertTrue(studentA1.compareTo(studentA2) > 0),
                () -> assertTrue(studentA2.compareTo(studentB) > 0),
                () -> assertTrue(studentB.compareTo(studentA1) < 0)
        );
    }

    @Test
    void testSizeOfEmptyMap() {
        assertEquals(0, map.size());
    }

    @Test
    void testSizeAfterModifyingMap() {
        map.put(studentA, 11);
        map.put(studentB, 26);
        assertEquals(2, map.size());

        map.remove(studentB);
        assertEquals(1, map.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(map.isEmpty());

        map.put(studentA, 11);
        assertFalse(map.isEmpty());

        map.remove(studentA);
        assertTrue(map.isEmpty());
    }

    @Test
    void testContainsExistingKey() {
        map.put(student1, 1);
        map.put(student2, 2);

        assertTrue(map.containsKey(student2));
    }

    @Test
    void testContainsUnexistingKey() {
        assertFalse(map.containsKey(studentA));
    }

    @Test
    void testContainsKeyWithWrongObjectType() {
        assertFalse(map.containsKey(str));
    }

    @Test
    void testContainsExistingValue() {
        map.put(studentA, 11);

        assertTrue(map.containsValue(11));
    }

    @Test
    void testContainsUnexistingValue() {
        assertFalse(map.containsValue(12));
    }

    @Test
    void testContainsValueWithIncompatibleObjectType() {
        assertFalse(map.containsValue(str));
    }

    @Test
    void testGetExistingPair() {
        map.put(studentA, 11);
        map.put(studentB, 26);

        assertEquals(11, map.get(studentA));
    }

    @Test
    void testGetUnexistingPair() {
        assertNull(map.get(studentA));
    }

    @Test
    void testGetWithIncompatibleObjectType() {
        assertNull(map.get(str));
    }

    @Test
    void testPutNewPair() {
        assertAll(
                () -> assertNull(map.put(studentA, 11)),
                () -> assertEquals(1, map.size()),
                () -> assertTrue(map.containsKey(studentA)),
                () -> assertTrue(map.containsValue(11)),
                () -> assertEquals(11, map.get(studentA))
        );
    }

    @Test
    void testPutOverwriteValue() {
        assertAll(
                () -> assertNull(map.put(studentA, 11)),
                () -> assertEquals(11, map.put(studentA, 12)),
                () -> assertEquals(1, map.size()),
                () -> assertTrue(map.containsKey(studentA)),
                () -> assertTrue(map.containsValue(12)),
                () -> assertFalse(map.containsValue(11)),
                () -> assertEquals(12, map.get(studentA))
        );
    }

    @Test
    void testPutExistingValue() {
        assertAll(
                () -> assertNull(map.put(studentA, 11)),
                () -> assertEquals(11, map.put(studentA, 11)),
                () -> assertEquals(1, map.size()),
                () -> assertTrue(map.containsKey(studentA)),
                () -> assertTrue(map.containsValue(11)),
                () -> assertEquals(11, map.get(studentA))
        );
    }

    @Test
    void testAddObjectsWithSameHashCode() {
        map.put(student1, 1);
        map.put(student2, 2);

        assertEquals(1, map.get(student1));
        assertEquals(2, map.get(student2));
    }

    @Test
    void testRemoveExistingKey() {
        map.put(studentA, 11);

        assertAll(
                () -> assertEquals(1, map.size()),
                () -> assertEquals(11, map.remove(studentA)),
                () -> assertFalse(map.containsValue(11)),
                () -> assertEquals(0, map.size())
        );
    }

    @Test
    void testRemoveOneOfPairsWithSameHashCode() {
        map.put(student1, 1);
        map.put(student2, 2);

        assertAll(
                () -> assertEquals(2, map.size()),
                () -> assertEquals(2, map.remove(student2)),
                () -> assertEquals(1, map.size())
        );
    }

    @Test
    void testRemoveUnexistingKey() {
        assertNull(map.remove(studentA));
    }

    @Test
    void testRemoveWithIncompatibleObjectType() {
        assertNull(map.remove(str));
    }

    @Test
    void testPutAll() {
        var hashMap = new HashMap<Student, Integer>();
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
        map.put(studentA, 11);
        map.put(studentB, 26);

        var keySet = map.keySet();

        assertAll(
                () -> assertEquals(map.size(), keySet.size()),
                () -> assertTrue(keySet.contains(studentB)),
                () -> assertTrue(keySet.remove(studentB)),
                () -> assertFalse(keySet.contains(studentC)),
                () -> assertFalse(keySet.remove(studentC)),
                () -> assertEquals(1, keySet.size()),
                keySet::clear,
                () -> assertEquals(map.size(), keySet.size())
        );
    }

    @Test
    void testKeySetIterator() {
        map.put(studentA, 11);
        map.put(studentB, 26);

        var keySet = map.keySet();
        var it = keySet.iterator();

        assertTrue(it.hasNext());
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        assertAll(
                () -> assertFalse(map.containsKey(studentA)),
                () -> assertFalse(map.containsValue(26)),
                () -> assertEquals(0, map.size()),
                () -> assertFalse(it.hasNext())
        );
    }

    @Test
    void testKeySetIteratorThrowsNoSuchElementExceptionWhenHasNoNextElement() {
        var keySet = map.keySet();
        var it = keySet.iterator();

        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void testValues() {
        map.put(studentA, 11);
        map.put(studentB, 26);

        var values = map.values();

        assertAll(
                () -> assertEquals(map.size(), values.size()),
                () -> assertTrue(values.contains(26)),
                () -> assertTrue(values.remove(26)),
                () -> assertFalse(values.contains(30)),
                () -> assertFalse(values.remove(30)),
                () -> assertEquals(1, values.size()),
                values::clear,
                () -> assertEquals(map.size(), values.size())
        );
    }

    @Test
    void testValuesIterator() {
        map.put(studentA, 11);
        map.put(studentB, 26);

        var values = map.values();
        var it = values.iterator();

        assertTrue(it.hasNext());
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        assertAll(
                () -> assertFalse(map.containsKey(studentA)),
                () -> assertFalse(map.containsValue(26)),
                () -> assertEquals(0, map.size()),
                () -> assertFalse(it.hasNext()),
                () -> assertThrows(NoSuchElementException.class, it::next)
        );
    }

    @Test
    void testValuesIteratorThrowsNoSuchElementExceptionWhenHasNoNextElement() {
        var values = map.values();
        var it = values.iterator();

        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void testEntrySet() {
        assertThrows(UnsupportedOperationException.class, map::entrySet);
    }

}