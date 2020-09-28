package battleShips;

import java.io.IOException;

public interface IBattleShipsSender
{
    /**
     * erlaubt im Zustand START -> DICE_SENT
     * @param random
     * @throws StatusException
     */
    void sendDice(int random) throws StatusException, IOException;

    /**
     * erlaubt im Zustand ACTIVE -> PASSIVE_RESPONSE
     * @param row
     * @param column
     * @throws StatusException
     */
    void sendFire(int row, int column) throws StatusException, IOException;

    /**
     * erlaubt im Zustand ACTIVE -> LOST
     * @throws StatusException
     */
    void sendSurrender() throws StatusException, IOException;

    /**
     * erlaubt im Zustand ACTIVE_RESPONSE -> ACTIVE
     */
    void sentResponse(boolean hit) throws StatusException, IOException;
}