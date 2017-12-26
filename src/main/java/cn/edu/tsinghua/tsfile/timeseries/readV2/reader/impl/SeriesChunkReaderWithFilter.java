package cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl;

import cn.edu.tsinghua.tsfile.file.metadata.enums.CompressionTypeName;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.format.PageHeader;
import cn.edu.tsinghua.tsfile.timeseries.filter.utils.DigestForFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl.SeriesFilter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.TimeValuePairFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.impl.DigestFilterVisitor;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.visitor.impl.TimeValuePairFilterVisitorImpl;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;

import java.io.InputStream;

/**
 * Created by zhangjinrui on 2017/12/24.
 */
public class SeriesChunkReaderWithFilter extends SeriesChunkReader {

    private SeriesFilter<?> filter;
    private DigestFilterVisitor digestFilterVisitor;
    private TimeValuePairFilterVisitor<Boolean> timeValuePairFilterVisitor;

    public SeriesChunkReaderWithFilter(InputStream seriesChunkInputStream, TSDataType dataType,
                                       CompressionTypeName compressionTypeName, SeriesFilter<?> filter) {
        super(seriesChunkInputStream, dataType, compressionTypeName);
        this.filter = filter;
        this.timeValuePairFilterVisitor = new TimeValuePairFilterVisitorImpl();
        this.digestFilterVisitor = new DigestFilterVisitor();
    }

    @Override
    public boolean pageSatisfied(PageHeader pageHeader) {
        DigestForFilter timeDigest = new DigestForFilter(pageHeader.data_page_header.getMin_timestamp(),
                pageHeader.data_page_header.getMax_timestamp());
        DigestForFilter valueDigest = new DigestForFilter(
                pageHeader.data_page_header.digest.min, pageHeader.data_page_header.digest.max, dataType);
        return digestFilterVisitor.satisfy(timeDigest, valueDigest, filter.getFilter());
    }

    @Override
    public boolean timeValuePairSatisfied(TimeValuePair timeValuePair) {
        return timeValuePairFilterVisitor.satisfy(timeValuePair, filter.getFilter());
    }
}
