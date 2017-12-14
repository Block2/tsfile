package cn.edu.tsinghua.tsfile.timeseries.readV2.query.timeGenerator;

import cn.edu.tsinghua.tsfile.timeseries.readV2.reader.SeriesReader;

import java.util.NoSuchElementException;

/**
 * @author Jinrui Zhang
 */
public class NonLeafNode implements Node {
    private NodeOperator operator;
    private Node leftChild;
    private Node rightChild;

    private boolean hasCachedNextValue;
    private long cachedNextValue;

    public NonLeafNode(Node leftChild, Node rightChild, NodeOperator operator) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.operator = operator;
    }

    @Override
    public boolean hasNext() {
        if (hasCachedNextValue) {
            return true;
        }
        return leftChild.hasNext() || rightChild.hasNext();
    }

    @Override
    public long next() {
        if (hasCachedNextValue) {
            hasCachedNextValue = false;
            return cachedNextValue;
        }
        if (leftChild.hasNext() && rightChild.hasNext()) {
            long leftChildTimestamp = leftChild.next();
            long rightChildTimestamp = rightChild.next();
            if (leftChildTimestamp > rightChildTimestamp) {
                hasCachedNextValue = true;
                cachedNextValue = leftChildTimestamp;
                return rightChildTimestamp;
            } else if (leftChildTimestamp < rightChildTimestamp) {
                hasCachedNextValue = true;
                cachedNextValue = rightChildTimestamp;
                return leftChildTimestamp;
            } else {
                return leftChildTimestamp;
            }
        } else if (leftChild.hasNext() && !rightChild.hasNext()) {
            return leftChild.next();
        } else if (!leftChild.hasNext() && rightChild.hasNext()) {
            return rightChild.next();
        } else {
            throw new NoSuchElementException("No more timestamp in current Node");
        }
    }
}
