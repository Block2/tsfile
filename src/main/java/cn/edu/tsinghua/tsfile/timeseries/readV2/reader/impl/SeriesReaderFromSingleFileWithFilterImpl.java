package cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl;

import cn.edu.tsinghua.tsfile.timeseries.filterV2.basic.Filter;
import cn.edu.tsinghua.tsfile.timeseries.filterV2.expression.impl.SeriesFilter;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.MemSeriesChunk;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesChunkDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.SeriesChunkLoader;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.SeriesChunkLoaderImpl;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhangjinrui on 2017/12/25.
 */
public class SeriesReaderFromSingleFileWithFilterImpl extends SeriesReaderFromSingleFile {

    private Filter<?> filter;

    public SeriesReaderFromSingleFileWithFilterImpl(SeriesChunkLoader seriesChunkLoader
            , List<SeriesChunkDescriptor> seriesChunkDescriptorList, Filter<?> filter) {
        super(seriesChunkLoader, seriesChunkDescriptorList);
        this.filter = filter;
    }

    protected void initSeriesChunkReader(SeriesChunkDescriptor seriesChunkDescriptor) throws IOException {
        MemSeriesChunk memSeriesChunk = seriesChunkLoader.getMemSeriesChunk(seriesChunkDescriptor);
        this.seriesChunkReader = new SeriesChunkReaderWithFilterImpl(memSeriesChunk.getSeriesChunkBodyStream(),
                memSeriesChunk.getSeriesChunkDescriptor().getDataType(),
                memSeriesChunk.getSeriesChunkDescriptor().getCompressionTypeName(),
                filter);
    }
}
