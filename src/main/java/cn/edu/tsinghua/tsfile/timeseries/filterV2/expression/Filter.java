package cn.edu.tsinghua.tsfile.timeseries.filterV2.expression;


import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.NormalFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.ValueFilterVisitor;

/**
 * Filter is a top level filter abstraction.
 * Filter has two types of implementations : {@link BinaryFilter} and
 * {@link UnaryFilter}
 * Filter is a role of interviewee in visitor pattern.
 *
 * @author CGF
 */
public interface Filter<T extends Comparable<T>> {
    /**
     * All subclass of accept a NormalFilterVisitor, per the visitor pattern
     * @param visitor filter visitor
     * @param <R> return type
     * @return corret filter
     */
    <R> R accept(NormalFilterVisitor<R> visitor);

    <R> R accept(T value, ValueFilterVisitor<R> visitor);
}
