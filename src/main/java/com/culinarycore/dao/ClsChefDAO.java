package com.culinarycore.dao;

import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.dao.singleton.ClsDatabaseConnection;
import com.culinarycore.model.ClsChef;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsChefDAO implements IRepository<ClsChef> {
 private    ClsDatabaseConnection _databaseConnection;
    ClsChefDAO(){
        _databaseConnection=ClsDatabaseConnection.getInstance();
    }
    @Override
    public Optional<ClsChef> findByID(int ID) {
        Connection connection=_databaseConnection.getConnection();
        String query="SELECT * FROM chef WHERE chef.ID=?";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            statement.setInt(1,ID);
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()) {
                ClsChef chef = new ClsChef(resultSet.getString("bio"),
                     resultSet.getString("[first name]"),
                        resultSet.getString("[last name]"),
                        resultSet.getString("expertise"));
                chef.setID(resultSet.getInt("ID"));
                return Optional.of(chef);
            }

        }
            catch(SQLException es){
                System.err.println("Exception: "+es.getMessage());
            }
        return Optional.empty();
    }

    @Override
    public List<ClsChef> findAll() {
        Connection connection=_databaseConnection.getConnection();
        String query="SELECT * FROM chef";
        try(PreparedStatement statement=connection.prepareStatement(query);){
        List<ClsChef>chefs=new ArrayList<>();
            ResultSet resultSet= statement.executeQuery();
            while(resultSet.next()) {
                ClsChef chef = new ClsChef(resultSet.getString("bio"),
                        resultSet.getString("[first name]"),
                        resultSet.getString("[last name]"),
                        resultSet.getString("expertise"));
                chef.setID(resultSet.getInt("ID"));
               chefs.add(chef);
            }
return chefs;
        }
        catch(SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return List.of();
    }

    @Override
    public boolean save(ClsChef entity) {
        Connection connection=_databaseConnection.getConnection();
        String query="INSERT INTO chef ([first name],[last name],bio,expertise) VALUES(?,?,?,?)";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            statement.setString(1,entity.getFirstName());
            statement.setString(2,entity.getLastName());
            statement.setString(3,entity.getBio());
            statement.setString(4,entity.getExpertise());
            return statement.executeUpdate()>0;
        }

         catch(SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsChef entity) {
        Connection connection=_databaseConnection.getConnection();
        String query="UPDATE chef SET [first name]=?,[last name]=?,bio=?,expertise=? WHERE ID=?";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            statement.setString(1,entity.getFirstName());
            statement.setString(2,entity.getLastName());
            statement.setString(3,entity.getBio());
            statement.setString(4,entity.getExpertise());
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
        Connection connection=_databaseConnection.getConnection();
        String query="DELETE FROM chef WHERE ID=?";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            statement.setInt(1,ID);
            return statement.executeUpdate()>0;
        }
        catch(SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return false;
    }
}
