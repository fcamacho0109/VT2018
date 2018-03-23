package Core;

public class User {
    public enum Rol{
        Admin,          //Dueño del local
        Usuario,        //Empleado del local
        Master          //Es el desarrollador
    }
    public String name, lastName, phone;
    public Rol rol;
    public User(String name, String lastName, String phone, Rol rol) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.rol = rol;
    }
}
