package ca.umanitoba.cs.veranyan.model.collections;

import java.util.*;

public class ArraySet<T> implements Set<T> {
    private final ArrayList<T> elements;

    public ArraySet(){
        this.elements = new ArrayList<>();
    }

    public T get(int i) {
        return this.elements.get(i);
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public boolean isEmpty() {
        return this.elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.elements.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return this.elements.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.elements.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return this.elements.toArray(a);
    }

    @Override
    public boolean add(T t) {
        boolean isAdded = false;

        if(!this.elements.contains(t)) {
            this.elements.add(t);
            isAdded = true;
        }

        return isAdded;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        this.elements.clear();
    }
}
