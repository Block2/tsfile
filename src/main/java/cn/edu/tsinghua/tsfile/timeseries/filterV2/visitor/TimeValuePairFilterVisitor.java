package cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor;


import cn.edu.tsinghua.tsfile.timeseries.filterV2.basic.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.operator.*;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;

/**
 * NormalFilterExpressionVisitor is implemented by visitor pattern.
 * Implemented using visitor pattern.
 *
 * A FilterVistor must visit all these methods below, per visitor design pattern.
 * And a Filter just need implements an accept() method.
 *
 * @author CGF
 */
public interface TimeValuePairFilterVisitor<R> {

    <T extends Comparable<T>> R satisfy(TimeValuePair value, Filter<T> filter);

    <T extends Comparable<T>> R visit(TimeValuePair value, Eq<T> eq);

    <T extends Comparable<T>> R visit(TimeValuePair value, NotEq<T> notEq);

    <T extends Comparable<T>> R visit(TimeValuePair value, LtEq<T> ltEq);

    <T extends Comparable<T>> R visit(TimeValuePair value, GtEq<T> gtEq);

    <T extends Comparable<T>> R visit(TimeValuePair value, Gt<T> gt);

    <T extends Comparable<T>> R visit(TimeValuePair value, Lt<T> lt);

    <T extends Comparable<T>> R visit(TimeValuePair value, Not<T> not);

    <T extends Comparable<T>> R visit(TimeValuePair value, And<T> and);

    <T extends Comparable<T>> R visit(TimeValuePair value, Or<T> or);

    <T extends Comparable<T>> R visit(TimeValuePair value, NoRestriction<T> noFilter);
}
