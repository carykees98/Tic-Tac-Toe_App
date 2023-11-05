package clarkson.ee408.tictactoev4.socket;

/**
 * Clients request that is sent to the server
 */
public class Request {
    private final String m_Data;
    private RequestType m_Type;

    /**
     * Request object default constructor
     */
    public Request() {
        m_Type = null;
        m_Data = "Uninitialized";
    }

    /**
     * Request constructor that sets type and data
     *
     * @param type
     * @param data
     */
    public Request(RequestType type, String data) {
        m_Type = type;
        m_Data = data;
    }

    /**
     * Get type of request
     *
     * @return request tyoe
     */
    public RequestType getType() {
        return m_Type;
    }

    /**
     * Ser request tyoe
     *
     * @param type value to set request to
     */
    public void setType(RequestType type) {
        m_Type = type;
    }

    /**
     * Get Request object data
     *
     * @return data
     */
    public String getData() {
        return m_Data;
    }

    /**
     * Enums for the possible request types
     */
    public enum RequestType {
        LOGIN,
        REGISTER,
        UPDATE_PAIRING,
        SEND_INVITATION,
        ACCEPT_INVITATION,
        ACKNOWLEDGE_RESPONSE,
        REQUEST_MOVE,
        SEND_MOVE,
        ABORT_GAME,
        COMPLETE_GAME
    }
}
