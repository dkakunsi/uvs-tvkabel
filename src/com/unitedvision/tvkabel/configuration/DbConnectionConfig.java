package com.unitedvision.tvkabel.configuration;

public class DbConnectionConfig {
	public static final String HOST = "localhost";
	public static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    public static final String PROPERTY_NAME_DATABASE_URL = String.format("jdbc:mysql://%s:3306/unitedvision_tvkabel", HOST);
    public static final String PROPERTY_NAME_DATABASE_USERNAME = "uv_tvk";
    public static final String PROPERTY_NAME_DATABASE_PASSWORD = "tvkabel001";
    public static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.unitedvision.tvkabel.entity";
}
