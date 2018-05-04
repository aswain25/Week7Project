package com.example.admin.server;

public class DataContract {
    String name;
    String primaryKeyName;
    String primaryKeyType;
    String[] columnNames;
    String[] columnTypes;
    int version;

    public DataContract(String name, String primaryKeyName, String primaryKeyType, String[] columnNames, String[] columnTypes, int version) {
        this.name = name;
        this.primaryKeyName = primaryKeyName;
        this.primaryKeyType = primaryKeyType;
        this.columnNames = columnNames;
        this.columnTypes = columnTypes;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public String getPrimaryKeyType() {
        return primaryKeyType;
    }

    public void setPrimaryKeyType(String primaryKeyType) {
        this.primaryKeyType = primaryKeyType;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(String[] columnTypes) {
        this.columnTypes = columnTypes;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
