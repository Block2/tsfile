package cn.edu.tsinghua.tsfile.timeseries.filterV2.operator;

import cn.edu.tsinghua.tsfile.timeseries.filterV2.basic.UnaryFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.TimeValuePairFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;

/**
 * less than
 *
 * @param <T> comparable data type
 * @author CGF
 */
public class Lt<T extends Comparable<T>> extends UnaryFilter<T> {

    private static final long serialVersionUID = -2088181659871608986L;

    public Lt(T value) {
        super(value);
    }

    @Override
    public <R> R accept(TimeValuePair value, TimeValuePairFilterVisitor<R> visitor) {
        return visitor.visit(value, this);
    }

    @Override
    public String toString() {
        return " < " + value;
    }
}
