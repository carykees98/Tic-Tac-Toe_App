package clarkson.ee408.tictactoev4.model;

//import org.jetbrains.annotations.NotNull;

/**
 * Class containing information related to a user
 */
public class User {
    private final String m_Username;
    private String m_Password;
    private String m_DisplayName;
    private boolean m_Online;

    /**
     * Default constructor for a User
     */
    public User() {
        m_Username = m_Password = m_DisplayName = "Uninitialized";
        m_Online = false;
    }

    /**
     * @param username    A string representation of user’s username
     * @param password    A string representation of user’s password
     * @param displayName A string representation of user’s display name
     * @param online      A Boolean variable to indicate if a user is online or not
     */
    public User(String username, String password, String displayName, boolean online) {
        m_Username = username;
        m_Password = password;
        m_DisplayName = displayName;
        m_Online = online;
    }

    /**
     * @return Returns value of m_Username
     */
    public String getUsername() {
        return m_Username;
    }

    /**
     * @return Returns value of m_Password
     */
    public String getPassword() {
        return m_Password;
    }

    /**
     * @param password Value to assign to m_Password
     */
    public void setPassword(String password) {
        m_Password = password;
    }

    /**
     * @return Returns value of m_DisplayName
     */
    public String getDisplayName() {
        return m_DisplayName;
    }

    /**
     * @param displayName Value to assign to m_DisplayName
     */
    public void setDisplayName(String displayName) {
        m_DisplayName = displayName;
    }

    /**
     * @return Returns value of m_Online
     */
    public boolean isOnline() {
        return m_Online;
    }

    /**
     * @param online Value to assign to m_Online
     */
    public void setOnlineStatus(boolean online) {
        m_Online = online;
    }

    /**
     * @param otherUser User object to compare against
     * @return boolean value representing whether two users are equal
     */
    public boolean equals(User otherUser) {
        return m_Username.equals(otherUser.m_Username);
    }
}