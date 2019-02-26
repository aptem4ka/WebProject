package com.epam.hotel.dao;

import com.epam.hotel.dao.util.EncryptMD5;
import com.epam.hotel.entity.Room;
import com.epam.hotel.entity.User;
import com.epam.hotel.entity.role.AccountRole;
import com.epam.hotel.entity.room_info.AllocationType;
import com.epam.hotel.entity.room_info.RoomType;
import com.epam.hotel.dao.util.ConnectionManager;

import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserDao {

    public static ResultSet resultSet=null;
    public static Connection currentConnection=null;


    public static User login(User user, HttpServletRequest req) throws Exception{
        System.out.println("started userDAO loginUser");
        Statement statement=null;
        currentConnection= ConnectionManager.getConnection();
        System.out.println("after getConnection");
        String query="SELECT * FROM users WHERE  email='"
                +user.getEmail()+ "' AND password='"
                +user.getPassword()+"';";


        statement=currentConnection.createStatement();

        System.out.println("afterstatement");

        resultSet=statement.executeQuery(query);
        boolean hasRow=resultSet.next();

        System.out.println("result set applied");

        if (!hasRow){
            user.setValid(false);
        }
        else if (hasRow){
            user.setUserID(resultSet.getInt("userID"));
        user.setEmail(resultSet.getString("email"));
        user.setRole(resultSet.getString("role"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setValid(true);

        }
        System.out.println("Присвоил юзеру данные");
        currentConnection=null;

        return user;
    }

    public static boolean register(HttpServletRequest req) throws Exception{
        System.out.println("in register");
        currentConnection=ConnectionManager.getConnection();
        Statement statement=currentConnection.createStatement();

        String query="SELECT email FROM users WHERE email='"+req.getParameter("email")+"';";
        ResultSet resultSet1=statement.executeQuery(query);
        System.out.println("resultSet created");
        if (!resultSet1.next()){
            query="INSERT INTO users (name,surname,email,phone,password, birth) values (?,?,?,?,?,?)";
            PreparedStatement preparedStatement=currentConnection.prepareStatement(query);
            preparedStatement.setString(1,req.getParameter("name"));
            preparedStatement.setString(2,req.getParameter("surname"));
            preparedStatement.setString(3,req.getParameter("email"));
            preparedStatement.setString(4,req.getParameter("phone"));
            preparedStatement.setString(5, EncryptMD5.encrypt(req.getParameter("password")));

            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date date=format.parse(req.getParameter("birth"));

            preparedStatement.setDate(6,new java.sql.Date(date.getTime()));
            System.out.println("tried to create a row");
            System.out.println(new java.sql.Date(date.getTime()));
            preparedStatement.execute();
            //statement.executeQuery(query);
            return true;
        }
        else {
            System.out.println("exists");
            return false;
        }

        }

        public static List<Room> getRoomList(HttpServletRequest req){
            List<Room> rooms=new ArrayList<>();
try {
            currentConnection=ConnectionManager.getConnection();
            Statement statement=currentConnection.createStatement();

            String query="SELECT DISTINCT * FROM rooms;";
            ResultSet resultSet=statement.executeQuery(query);
            Room room=null;
            while (resultSet.next()){
                room=new Room();
                room.setRoomID(resultSet.getInt("roomID"));
                room.setType(RoomType.valueOf(resultSet.getString("type").toUpperCase()));
                room.setAllocation(AllocationType.valueOf(resultSet.getString("adults")));

                rooms.add(room);
            }
}catch (Exception e){
    System.out.println("roomlist temp exception");
}
            return rooms;
        }



        public static List<Room> searchRooms(HttpServletRequest req){

        List<Room> roomList=new ArrayList<>();
        Room room=null;

        try {
            currentConnection = ConnectionManager.getConnection();
            Statement statement=currentConnection.createStatement();
            System.out.println("before dates");
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date resFrom=format.parse(req.getParameter("resFrom"));
            Date resTo=format.parse(req.getParameter("resTo"));

            System.out.println("before query");
            String query="SELECT roomID, type, adults, price FROM rooms\n" +
                    "WHERE type='"+req.getParameter("type")+"' AND adults='"+req.getParameter("adults")+"' AND available=1\n" +
                    "AND roomID NOT IN(\n" +
                    "SELECT rooms.roomID FROM orders\n" +
                    "INNER JOIN rooms ON rooms.roomID=orders.roomID\n" +
                    "WHERE ('"+new java.sql.Date(resFrom.getTime())+"'>=orders.resFrom AND '"+new java.sql.Date(resFrom.getTime())+"'<=orders.resTo)\n" +
                    "OR ('"+new java.sql.Date(resTo.getTime())+"'>orders.resFrom AND '"+new java.sql.Date(resTo.getTime())+"'<=resTo\n" +
                    ")\n" +
                    "OR ('"+new java.sql.Date(resFrom.getTime())+"'<=orders.resFrom AND '"+new java.sql.Date(resTo.getTime())+"'>=orders.resTo)\n" +
                    ");";


            System.out.println("after query");
            ResultSet resultSet=statement.executeQuery(query);
            System.out.println("after resultSet before cycle");
    if (resultSet.next()) {
        room=new Room();
        room.setRoomID(resultSet.getInt("roomID"));
        room.setType(RoomType.valueOf(resultSet.getString("type").toUpperCase()));
        room.setAllocation(AllocationType.valueOf(resultSet.getString("adults")));
        room.setPrice(resultSet.getDouble("price"));
        room.setChildren(Integer.parseInt(req.getParameter("children")));
        room.setResFrom(resFrom);
        room.setResTo(resTo);

        roomList.add(room);
        System.out.println(resultSet.getInt("roomID"));
        System.out.println(RoomType.valueOf(resultSet.getString("type").toUpperCase()));
        System.out.println(AllocationType.valueOf(resultSet.getString("adults")));
        System.out.println(resultSet.getDouble("price"));
    }


        }catch (Exception E){
            System.out.println("temp exception in searchRooms");
        }
        return roomList;
        }

    }

