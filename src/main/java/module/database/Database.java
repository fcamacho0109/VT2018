package module.database;
/**
 *
 * */
import java.sql.*;

public class Database extends Thread {
    protected Connection conObj;
    protected Statement stObj;
    protected PreparedStatement ps = null;
    /**
     *
     * */
    private  void Setup() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver"); /*Loading Driver class for JDBC*/
        conObj = DriverManager.getConnection("jdbc:mysql://sql3.freemysqlhosting.net:3306/veterinaria_local","sql3223639","MrMdq6v7EL");
        stObj = conObj.createStatement();
    }
    /**
     *
     * */
    public void run() {
        try {
            Setup();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Database() throws SQLException , ClassNotFoundException {
        run();
    }
    /**
     *
     * */
    public int validateUser(String user, String pass,String role) throws Exception {
        int rol = -1;
        System.out.println(role);
        switch (role) {
            case "Admin": rol = 1;
                break;
            case "Gerente": rol = 2;
                break;
            case "Medico": rol = 3;
                break;
            case "N/A": rol = 4;
                break;
        }
        System.out.println(rol);
        String query = "select * from user where username = ? and pass = ? and role = ?";
        ps = (PreparedStatement) conObj.prepareStatement(query);
        ps.setString(1,user);
        ps.setString(2,pass);
        ps.setInt(3, rol);
        ResultSet rs = ps.executeQuery();

        if(rs.next())
            return rs.getInt(1);
        return 0;
    }
}
