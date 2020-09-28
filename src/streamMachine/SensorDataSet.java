package streamMachine;

public class SensorDataSet implements ISensorDataSet
{
    private final Long timeStamp;
    private final float[] values;

    public SensorDataSet(Long timeStamp, float[] values)
    {
        this.timeStamp = timeStamp;
        this.values = values;
    }

    @Override
    public long getTime()
    {
        return timeStamp;
    }

    @Override
    public float[] getValues()
    {
        return values;
    }
}
