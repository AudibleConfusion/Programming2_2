package streamMachine;

public interface ISensorDataSet
{
    /**
     * @return time of value creation
     */
    long getTime();

    /**
     * @return actual values
     */
    float[] getValues();
}
