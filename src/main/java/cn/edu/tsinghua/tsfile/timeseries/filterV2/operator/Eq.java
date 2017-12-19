package cn.edu.tsinghua.tsfile.timeseries.filterV2.operator;

import cn.edu.tsinghua.tsfile.timeseries.filterV2.basic.UnaryFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.TimeValuePairFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;

/**
 * Equals
 *
 * @param <T> comparable data type
 * @author CGF
 */
public class Eq<T extends Comparable<T>> extends UnaryFilter<T> {

    private static final long serialVersionUID = -6668083116644568248L;

    public Eq(T value) {
        super(value);
    }

    @Override
    public String toString() {
        return " == " + value;
    }

    @Override
    public <R> R accept(TimeValuePair value, TimeValuePairFilterVisitor<R> visitor) {
        return visitor.visit(value, this);
    }
}
