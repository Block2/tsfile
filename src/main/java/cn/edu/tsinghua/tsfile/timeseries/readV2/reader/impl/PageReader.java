package cn.edu.tsinghua.tsfile.timeseries.readV2.reader.impl;

import cn.edu.tsinghua.tsfile.encoding.decoder.Decoder;
import cn.edu.tsinghua.tsfile.file.metadata.enums.TSDataType;
import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;
import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.TimeValuePairReader;

import java.io.InputStream;

/**
 * @author Jinrui Zhang
 */
public class PageReader implements TimeValuePairReader {

    private TSDataType dataType;
    private Decoder valueDecoder;
    private Decoder timeDecoder;

    public PageReader(InputStream page, TSDataType dataType, Decoder valueDecoder, Decoder timeDecoder) {
        this.dataType = dataType;
        this.valueDecoder = valueDecoder;
        this.timeDecoder = timeDecoder;
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
