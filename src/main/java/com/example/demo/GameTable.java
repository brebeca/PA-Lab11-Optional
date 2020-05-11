package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameTable {
    Connection conn;
    List<String> list ;


    GameTable(Connection connection){
        this.conn=connection;
        list=new ArrayList<>();
    }
    /**
     * initializeaza obiectul statement care va executa queryul
     *  executa query ul de insert
     * @param name
     * @throws SQLException
     */
    public void create(String name, int nrPlayers, int boardDim ) throws SQLException {

        Statement statement = conn.createStatement();
        statement.executeUpdate("insert into  games(nr_players,winner,board_dimension) values("+nrPlayers+",'"+name+"',"+boardDim+")");

    }

    /**
     * initializeaza obiectul statement care va executa queryul
     * si incearaca sa execute query ul si sa memoreze rezultatul intr un obiectde tip ResultSet
     * returneaza lista cu inregistrarile date de interogare
     * @param name
     * @throws SQLException
     */
    public List<String> findByWinner(String name ) throws SQLException {
        List<String> findName=new ArrayList<>();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("select * from games where winner='"+name+"'");
        while(rs.next()) {
            Game game= new Game(rs.getString(2),rs.getInt(1),rs.getInt(3));
            findName.add(game.toString());
        }
        return findName;

    }

    public List<String> getList() throws SQLException {
        getGames();
        return list;
    }
    /**
     * sterge tot din lista
     * apoi inregistreaza in lista rezultatul interogarii care scoate toate inregistrarile din tabela
     * @throws SQLException
     */
    public void getGames() throws SQLException {
        list.clear();

        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("select * from games");

        while(rs.next()) {
            Game game=new Game(rs.getString(2),rs.getInt(1),rs.getInt(3));
            list.add(game.toString());
        }
    }

    /**
     * returneaza numerul de randuri din tabela prin interogarea cu count(*) a aceasteaia
     * @return
     * @throws SQLException
     */
    public int getCount() throws SQLException {
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("select count(*) from games");
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
        ResultSet rs=stmt.executeQuery("UPDATE games SET winner= '" + name+
                "' WHERE winner='"+old_name+"'");
    }

    /**
     * sterge inregistrarea cu numele name
     * @param name
     * @throws SQLException
     */
    public void delete(String name) throws SQLException {
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("delete from games WHERE winner='"+name+"'");
    }
   
}
