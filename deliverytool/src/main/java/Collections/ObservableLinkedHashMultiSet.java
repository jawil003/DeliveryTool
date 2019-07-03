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

    public void add(E e) {
        linkedHashMultiset.add(e);
        addedChangedetect(e);
    }

    private void addedChangedetect(E e) {
        if (listeners != null) {
            for (SetChangeListener<E> m : listeners) {
                m.onChanged(new AddSetChange<>(e));
            }
        }
    }

    private void removeChangedetect(E e) {
        if (listeners != null) {
            for (SetChangeListener<E> m : listeners) {
                m.onChanged(new RemoveSetChange<>(e));
            }
        }
    }

    public void add(E e, int occurencies) {
        linkedHashMultiset.add(e, occurencies);
        addedChangedetect(e);
    }

    public void addListener(SetChangeListener<E> listener) {
        if (listeners == null) {
            listeners = new LinkedList<>();
        }

        listeners.add(listener);

    }

    public boolean remove(E e) {
        removeChangedetect(e);
        return linkedHashMultiset.remove(e);
    }

    public int remove(E e, int occurencies) {
        removeChangedetect(e);
        return linkedHashMultiset.remove(e, occurencies);
    }

    public Set<Multiset.Entry<E>> entrySet() {
        return linkedHashMultiset.entrySet();
    }

    public Set<E> elementSet() {
        return linkedHashMultiset.elementSet();

    }

    public int size() {
        return linkedHashMultiset.size();
    }

    public int count(E e) {
        return linkedHashMultiset.count(e);
    }

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
