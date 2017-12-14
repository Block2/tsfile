package cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl;

import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.NormalFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.ValueFilterVisitor;

import java.io.Serializable;

/**
 * Not necessary. Use InvertExpressionVisitor
 *
 * @author CGF
 */
public class Not<T extends Comparable<T>> implements Filter<T>, Serializable {

    private static final long serialVersionUID = 584860326604020881L;
    private Filter that;

    public Not(Filter that) {
        this.that = that;
    }

    @Override
    public <T> T accept(NormalFilterVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <R> R accept(T value, ValueFilterVisitor<R> visitor) {
        return visitor.visit(value, this);
    }

    public Filter getFilterExpression() {
        return this.that;
    }

    @Override
    public String toString() {
        return "Not: " + that;
    }

}
