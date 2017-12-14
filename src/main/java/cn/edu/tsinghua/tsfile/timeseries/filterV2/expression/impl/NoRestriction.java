package cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl;


import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.NormalFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.ValueFilterVisitor;

/**
 * <code>NoRestriction</code> means that there is no filter.
 */
public class NoRestriction<T extends Comparable<T>> implements Filter<T> {
    private static final NoRestriction INSTANCE = new NoRestriction();

    public static final NoRestriction getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T accept(NormalFilterVisitor<T> visitor) {
        return null;
    }

    @Override
    public <R> R accept(T value, ValueFilterVisitor<R> visitor) {
        return visitor.visit(value, this);
    }
}
