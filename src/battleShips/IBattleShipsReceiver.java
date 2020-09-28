package battleShips;

import java.io.IOException;

public interface IBattleShipsReceiver
{
    /**
     * erlaubt im Zustand DICE_SENT
     */
    void receiveDice(int random) throws StatusException, IOException;

    /**
     * Erlaubt im Zustand PASSIVE -> AKTIVE_RESPONSE
     */
    void receiveFire(int x, int y) throws StatusException, IOException;

    /**
     * erlaubt im Zustand PASSIVE -> WON
     */
    void receiveSurrender() throws IOException, StatusException;

    /**
     * Erlaubt im Zustand PASSIVE_RESPONSE -> AKTIVE
     */
    void receiveResponse(boolean hit) throws StatusException, IOException;
}