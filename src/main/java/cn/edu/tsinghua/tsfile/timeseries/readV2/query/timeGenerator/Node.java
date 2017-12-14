package cn.edu.tsinghua.tsfile.timeseries.readV2.query.timeGenerator;

import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.SeriesReader;

/**
 * @author Jinrui Zhang
 */
public interface Node {

    boolean hasNext();

    long next();
}
