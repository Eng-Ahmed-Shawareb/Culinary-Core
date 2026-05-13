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
        String query = "INSERT INTO Supplier ( name ) VALUES( ? )";
        Connection connection= _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,entity.getName());
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
            statement.setString(1,entity.getName());
            statement.setInt(2,entity.getID());
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
    public Optional<ClsSupplier> findTopByQuantityLastMonth() {
        String query = "SELECT TOP 1 S.ID, S.name " +
                "FROM Supplier S " +
                "JOIN Ingredient_batch B ON S.ID = B.FK_SupplierID " +
                "WHERE B.[delivery date] >= DATEADD(month, -1, GETDATE()) " +
                "GROUP BY S.ID, S.name " +
                "ORDER BY SUM(B.units) DESC";

        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new ClsSupplier(
                        resultSet.getInt("ID"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Exception findTop: " + e.getMessage());
        }
        return Optional.empty();
    }
}
