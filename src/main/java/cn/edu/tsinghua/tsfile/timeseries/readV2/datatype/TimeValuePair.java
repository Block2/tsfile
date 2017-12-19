package cn.edu.tsinghua.tsfile.timeseries.readV2.datatype;

/**
 * @author Jinrui Zhang
 */
public class TimeValuePair {
    private long timestamp;
    private TsPrimitiveType value;

    public TimeValuePair(long timestamp, TsPrimitiveType value){
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TsPrimitiveType getValue() {
        return value;
    }

    public void setValue(TsPrimitiveType value) {
        this.value = value;
    }
}
