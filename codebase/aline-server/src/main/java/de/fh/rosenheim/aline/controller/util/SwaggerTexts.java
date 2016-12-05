package de.fh.rosenheim.aline.controller.util;

/**
 * Define texts for swagger here to avoid duplicates and allow easy changes
 */
public class SwaggerTexts {

    public final static String GET_USER_DATA = "Returns the data for the given user or if no username is given from the current user (detected via token).";

    public final static String GET_DIVISION_USERS = "Returns the users for the given division or if no division is given, the division from the current user is used (detected via token). ";

    /**
     * Getting data about a user other than yourself
     */
    public final static String SENSITIVE_DATA = "Accessing other user's data requires specific authorities.";
}
