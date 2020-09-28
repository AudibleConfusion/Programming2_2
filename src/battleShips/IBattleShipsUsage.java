package battleShips;

import java.io.IOException;

public interface IBattleShipsUsage
{
    /**
     * figure out who starts
     */
    void doDice() throws StatusException, IOException;

    /**
     *
     * @return ture if game is running
     */
    boolean isActive();

    /**
     * fire at position x, y
     * @param x
     * @param y
     */
    void fire(int x, int y) throws GameException, StatusException;
}
