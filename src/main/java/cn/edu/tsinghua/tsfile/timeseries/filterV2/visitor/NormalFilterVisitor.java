package cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor;


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
public interface NormalFilterVisitor<R> {

    <T extends Comparable<T>> R visit(Eq<T> eq);

    <T extends Comparable<T>> R visit(NotEq<T> notEq);

    <T extends Comparable<T>> R visit(LtEq<T> ltEq);

    <T extends Comparable<T>> R visit(GtEq<T> gtEq);

    <T extends Comparable<T>> R visit(Gt<T> gtEq);

    <T extends Comparable<T>> R visit(Lt<T> gtEq);

    R visit(Not not);

    R visit(And and);

    R visit(Or or);

    R visit(NoRestriction noFilter);
}
