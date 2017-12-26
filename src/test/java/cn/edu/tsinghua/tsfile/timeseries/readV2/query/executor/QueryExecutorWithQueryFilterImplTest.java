package cn.edu.tsinghua.tsfile.timeseries.readV2.query.executor;

import cn.edu.tsinghua.tsfile.common.conf.TSFileDescriptor;
import cn.edu.tsinghua.tsfile.common.utils.Binary;
import cn.edu.tsinghua.tsfile.common.utils.ITsRandomAccessFileReader;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.TimeFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.ValueFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.basic.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.QueryFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl.QueryFilterFactory;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl.SeriesFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.factory.FilterFactory;
import cn.edu.tsinghua.tsfile.timeseries.read.TsRandomAccessLocalFileReader;
import cn.edu.tsinghua.tsfile.timeseries.read.support.Path;
import cn.edu.tsinghua.tsfile.timeseries.readV2.TsFileGeneratorForTest;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.MetadataQuerierByFileImpl;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.SeriesChunkLoader;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.SeriesChunkLoaderImpl;
import cn.edu.tsinghua.tsfile.timeseries.readV2.query.QueryDataSet;
import cn.edu.tsinghua.tsfile.timeseries.readV2.query.QueryExpression;
import cn.edu.tsinghua.tsfile.timeseries.readV2.query.impl.QueryExecutorWithQueryFilterImpl;
import cn.edu.tsinghua.tsfile.timeseries.write.exception.WriteProcessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zhangjinrui on 2017/12/26.
 */
public class QueryExecutorWithQueryFilterImplTest {


    private static final String FILE_PATH = TsFileGeneratorForTest.outputDataFile;
    private ITsRandomAccessFileReader randomAccessFileReader;
    private MetadataQuerierByFileImpl metadataQuerierByFile;
    private SeriesChunkLoader seriesChunkLoader;
    private int rowCount = 1000;
    private QueryExecutorWithQueryFilterImpl queryExecutorWithQueryFilter;

    @Before
    public void before() throws InterruptedException, WriteProcessException, IOException {
        TSFileDescriptor.getInstance().getConfig().timeSeriesEncoder = "TS_2DIFF";
        TsFileGeneratorForTest.generateFile(rowCount, 10 * 1024 * 1024, 10000);
        randomAccessFileReader = new TsRandomAccessLocalFileReader(FILE_PATH);
        metadataQuerierByFile = new MetadataQuerierByFileImpl(randomAccessFileReader);
        seriesChunkLoader = new SeriesChunkLoaderImpl(randomAccessFileReader);
        queryExecutorWithQueryFilter = new QueryExecutorWithQueryFilterImpl(seriesChunkLoader, metadataQuerierByFile);
    }

    @After
    public void after() throws IOException {
        randomAccessFileReader.close();
        TsFileGeneratorForTest.after();
    }

    @Test
    public void query1() throws IOException {
        Filter<Integer> filter = TimeFilter.lt(1480562618100L);
        Filter<Binary> filter2 = ValueFilter.gt(new Binary("dog"));
        Filter<Integer> filter3 = FilterFactory.and(TimeFilter.gtEq(1480562618000L), TimeFilter.ltEq(1480562618100L));

//        QueryFilter queryFilter = QueryFilterFactory.and(
//                new SeriesFilter<>(new SeriesDescriptor(new Path("d1.s1"), TSDataType.INT32), filter),
//                new SeriesFilter<>(new SeriesDescriptor(new Path("d1.s4"), TSDataType.TEXT), filter2)
//        );

        QueryFilter queryFilter = new SeriesFilter<>(new SeriesDescriptor(new Path("d1.s1"), TSDataType.INT32), filter);

        QueryExpression queryExpression = QueryExpression.create()
                .addSelectedPath(new Path("d1.s1"))
                .addSelectedPath(new Path("d1.s2"))
                .addSelectedPath(new Path("d1.s4"))
                .addSelectedPath(new Path("d1.s5"))
                .setQueryFilter(queryFilter);
        QueryDataSet queryDataSet = queryExecutorWithQueryFilter.execute(queryExpression);
        while (queryDataSet.hasNext()) {
            System.out.println(queryDataSet.next());
        }

    }
}
