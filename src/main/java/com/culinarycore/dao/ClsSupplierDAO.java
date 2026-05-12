package com.culinarycore.dao;

import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.dao.singleton.ClsDatabaseConnection;
import com.culinarycore.model.ClsSupplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsSupplierDAO implements IRepository<ClsSupplier> {
    private ClsDatabaseConnection _databaseConnection;

    public ClsSupplierDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    @Override
    public Optional<ClsSupplier> findByID(int ID) {
        String query="SELECT * FROM Supplier WHERE ID= ? ";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement= connection.prepareStatement(query)){
            statement.setInt(1,ID);
            ResultSet resultSet=statement.executeQuery();
            if (resultSet.next()){
                return Optional.of( new ClsSupplier( resultSet.getInt("ID"), resultSet.getString("name") ));
            }

        } catch (SQLException es) {
            System.out.println("Exception" + es.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ClsSupplier> findAll() {
        String query ="SELECT * FROM Supplier";
        List<ClsSupplier> suppliers=new ArrayList<>();
        Connection connection= _databaseConnection.getConnection();
        try (PreparedStatement statement=connection.prepareStatement(query)){
            ResultSet resultSet =statement.executeQuery();
            while (resultSet.next()){
                suppliers.add( new ClsSupplier( resultSet.getInt("ID"), resultSet.getString("name") ));
            }
            return suppliers;
        } catch (SQLException e) {
            System.out.println("Exception" + e.getMessage());

        }
        return List.of();
    }

    @Override
    public boolean save(ClsSupplier entity) {
        String query = "INSERT INTO Supplier ( ID , name ) VALUES( ? , ? )";
        Connection connection= _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,entity.getID());
            statement.setString(2,entity.getName());
            return statement.executeUpdate()>0;
        } catch (SQLException es) {
            System.out.println("Exception save: " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsSupplier entity) {
        String Query ="UPDATE Supplier SET name= ? WHERE ID = ?";
        Connection connection= _databaseConnection.getConnection();
        try (PreparedStatement statement=connection.prepareStatement(Query)){
            statement.setString(2,entity.getName());
            statement.setInt(1,entity.getID());
            return statement.executeUpdate()>0;
        } catch (SQLException es) {
            System.out.println("Exception Update: " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        Connection connection = _databaseConnection.getConnection();
        String query = "DELETE FROM Supplier WHERE ID = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception delete: " + es.getMessage());
        }
        return false;
    }
}
