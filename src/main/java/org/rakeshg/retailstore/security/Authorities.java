package org.rakeshg.retailstore.security;

public final class Authorities {

    public static final String ROLE_OWNER = "ROLE_OWNER";
    public static final String ROLE_STAFF = "ROLE_STAFF";

    public static final String STORE_READ = "STORE_READ";
    public static final String STORE_WRITE = "STORE_WRITE";
    public static final String INVENTORY_READ = "INVENTORY_READ";
    public static final String INVENTORY_WRITE = "INVENTORY_WRITE";

    private Authorities() {}
}
