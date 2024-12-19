package de.dip.dbsync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import de.dip.dbsync.business.ViewColumnDTO;
import de.dip.dbsync.services.mapper.ViewRowMapper;

import java.util.List;

@Service
public class SourceDatabaseService {

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllViewNames() {
        String sql = "SELECT VIEW_NAME FROM USER_VIEWS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("VIEW_NAME"));
    }

    public List<ViewColumnDTO> getViewDefinition(String viewName) {
        String sql = "SELECT column_name, data_type, data_length  FROM all_tab_columns WHERE table_name = ? ORDER BY column_id";
        return jdbcTemplate.queryForObject(sql, new ViewRowMapper(), viewName);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
