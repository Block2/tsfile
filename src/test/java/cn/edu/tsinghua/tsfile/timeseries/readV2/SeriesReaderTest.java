package cn.edu.tsinghua.tsfile.timeseries.readV2;

import cn.edu.tsinghua.tsfile.common.utils.ITsRandomAccessFileReader;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.TimeFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.ValueFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.basic.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl.SeriesFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.factory.FilterFactory;
import cn.edu.tsinghua.tsfile.timeseries.read.TsRandomAccessLocalFileReader;
import cn.edu.tsinghua.tsfile.timeseries.read.support.Path;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesChunkDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.MetadataQuerierByFileImpl;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.SeriesChunkLoader;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.SeriesReader;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl.SeriesReaderFromSingleFileWithFilterImpl;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl.SeriesReaderFromSingleFileWithoutFilterImpl;
import cn.edu.tsinghua.tsfile.timeseries.write.exception.WriteProcessException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhangjinrui on 2017/12/25.
 */
public class SeriesReaderTest {

    private static final String FILE_PATH = TsFileGeneratorForTest.outputDataFile;
    private ITsRandomAccessFileReader randomAccessFileReader;
    private MetadataQuerierByFileImpl metadataQuerierByFile;

    @Before
    public void before() throws InterruptedException, WriteProcessException, IOException {
        TsFileGeneratorForTest.generateFile(1000000, 10 * 1024 * 1024, 10000);
        randomAccessFileReader = new TsRandomAccessLocalFileReader(FILE_PATH);
        metadataQuerierByFile = new MetadataQuerierByFileImpl(randomAccessFileReader);
    }

    @After
    public void after() throws IOException {
        randomAccessFileReader.close();
        TsFileGeneratorForTest.after();
    }

    @Test
    public void readTest() throws IOException {
        int count = 0;
        SeriesChunkLoader seriesChunkLoader = new SeriesChunkLoader(randomAccessFileReader);
        List<SeriesChunkDescriptor> seriesChunkDescriptorList = metadataQuerierByFile.getSeriesChunkDescriptorList(new Path("d1.s1"));

        SeriesReader seriesReader = new SeriesReaderFromSingleFileWithoutFilterImpl(seriesChunkLoader, seriesChunkDescriptorList);
        long startTime = TsFileGeneratorForTest.START_TIMESTAMP;
        long startTimestamp = System.currentTimeMillis();
        while (seriesReader.hasNext()) {
            TimeValuePair timeValuePair = seriesReader.next();
            Assert.assertEquals(startTime, timeValuePair.getTimestamp());
            startTime++;
        }
        long endTimestamp = System.currentTimeMillis();
        System.out.println("SeriesReadTest. [Time used]: " + (endTimestamp - startTimestamp) +
                " ms. [Read Count]: " + (startTime - TsFileGeneratorForTest.START_TIMESTAMP));


        seriesChunkDescriptorList = metadataQuerierByFile.getSeriesChunkDescriptorList(new Path("d1.s4"));
        seriesReader = new SeriesReaderFromSingleFileWithoutFilterImpl(seriesChunkLoader, seriesChunkDescriptorList);
        count = 0;
        startTimestamp = System.currentTimeMillis();
        while (seriesReader.hasNext()) {
            TimeValuePair timeValuePair = seriesReader.next();
            startTime++;
            count ++;
        }
        endTimestamp = System.currentTimeMillis();
        System.out.println("SeriesReadTest. [Time used]: " + (endTimestamp - startTimestamp) +
                " ms. [Read Count]: " + count);
    }

    @Test
    public void readWithFilterTest() throws IOException {
        SeriesChunkLoader seriesChunkLoader = new SeriesChunkLoader(randomAccessFileReader);
        List<SeriesChunkDescriptor> seriesChunkDescriptorList = metadataQuerierByFile.getSeriesChunkDescriptorList(new Path("d1.s1"));

        Filter<Integer> filter = new FilterFactory().or(
                FilterFactory.and(TimeFilter.gt(1480563570029L), TimeFilter.lt(1480563570033L)),
                FilterFactory.and(ValueFilter.gtEq(9520331), ValueFilter.ltEq(9520361)));
        SeriesFilter<Integer> seriesFilter = new SeriesFilter<>(new SeriesDescriptor(new Path("d1.s1"), TSDataType.INT32), filter);
        SeriesReader seriesReader = new SeriesReaderFromSingleFileWithFilterImpl(seriesChunkLoader, seriesChunkDescriptorList, seriesFilter);
        long startTimestamp = System.currentTimeMillis();

        int count = 0;
        long aimedTimestamp = 1480563570030L;
        while (seriesReader.hasNext()) {
            TimeValuePair timeValuePair = seriesReader.next();
            count++;
            Assert.assertEquals(aimedTimestamp++, timeValuePair.getTimestamp());
        }
        long endTimestamp = System.currentTimeMillis();
        System.out.println("SeriesReadWithFilterTest. [Time used]: " + (endTimestamp - startTimestamp) +
                " ms. [Read Count]: " + count);
    }
}
