package cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.impl;

import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl.*;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.ValueFilterVisitor;

/**
 * @author Jinrui Zhang
 */
public class ValueFilterVisitorImpl implements ValueFilterVisitor<Boolean> {

    @Override
    public <T extends Comparable<T>> Boolean satisfy(T value, Filter<T> filter) {
        return filter.accept(value, this);
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, Eq<T> eq) {
        if (eq.getValue().equals(value)) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, NotEq<T> notEq) {
        if (!notEq.getValue().equals(value)) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, LtEq<T> ltEq) {
        if (ltEq.getValue().compareTo(value) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, GtEq<T> gtEq) {
        if (gtEq.getValue().compareTo(value) <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, Gt<T> gt) {
        if (gt.getValue().compareTo(value) < 0) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, Lt<T> lt) {
        if (lt.getValue().compareTo(value) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, Not not) {
        if (!satisfy(value, not.getFilterExpression())) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, And and) {
        return satisfy(value, and.getLeft()) && satisfy(value, and.getRight());
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, Or or) {
        return satisfy(value, or.getLeft()) || satisfy(value, or.getRight());
    }

    @Override
    public <T extends Comparable<T>> Boolean visit(T value, NoRestriction noFilter) {
        return true;
    }
}
