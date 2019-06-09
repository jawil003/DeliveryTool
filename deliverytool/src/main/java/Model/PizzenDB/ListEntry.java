/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.PizzenDB;

public abstract class ListEntry {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    private String name;

    /**
     * @param name
     */
    public ListEntry(String name) {
        this.name = name;
    }

    /**
     * @return the name of the ListEntry
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the Parameters as String
     */
    @Override
    public String toString() {
        return "ListEntry{" +
                "name='" + name + '\'' +
                '}';
    }
}
