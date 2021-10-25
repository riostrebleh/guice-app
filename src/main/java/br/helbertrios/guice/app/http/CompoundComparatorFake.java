package br.helbertrios.guice.app.http;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class CompoundComparatorFake<T> implements Comparator<T>, Serializable {

    private final List<InvertibleComparatorFake> comparators;


    /**
     * Construct a CompoundComparator with initially no Comparators. Clients
     * must add at least one Comparator before calling the compare method or an
     * IllegalStateException is thrown.
     */
    public CompoundComparatorFake() {
        this.comparators = new ArrayList<InvertibleComparatorFake>();
    }

    /**
     * Construct a CompoundComparator from the Comparators in the provided array.
     * <p>All Comparators will default to ascending sort order,
     * unless they are InvertibleComparators.
     *
     * @param comparators the comparators to build into a compound comparator
     * @see InvertibleComparatorFake
     */
    @SuppressWarnings("unchecked")
    public CompoundComparatorFake(Comparator... comparators) {
        this.comparators = new ArrayList<InvertibleComparatorFake>(comparators.length);
        for (Comparator comparator : comparators) {
            addComparator(comparator);
        }
    }


    /**
     * Add a Comparator to the end of the chain.
     * <p>The Comparator will default to ascending sort order,
     * unless it is a InvertibleComparator.
     *
     * @param comparator the Comparator to add to the end of the chain
     * @see InvertibleComparatorFake
     */
    @SuppressWarnings("unchecked")
    public void addComparator(Comparator<? extends T> comparator) {
        if (comparator instanceof InvertibleComparatorFake) {
            this.comparators.add((InvertibleComparatorFake) comparator);
        } else {
            this.comparators.add(new InvertibleComparatorFake(comparator));
        }
    }

    /**
     * Add a Comparator to the end of the chain using the provided sort order.
     *
     * @param comparator the Comparator to add to the end of the chain
     * @param ascending  the sort order: ascending (true) or descending (false)
     */
    @SuppressWarnings("unchecked")
    public void addComparator(Comparator<? extends T> comparator, boolean ascending) {
        this.comparators.add(new InvertibleComparatorFake(comparator, ascending));
    }

    /**
     * Replace the Comparator at the given index.
     * <p>The Comparator will default to ascending sort order,
     * unless it is a InvertibleComparator.
     *
     * @param index      the index of the Comparator to replace
     * @param comparator the Comparator to place at the given index
     * @see InvertibleComparatorFake
     */
    @SuppressWarnings("unchecked")
    public void setComparator(int index, Comparator<? extends T> comparator) {
        if (comparator instanceof InvertibleComparatorFake) {
            this.comparators.set(index, (InvertibleComparatorFake) comparator);
        } else {
            this.comparators.set(index, new InvertibleComparatorFake(comparator));
        }
    }

    /**
     * Replace the Comparator at the given index using the given sort order.
     *
     * @param index      the index of the Comparator to replace
     * @param comparator the Comparator to place at the given index
     * @param ascending  the sort order: ascending (true) or descending (false)
     */
    public void setComparator(int index, Comparator<T> comparator, boolean ascending) {
        this.comparators.set(index, new InvertibleComparatorFake<T>(comparator, ascending));
    }

    /**
     * Invert the sort order of each sort definition contained by this compound
     * comparator.
     */
    public void invertOrder() {
        for (InvertibleComparatorFake comparator : this.comparators) {
            comparator.invertOrder();
        }
    }

    /**
     * Invert the sort order of the sort definition at the specified index.
     *
     * @param index the index of the comparator to invert
     */
    public void invertOrder(int index) {
        this.comparators.get(index).invertOrder();
    }

    /**
     * Change the sort order at the given index to ascending.
     *
     * @param index the index of the comparator to change
     */
    public void setAscendingOrder(int index) {
        this.comparators.get(index).setAscending(true);
    }

    /**
     * Change the sort order at the given index to descending sort.
     *
     * @param index the index of the comparator to change
     */
    public void setDescendingOrder(int index) {
        this.comparators.get(index).setAscending(false);
    }

    /**
     * Returns the number of aggregated comparators.
     */
    public int getComparatorCount() {
        return this.comparators.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compare(T o1, T o2) {
        for (InvertibleComparatorFake comparator : this.comparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompoundComparatorFake)) {
            return false;
        }
        CompoundComparatorFake<T> other = (CompoundComparatorFake<T>) obj;
        return this.comparators.equals(other.comparators);
    }

    @Override
    public int hashCode() {
        return this.comparators.hashCode();
    }

    @Override
    public String toString() {
        return "CompoundComparator: " + this.comparators;
    }
}
