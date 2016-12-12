package com.imie.unicorn.controller;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by Yornletard on 01/12/2016.
 */
public class Score {

    private static Score score;
    private Connection connection;

    private static final String queryCreateTable = "CREATE TABLE IF NOT EXISTS highscore (idPlayer VARCHAR(50), ip VARCHAR(50), pseudo VARCHAR(50), score INT, dateGame DATE)";
    private static final String queryClearDB = "DROP TABLE highscore";

    private Score(){
        this.checkDB();
        this.createDB();
    }

    public static Score getScore(){
        if(score == null){
            score = new Score();
        }
        return score;
    }

    private void checkDB() {
        try{
            this.connection = DriverManager.getConnection("jdbc:sqlite:unicorn.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            System.out.println("UnicornCore : DB ready...");
            System.out.println("UnicornCore : DB opened...");
        }
    }

    private void createDB(){
        Statement statement = null;
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate(queryCreateTable);
            statement.close();
            System.out.println("UnicornCore : " + queryCreateTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearDB(){
        Statement statement = null;
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate(queryClearDB);
            statement.close();
            System.out.println("UnicornCore : " + queryClearDB);
            System.out.println("UnicornCore : DB Dropped...");
            this.createDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayerScore(String idPlayer, String ip, String pseudo, int score){
        Statement statement = null;

        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Paris"));
        java.sql.Timestamp dateGame = new java.sql.Timestamp(cal.getTimeInMillis());
        System.out.println(dateGame);

        String queryAddScore = "INSERT INTO HIGHSCORE(idPlayer, ip, pseudo, score, dateGame) VALUES('" + idPlayer + "','" + ip + "','" + pseudo + "'," + score + ",'" + dateGame + "')";
        System.out.println(queryAddScore);
        try{
            statement = this.connection.createStatement();
            statement.executeUpdate(queryAddScore);
            statement.close();
            System.out.println("UnicornCore : " + queryAddScore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Player> getAllScores(){

        HashMap<String, Player> playerList = new HashMap<String, Player>();

        String queryGetAllScores = "SELECT * FROM HIGHSCORE";
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery(queryGetAllScores);
            System.out.println("UnicornCore :");
            System.out.println("-- High Scores --");

            while(resultSet.next()){
                String idPlayer = resultSet.getString("idPlayer");
                String ip = resultSet.getString("ip");
                String pseudo = resultSet.getString("pseudo");
                int score = resultSet.getInt("score");

                Player player = new Player(ip, pseudo, score, false);
                playerList.put(idPlayer, player);
            }

            while(!resultSet.next()){
                System.out.println("no records found...");
                break;
            }

            return playerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }




}
