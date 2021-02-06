package com.database;

import com.models.Country;
import com.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UsersRepository extends BaseRepository {

    public UsersRepository() throws Exception {
        super();
    }

    public User insert(User user) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into users " +
                            "(first_name, " +
                            "last_name, " +
                            "username, " +
                            "email, " +
                            "password, " +
                            "type_id, " +
                            "is_super_user, " +
                            "country_id, " +
                            "picture, " +
                            "birth_date, " +
                            "adress, " +
                            "postal_code, " +
                            "phone_number, " +
                            "gender_id, " +
                            "abbreviation, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getTypeId());
            preparedStatement.setBoolean(7, user.getIsSuperUser());
            preparedStatement.setInt(8, user.getCountryId());
            preparedStatement.setBinaryStream(9, user.getPicture());
            preparedStatement.setDate(10, user.getBirthDate());
            preparedStatement.setString(11, user.getAdress());
            preparedStatement.setString(12, user.getPostalCode());
            preparedStatement.setString(13, user.getPhoneNumber());
            preparedStatement.setInt(14, user.getGenderId());
            preparedStatement.setString(15, user.getAbbreviation());
            preparedStatement.setTimestamp(16, user.getCreatedAt());
            preparedStatement.setTimestamp(17, user.getUpdatedAt());
            preparedStatement.setLong(18, user.getCreatedUserId());
            preparedStatement.setLong(19, user.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from users order by id desc limit 1");
                resultSet.next();
                user.setId(resultSet.getInt("id"));
            }
            return user;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public User getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            User user = new User();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from users where id=" + id);
            resultSet.next();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
            user.setPassword(resultSet.getString("password"));
            user.setTypeId(resultSet.getByte("type_id"));
            user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
            user.setCountryId(resultSet.getInt("country_id"));
            user.setPicture(resultSet.getBinaryStream("picture"));
            user.setBirthDate(resultSet.getDate("birth_date"));
            user.setAdress(resultSet.getString("adress"));
            user.setPostalCode(resultSet.getString("postal_code"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setRememberToken(resultSet.getString("remember_token"));
            user.setGenderId(resultSet.getInt("gender_id"));
            user.setAbbreviation(resultSet.getString("abbreviation"));
            user.setCreatedAt(resultSet.getTimestamp("created_at"));
            user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            user.setCreatedUserId(resultSet.getLong("created_user_id"));
            user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            user.setActive(resultSet.getBoolean("active"));
            return user;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<User> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<User> users = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from users where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
                user.setPassword(resultSet.getString("password"));
                user.setTypeId(resultSet.getByte("type_id"));
                user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
                user.setCountryId(resultSet.getInt("country_id"));
                user.setPicture(resultSet.getBinaryStream("picture"));
                user.setBirthDate(resultSet.getDate("birth_date"));
                user.setAdress(resultSet.getString("adress"));
                user.setPostalCode(resultSet.getString("postal_code"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setRememberToken(resultSet.getString("remember_token"));
                user.setGenderId(resultSet.getInt("gender_id"));
                user.setAbbreviation(resultSet.getString("abbreviation"));
                user.setCreatedAt(resultSet.getTimestamp("created_at"));
                user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                user.setCreatedUserId(resultSet.getLong("created_user_id"));
                user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                user.setActive(resultSet.getBoolean("active"));
                users.add(user);
            }
            return FXCollections.observableList(users);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<User> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<User> users = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from users where active=true");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
                user.setPassword(resultSet.getString("password"));
                user.setTypeId(resultSet.getByte("type_id"));
                user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
                user.setCountryId(resultSet.getInt("country_id"));
                user.setPicture(resultSet.getBinaryStream("picture"));
                user.setBirthDate(resultSet.getDate("birth_date"));
                user.setAdress(resultSet.getString("adress"));
                user.setPostalCode(resultSet.getString("postal_code"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setRememberToken(resultSet.getString("remember_token"));
                user.setGenderId(resultSet.getInt("gender_id"));
                user.setAbbreviation(resultSet.getString("abbreviation"));
                user.setCreatedAt(resultSet.getTimestamp("created_at"));
                user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                user.setCreatedUserId(resultSet.getLong("created_user_id"));
                user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                user.setActive(resultSet.getBoolean("active"));
                users.add(user);
            }
            return FXCollections.observableList(users);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public User update(User user) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            user.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update users set " +
                            "first_name = ?," +
                            " last_name = ?," +
                            " username = ?, " +
                            " email = ?, " +
                            " email_verified_at = ?, " +
                            " password = ?, " +
                            " type_id = ?, " +
                            " is_super_user = ?, " +
                            " country_id = ?, " +
                            " picture = ?, " +
                            " birth_date = ?, " +
                            " adress = ?, " +
                            " postal_code = ?, " +
                            " phone_number = ?, " +
                            " remember_token = ?, " +
                            " gender_id = ?, " +
                            " abbreviation = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setTimestamp(5, user.getEmailVerifiedAt());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setInt(7, user.getTypeId());
            preparedStatement.setBoolean(8, user.getIsSuperUser());
            preparedStatement.setInt(9, user.getCountryId());
            preparedStatement.setBinaryStream(10, user.getPicture());
            preparedStatement.setDate(11, user.getBirthDate());
            preparedStatement.setString(12, user.getAdress());
            preparedStatement.setString(13, user.getPostalCode());
            preparedStatement.setString(14, user.getPhoneNumber());
            preparedStatement.setString(15, user.getRememberToken());
            preparedStatement.setInt(16, user.getGenderId());
            preparedStatement.setString(17, user.getAbbreviation());
            preparedStatement.setTimestamp(18, user.getUpdatedAt());
            preparedStatement.setLong(19, user.getUpdatedUserId());
            preparedStatement.setLong(20, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public User delete(User user) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            user.setActive(false);
            preparedStatement = connect.prepareStatement("update users set active = ? where id = ?");
            preparedStatement.setBoolean(1, user.getActive());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<User> loadCreatedUsers(ObservableList<User> users) throws Exception {
        if (users.isEmpty())
            return users;

        try {
            connect = DriverManager.getConnection(CONN);
            for (User userItem : users) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + userItem.getCreatedUserId());
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
                    user.setPassword(resultSet.getString("password"));
                    user.setTypeId(resultSet.getByte("type_id"));
                    user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
                    user.setCountryId(resultSet.getInt("country_id"));
                    user.setPicture(resultSet.getBinaryStream("picture"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setAdress(resultSet.getString("adress"));
                    user.setPostalCode(resultSet.getString("postal_code"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));
                    user.setRememberToken(resultSet.getString("remember_token"));
                    user.setGenderId(resultSet.getInt("gender_id"));
                    user.setAbbreviation(resultSet.getString("abbreviation"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    userItem.setCreatedUser(user);
                }
            }
            return users;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<User> loadUpdatedUsers(ObservableList<User> users) throws Exception {
        if (users.isEmpty())
            return users;

        try {
            connect = DriverManager.getConnection(CONN);
            for (User userItem : users) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + userItem.getUpdatedUserId());
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
                    user.setPassword(resultSet.getString("password"));
                    user.setTypeId(resultSet.getByte("type_id"));
                    user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
                    user.setCountryId(resultSet.getInt("country_id"));
                    user.setPicture(resultSet.getBinaryStream("picture"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setAdress(resultSet.getString("adress"));
                    user.setPostalCode(resultSet.getString("postal_code"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));
                    user.setRememberToken(resultSet.getString("remember_token"));
                    user.setGenderId(resultSet.getInt("gender_id"));
                    user.setAbbreviation(resultSet.getString("abbreviation"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    userItem.setUpdatedUser(user);
                }
            }
            return users;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<User> loadCountries(ObservableList<User> users) throws Exception {
        if (users.isEmpty())
            return users;

        try {
            connect = DriverManager.getConnection(CONN);
            for (User user : users) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from countries where active=true and id=" + user.getCountryId());
                while (resultSet.next()) {
                    Country country = new Country();
                    country.setId(resultSet.getInt("id"));
                    country.setName(resultSet.getString("name"));
                    country.setFlag(resultSet.getBinaryStream("flag"));
                    country.setShortcut(resultSet.getString("shortcut"));
                    country.setContinentId(resultSet.getByte("continent_id"));
                    country.setFileName(resultSet.getString("file_name"));
                    country.setCreatedAt(resultSet.getTimestamp("created_at"));
                    country.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    country.setCreatedUserId(resultSet.getLong("created_user_id"));
                    country.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    country.setActive(resultSet.getBoolean("active"));
                    user.setCountry(country);
                }
            }
            return users;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }
}
