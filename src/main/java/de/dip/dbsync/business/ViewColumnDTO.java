package de.dip.dbsync.business;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ViewColumnDTO {

    private String columnName;
    private String dataType;
    private String dataLength;

    public void setDataType(String dataType) {
        this.dataType = convertOracleToMySQL(dataType);
    }

    private String convertOracleToMySQL(String oracleDataType) {
        switch (oracleDataType.toUpperCase()) {
            case "VARCHAR2":
            case "CHAR":
                return "VARCHAR"; // Oracle VARCHAR2 wird in MySQL zu VARCHAR
            case "NUMBER":
                return "INT"; // Oracle NUMBER wird in MySQL zu INT
            case "DATE":
                return "DATETIME"; // Oracle DATE wird in MySQL zu DATETIME
            case "TIMESTAMP":
                return "DATETIME"; // Oracle TIMESTAMP wird in MySQL zu DATETIME
            case "CLOB":
                return "TEXT"; // Oracle CLOB wird in MySQL zu TEXT
            case "BLOB":
                return "BLOB"; // Oracle BLOB bleibt BLOB in MySQL
            case "LONG":
                return "TEXT"; // Oracle LONG wird in MySQL zu TEXT
            case "FLOAT":
                return "FLOAT"; // Oracle FLOAT bleibt FLOAT in MySQL
            case "DOUBLE":
                return "DOUBLE"; // Oracle DOUBLE bleibt DOUBLE in MySQL
            case "BINARY_FLOAT":
                return "FLOAT"; // Oracle BINARY_FLOAT wird in MySQL zu FLOAT
            case "BINARY_DOUBLE":
                return "DOUBLE"; // Oracle BINARY_DOUBLE wird in MySQL zu DOUBLE
            case "LONG RAW":
                return "BLOB"; // Oracle LONG RAW wird in MySQL zu BLOB
            case "RAW":
                return "VARBINARY"; // Oracle RAW wird in MySQL zu VARBINARY
            case "BOOLEAN":
                return "BOOLEAN"; // Oracle BOOLEAN bleibt BOOLEAN in MySQL
            default:
                return "VARCHAR"; // Standardfall, wenn der Oracle-Typ nicht erkannt wird
        }
    }
}
