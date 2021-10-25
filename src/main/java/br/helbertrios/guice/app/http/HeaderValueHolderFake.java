package br.helbertrios.guice.app.http;


import java.util.*;

class HeaderValueHolderFake {
    private final List<Object> values = new LinkedList<Object>();

    /**
     * Find a HeaderValueHolderFake by name, ignoring casing.
     *
     * @param headers the Map of header names to HeaderValueHolders
     * @param name    the name of the desired header
     * @return the corresponding HeaderValueHolderFake,
     * or {@code null} if none found
     */
    public static HeaderValueHolderFake getByName(Map<String, HeaderValueHolderFake> headers, String name) {
        for (String headerName : headers.keySet()) {
            if (headerName.equalsIgnoreCase(name)) {
                return headers.get(headerName);
            }
        }
        return null;
    }

    public void addValue(Object value) {
        this.values.add(value);
    }

    public void addValues(Collection<?> values) {
        this.values.addAll(values);
    }

    public void addValueArray(Object values) {
        CollectionUtilsFake.mergeArrayIntoCollection(values, this.values);
    }

    public List<Object> getValues() {
        return Collections.unmodifiableList(this.values);
    }

    public List<String> getStringValues() {
        List<String> stringList = new ArrayList<String>(this.values.size());
        for (Object value : this.values) {
            stringList.add(value.toString());
        }
        return Collections.unmodifiableList(stringList);
    }

    public Object getValue() {
        return (!this.values.isEmpty() ? this.values.get(0) : null);
    }

    public void setValue(Object value) {
        this.values.clear();
        this.values.add(value);
    }

    public String getStringValue() {
        return (!this.values.isEmpty() ? String.valueOf(this.values.get(0)) : null);
    }

    @Override
    public String toString() {
        return this.values.toString();
    }
}