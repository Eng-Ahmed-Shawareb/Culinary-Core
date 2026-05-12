package com.culinarycore.dao;

import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.dao.singleton.ClsDatabaseConnection;
import com.culinarycore.model.ClsConsume;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsConsumeDAO implements IRepository<ClsConsume> {
    private ClsDatabaseConnection _databaseConnection;

    public ClsConsumeDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    public Optional<ClsConsume> findById(int bID, int wID) {
        String query = "SELECT * FROM Consume WHERE FK_BatchID = ? AND FK_WorkshopID = ?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bID);
            statement.setInt(2, wID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ClsConsume(
                        resultSet.getInt("FK_BatchID"),
                        resultSet.getInt("FK_WorkshopID"),
                        resultSet.getInt("qantity"),
                        resultSet.getDate("consuming date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Exception findById (Composite): " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean delete(int bID, int wID) {
        String query = "DELETE FROM Consume WHERE FK_BatchID = ? AND FK_WorkshopID = ?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bID);
            statement.setInt(2, wID);
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception delete (Composite): " + es.getMessage());
        }
        return false;
    }

    @Override
    public List<ClsConsume> findAll() {
        String query = "SELECT * FROM Consume";
        List<ClsConsume> consumes = new ArrayList<>();
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                consumes.add(new ClsConsume(
                        resultSet.getInt("FK_BatchID"),
                        resultSet.getInt("FK_WorkshopID"),
                        resultSet.getInt("qantity"),
                        resultSet.getDate("consuming date").toLocalDate()
                ));
            }
            return consumes;
        } catch (SQLException e) {
            System.out.println("Exception findAll: " + e.getMessage());
        }
        return List.of();
    }

    @Override
    public boolean save(ClsConsume entity) {
        String query = "INSERT INTO Consume (FK_BatchID, FK_WorkshopID, qantity, [consuming date]) VALUES (?, ?, ?, ?)";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.getBatchID());
            statement.setInt(2, entity.getWorkshopID());
            statement.setInt(3, entity.getQuantity());
            statement.setDate(4, Date.valueOf(entity.getConsumeDate()));

            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception save: " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsConsume entity) {
        String query = "UPDATE Consume SET qantity = ?, [consuming date] = ? WHERE FK_BatchID = ? AND FK_WorkshopID = ?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.getQuantity());
            statement.setDate(2, Date.valueOf(entity.getConsumeDate()));
            statement.setInt(3, entity.getBatchID());
            statement.setInt(4, entity.getWorkshopID());

            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception update: " + es.getMessage());
        }
        return false;
    }

    @Override
    public Optional<ClsConsume> findByID(int ID) {
        throw new UnsupportedOperationException("Table 'Consume' uses a composite primary key. Use findById(int bID, int wID) instead.");
    }

    @Override
    public boolean delete(int ID) {
        throw new UnsupportedOperationException("Table 'Consume' uses a composite primary key. Use delete(int bID, int wID) instead.");
    }
}