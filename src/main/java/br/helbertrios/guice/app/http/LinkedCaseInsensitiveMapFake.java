package br.helbertrios.guice.app.http;


import java.io.Serializable;
import java.util.*;

class LinkedCaseInsensitiveMapFake<V> implements Map<String, V>, Serializable, Cloneable {

    private final LinkedHashMap<String, V> targetMap;
    private final HashMap<String, String> caseInsensitiveKeys;
    private final Locale locale;

    public LinkedCaseInsensitiveMapFake() {
        this((Locale) null);
    }

    public LinkedCaseInsensitiveMapFake(Locale locale) {
        this(16, locale);
    }

    public LinkedCaseInsensitiveMapFake(int initialCapacity) {
        this(initialCapacity, null);
    }

    public LinkedCaseInsensitiveMapFake(int initialCapacity, Locale locale) {
        this.targetMap = new LinkedHashMap<String, V>(initialCapacity) {
            public boolean containsKey(Object key) {
                return LinkedCaseInsensitiveMapFake.this.containsKey(key);
            }

            protected boolean removeEldestEntry(Map.Entry<String, V> eldest) {
                boolean doRemove = LinkedCaseInsensitiveMapFake.this.removeEldestEntry(eldest);
                if (doRemove) {
                    LinkedCaseInsensitiveMapFake.this.caseInsensitiveKeys.remove(LinkedCaseInsensitiveMapFake.this.convertKey(eldest.getKey()));
                }

                return doRemove;
            }
        };
        this.caseInsensitiveKeys = new HashMap(initialCapacity);
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    private LinkedCaseInsensitiveMapFake(LinkedCaseInsensitiveMapFake<V> other) {
        this.targetMap = (LinkedHashMap) other.targetMap.clone();
        this.caseInsensitiveKeys = (HashMap) other.caseInsensitiveKeys.clone();
        this.locale = other.locale;
    }

    public int size() {
        return this.targetMap.size();
    }

    public boolean isEmpty() {
        return this.targetMap.isEmpty();
    }

    public boolean containsKey(Object key) {
        return key instanceof String && this.caseInsensitiveKeys.containsKey(this.convertKey((String) key));
    }

    public boolean containsValue(Object value) {
        return this.targetMap.containsValue(value);
    }

    public V get(Object key) {
        if (key instanceof String) {
            String caseInsensitiveKey = this.caseInsensitiveKeys.get(this.convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return this.targetMap.get(caseInsensitiveKey);
            }
        }

        return null;
    }

    public V getOrDefault(Object key, V defaultValue) {
        if (key instanceof String) {
            String caseInsensitiveKey = this.caseInsensitiveKeys.get(this.convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return this.targetMap.get(caseInsensitiveKey);
            }
        }

        return defaultValue;
    }

    public V put(String key, V value) {
        String oldKey = this.caseInsensitiveKeys.put(this.convertKey(key), key);
        if (oldKey != null && !oldKey.equals(key)) {
            this.targetMap.remove(oldKey);
        }

        return this.targetMap.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends V> map) {
        if (!map.isEmpty()) {
            Iterator var2 = map.entrySet().iterator();

            while (var2.hasNext()) {
                Entry<? extends String, ? extends V> entry = (Entry) var2.next();
                this.put(entry.getKey(), entry.getValue());
            }

        }
    }

    public V remove(Object key) {
        if (key instanceof String) {
            String caseInsensitiveKey = this.caseInsensitiveKeys.remove(this.convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return this.targetMap.remove(caseInsensitiveKey);
            }
        }

        return null;
    }

    public void clear() {
        this.caseInsensitiveKeys.clear();
        this.targetMap.clear();
    }

    public Set<String> keySet() {
        return this.targetMap.keySet();
    }

    public Collection<V> values() {
        return this.targetMap.values();
    }

    public Set<Entry<String, V>> entrySet() {
        return this.targetMap.entrySet();
    }

    public LinkedCaseInsensitiveMapFake<V> clone() {
        return new LinkedCaseInsensitiveMapFake(this);
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

    public Locale getLocale() {
        return this.locale;
    }

    protected String convertKey(String key) {
        return key.toLowerCase(this.getLocale());
    }

    protected boolean removeEldestEntry(Entry<String, V> eldest) {
        return false;
    }

}
