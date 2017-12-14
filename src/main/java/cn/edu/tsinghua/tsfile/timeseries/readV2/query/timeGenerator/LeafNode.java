package cn.edu.tsinghua.tsfile.timeseries.readV2.query.timeGenerator;

import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.SeriesReader;

/**
 * @author Jinrui Zhang
 */
public class LeafNode implements Node {

    private SeriesReader seriesReader;

    public LeafNode(SeriesReader seriesReader) {
        this.seriesReader = seriesReader;
    }

    @Override
    public boolean hasNext() {
        return seriesReader.hasNext();
    }

    @Override
    public long next() {
        return seriesReader.next().getTimestamp();
    }
}
