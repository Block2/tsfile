package cn.edu.tsinghua.tsfile.timeseries.readV2.controller;

import cn.edu.tsinghua.tsfile.common.exception.cache.CacheException;
import cn.edu.tsinghua.tsfile.common.utils.ITsRandomAccessFileReader;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.MemSeriesChunk;
import cn.edu.tsinghua.tsfile.timeseries.readV2.common.SeriesChunkDescriptor;
import cn.edu.tsinghua.tsfile.timeseries.utils.cache.LRUCache;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by zhangjinrui on 2017/12/25.
 */
public class SeriesChunkLoader {
    private static final int MEMSERISCHUNK_CACHE_SIZE = 100;
    private ITsRandomAccessFileReader randomAccessFileReader;
    private LRUCache<SeriesChunkDescriptor, MemSeriesChunk> memSeriesChunkCache;

    public SeriesChunkLoader(ITsRandomAccessFileReader randomAccessFileReader) {
        this.randomAccessFileReader = randomAccessFileReader;
        memSeriesChunkCache = new LRUCache<SeriesChunkDescriptor, MemSeriesChunk>(MEMSERISCHUNK_CACHE_SIZE) {
            @Override
            public void beforeRemove(MemSeriesChunk object) throws CacheException {
                return;
            }

            @Override
            public MemSeriesChunk loadObjectByKey(SeriesChunkDescriptor key) throws CacheException {
                try {
                    return load(key);
                } catch (IOException e) {
                    throw new CacheException(e);
                }
            }
        };
    }

    public MemSeriesChunk getMemSeriesChunk(SeriesChunkDescriptor seriesChunkDescriptor) throws IOException {
        try {
            return memSeriesChunkCache.get(seriesChunkDescriptor);
        } catch (CacheException e) {
            throw new IOException(e);
        }
    }

    private MemSeriesChunk load(SeriesChunkDescriptor seriesChunkDescriptor) throws IOException {
        int seriesChunkLength = (int) seriesChunkDescriptor.getLengthOfBytes();
        byte[] buf = new byte[seriesChunkLength];
        randomAccessFileReader.seek(seriesChunkDescriptor.getOffsetInFile());
        int readLength = randomAccessFileReader.read(buf, 0, seriesChunkLength);
        if (readLength != seriesChunkLength) {
            throw new IOException("length of seriesChunk read from file is not right. Expected:" + seriesChunkLength + ". Actual: " + readLength);
        }
        return new MemSeriesChunk(seriesChunkDescriptor, new ByteArrayInputStream(buf));
    }
}
