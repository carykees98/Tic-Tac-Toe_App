package clarkson.ee408.tictactoev4.socket;

/**
 * Object for server response to a client request
 */
public class Response {
    private final ResponseStatus m_Status;
    private final String m_Message;

    /**
     * Default constructor for Response Object
     * m_status is set to null
     * m_Message is set to "Uninitialized"
     */
    public Response() {
        m_Status = null;
        m_Message = "Uninitialized";
    }

    /**
     * Response object constructor
     * @param status ResponseStatus::status
     * @param message string message
     */
    public Response(ResponseStatus status, String message) {
        m_Status = status;
        m_Message = message;
    }

    /**
     * @return Message from object
     */
    public String getMessage() {
        return m_Message;
    }

    /**
     * @return Status from object
     */
    public ResponseStatus getStatus() {
        return m_Status;
    }

    public ResponseStatus getType() {return m_Status;}

    /**
     * enums for Response Status
     * handles
     * SUCCESS
     * FAILURE
     */
    public enum ResponseStatus {
        SUCCESS,
        FAILURE
    }
}
