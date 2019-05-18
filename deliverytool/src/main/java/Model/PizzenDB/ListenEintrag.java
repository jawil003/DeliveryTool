package Model.PizzenDB;

public abstract class ListenEintrag {

    private String name;

    public ListenEintrag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ListenEintrag{" +
                "name='" + name + '\'' +
                '}';
    }
}
