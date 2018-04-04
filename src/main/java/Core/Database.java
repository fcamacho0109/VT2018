package Core;

import Core.models.Producto;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
    protected Connection conObj;
    protected Statement stObj;
    protected PreparedStatement ps = null;
    private static Database sharedInstance = new Database();

    public static Database getInstance() {
        return sharedInstance;
    }

    private Database() {
        String host = "localhost"; //sql3.freemysqlhosting.net
        String base = "croquetos"; // veterinaria_local
        String user = "root"; //sql3223639
        String pass = "123123123"; //MrMdq6v7EL
        try{
            Class.forName("com.mysql.jdbc.Driver"); /*Loading Driver class for JDBC*/
            conObj = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+base,user,pass);
            stObj = conObj.createStatement();
            System.out.println("Conexion exitosa");
        }
        catch(Exception e){
            System.out.println("No exitosa");
            e.printStackTrace();
        }
    }



    public ArrayList<Entry<?>> getEventos(){
        ArrayList<Entry<?>> eventos = new ArrayList<>();
        String query = "Select id,calendar_id,title,fullDay,startDate,startTime,endDate,endTime,description, recurring,rrule from eventos";

        try{
          ps = conObj.prepareStatement(query);
          ResultSet rs = ps.executeQuery();
          while(rs.next()){
              Entry<?> entry = new Entry<>();
              entry.setId((String.valueOf(rs.getInt(1))));
              entry.setCalendar(Session.getInstance().getCalendars().get(rs.getInt(2)-1));
              entry.setTitle(rs.getString(3));
              entry.setFullDay(rs.getBoolean(4));
              LocalDateTime start,end;
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
              start = LocalDateTime.parse(rs.getString(5)+" "+rs.getString(6),formatter);
              end = LocalDateTime.parse(rs.getString(7)+" "+rs.getString(8),formatter);
              entry.setInterval(start,end);
              entry.setLocation(rs.getString(9));
              eventos.add(entry);
             // Session.getInstance().getCalendars().get(rs.getInt(2)).addEntry(entry);
              System.out.println(entry.toString());

          }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Session.getInstance().setEvents(eventos);
        return eventos;
    }

    public ArrayList<Calendar> getCalendarios(){
        ArrayList<Calendar> calendarios = new ArrayList<>();
        String query = "Select id,name,style from calendarios;";
        try {
            ps = conObj.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Calendar c = new Calendar();
                c.setName(rs.getString(2));
                switch (rs.getInt(3)){
                    case 1:
                        c.setStyle(Calendar.Style.STYLE1);
                        break;
                    case 2:
                        c.setStyle(Calendar.Style.STYLE2); break;
                    case 3:
                        c.setStyle(Calendar.Style.STYLE3); break;
                    case 4:
                        c.setStyle(Calendar.Style.STYLE4); break;

                }
                calendarios.add(c);
                System.out.println(c.toString());
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        Session.getInstance().setCalendars(calendarios);
        return calendarios;
    }
    public ArrayList<Producto> getProductos(){
        ArrayList<Producto> productos   = new ArrayList<>();
        String query = "Select * from productos;";
        try{
            ps = conObj.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Producto producto = new Producto();
                producto.setId(rs.getInt(1));
                producto.setName(rs.getString(2));
                producto.setDescription(rs.getString(3));
                producto.setPrice(rs.getFloat(4));
                producto.setPrice_sale(rs.getFloat(5));
                producto.setCode(rs.getString(6));
                producto.setStock(rs.getInt(7));
                productos.add(producto);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Session.getInstance().setEvents(eventos);
        System.out.println(productos.size());
        return productos;
    }
}

