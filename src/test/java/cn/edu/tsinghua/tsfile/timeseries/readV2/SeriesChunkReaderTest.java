package cn.edu.tsinghua.tsfile.timeseries.readV2;

import cn.edu.tsinghua.tsfile.common.conf.TSFileDescriptor;
import cn.edu.tsinghua.tsfile.common.utils.Pair;
import cn.edu.tsinghua.tsfile.file.metadata.enums.CompressionTypeName;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSEncoding;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl.SeriesChunkReader;
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
    private static final int MAX_PAGE_VALUE_COUNT = 100;
    private static final int TIME_VALUE_PAIR_COUNT = 10005;
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
        Pair<List<ByteArrayInputStream>, CompressionTypeName> pagePairData = (Pair<List<ByteArrayInputStream>, CompressionTypeName>) ret.get(1);
        List<ByteArrayInputStream> pages = pagePairData.left;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (ByteArrayInputStream page : pages) {
            byte[] buf = new byte[page.available()];
            page.read(buf, 0, buf.length);
            out.write(buf, 0, buf.length);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private CompressionTypeName getCompressionTypeName() {
        Pair<List<ByteArrayInputStream>, CompressionTypeName> pagePairData = (Pair<List<ByteArrayInputStream>, CompressionTypeName>) ret.get(1);
        return pagePairData.right;
    }

    @Test
    public void readOneSeriesChunk() {
        try {
            for (int i = 0; i < TIME_VALUE_PAIR_COUNT; i++) {
                seriesWriter.write((long) i, (long) i);
            }
            ret = seriesWriter.query();
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
            fail();
        }

    }
}
