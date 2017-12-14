package cn.edu.tsinghua.tsfile.timeseries.readV2.reader;

import cn.edu.tsinghua.tsfile.timeseries.readV2.datatype.TimeValuePair;

import java.util.Iterator;

/**
 * @author Jinrui Zhang
 */
public interface TimeValuePairReader extends Iterator<TimeValuePair>{
    void skipCurrentTimeValuePair();
}

