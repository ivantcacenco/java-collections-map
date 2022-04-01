package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentMapVSHashMapTest {
    StudentMap studentMap;
    HashMap<Student, Integer> hashMap;

    Student studentA = new Student("aaa aaa", LocalDate.parse("2010-09-30"), "");
    Student studentB = new Student("bbb bbb", LocalDate.parse("1995-11-13"), "");
    Student studentC = new Student("ccc ccc", LocalDate.parse("1997-09-20"), "");
    Student student1 = new Student("xxx", LocalDate.parse("1989-11-14"), "");
    Student student2 = new Student("xxasdx", LocalDate.parse("1989-11-14"), "");
    String str = "";

    @BeforeEach
    void beforeEach() {
        studentMap = new StudentMap();
        hashMap = new HashMap<>();
    }

    @Test
    void testSizeOfEmptyMap() {
        assertEquals(studentMap.size(), hashMap.size());
    }

    @Test
    void testSizeAfterPuttingAndRemovingValues() {
        studentMap.put(studentA, 11);
        hashMap.put(studentB, 26);

        assertEquals(studentMap.size(), hashMap.size());
        assertEquals(studentMap.isEmpty(), hashMap.isEmpty());

        studentMap.remove(studentA);
        hashMap.remove(studentB);

        assertEquals(studentMap.size(), hashMap.size());
    }

    @Test
    void testIsEmpty() {
        assertEquals(studentMap.isEmpty(), hashMap.isEmpty());

        studentMap.put(studentB, 26);
        hashMap.put(studentA, 11);

        assertEquals(studentMap.isEmpty(), hashMap.isEmpty());

        studentMap.remove(studentB);
        hashMap.remove(studentA);

        assertEquals(studentMap.isEmpty(), hashMap.isEmpty());
    }

    @Test
    void testContainsExistingKey() {
        studentMap.put(studentA, 1);
        hashMap.put(studentA, 1);

        assertEquals(studentMap.containsKey(student2), hashMap.containsKey(student2));
    }

    @Test
    void testContainsUnexistingKey() {
        assertEquals(studentMap.containsKey(studentA), hashMap.containsKey(studentA));
    }

    @Test
    void testContainsKeyWithIncompatibleObjectType() {
        assertEquals(studentMap.containsKey(str), hashMap.containsKey(str));
    }

    @Test
    void testContainsExistingValue() {
        studentMap.put(studentA, 11);
        hashMap.put(studentB, 11);

        assertEquals(studentMap.containsValue(11), hashMap.containsValue(11));
    }

    @Test
    void testContainsUnexistingValue() {
        assertEquals(studentMap.containsValue(12), hashMap.containsValue(12));
    }

    @Test
    void testContainsValueWithIncompatibleObjectType() {
        assertEquals(studentMap.containsValue(str), hashMap.containsValue(str));
    }

    @Test
    void testGetExistingPair() {
        studentMap.put(studentA, 11);
        hashMap.put(studentA, 11);

        assertEquals(studentMap.get(studentA), hashMap.get(studentA));
    }

    @Test
    void testGetUnexistingPair() {
        assertEquals(studentMap.get(studentA), hashMap.get(studentA));
    }

    @Test
    void testGetWithIncompatibleObjectType() {
        assertEquals(studentMap.get(str), hashMap.get(str));
    }

    @Test
    void testPutNewPair() {
        assertAll(
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMap.put(studentA, 11), hashMap.put(studentA, 11)),
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMap.containsKey(studentA), hashMap.containsKey(studentA))
        );
    }

    @Test
    void testPutOverwriteValue() {
        assertAll(
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMap.put(studentA, 11), hashMap.put(studentA, 11)),
                () -> assertEquals(studentMap.put(studentA, 12), hashMap.put(studentA, 12)),
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMap.containsValue(12), hashMap.containsValue(12)),
                () -> assertEquals(studentMap.containsValue(11), hashMap.containsValue(11))
        );
    }

    @Test
    void testPutExistingValue() {
        assertAll(
                () -> assertEquals(studentMap.put(studentA, 11), hashMap.put(studentA, 11)),
                () -> assertEquals(studentMap.put(studentA, 11), hashMap.put(studentA, 11)),
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMap.containsKey(studentA), hashMap.containsKey(studentA))
        );
    }

    @Test
    void testRemoveExistingKey() {
        studentMap.put(student1, 1);
        hashMap.put(student1, 1);

        assertEquals(studentMap.remove(student1), hashMap.remove(student1));
    }

    @Test
    void testRemoveUnexistingKey() {
        assertEquals(studentMap.remove(studentA), hashMap.remove(studentA));
    }

    @Test
    void testRemoveWithIncompatibleObjectType() {
        assertEquals(studentMap.remove(str), hashMap.remove(str));
    }

    @Test
    void testPutAll() {
        var map = new HashMap<Student, Integer>();
        map.put(studentA, 11);
        map.put(studentB, 26);

        studentMap.putAll(map);
        hashMap.putAll(map);

        assertAll(
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMap.containsKey(studentA), hashMap.containsKey(studentA)),
                () -> assertEquals(studentMap.containsValue(26), hashMap.containsValue(26))
        );
    }

    @Test
    void testClear() {
        studentMap.put(studentA, 11);
        hashMap.put(studentB, 26);

        studentMap.clear();
        hashMap.clear();

        assertAll(
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMap.isEmpty(), hashMap.isEmpty()),
                () -> assertEquals(studentMap.get(studentA), hashMap.get(studentA)),
                () -> assertEquals(studentMap.containsKey(studentB), hashMap.containsKey(studentB))
        );
    }

    @Test
    void testKeySet() {
        studentMap.put(studentA, 11);
        studentMap.put(studentB, 26);
        hashMap.put(studentA, 11);
        hashMap.put(studentB, 26);

        var studentMapKeySet = studentMap.keySet();
        var hashMapKeySet = hashMap.keySet();

        assertAll(
                () -> assertEquals(studentMapKeySet.size(), hashMapKeySet.size()),
                () -> assertEquals(studentMapKeySet.contains(studentB), hashMapKeySet.contains(studentB)),
                () -> assertEquals(studentMapKeySet.remove(studentB), hashMapKeySet.remove(studentB)),
                () -> assertEquals(studentMapKeySet.contains(studentC), hashMapKeySet.contains(studentC)),
                () -> assertEquals(studentMapKeySet.remove(studentC), hashMapKeySet.remove(studentC)),
                studentMapKeySet::clear,
                hashMapKeySet::clear,
                () -> assertEquals(studentMapKeySet.size(), hashMapKeySet.size())
        );
    }

    @Test
    void testKeySetIterator() {
        studentMap.put(studentA, 11);
        studentMap.put(studentB, 26);
        hashMap.put(studentA, 11);
        hashMap.put(studentB, 26);

        var studentMapKeySet = studentMap.keySet();
        var hashMapKeySet = hashMap.keySet();

        var studentMapKeySetIt = studentMapKeySet.iterator();
        var hashMapKeySetIt = hashMapKeySet.iterator();

        assertEquals(studentMapKeySetIt.hasNext(), hashMapKeySetIt.hasNext());
        while (studentMapKeySetIt.hasNext() && hashMapKeySetIt.hasNext()) {
            studentMapKeySetIt.next();
            hashMapKeySetIt.next();
            studentMapKeySetIt.remove();
            hashMapKeySetIt.remove();
        }

        assertAll(
                () -> assertEquals(studentMap.containsKey(studentA), hashMap.containsKey(studentA)),
                () -> assertEquals(studentMap.containsValue(26), hashMap.containsValue(26)),
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMapKeySetIt.hasNext(), hashMapKeySetIt.hasNext())
        );
    }

    @Test
    void testKeySetIteratorThrowsNoSuchElementExceptionWhenHasNoNextElement() {
        var studentMapKeySet = studentMap.keySet();
        var hashMapKeySet = hashMap.keySet();

        var studentMapKeySetIt = studentMapKeySet.iterator();
        var hashMapKeySetIt = hashMapKeySet.iterator();

        assertAll(
                () -> assertEquals(studentMapKeySetIt.hasNext(), hashMapKeySetIt.hasNext()),
                () -> assertThrows(NoSuchElementException.class, studentMapKeySetIt::next),
                () -> assertThrows(NoSuchElementException.class, hashMapKeySetIt::next)
        );

    }

    @Test
    void testValues() {
        studentMap.put(studentA, 11);
        studentMap.put(studentB, 26);
        hashMap.put(studentA, 11);
        hashMap.put(studentB, 26);

        var studentMapValues = studentMap.values();
        var hashMapValues = hashMap.values();

        assertAll(
                () -> assertEquals(studentMapValues.size(), hashMapValues.size()),
                () -> assertEquals(studentMapValues.contains(26), hashMapValues.contains(26)),
                () -> assertEquals(studentMapValues.remove(26), hashMapValues.remove(26)),
                () -> assertEquals(studentMapValues.contains(30), studentMapValues.contains(30)),
                () -> assertEquals(studentMapValues.remove(30), studentMapValues.remove(30)),
                studentMapValues::clear,
                hashMapValues::clear,
                () -> assertEquals(studentMapValues.size(), hashMapValues.size())
        );
    }

    @Test
    void testValuesIterator() {
        studentMap.put(studentA, 11);
        studentMap.put(studentB, 26);
        hashMap.put(studentA, 11);
        hashMap.put(studentB, 26);

        var studentMapValues = studentMap.values();
        var hashMapValues = hashMap.values();

        var studentMapValuesIt = studentMapValues.iterator();
        var hashMapValuesIt = hashMapValues.iterator();

        assertEquals(studentMapValuesIt.hasNext(), hashMapValuesIt.hasNext());
        while (studentMapValuesIt.hasNext() && hashMapValuesIt.hasNext()) {
            studentMapValuesIt.next();
            hashMapValuesIt.next();
            studentMapValuesIt.remove();
            hashMapValuesIt.remove();
        }

        assertAll(
                () -> assertEquals(studentMap.containsKey(studentA), hashMap.containsKey(studentA)),
                () -> assertEquals(studentMap.containsValue(26), hashMap.containsValue(26)),
                () -> assertEquals(studentMap.size(), hashMap.size()),
                () -> assertEquals(studentMapValuesIt.hasNext(), hashMapValuesIt.hasNext())
        );
    }

    @Test
    void testValuesIteratorThrowsNoSuchElementExceptionWhenHasNoNextElement() {
        var studentMapValues = studentMap.values();
        var hashMapValues = hashMap.values();

        var studentMapValuesIt = studentMapValues.iterator();
        var hashMapValuesIt = hashMapValues.iterator();

        assertAll(
                () -> assertEquals(studentMapValuesIt.hasNext(), hashMapValuesIt.hasNext()),
                () -> assertThrows(NoSuchElementException.class, studentMapValuesIt::next),
                () -> assertThrows(NoSuchElementException.class, hashMapValuesIt::next)
        );
    }
}
