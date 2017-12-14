package cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl;

import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.BinaryFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.NormalFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.ValueFilterVisitor;

import java.io.Serializable;

/**
 * Either of the left and right operators of And must satisfy the condition.
 *
 * @author CGF
 */
public class Or<T extends Comparable<T>> extends BinaryFilter<T> implements Serializable {

    private static final long serialVersionUID = -968055896528472694L;

    public Or(Filter left, Filter right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "OR: ( " + left + "," + right + " )";
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
