package cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl;

import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesChunkDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.readV2.controller.SeriesChunkLoader;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.SeriesReader;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhangjinrui on 2017/12/25.
 */
public abstract class SeriesReaderFromSingleFile implements SeriesReader {

    protected SeriesChunkLoader seriesChunkLoader;
    private List<SeriesChunkDescriptor> seriesChunkDescriptorList;

    protected SeriesChunkReader seriesChunkReader;
    private boolean seriesChunkReaderInitialized;
    private int currentReadSeriesChunkIndex;

    public SeriesReaderFromSingleFile(SeriesChunkLoader seriesChunkLoader, List<SeriesChunkDescriptor> seriesChunkDescriptorList) {
        this.seriesChunkLoader = seriesChunkLoader;
        this.seriesChunkDescriptorList = seriesChunkDescriptorList;
        this.currentReadSeriesChunkIndex = -1;
        this.seriesChunkReaderInitialized = false;
    }

    @Override
    public boolean hasNext() throws IOException {
        while ((currentReadSeriesChunkIndex + 1) < seriesChunkDescriptorList.size()) {
            if (!seriesChunkReaderInitialized) {
                initSeriesChunkReader(seriesChunkDescriptorList.get(++currentReadSeriesChunkIndex));
                seriesChunkReaderInitialized = true;
            }
            if (seriesChunkReader.hasNext()) {
                return true;
            } else {
                seriesChunkReaderInitialized = false;
            }
        }
        return false;
    }

    @Override
    public TimeValuePair next() throws IOException {
        return seriesChunkReader.next();
    }

    @Override
    public void skipCurrentTimeValuePair() throws IOException {
        next();
    }

    protected abstract void initSeriesChunkReader(SeriesChunkDescriptor seriesChunkDescriptor) throws IOException;
}
