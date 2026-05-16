package com.culinarycore.dao;

import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.dao.singleton.ClsDatabaseConnection;
import com.culinarycore.model.ClsWorkshop;
import com.culinarycore.model.StatusEnums.EnWorkshopStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsWorkshopDAO implements IRepository<ClsWorkshop> {
    private ClsDatabaseConnection _databaseConnection;

    public ClsWorkshopDAO(){
        _databaseConnection = ClsDatabaseConnection.getInstance();
    }

    @Override
    public Optional<ClsWorkshop> findByID(int ID) {
        String query = "SELECT * FROM Workshop WHERE ID = ?";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new ClsWorkshop(resultSet.getInt("ID") ,
                            resultSet.getInt("FK_KitchenID") ,
                            resultSet.getInt("FK_ChefID") , resultSet.getString("title") ,
                            resultSet.getObject("start date" , LocalDate.class) ,
                            resultSet.getObject("end date" , LocalDate.class) ,
                            resultSet.getDouble("price") ,
                            EnWorkshopStatus.valueOf(resultSet.getString("state")) ,
                            resultSet.getString("technique")));
                }
            }
        } catch (SQLException es) {
            System.out.println("Exception findByID: " + es.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ClsWorkshop> findAll() {
        List<ClsWorkshop> resultList = new ArrayList<>();
        String query = "SELECT * FROM Workshop";
        Connection connection = _databaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ClsWorkshop workshop = (new ClsWorkshop(resultSet.getInt("ID") ,
                            resultSet.getInt("FK_KitchenID") ,
                            resultSet.getInt("FK_ChefID") , resultSet.getString("title") ,
                            resultSet.getObject("start date" , LocalDate.class) ,
                            resultSet.getObject("end date" , LocalDate.class) ,
                            resultSet.getDouble("price") ,
                            EnWorkshopStatus.valueOf(resultSet.getString("state")) ,
                            resultSet.getString("technique")));
                    resultList.add(workshop);
                }
            }
        } catch (SQLException es) {
            System.out.println("Exception findAll: " + es.getMessage());
        }

        return resultList;
    }

    @Override
    public boolean save(ClsWorkshop entity) {
        Connection connection = _databaseConnection.getConnection();
        String query = "INSERT INTO Workshop(FK_KitchenID , FK_ChefID , [start date] , [end date] , price , state , technique , title) VALUES(? , ? , ? , ? , ? , ? , ? , ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1 , entity.getKitchenID());
            statement.setInt(2 , entity.getChefID());
            statement.setObject(3 , entity.getStartDate());
            statement.setObject(4 , entity.getEndDate());
            statement.setDouble(5 , entity.getPrice());
            statement.setString(6 , entity.getStatus().name());
            statement.setString(7 , entity.getTechnique());
            statement.setString(8 , entity.getTitle());

            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsWorkshop entity) {
        Connection connection = _databaseConnection.getConnection();
        String query = "UPDATE Workshop set [start date] = ? , [end date] = ? , price = ? , state = ? , technique = ? , title = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1 , entity.getStartDate());
            statement.setObject(2 , entity.getEndDate());
            statement.setDouble(3 , entity.getPrice());
            statement.setString(4 , entity.getStatus().name());
            statement.setString(5 , entity.getTechnique());
            statement.setString(6 , entity.getTitle());
            statement.setInt(7 , entity.getID());
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        Connection connection = _databaseConnection.getConnection();
        String query = "DELETE FROM Workshop WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1 , ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

}
