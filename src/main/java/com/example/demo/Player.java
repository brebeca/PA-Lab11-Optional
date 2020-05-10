package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Player {

    Connection conn;
    List<String> list ;

    Player(Connection connection){
        this.conn=connection;
        list=new ArrayList<>();
    }
    /**
     * initializeaza obiectul statement care va executa queryul
     *  executa query ul de insert
     * @param name
     * @throws SQLException
     */
    public void create(String name) throws SQLException {

        Statement statement = conn.createStatement();
        statement.executeUpdate("insert into  players(name) values('"+name+"')");

    }

    /**
     * initializeaza obiectul statement care va executa queryul
     * si incearaca sa execute query ul si sa memoreze rezultatul intr un obiectde tip ResultSet
     * returneaza lista cu inregistrarile date de interogare
     * @param name
     * @throws SQLException
     */

    public List<String> findByName(String name ) throws SQLException {
        List<String> findName=new ArrayList<>();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("select * from players where name='"+name+"'");
        while(rs.next())
            findName.add(rs.getString(1));
        return findName;

    }

    public List<String> getList() throws SQLException {
        getPlayers();
        return list;
    }
    /**
     * sterge tot din lista
     * apoi inregistreaza in lista rezultatul interogarii care scoate toate inregistrarile din tabela
     * @throws SQLException
     */
    public void getPlayers() throws SQLException {
        list.clear();

        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("select * from players");

        while(rs.next())
            list.add(rs.getString(1));
    }

    /**
     * returneaza numerul de randuri din tabela prin interogarea cu count(*) a aceasteaia
     * @return
     * @throws SQLException
     */
    public int getCount() throws SQLException {
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("select count(*) from players");
        rs.next();
        return rs.getInt(1);
    }

    /**
     * upateaza numele cu name in inregistrarea cu numele old_name
     * @param name
     * @param old_name
     * @throws SQLException
     */
    public void setName(String name, String old_name) throws SQLException {
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("UPDATE players SET name = '" + name+
                "' WHERE name='"+old_name+"'");

    }

    /**
     * sterge inregistrarea cu numele name
     * @param name
     * @throws SQLException
     */
    public void delete(String name) throws SQLException {
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("delete from players WHERE name='"+name+"'");
    }
}
