package cn.edu.tsinghua.tsfile.timeseries.readV2.query.timeGenerator;

import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.SeriesReader;

import java.io.IOException;

/**
 * @author Jinrui Zhang
 */
public class LeafNode implements Node {

    private SeriesReader seriesReader;

    public LeafNode(SeriesReader seriesReader) {
        this.seriesReader = seriesReader;
    }

    @Override
    public boolean hasNext() throws IOException {
        return seriesReader.hasNext();
    }

    @Override
    public long next() throws IOException {
        return seriesReader.next().getTimestamp();
    }
}
