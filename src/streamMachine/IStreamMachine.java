package streamMachine;

public interface IStreamMachine
{
    /**
     * This method can be called by a sensor to save a data package
     * @param time UNIX time when measurement took place
     * @param values sensor data
     * @throws PersistenceException if something unexpected happened
     */
    void saveData(long time, float[] values) throws PersistenceException;

    /**
     * @return number of data sets
     */
    int size();

    /**
     * @param index of data set
     * @return DataSet at index
     */
    ISensorDataSet getDataSet(int index) throws PersistenceException;

    /**
     * Deletes a saved files
     */
    void clean();
}