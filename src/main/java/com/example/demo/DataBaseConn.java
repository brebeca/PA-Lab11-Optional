package com.example.demo;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConn {

    private static DataBaseConn instanta = null;
    private Connection conexiune;

    /**
     * realizeaza conexiunea in obiectul conexiune de tip Connection

     */
    DataBaseConn() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conexiune= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","student","student");
    }

    public Connection getConnection() {
        return conexiune;
    }
    public void closeConnection() throws SQLException {
        conexiune.close();
    }

    /**
     * asigura faptul ca se poate crea o singura instanta a clasei
     * @return daca e prima instanta a clasei returneaza o clasa noua, daca nu o returneaza pe cea creata deja
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static DataBaseConn Database() throws SQLException, ClassNotFoundException {
        if (instanta == null)
            instanta = new DataBaseConn();
        return instanta;
    }
}






