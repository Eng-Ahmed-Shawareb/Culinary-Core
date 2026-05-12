package com.culinarycore.dao;

import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.dao.singleton.ClsDatabaseConnection;
import com.culinarycore.model.ClsRegister;
import com.culinarycore.model.StatusEnums.EnPaymentStatus;
import com.culinarycore.model.dto.ClsExpertiseEnrollmentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClsRegisterDAO implements IRepository<ClsRegister> {
    private ClsDatabaseConnection _databaseConnection;

    public ClsRegisterDAO(){
        _databaseConnection = ClsDatabaseConnection.getInstance();
    }


    @Override
    public Optional<ClsRegister> findByID(int ID) {
        return Optional.empty();
    }

    public Optional<ClsRegister> findByID(int studentID , int workshopID){
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM Register WHERE FK_StudentID = ? AND FK_WorkshopID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentID);
            statement.setInt(2 , workshopID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ClsRegister(resultSet.getInt("FK_StudentID") ,
                        resultSet.getInt("FK_WorkshopID") ,
                        resultSet.getObject("register date" , LocalDate.class) ,
                        EnPaymentStatus.valueOf(resultSet.getString("state"))));
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ClsRegister> findAll() {
        List<ClsRegister> resultList = new ArrayList<>();
        Connection connection = _databaseConnection.getConnection();
        String query = "SELECT * FROM Register";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
               ClsRegister registerInstance = new ClsRegister(resultSet.getInt("FK_StudentID") ,
                        resultSet.getInt("FK_WorkshopID") ,
                        resultSet.getObject("register date" , LocalDate.class) ,
                        EnPaymentStatus.valueOf(resultSet.getString("state")));
               resultList.add(registerInstance);
            }
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return resultList;
    }

    @Override
    public boolean save(ClsRegister entity) {
        Connection connection = _databaseConnection.getConnection();
        String query = "INSERT INTO Register(FK_StudentID , FK_WorkshopID , [register date] , state) VALUES(? , ? , ? , ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1 , entity.getStudentID());
            statement.setInt(2 , entity.getWorkshopID());
            statement.setObject(3 , entity.getRegisterDate());
            statement.setString(4 , entity.getPaymentStatus().name());
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ClsRegister entity) {
        Connection connection = _databaseConnection.getConnection();
        String query = "UPDATE Register SET [register date] = ? , state = ? WHERE FK_StudentID = ? AND FK_WorkshopID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1 , entity.getRegisterDate());
            statement.setString(2 , entity.getPaymentStatus().name());
            statement.setInt(3 , entity.getStudentID());
            statement.setInt(4 , entity.getWorkshopID());
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        return false;
    }

    public boolean delete(int studentID , int workshopID){
        Connection connection = _databaseConnection.getConnection();
        String query = "DELETE FROM Register WHERE FK_StudentID = ? AND FK_WorkshopID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1 , studentID);
            statement.setInt(2 , workshopID);
            return statement.executeUpdate() > 0;
        } catch (SQLException es) {
            System.out.println("Exception : " + es.getMessage());
        }
        return false;
    }

    public Optional<ClsExpertiseEnrollmentDTO> getTopExpertiseByEnrollments() {
        Connection connection = _databaseConnection.getConnection();

        String query = "SELECT TOP 1 c.expertise, COUNT(r.FK_StudentID) AS [Enrollment Count] " +
                "FROM Register r " +
                "INNER JOIN Workshop w ON r.FK_WorkshopID = w.ID " +
                "INNER JOIN chef c ON w.FK_ChefID = c.ID " +
                "GROUP BY c.expertise " +
                "ORDER BY [Enrollment Count] DESC;";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return Optional.of(new ClsExpertiseEnrollmentDTO(
                        resultSet.getString("expertise"),
                        resultSet.getInt("Enrollment Count")
                ));
            }
        } catch (SQLException es) {
            System.out.println("Exception: " + es.getMessage());
        }
        return Optional.empty();
    }
}
