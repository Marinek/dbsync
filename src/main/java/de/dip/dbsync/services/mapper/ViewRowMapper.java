package de.dip.dbsync.services.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import de.dip.dbsync.business.ViewColumnDTO;

public class ViewRowMapper implements RowMapper<List<ViewColumnDTO>> {

    @Override
    public List<ViewColumnDTO> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<ViewColumnDTO> columnList = new ArrayList<>();

        do {
            ViewColumnDTO viewDTO = new ViewColumnDTO();
    
            viewDTO.setColumnName(rs.getString("COLUMN_NAME"));
            viewDTO.setDataLength(rs.getString("DATA_LENGTH"));
            viewDTO.setDataType(rs.getString("DATA_TYPE"));

            columnList.add(viewDTO);

        } while (rs.next());

        return columnList;
    }

}
