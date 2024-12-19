package de.dip.dbsync.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import de.dip.dbsync.business.ViewColumnDTO;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TargetDatabaseService {

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    public void createTableFromView(String tableName, List<ViewColumnDTO> viewDefinition) {

        for(ViewColumnDTO viewColumn : viewDefinition) {
            log.info(viewColumn);
        }
        String createTableStatement = generateCreateTableStatement(tableName, viewDefinition);

        log.info(createTableStatement);
        
        jdbcTemplate.execute("DROP table if exists " + tableName);

        jdbcTemplate.execute(createTableStatement);
    }

    private String generateCreateTableStatement(String tableName, List<ViewColumnDTO> columns) {
        StringBuilder sb = new StringBuilder();
        
        // Beginne das CREATE TABLE Statement
        sb.append("CREATE TABLE ").append(tableName).append(" (\n");

        // Füge jede Spalte aus der Liste hinzu
        for (int i = 0; i < columns.size(); i++) {
            ViewColumnDTO column = columns.get(i);
            
            sb.append("    ").append(column.getColumnName()).append(" ")
              .append(column.getDataType());
            
            // Füge die Längeneinschränkung hinzu, wenn vorhanden
            if (column.getDataLength() != null && !column.getDataLength().isEmpty()) {
                sb.append("(").append(column.getDataLength()).append(")");
            }

            // Füge ein Komma hinzu, wenn es nicht die letzte Spalte ist
            if (i < columns.size() - 1) {
                sb.append(",");
            }

            sb.append("\n");
        }

        // Schließe das CREATE TABLE Statement ab
        sb.append(");");

        return sb.toString();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
