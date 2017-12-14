package cn.edu.tsinghua.tsfile.timeseries.filterV2.expression;

import java.io.Serializable;

/**
 * Definition for unary filter operations
 * @param <T> comparable data type
 * @author CGF
 */
public abstract class UnaryFilter<T extends Comparable<T>> implements Filter<T>, Serializable {

    private static final long serialVersionUID = 1431606024929453556L;
    protected final T value;

    protected UnaryFilter(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public abstract String toString();
}
