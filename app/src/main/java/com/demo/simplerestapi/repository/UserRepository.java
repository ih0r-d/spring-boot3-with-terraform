package com.demo.simplerestapi.repository;

import com.demo.simplerestapi.model.User;
import com.demo.simplerestapi.model.UserRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepository(@Qualifier("usersJdbcClient") JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final RowMapper<User> usersRowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("username"), rs.getString("email"));

    private final RowMapper<Long> createUserMapper = (rs, rowNum) -> rs.getLong("id");

    public List<User> users() {
        return jdbcClient.sql("select * from users")
                .query(this.usersRowMapper)
                .list();
    }

    public Long createUser(UserRequest userRequest) {
        return jdbcClient.sql("insert into users (username,email) values (?, ?) returning id")
                .params(userRequest.username(), userRequest.email())
                .query(this.createUserMapper)
                .single();
        // update will be return int value ( success or not )
    }

    public Optional<User> getById(Long id) {
        return jdbcClient.sql("select * from users where id = ?")
                .param(id)
                .query(User.class)
//                .single()
                .optional();
    }

    public Optional<User> updateUserById(Long id, UserRequest userRequest) {
        var optionalUser = getById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        User user = optionalUser.get();
        user.setEmail(userRequest.email());
        user.setUsername(userRequest.username());
        return Optional.of(user);
    }

    public Optional<Integer> removeUserById(Long id) {
        var optionalUser = getById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        int updateCount = jdbcClient.sql("delete from users where id = ?")
                .param(id)
                .update();
        return Optional.of(updateCount);
    }
}
