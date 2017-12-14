package cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor;


import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl.*;

/**
 * NormalFilterVisitor is implemented by visitor pattern.
 * Implemented using visitor pattern.
 *
 * A FilterVistor must visit all these methods below, per visitor design pattern.
 * And a Filter just need implements an accept() method.
 *
 * @author CGF
 */
public interface ValueFilterVisitor<R> {

    <T extends Comparable<T>> R satisfy(T value, Filter<T> filter);

    <T extends Comparable<T>> R visit(T value, Eq<T> eq);

    <T extends Comparable<T>> R visit(T value, NotEq<T> notEq);

    <T extends Comparable<T>> R visit(T value, LtEq<T> ltEq);

    <T extends Comparable<T>> R visit(T value, GtEq<T> gtEq);

    <T extends Comparable<T>> R visit(T value, Gt<T> gt);

    <T extends Comparable<T>> R visit(T value, Lt<T> lt);

    <T extends Comparable<T>> R visit(T value, Not not);

    <T extends Comparable<T>> R visit(T value, And and);

    <T extends Comparable<T>> R visit(T value, Or or);

    <T extends Comparable<T>> R visit(T value, NoRestriction noFilter);
}
