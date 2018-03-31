package Core.models;

public class Calendarios{
    public int id;
    public String name;
    public int style;

    public Calendarios(int id, String name, int style) {
        this.id = id;
        this.name = name;
        this.style = style;
    }

    @Override
    public String toString() {
        return "Calendarios{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", style=" + style +
                '}';
    }
}