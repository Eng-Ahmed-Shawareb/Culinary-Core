package com.culinarycore.dao;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.dao.singleton.ClsDatabaseConnection;
import com.culinarycore.model.ClsKitchen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsKitchenDAO implements IRepository<ClsKitchen> {
    private ClsDatabaseConnection _databaseConnection;
    public ClsKitchenDAO(){
        _databaseConnection=ClsDatabaseConnection.getInstance();
    }
    @Override
    public Optional<ClsKitchen> findByID(int ID) {
        Connection connection= _databaseConnection.getConnection();
        String query="SELECT * FROM kitchen WHERE kitchen.ID=?";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            statement.setInt(1,ID);
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()) {
                ClsKitchen kitchen = new ClsKitchen(resultSet.getString("name"),
                        resultSet.getString("type"));
                kitchen.setID(resultSet.getInt("ID"));
                return Optional.of(kitchen);
            }
        }
catch (SQLException es){
            System.err.println("Exception: "+es.getMessage());
}

        return Optional.empty();
    }

    @Override
    public List<ClsKitchen> findAll() {
        Connection connection= _databaseConnection.getConnection();
        String query="SELECT * FROM kitchen ";
        try(PreparedStatement statement=connection.prepareStatement(query);){

            ResultSet resultSet= statement.executeQuery();
            List<ClsKitchen>kitchens=new ArrayList<>();
            while(resultSet.next()) {
                ClsKitchen kitchen = new ClsKitchen(resultSet.getString("name"),
                        resultSet.getString("type"));
                kitchen.setID(resultSet.getInt("ID"));
                kitchens.add(kitchen);
            }
            return kitchens;
        }
        catch (SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return List.of();
    }

    @Override
    public boolean save(ClsKitchen entity) {
        Connection connection= _databaseConnection.getConnection();
        String query="INSERT INTO kitchen (name,type) values(?,?)";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getType());
         return statement.executeUpdate()>0;
        }
        catch (SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsKitchen entity) {
        Connection connection= _databaseConnection.getConnection();
        String query="UPDATE kitchen SET name=?,type=? WHERE ID=?";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getType());
            statement.setInt(3,entity.getID());
            return statement.executeUpdate()>0;
        }
        catch (SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        Connection connection= _databaseConnection.getConnection();
        String query="DELETE FROM kitchen WHERE ID=?";
        try(PreparedStatement statement=connection.prepareStatement(query);){
            statement.setInt(1,ID);
            return statement.executeUpdate()>0;
        }
        catch (SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }

        return false;
    }
    public List<ClsKitchen> getUnusedLastMonth(){
        String query="SELECT \n" +
                "    kitchen.ID, \n" +
                "   kitchen.name, \n" +
                "    kitchen.type,\n" +
                "    Workshop.ID\n" +
                "FROM kitchen\n" +
                "LEFT JOIN Workshop \n" +
                "    ON kitchen.ID = Workshop.FK_KitchenID \n" +
                "    AND Workshop.[start date] BETWEEN DATEADD(month, -1, GETDATE()) AND GETDATE()\n" +
                "WHERE Workshop.ID IS NULL";
        Connection connection= _databaseConnection.getConnection();
        try(PreparedStatement statement=connection.prepareStatement(query);){

            ResultSet resultSet= statement.executeQuery();
            List<ClsKitchen>kitchens=new ArrayList<>();
            while(resultSet.next()) {
                ClsKitchen kitchen = new ClsKitchen(resultSet.getString("name"),
                        resultSet.getString("type"));
                kitchen.setID(resultSet.getInt("ID"));
                kitchens.add(kitchen);
            }
            return kitchens;
        }
        catch (SQLException es){
            System.err.println("Exception: "+es.getMessage());
        }
        return List.of();

    }
}
