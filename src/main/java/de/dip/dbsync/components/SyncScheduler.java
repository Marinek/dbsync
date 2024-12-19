package de.dip.dbsync.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.dip.dbsync.services.SourceDatabaseService;
import de.dip.dbsync.services.TargetDatabaseService;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class SyncScheduler {

    @Autowired
    private SourceDatabaseService sourceDBService;
    
    @Autowired
    private TargetDatabaseService targetDBService;

    @Scheduled(fixedRate = 10000)
    public void doIt() {
        log.info("Starting to work wonders...");

        List<String> allViews = sourceDBService.getAllViewNames();

        for(String view : allViews) {
            log.info("Found: " + view);

            targetDBService.createTableFromView(view, sourceDBService.getViewDefinition(view));

            transferData(view);
        }
    }

    private void transferData (String viewName) {
        String selectQuery = "SELECT * FROM " + viewName;
    
        // Retrieve metadata and dynamically build the INSERT query
        sourceDBService.getJdbcTemplate().query(selectQuery, rs -> {
            StringBuilder insertQuery = new StringBuilder("INSERT INTO " + viewName + " (");
    
            // Dynamically get columns
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                insertQuery.append(rs.getMetaData().getColumnName(i));
                if (i < columnCount) {
                    insertQuery.append(", ");
                }
            }
            
            insertQuery.append(") VALUES (");
            for (int i = 1; i <= columnCount; i++) {
                insertQuery.append("?");
                if (i < columnCount) {
                    insertQuery.append(", ");
                }
            }
            insertQuery.append(")");
    
            // Prepare the values to insert
            targetDBService.getJdbcTemplate().update(insertQuery.toString(), ps -> {
                for (int i = 1; i <= columnCount; i++) {
                    ps.setObject(i, rs.getObject(i));
                }
            });
        });
    }
}
