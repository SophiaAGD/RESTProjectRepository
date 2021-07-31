package com.training.RunningTracker.dao;

import com.training.RunningTracker.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDataDao {

    public static final String GET_USERDATA = "select * from \"User_data\" where user_id=?;";
    public static final String INSERT_USERDATA = "insert into \"User_data\" (distance, date, time, id, user_id) values (?, ?, ?, ?, ?);";
    public static final String DELETE_USERDATA = "delete from \"User_data\" where user_id=?;";
    public static final String UPDATE_USER_RECORD = "update \"User_data\" set distance=?, date=?, time=? where user_id=? and id=?;";

    private final DataSource userDataSource;


    @Autowired
    public UserDataDao(DataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public UserData createUserData(UserData userData) {
        PreparedStatement statement;
        ResultSet resultSet;
        UserData data = new UserData();

        try (Connection connection = userDataSource.getConnection()) {
                statement = connection.prepareStatement(INSERT_USERDATA);
                statement.setFloat(1,
                        userData.getDistance());
                statement.setDate(2,
                        userData.getDate());
                statement.setTime(3,
                        userData.getTime());
                statement.setInt(4,
                        userData.getId());
                statement.setInt(5,
                        userData.getUserId());

               statement = connection.prepareStatement(GET_USERDATA);
               resultSet = statement.executeQuery();
               if(resultSet.next()){
                   data.setDistance(resultSet.getFloat("distance"));
                   data.setDate(resultSet.getDate("date"));
                   data.setTime(resultSet.getTime("time"));
                   data.setId(resultSet.getInt("id"));
                   data.setUserId(resultSet.getInt("user_id"));

               }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;

    }


    public List<UserData> getUserData(UserData userData) {
        PreparedStatement statement;
        ResultSet resultSet;
        List<UserData> dataList = new ArrayList<>();


        try (Connection connection = userDataSource.getConnection()) {

            statement = connection.prepareStatement(GET_USERDATA);
            statement.setInt(1,
                    userData.getUserId());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt("user_id") == userData.getUserId()) {
                    UserData data = new UserData();
                    data.setDistance(resultSet.getFloat("distance"));
                    data.setDate(resultSet.getDate("date"));
                    data.setTime(resultSet.getTime("time"));
                    data.setId(resultSet.getInt("id"));
                    data.setUserId(resultSet.getInt("user_id"));
                    dataList.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }


    public HttpStatus deleteUserData(UserData userData) {
        PreparedStatement statement;

        try (Connection connection = userDataSource.getConnection()) {
            statement = connection.prepareStatement(DELETE_USERDATA);
            statement.setInt(1,
                    userData.getUserId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpStatus.GONE;
    }

    public HttpStatus updateUserData(UserData userData) {
        PreparedStatement statement;

        try (Connection connection = userDataSource.getConnection()) {
            try {
                statement = connection.prepareStatement(UPDATE_USER_RECORD);

                statement.setFloat(1,
                        userData.getDistance());
                statement.setDate(2,
                        userData.getDate());
                statement.setTime(3,
                        userData.getTime());
                statement.setInt(4,
                        userData.getUserId());
                statement.setInt(5,
                        userData.getId());
                statement.executeUpdate();


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpStatus.OK;
    }
}
