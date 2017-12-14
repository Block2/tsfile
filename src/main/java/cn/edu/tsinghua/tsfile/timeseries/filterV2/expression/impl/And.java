package cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl;

import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.NormalFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.BinaryFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.ValueFilterVisitor;

import java.util.Comparator;

/**
 * Both the left and right operators of And must satisfy the condition.
 *
 * @author CGF
 */
public class And<T extends Comparable<T>> extends BinaryFilter<T> {

    private static final long serialVersionUID = 6705254093824897938L;

    public And(Filter left, Filter right) {
        super(left, right);
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
        return "AND: ( " + left + "," + right + " )";
    }
}
