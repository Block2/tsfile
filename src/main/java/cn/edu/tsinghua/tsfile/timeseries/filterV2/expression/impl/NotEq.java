package cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl;


import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.UnaryFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.NormalFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.ValueFilterVisitor;

/**
 * Not Equals
 *
 * @param <T> comparable data type
 * @author CGF
 */
public class NotEq<T extends Comparable<T>> extends UnaryFilter<T> {

    private static final long serialVersionUID = 2574090797476500965L;

    public NotEq(T value) {
        super(value);
    }

    @Override
    public <R> R accept(NormalFilterVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <R> R accept(T value, ValueFilterVisitor<R> visitor) {
        return visitor.visit(value, this);
    }

    @Override
    public String toString() {
        return " != " + value;
    }
}
