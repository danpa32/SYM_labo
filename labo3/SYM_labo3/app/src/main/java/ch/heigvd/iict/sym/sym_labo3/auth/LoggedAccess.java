package ch.heigvd.iict.sym.sym_labo3.auth;

/**
 * Allow the setting of the current level of authorization.
 */
public class LoggedAccess {
    public static final int HIGH_AUTH = 10;
    public static final int MEDIUM_AUTH = 7;
    public static final int LOW_AUTH = 4;
    public static final int NO_AUTH = 0;

    private String currAuthTag = null;
    private long tAuthStart = 0;

    /**
     * Get the security level
     * @return the security level.
     */
    public int getSecurityLevel() {
        long tAuthAccess = System.currentTimeMillis();
        long tDelta = tAuthAccess - tAuthStart;
        double timeAuth = (tDelta / 1000.0) / 60.0; // in minutes

        int mySecurityLevel = HIGH_AUTH - (int) timeAuth;

        return mySecurityLevel < NO_AUTH ? NO_AUTH : mySecurityLevel;
    }

    public void setAsUnlogged() {
        tAuthStart = 0;
    }

    public void setAsLogged(String authTag) {
        currAuthTag = authTag;
        tAuthStart = System.currentTimeMillis();
    }

    public boolean updateAuth(String authTag) {
        if(currAuthTag == null) {
            currAuthTag = authTag; // Associate the given tag with the "session"
        }
        if(currAuthTag.equals(authTag) && getSecurityLevel() != NO_AUTH) {
            tAuthStart = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
