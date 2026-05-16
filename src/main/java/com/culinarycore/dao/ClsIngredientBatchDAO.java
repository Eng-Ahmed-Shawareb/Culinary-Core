package com.culinarycore.dao;

import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.dao.singleton.ClsDatabaseConnection;
import com.culinarycore.model.ClsIngredientBatch;
import com.culinarycore.model.StatusEnums.EnIngredientBatch;
import com.culinarycore.model.dto.ClsKitchenBatchReportDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsIngredientBatchDAO implements IRepository<ClsIngredientBatch> {
    private ClsDatabaseConnection _databaseConnection;

    public ClsIngredientBatchDAO() {
        this._databaseConnection = ClsDatabaseConnection.getInstance();
    }

    @Override
    public Optional<ClsIngredientBatch> findByID(int ID) {

        String query = "SELECT * FROM Ingredient_batch WHERE ID = ?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ClsIngredientBatch(
                        resultSet.getInt("ID"),
                        resultSet.getInt("FK_SupplierID"),
                        resultSet.getInt("units"),
                        resultSet.getString("name"),
                        resultSet.getDate("expiration date").toLocalDate(),
                        resultSet.getDate("delivery date").toLocalDate(),
                        parseIngredientState(resultSet.getString("state"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("Exception findByID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ClsIngredientBatch> findAll() {
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM Ingredient_batch";
        List<ClsIngredientBatch> ingredientBatches = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ingredientBatches.add(new ClsIngredientBatch(
                        resultSet.getInt("ID"),
                        resultSet.getInt("FK_SupplierID"),
                        resultSet.getInt("units"),
                        resultSet.getString("name"),
                        resultSet.getDate("expiration date").toLocalDate(),
                        resultSet.getDate("delivery date").toLocalDate(),
                        parseIngredientState(resultSet.getString("state"))
                ));
            }
            return ingredientBatches;
        } catch (SQLException e) {
            System.out.println("Exception findAll: " + e.getMessage());
        }
        return List.of();
    }

    @Override
    public boolean save(ClsIngredientBatch entity) {
        String query = "INSERT INTO Ingredient_batch (FK_SupplierID, name, units, [expiration date], [delivery date], state) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = _databaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.getSupplierID());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getUnit());
            statement.setDate(4, Date.valueOf(entity.getExpirationDate()));
            statement.setDate(5, Date.valueOf(entity.getDeliveryDate()));
            statement.setString(6, entity.getState().name());

            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception save: " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsIngredientBatch entity) {
        String query = "UPDATE Ingredient_batch SET FK_SupplierID = ?, name = ?, units = ?, [expiration date] = ?, [delivery date] = ?, state = ? WHERE ID = ?";
        Connection connection = _databaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entity.getSupplierID());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getUnit());
            statement.setDate(4, Date.valueOf(entity.getExpirationDate()));
            statement.setDate(5, Date.valueOf(entity.getDeliveryDate()));
            statement.setString(6, entity.getState().name());


            statement.setInt(7, entity.getID());

            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception update: " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        String query = "DELETE FROM Ingredient_batch WHERE ID = ?";
        Connection connection = _databaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception delete: " + es.getMessage());
        }
        return false;
    }

    public List<ClsIngredientBatch> findBySupplier(int sID) {
        String query = "SELECT * FROM Ingredient_batch WHERE FK_SupplierID = ?";
        List<ClsIngredientBatch> batches = new ArrayList<>();
        Connection connection = _databaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, sID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                batches.add(new ClsIngredientBatch(
                        resultSet.getInt("ID"),
                        resultSet.getInt("FK_SupplierID"),
                        resultSet.getInt("units"),
                        resultSet.getString("name"),
                        resultSet.getDate("expiration date").toLocalDate(),
                        resultSet.getDate("delivery date").toLocalDate(),
                        parseIngredientState(resultSet.getString("state"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("Exception findBySupplier: " + e.getMessage());
        }
        return batches;
    }

    public List<ClsKitchenBatchReportDTO> getKitchenConsumptionReportDTO() {
        String query = "SELECT K.name, B.name, SUM(C.qantity) " +
                "FROM Kitchen K " +
                "JOIN Workshop W ON K.ID = W.FK_KitchenID " +
                "JOIN Consume C ON W.ID = C.FK_WorkshopID " +
                "JOIN Ingredient_batch B ON C.FK_BatchID = B.ID " +
                "WHERE C.[consuming date] >= DATEADD(month, -1, GETDATE()) " +
                "GROUP BY K.name, B.name";

        List<ClsKitchenBatchReportDTO> report = new ArrayList<>();
        Connection connection = _databaseConnection.getConnection();
        try (
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                report.add(new ClsKitchenBatchReportDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }
        } catch (SQLException e) {
            System.out.println("DTO Report Error: " + e.getMessage());
        }
        return report;
    }

    private EnIngredientBatch parseIngredientState(String stateValue) {
        if (stateValue == null) {
            return EnIngredientBatch.EXIST;
        }

        String normalized = stateValue.trim().toUpperCase();

        if ("CONSUMED".equals(normalized)) {
            return EnIngredientBatch.CONSUMED;
        }

        if ("EXIST".equals(normalized) ||
            "FRESH".equals(normalized) ||
            "SEALED".equals(normalized) ||
            "REFRIGERATED".equals(normalized) ||
            "DRY".equals(normalized)) {
            return EnIngredientBatch.EXIST;
        }

        return EnIngredientBatch.EXIST;
    }
}
