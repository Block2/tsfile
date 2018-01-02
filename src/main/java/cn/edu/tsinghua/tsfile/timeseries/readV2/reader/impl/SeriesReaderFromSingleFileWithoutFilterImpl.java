package cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl;

import cn.edu.tsinghua.tsfile.timeseries.readV2.common.MemSeriesChunk;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesChunkDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.MetadataQuerier;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.SeriesChunkLoader;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhangjinrui on 2017/12/25.
 */
public class SeriesReaderFromSingleFileWithoutFilterImpl extends SeriesReaderFromSingleFile {

    public SeriesReaderFromSingleFileWithoutFilterImpl(SeriesChunkLoader seriesChunkLoader, List<SeriesChunkDescriptor> seriesChunkDescriptorList) {
        super(seriesChunkLoader, seriesChunkDescriptorList);
    }

    protected void initSeriesChunkReader(SeriesChunkDescriptor seriesChunkDescriptor) throws IOException {
        MemSeriesChunk memSeriesChunk = seriesChunkLoader.getMemSeriesChunk(seriesChunkDescriptor);
        this.seriesChunkReader = new SeriesChunkReaderWithoutFilter(memSeriesChunk.getSeriesChunkBodyStream(),
                memSeriesChunk.getSeriesChunkDescriptor().getDataType(),
                memSeriesChunk.getSeriesChunkDescriptor().getCompressionTypeName());
    }
}
