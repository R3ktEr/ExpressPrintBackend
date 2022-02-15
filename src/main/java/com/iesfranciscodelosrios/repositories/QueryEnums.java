package com.iesfranciscodelosrios.repositories;

/**
 * Consultas personalizadas atacando a varias tablas
 */
public enum QueryEnums {
    historicalOffSet("SELECT 'color' as type,\n" +
            "       id,\n" +
            "       price,\n" +
            "       valid,\n" +
            "       date,\n" +
            "       is_color,\n" +
            "       -1      as ended_type,\n" +
            "       -1      as impressions_type,\n" +
            "       -1      as size_sheet,\n" +
            "       null    as sheet_size,\n" +
            "       null    as description,\n" +
            "       -1      as thickness_type\n" +
            "FROM color\n" +
            "UNION ALL\n" +
            "SELECT 'copy' as type,\n" +
            "       id,\n" +
            "       price,\n" +
            "       valid,\n" +
            "       date,\n" +
            "       null   as is_color,\n" +
            "       -1     as ended_type,\n" +
            "       -1     as impressions_type,\n" +
            "       -1     as size_sheet,\n" +
            "       null   as sheet_size,\n" +
            "       null   as description,\n" +
            "       -1     as thickness_type\n" +
            "FROM copy\n" +
            "UNION ALL\n" +
            "SELECT 'ended' as type,\n" +
            "       id,\n" +
            "       price,\n" +
            "       valid,\n" +
            "       date,\n" +
            "       null    as is_color,\n" +
            "       ended_type,\n" +
            "       -1      as impressions_type,\n" +
            "       -1      as size_sheet,\n" +
            "       null    as sheet_size,\n" +
            "       null    as description,\n" +
            "       -1      as thickness_type\n" +
            "FROM ended\n" +
            "UNION ALL\n" +
            "SELECT 'impressionperside' as type,\n" +
            "       id,\n" +
            "       price,\n" +
            "       valid,\n" +
            "       date,\n" +
            "       null                as is_color,\n" +
            "       -1                  as ended_type,\n" +
            "       impressions_type,\n" +
            "       -1                  as size_sheet,\n" +
            "       null                as sheet_size,\n" +
            "       null                as description,\n" +
            "       -1                  as thickness_type\n" +
            "FROM impression_per_side\n" +
            "UNION ALL\n" +
            "SELECT 'size' as type,\n" +
            "       id,\n" +
            "       price,\n" +
            "       valid,\n" +
            "       date,\n" +
            "       null   as is_color,\n" +
            "       -1     as ended_type,\n" +
            "       -1     as impressions_type,\n" +
            "       size_sheet,\n" +
            "       sheet_size,\n" +
            "       null   as description,\n" +
            "       -1     as thickness_type\n" +
            "FROM size\n" +
            "UNION ALL\n" +
            "SELECT 'thickness' as type,\n" +
            "       id,\n" +
            "       price,\n" +
            "       valid,\n" +
            "       date,\n" +
            "       null        as is_color,\n" +
            "       -1          as ended_type,\n" +
            "       -1          as impressions_type,\n" +
            "       -1          as size_sheet,\n" +
            "       null        as sheet_size,\n" +
            "       description,\n" +
            "       thickness_type\n" +
            "FROM thickness\n" +
            "ORDER BY date desc, id desc LIMIT 10 OFFSET ?");

    private final String query;

    QueryEnums(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
