package ch.heigvd.iict.sym.sym_labo3.auth;

/**
 * Allow the setting of the current level of authorization.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class LoggedAccess {
    public static final int HIGH_AUTH = 10;
    public static final int MEDIUM_AUTH = 7;
    public static final int LOW_AUTH = 4;
    public static final int NO_AUTH = 0;

    private String currAuthTag = null;
    private long tAuthStart = 0;

    /**
     * Get the security level. The security level of the user is decremented by 1 every minutes.
     * @return the current security level.
     */
    public int getSecurityLevel() {
        long tAuthAccess = System.currentTimeMillis();
        long tDelta = tAuthAccess - tAuthStart;
        double timeAuth = (tDelta / 1000.0) / 60.0; // in minutes

        int mySecurityLevel = HIGH_AUTH - (int) timeAuth;

        return mySecurityLevel < NO_AUTH ? NO_AUTH : mySecurityLevel;
    }

    /**
     * Set the current session as unlogged.
     */
    public void setAsUnlogged() {
        tAuthStart = 0;
    }

    /**
     * Set the current session as logged
     * @param authTag The tag used to log in.
     */
    public void setAsLogged(String authTag) {
        currAuthTag = authTag;
        tAuthStart = System.currentTimeMillis();
    }

    /**
     * Update the authorization level with the given tag
     * @param authTag The tag used to update the authorization level.
     * @return True if the update has been accepted, false otherwise.
     */
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
