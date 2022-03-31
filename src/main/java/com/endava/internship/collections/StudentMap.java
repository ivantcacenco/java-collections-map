package com.endava.internship.collections;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class StudentMap implements Map<Student, Integer> {

    private final Pair<Student, Integer>[] buckets;
    private static final int INITIAL_CAPACITY = 1 << 4;
    private int size = 0;

    public StudentMap() {
        this(INITIAL_CAPACITY);
    }

    public StudentMap(final int capacity) {
        if (capacity < 1) {
            this.buckets = new Pair[INITIAL_CAPACITY];
        } else if (1 == capacity % 2) {
            this.buckets = new Pair[capacity+1];
        } else {
            this.buckets = new Pair[capacity];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentMap map = (StudentMap) o;
        return size == map.size && Arrays.equals(buckets, map.buckets);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(buckets);
        return result;
    }

    static class Pair<K, V> {
        private final K key;
        private V value;
        private Pair<K, V> next;

        public Pair(final K key, final V value, final Pair<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Pair<K, V> getNext() {
            return next;
        }

        @Override
        public String toString() {
            return key.toString() + "\nAge: "  + value.toString();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var pair = (Pair<?, ?>) o;
            return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    @Override
    public boolean containsKey(final Object o) {
        if (!(o instanceof Student)) {
            return false;
        }
        final var index = Math.abs(o.hashCode()) % buckets.length;

        var existing = buckets[index];
        if (null != existing) {
            do {
                if (existing.key.equals(o)) {
                    return true;
                }
                existing = existing.getNext();
            } while (null != existing);
        }
        return false;
    }

    @Override
    public boolean containsValue(final Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }

        Pair<Student, Integer> pair;
        for (var bucket : buckets) {
            if (null == bucket) {
                continue;
            }
            pair = bucket;
            do {
                if (o == pair.getValue()) {
                    return true;
                }
                pair = pair.getNext();
            } while (null != pair);
        }
        return false;
    }

    @Override
    public Integer get(final Object o) {
        if (!(o instanceof Student)) {
            return null;
        }
        var pair = buckets[Math.abs(o.hashCode()) % buckets.length];

        while (null != pair) {
            if (pair.key.equals(o)) {
                return pair.getValue();
            }
            pair = pair.getNext();
        }
        return null;
    }

    @Override
    public Integer put(final Student student, final Integer integer) {
        final var pair = new Pair<>(student, integer, null);

        final var index = Math.abs(student.hashCode()) % buckets.length;

        var existing = buckets[index];
        if (null == existing) {
            buckets[index] = pair;
            size++;
        } else {
            while (null != existing) {
                if (existing.key.equals(student)) {
                    final var oldValue = existing.getValue();
                    existing.value = integer;
                    return oldValue;
                }
                if (null == existing.getNext()) {
                    existing.next = pair;
                    size++;
                }
                existing = existing.getNext();
            }
        }
        return null;
    }

    @Override
    public Integer remove(final Object o) {
        if (!(o instanceof Student)) {
            return null;
        }

        final var index = Math.abs(o.hashCode()) % buckets.length;
        var pair = buckets[index];

        while (null != pair) {
            if (pair.key.equals(o)) {
                final var value = pair.getValue();
                buckets[index] = pair.getNext();
                size--;
                return value;
            }
            pair = pair.getNext();
        }
        return null;
    }

    @Override
    public void putAll(final Map<? extends Student, ? extends Integer> map) {
        for (var e : map.entrySet()) {
            final var key = e.getKey();
            final var value = e.getValue();
            put(key, value);
        }
    }

    @Override
    public void clear() {
        size = 0;
        for (var i = 0; i < buckets.length; i++) {
            if (null == buckets[i]) {
                continue;
            }
            buckets[i] = null;
        }
    }

    @Override
    public Set<Student> keySet() {
        return new KeySet();
    }

    final class KeySet extends AbstractSet<Student> {

        public int size() {
            return size;
        }

        @Override
        public void clear() {
            StudentMap.this.clear();
        }

        public Iterator<Student> iterator() {
            return new KeyIterator();
        }

        @Override
        public boolean contains(final Object o) {
            return containsKey(o);
        }

        @Override
        public boolean remove(final Object key) {
            return StudentMap.this.remove(key) != null;
        }
    }

    @Override
    public Collection<Integer> values() {
        return new Values();
    }

    final class Values extends AbstractCollection<Integer> {

        public int size() {
            return size;
        }

        @Override
        public void clear() {
            StudentMap.this.clear();
        }

        public Iterator<Integer> iterator() {
            return new StudentMap.ValueIterator();
        }

        @Override
        public boolean contains(final Object o) {
            return containsValue(o);
        }
    }

    @Override
    public Set<Entry<Student, Integer>> entrySet() {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    abstract class HashIterator {
        StudentMap.Pair<Student, Integer> next;
        StudentMap.Pair<Student, Integer> current;
        int index;

        HashIterator() {
            var pairs = buckets;
            current = next = null;
            index = 0;
            if (pairs != null && size > 0) {
                do{} while (index < pairs.length && null == (next = pairs[index++]));
            }
        }

        public final boolean hasNext() {
            return next != null;
        }

        final StudentMap.Pair<Student, Integer> nextPair() {
            var pairs = buckets;
            var e = next;
            if (e == null) {
                throw new NoSuchElementException();
            }
            current = e;
            next = e.getNext();
            if (null == next && null != pairs) {
               do{} while (index < pairs.length && null == (next = pairs[index++]));
            }
            return e;
        }

        public final void remove() {
            var pair = current;
            current = null;
            StudentMap.this.remove(pair.getKey());
        }
    }

    final class KeyIterator extends StudentMap.HashIterator implements Iterator<Student> {
        public Student next() {
            return nextPair().getKey();
        }
    }

    final class ValueIterator extends StudentMap.HashIterator implements Iterator<Integer> {
        public Integer next() {
            return nextPair().getValue();
        }
    }
}

