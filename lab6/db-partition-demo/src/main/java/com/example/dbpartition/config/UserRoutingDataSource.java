package com.example.dbpartition.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * HORIZONTAL PARTITION — Core routing
 *
 * AbstractRoutingDataSource cho phep chon DataSource
 * khac nhau dua tren ThreadLocal context.
 *
 * Flow:
 *   UserService.saveUser()
 *     -> setDataSource("MALE" hoac "FEMALE")
 *     -> Spring goi determineCurrentLookupKey()
 *     -> Tra ve dung datasource
 */
public class UserRoutingDataSource extends AbstractRoutingDataSource {

    // ThreadLocal: moi thread co 1 gia tri rieng (an toan voi multi-thread)
    private static final ThreadLocal<String> CURRENT_DS = new ThreadLocal<>();

    // Key constants
    public static final String MALE   = "MALE";
    public static final String FEMALE = "FEMALE";

    /**
     * Goi truoc khi thuc hien DB operation
     * Gia tri phai khop voi key trong targetDataSources map
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String key = CURRENT_DS.get();
        System.out.println("[Routing] --> DataSource: " + key);
        return key;
    }

    /** Chuyen sang DB Nam (table_user_01) */
    public static void useMale() {
        CURRENT_DS.set(MALE);
    }

    /** Chuyen sang DB Nu (table_user_02) */
    public static void useFemale() {
        CURRENT_DS.set(FEMALE);
    }

    /** Set theo gender string */
    public static void setByGender(String gender) {
        if ("MALE".equalsIgnoreCase(gender) || "NAM".equalsIgnoreCase(gender)) {
            useMale();
        } else {
            useFemale();
        }
    }

    /**
     * QUAN TRONG: Luon clear sau khi dung
     * Tranh memory leak trong thread pool
     */
    public static void clear() {
        CURRENT_DS.remove();
    }
}
