package cn.edu.tsinghua.tsfile.timeseries.readV2;

import cn.edu.tsinghua.tsfile.common.conf.TSFileDescriptor;
import cn.edu.tsinghua.tsfile.common.utils.Pair;
import cn.edu.tsinghua.tsfile.file.metadata.enums.CompressionTypeName;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSEncoding;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.TimeFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.ValueFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.basic.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl.SeriesFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.factory.FilterFactory;
import cn.edu.tsinghua.tsfile.timeseries.read.support.Path;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl.SeriesChunkReader;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl.SeriesChunkReaderByFilter;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl.SeriesChunkReaderWithoutFilter;
import cn.edu.tsinghua.tsfile.timeseries.write.desc.MeasurementDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.write.page.IPageWriter;
import cn.edu.tsinghua.tsfile.timeseries.write.page.PageWriterImpl;
import cn.edu.tsinghua.tsfile.timeseries.write.series.ISeriesWriter;
import cn.edu.tsinghua.tsfile.timeseries.write.series.SeriesWriterImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhangjinrui on 2017/12/24.
 */
public class SeriesChunkReaderTest {

    private static final int PAGE_SIZE_THRESHOLD = 100000;
    private static final int MAX_PAGE_VALUE_COUNT = 1000;
    private static final int TIME_VALUE_PAIR_COUNT = 10000000;
    private ISeriesWriter seriesWriter;
    private List<Object> ret;

    @Before
    public void before() {
        TSFileDescriptor.getInstance().getConfig().duplicateIncompletedPage = true;
        TSFileDescriptor.getInstance().getConfig().maxNumberOfPointsInPage = MAX_PAGE_VALUE_COUNT;
        MeasurementDescriptor measurementDescriptor = new MeasurementDescriptor("s1", TSDataType.INT64, TSEncoding.RLE);
        IPageWriter pageWriter = new PageWriterImpl(measurementDescriptor);
        seriesWriter = new SeriesWriterImpl("d1", measurementDescriptor, pageWriter, PAGE_SIZE_THRESHOLD);
    }

    private ByteArrayInputStream getSeriesChunk() {
        try {
            for (int i = 0; i < TIME_VALUE_PAIR_COUNT; i++) {
                seriesWriter.write((long) i, (long) i);
            }
            ret = seriesWriter.query();
            Pair<List<ByteArrayInputStream>, CompressionTypeName> pagePairData = (Pair<List<ByteArrayInputStream>, CompressionTypeName>) ret.get(1);
            List<ByteArrayInputStream> pages = pagePairData.left;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (ByteArrayInputStream page : pages) {
                byte[] buf = new byte[page.available()];
                page.read(buf, 0, buf.length);
                out.write(buf, 0, buf.length);
            }
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        return null;
    }

    private CompressionTypeName getCompressionTypeName() {
        Pair<List<ByteArrayInputStream>, CompressionTypeName> pagePairData = (Pair<List<ByteArrayInputStream>, CompressionTypeName>) ret.get(1);
        return pagePairData.right;
    }

    @Test
    public void readOneSeriesChunkWithoutFilter() {
        try {
            ByteArrayInputStream seriesChunkInputStream = getSeriesChunk();
            SeriesChunkReader seriesChunkReader = new SeriesChunkReaderWithoutFilter(seriesChunkInputStream, TSDataType.INT64, getCompressionTypeName());

            long aimedValue = 0;
            while (seriesChunkReader.hasNext()) {
                TimeValuePair timeValuePair = seriesChunkReader.next();
                assertEquals(aimedValue, timeValuePair.getTimestamp());
                assertEquals(aimedValue, timeValuePair.getValue().getLong());
                aimedValue++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void readOneSeriesChunkWithFilter() {
        try {
            long valueLeft = 5L;
            long valueRight = 10L;
            long timeLeft = 9000L;
            long timeRight = 9002L;
            Filter<Long> filter = FilterFactory.or(
                    FilterFactory.and(TimeFilter.gtEq(timeLeft), TimeFilter.ltEq(timeRight)),
                    FilterFactory.and(ValueFilter.gtEq(valueLeft), ValueFilter.ltEq(valueRight))
            );
            SeriesFilter<Long> seriesFilter = new SeriesFilter(new SeriesDescriptor(new Path("d1.s1"), TSDataType.INT64), filter);
            ByteArrayInputStream seriesChunkInputStream = getSeriesChunk();
            SeriesChunkReader seriesChunkReader = new SeriesChunkReaderByFilter(seriesChunkInputStream, TSDataType.INT64,
                    getCompressionTypeName(), seriesFilter);
            long aimedValue = valueLeft;
            while (seriesChunkReader.hasNext()) {
                TimeValuePair timeValuePair = seriesChunkReader.next();
                assertEquals(aimedValue, timeValuePair.getTimestamp());
                assertEquals(aimedValue, timeValuePair.getValue().getLong());
                if (aimedValue == valueRight) {
                    aimedValue = timeLeft;
                } else {
                    aimedValue++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
