package com.culinarycore.dao;

import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.dao.singleton.ClsDatabaseConnection;
import com.culinarycore.model.ClsStudent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsStudentDAO implements IRepository<ClsStudent> {
private ClsDatabaseConnection _databaseConnection;
public ClsStudentDAO(){
    _databaseConnection=ClsDatabaseConnection.getInstance();
}

    @Override
    public Optional<ClsStudent> findByID(int ID) {
        Connection connection= _databaseConnection.getConnection();

        String query="SELECT * FROM student WHERE student.ID=?";
        try(
                PreparedStatement statement=connection.prepareStatement(query);){
            statement.setInt(1,ID);
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                ClsStudent student=new ClsStudent(resultSet.getString("phone"),
                        resultSet.getString("gender").charAt(0),
                        resultSet.getString("[last name]"),
                        resultSet.getString("[first name]"));
                student.setID(resultSet.getInt("ID"));
                return Optional.of(student);
            }
        }
        catch(SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ClsStudent> findAll() {
        Connection connection= _databaseConnection.getConnection();

        String query="SELECT * FROM student";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            ResultSet resultSet= statement.executeQuery();
            List<ClsStudent>students=new ArrayList<>();
            while(resultSet.next()){
                ClsStudent student=new ClsStudent(resultSet.getString("phone"),
                        resultSet.getString("gender").charAt(0),
                        resultSet.getString("[last name]"),
                        resultSet.getString("[first name]"));
                student.setID(resultSet.getInt("ID"));
               students.add(student);
            }
            return students;
        }
        catch(SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
    return List.of();
    }

    @Override
    public boolean save(ClsStudent entity) {
    String query="INSERT INTO student([first name],[last name],gender,phone) VALUES(?,?,?,?)";
    Connection connection=_databaseConnection.getConnection();
    try(PreparedStatement statement= connection.prepareStatement(query);){
        statement.setString(1,entity.getFirstName());
        statement.setString(2,entity.getLastName());
        statement.setString(3,String.valueOf(entity.getGender()));
        statement.setString(4, entity.getPhone());
return statement.executeUpdate()>0;
    }
    catch(SQLException es){
        System.err.println("Exception: "+es.getMessage());
    }
        return false;
    }

    @Override
    public boolean update(ClsStudent entity) {
        String query=" UPDATE student SET [first name]=?, [last name]=?, gender=?, phone=? WHERE ID=?";
        Connection connection=_databaseConnection.getConnection();
        try(PreparedStatement statement= connection.prepareStatement(query);){
            statement.setString(1,entity.getFirstName());
            statement.setString(2,entity.getLastName());
            statement.setString(3,String.valueOf(entity.getGender()));
            statement.setString(4, entity.getPhone());
            statement.setInt(5,entity.getID());
            return statement.executeUpdate()>0;
        }
        catch(SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return false;
    }
    @Override
    public boolean delete(int ID) {
        String query="DELETE FROM student WHERE ID=?";
        Connection connection=_databaseConnection.getConnection();
        try(PreparedStatement statement= connection.prepareStatement(query);){
            statement.setInt(1,ID);
            return statement.executeUpdate()>0;
        }
        catch(SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return false;
    }
}
