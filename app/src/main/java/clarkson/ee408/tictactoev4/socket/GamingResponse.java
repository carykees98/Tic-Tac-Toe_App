package clarkson.ee408.tictactoev4.socket;

/**
 * Class that contains information about the last move of the
 * game and the status of the current game active/inactive
 */
public class GamingResponse extends Response {
    private int m_Move;
    private boolean m_Active;

    /**
     * A default constructor for the class. Must call the constructor of super class
     */
    public GamingResponse() {
        super();
        m_Move = -1;
        m_Active = false;
    }

    /**
     * A constructor that sets all attributes of this class. Must call the constructor of super class
     *
     * @param status  Response::ResponseStatus
     * @param message A string message description about the status of the client-server communication
     * @param move    An integer representing the last move made by the current player’s opponent. The value from 0-8 represents the cell of TicTacToe from top-bottom, left-right
     * @param active  A boolean variable to indicate if the opponent is still active in the game.
     */
    public GamingResponse(ResponseStatus status, String message, int move, boolean active) {
        super(status, message);
        m_Move = move;
        m_Active = active;
    }

    /**
     * getMove()
     *
     * @return move as an integer representation
     */
    public int getMove() {
        return m_Move;
    }

    /**
     * Set move to:
     *
     * @param move set GamingResponse move to parameter int
     */
    public void setMove(int move) {
        m_Move = move;
    }

    /**
     * Check the status of GamingResponse
     *
     * @return true or false based on state
     */
    public boolean isActive() {
        return m_Active;
    }

    /**
     * Set the status of GamingResponse
     *
     * @param active new state has been set to: true/false
     */
    public void setActive(boolean active) {
        m_Active = active;
    }
}
