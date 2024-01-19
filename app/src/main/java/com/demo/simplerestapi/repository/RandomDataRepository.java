package com.demo.simplerestapi.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import ua.lviv.javaclub.clients.model.ApplianceData;

import java.util.List;
import java.util.Optional;

@Repository
public class RandomDataRepository {
    private final JdbcClient jdbcClient;

    public RandomDataRepository(@Qualifier("randomJdbcClient") JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final RowMapper<Long> idRowMapper = (rs, rowNum) -> rs.getLong("id");

    public List<ApplianceData> getApplianceData() {
        return jdbcClient.sql("select * from appliances")
                .query(ApplianceData.class)
                .list();
    }

    public Long createRandomAppliance(ApplianceData applianceData) {
        return jdbcClient.sql("insert into appliances (id,uid,brand,equipment) values (?,?,?,?) returning id")
                .params(applianceData.id(), applianceData.uid(),applianceData.brand(),applianceData.equipment())
                .query(this.idRowMapper)
                .single();
        // update will be return int value ( success or not )
    }

    public Optional<ApplianceData> getById(Long id) {
        return jdbcClient.sql("select * from appliances where id = ?")
                .param(id)
                .query(ApplianceData.class)
                .optional();
    }

    public Optional<Integer> removeDataById(Long id) {
        var optionalUser = getById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        int updateCount = jdbcClient.sql("delete from appliances where id = ?")
                .param(id)
                .update();
        return Optional.of(updateCount);
    }
}
