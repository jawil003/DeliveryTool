/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Collections;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import javafx.collections.FXCollections;
import javafx.collections.SetChangeListener;

import java.util.LinkedList;
import java.util.Set;


/**
 * @author Jannik Will
 * @version 1.0
 */
public class ObservableLinkedHashMultiSet<E> {
    private LinkedHashMultiset<E> linkedHashMultiset;
    private LinkedList<SetChangeListener<E>> listeners;

    public ObservableLinkedHashMultiSet() {
        linkedHashMultiset = LinkedHashMultiset.create();
    }

    /**
     * @param e Adds one occurency of the specified element
     */
    public void add(E e) {
        linkedHashMultiset.add(e);
        addedChangedetect(e);
    }

    /**
     * @param e Create an addingchange for the specified Element
     */
    private void addedChangedetect(E e) {
        if (listeners != null) {
            for (SetChangeListener<E> m : listeners) {
                m.onChanged(new AddSetChange<>(e));
            }
        }
    }

    /**
     * @param e Create an removingchange for the specified Element
     */
    private void removeChangedetect(E e) {
        if (listeners != null) {
            for (SetChangeListener<E> m : listeners) {
                m.onChanged(new RemoveSetChange<>(e));
            }
        }
    }

    /**
     * @param e           Adds the specified number of occurrences of the specified element
     * @param occurencies the specified number of occurrences added
     */
    public void add(E e, int occurencies) {
        linkedHashMultiset.add(e, occurencies);
        addedChangedetect(e);
    }

    /**
     * @param listener the Listener added to observe the MultiSet
     */
    public void addListener(SetChangeListener<E> listener) {
        if (listeners == null) {
            listeners = new LinkedList<>();
        }

        listeners.add(listener);

    }

    /**
     * @param listener the Listener removed to observe the MultiSet
     * @return wheather the listener was successful removed or not
     */
    public boolean removeListener(SetChangeListener<E> listener) {
        if (listeners == null) {
            return false;
        }

        return listeners.remove(listener);

    }

    /**
     * @param e Element which number of occurrences should be removed
     * @return wheather the element was removed or not
     */
    public boolean remove(E e) {
        removeChangedetect(e);
        return linkedHashMultiset.remove(e);
    }

    /**
     * @param e Element which number of occurrences should be removed
     * @param occurencies number of occurrences should be removed
     * @return the old number of occurrences of the Element
     */
    public int remove(E e, int occurencies) {
        removeChangedetect(e);
        return linkedHashMultiset.remove(e, occurencies);
    }

    /**
     * @return a Set<Multiset.Entry<E>>, containing entries supporting getElement() and getCount()
     */
    public Set<Multiset.Entry<E>> entrySet() {
        return linkedHashMultiset.entrySet();
    }

    /**
     * @return the distinct elements of a Multiset<E> as a Set<E>
     */
    public Set<E> elementSet() {
        return linkedHashMultiset.elementSet();

    }

    /**
     * @return the total number of occurrences of all elements in the Multiset
     */
    public int size() {
        return linkedHashMultiset.size();
    }

    /**
     * @param e Element which number of occurrences needed
     * @return the number of occurrences of an element that have been added to this multiset
     */
    public int count(E e) {
        return linkedHashMultiset.count(e);
    }

    /**
     * @param e     Element which number of occurrences wanted to set
     * @param count the new number of ooccurrences
     * @return the oldCount of the Element
     */
    public int setCount(E e, int count) {
        return linkedHashMultiset.setCount(e, count);
    }

    /**
     * Remove all Elements from the Multiset
     */
    public void clear() {
        linkedHashMultiset.clear();
    }

    public class AddSetChange<E> extends SetChangeListener.Change<E> {

        private E e;

        public AddSetChange(E e) {
            super(FXCollections.observableSet());
            this.e = e;

        }

        /**
         * If this change is a result of add operation.
         *
         * @return true if a new element was added to the set
         */
        @Override
        public boolean wasAdded() {
            return true;
        }

        /**
         * If this change is a result of removal operation.
         *
         * @return true if an old element was removed from the set
         */
        @Override
        public boolean wasRemoved() {
            return false;
        }

        /**
         * Get the new element. Return null if this is a removal.
         *
         * @return the element that was just added
         */
        @Override
        public E getElementAdded() {
            return e;
        }

        /**
         * Get the old element. Return null if this is an addition.
         *
         * @return the element that was just removed
         */
        @Override
        public E getElementRemoved() {
            return null;
        }
    }

    public class RemoveSetChange<E> extends SetChangeListener.Change<E> {

        private E e;

        public RemoveSetChange(E e) {
            super(FXCollections.observableSet());
            this.e = e;

        }

        /**
         * If this change is a result of add operation.
         *
         * @return true if a new element was added to the set
         */
        @Override
        public boolean wasAdded() {
            return false;
        }

        /**
         * If this change is a result of removal operation.
         *
         * @return true if an old element was removed from the set
         */
        @Override
        public boolean wasRemoved() {
            return true;
        }

        /**
         * Get the new element. Return null if this is a removal.
         *
         * @return the element that was just added
         */
        @Override
        public E getElementAdded() {
            return null;
        }

        /**
         * Get the old element. Return null if this is an addition.
         *
         * @return the element that was just removed
         */
        @Override
        public E getElementRemoved() {
            return e;
        }
    }

}
