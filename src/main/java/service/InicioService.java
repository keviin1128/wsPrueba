package service;

/**
 *
 * @author KEVIN
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InicioService {

    Connection connect;

    public InicioService() {
        //Conexión
        try {
            connect = conexionBD("APPSOFTCOVES", "APP490010", "BD_APP_FUEC", "181.206.7.217");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InicioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Fin Conexión
        //Fin Conexión
    }

    public static Connection conexionBD(String _user, String _pass, String _DB, String _server) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connURL = "jdbc:sqlserver://" + _server + ";" + "databaseName=" + _DB + ";user=" + _user + ";password=" + _pass + ";";
            conn = DriverManager.getConnection(connURL);
            System.out.println("Conexión existosa");
        } catch (SQLException se) {
            System.out.println("Error de conexion: " + se.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }

        return conn;
    }

    public String IniciarSesion(String usuario, String contraseña) {
        Statement stm;
        String result = "";
        try {
            stm = connect.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM USUARIO_CLIENTE WHERE USUARIO='" + usuario + "'");

            if (rs.next()) {
                String contraseñaBD = rs.getString(3);
                if (contraseñaBD.equals(contraseña)) {
                    result = "CLIENTE";
                } else {
                    result = "Contraseña incorrecta";
                }
            } else {
                Statement stm2 = connect.createStatement();
                ResultSet rs2 = stm.executeQuery("SELECT * FROM USUARIO_APROBADOR WHERE CEDULA_APROBADOR='" + usuario + "'");
                if (rs2.next()) {
                    String contraseñaBD2 = rs2.getString(7);
                    if (contraseñaBD2.equals(contraseña)) {
                        result = "APROBADOR";
                    } else {
                        result = "Contraseña incorrecta...";
                    }
                } else {
                    result = "Usuario no encontrado";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InicioService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
