package br.helbertrios.guice.app.http;



import java.io.Serializable;
import java.util.*;

class LinkedMultiValueMapFake<K, V> implements MultiValueMapFake<K, V>, Serializable, Cloneable {
    private static final long serialVersionUID = 3801124242820219131L;
    private final Map<K, List<V>> targetMap;

    public LinkedMultiValueMapFake() {
        this.targetMap = new LinkedHashMap();
    }

    public LinkedMultiValueMapFake(int initialCapacity) {
        this.targetMap = new LinkedHashMap(initialCapacity);
    }

    public LinkedMultiValueMapFake(Map<K, List<V>> otherMap) {
        this.targetMap = new LinkedHashMap(otherMap);
    }

    public V getFirst(K key) {
        List<V> values = this.targetMap.get(key);
        return values != null && !values.isEmpty() ? values.get(0) : null;
    }

    public void add(K key, V value) {
        List<V> values = this.targetMap.get(key);
        if (values == null) {
            values = new LinkedList();
            this.targetMap.put(key, values);
        }

        values.add(value);
    }

    public void set(K key, V value) {
        List<V> values = new LinkedList();
        values.add(value);
        this.targetMap.put(key, values);
    }

    public void setAll(Map<K, V> values) {
        Iterator var2 = values.entrySet().iterator();

        while (var2.hasNext()) {
            Entry<K, V> entry = (Entry) var2.next();
            this.set(entry.getKey(), entry.getValue());
        }

    }

    public Map<K, V> toSingleValueMap() {
        LinkedHashMap<K, V> singleValueMap = new LinkedHashMap(this.targetMap.size());
        Iterator var2 = this.targetMap.entrySet().iterator();

        while (var2.hasNext()) {
            Entry<K, List<V>> entry = (Entry) var2.next();
            List<V> values = entry.getValue();
            if (values != null && !values.isEmpty()) {
                singleValueMap.put(entry.getKey(), values.get(0));
            }
        }

        return singleValueMap;
    }

    public int size() {
        return this.targetMap.size();
    }

    public boolean isEmpty() {
        return this.targetMap.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.targetMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.targetMap.containsValue(value);
    }

    public List<V> get(Object key) {
        return this.targetMap.get(key);
    }

    public List<V> put(K key, List<V> value) {
        return this.targetMap.put(key, value);
    }

    public List<V> remove(Object key) {
        return this.targetMap.remove(key);
    }

    public void putAll(Map<? extends K, ? extends List<V>> map) {
        this.targetMap.putAll(map);
    }

    public void clear() {
        this.targetMap.clear();
    }

    public Set<K> keySet() {
        return this.targetMap.keySet();
    }

    public Collection<List<V>> values() {
        return this.targetMap.values();
    }

    public Set<Entry<K, List<V>>> entrySet() {
        return this.targetMap.entrySet();
    }

    public LinkedMultiValueMapFake<K, V> deepCopy() {
        LinkedMultiValueMapFake<K, V> copy = new LinkedMultiValueMapFake(this.targetMap.size());
        Iterator var2 = this.targetMap.entrySet().iterator();

        while (var2.hasNext()) {
            Entry<K, List<V>> entry = (Entry) var2.next();
            copy.put(entry.getKey(), new LinkedList(entry.getValue()));
        }

        return copy;
    }

    public LinkedMultiValueMapFake<K, V> clone() {
        return new LinkedMultiValueMapFake(this);
    }

    public boolean equals(Object other) {
        return this == other || this.targetMap.equals(other);
    }

    public int hashCode() {
        return this.targetMap.hashCode();
    }

    public String toString() {
        return this.targetMap.toString();
    }

}
