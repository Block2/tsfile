package cn.edu.tsinghua.tsfile.timeseries.filter.definition;

import cn.edu.tsinghua.tsfile.timeseries.filter.definition.filterseries.FilterSeries;
import cn.edu.tsinghua.tsfile.timeseries.filter.visitorImpl.FilterVisitor;

/**
 * Filter is a top level filter abstraction.
 * Filter has two types of subclass : {@link SingleSeriesFilterExpression} and
 * {@link CrossSeriesFilterExpression}
 * Filter is a role of interviewee in visitor pattern.
 *
 * @author CGF
 */
public interface FilterExpression {
    /**
     * All subclass of accept a NormalFilterExpressionVisitor, per the visitor pattern
     * @param visitor filter visitor
     * @param <T> return type
     * @return corret filter
     */
    <T> T accept(FilterVisitor<T> visitor);

    /**
     * FilterSeries get method.
     *
     * @return FilterSeries
     */
    FilterSeries<?> getFilterSeries();
}
