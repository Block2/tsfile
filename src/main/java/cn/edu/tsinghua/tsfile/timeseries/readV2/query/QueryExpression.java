package cn.edu.tsinghua.tsfile.timeseries.readV2.query;

import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.QueryFilter;
import cn.edu.tsinghua.tsfile.timeseries.read.support.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjinrui on 2017/12/26.
 */
public class QueryExpression {
    private List<Path> selectedSeries;
    private QueryFilter queryFilter;

    private QueryExpression() {
        selectedSeries = new ArrayList<>();
    }

    public static QueryExpression create() {
        return new QueryExpression();
    }

    public QueryExpression addSelectedPath(Path path) {
        this.selectedSeries.add(path);
        return this;
    }

    public QueryExpression setQueryFilter(QueryFilter queryFilter) {
        this.queryFilter = queryFilter;
        return this;
    }

    public QueryExpression setSelectSeries(List<Path> selectedSeries) {
        this.selectedSeries = selectedSeries;
        return this;
    }

    public QueryFilter getQueryFilter() {
        return queryFilter;
    }

    public List<Path> getSelectedSeries() {
        return selectedSeries;
    }
}
