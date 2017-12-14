package cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl;

import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.TimeValuePairReader;

import java.io.InputStream;

/**
 * @author Jinrui Zhang
 */
public class SeriesChunkReader implements TimeValuePairReader {

    private TSDataType dataType;
    private InputStream seriesChunkInputStream;

    public SeriesChunkReader(InputStream seriesChunkInputStream, TSDataType dataType) {
        this.seriesChunkInputStream = seriesChunkInputStream;
        this.dataType = dataType;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public TimeValuePair next() {
        return null;
    }

    @Override
    public void skipCurrentTimeValuePair() {

    }
}
