
package org.encog.workbench.tabs.visualize.scatter;

import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;


public class ScatterXY extends AbstractXYDataset 
        implements XYDataset, DomainInfo, RangeInfo {

    /** The series count. */
    private static final int DEFAULT_SERIES_COUNT = 4;

    /** The item count. */
    private static final int DEFAULT_ITEM_COUNT = 40;

    /** The range. */
    private static final double DEFAULT_RANGE = 200;

    /** The x values. */
    private Double[][] xValues;

    /** The y values. */
    private Double[][] yValues;

    /** The number of series. */
    private int seriesCount;

    /** The number of items. */
    private int itemCount;

    /** The minimum domain value. */
    private Number domainMin;

    /** The maximum domain value. */
    private Number domainMax;

    /** The minimum range value. */
    private Number rangeMin;

    /** The maximum range value. */
    private Number rangeMax;

    /** The range of the domain. */
    private Range domainRange;

    /** The range. */
    private Range range;

    /**
     * Creates a sample dataset using default settings (4 series, 100 data items per series,
     * random data in the range 0 - 200).
     */
    public ScatterXY() {
        this(DEFAULT_SERIES_COUNT, DEFAULT_ITEM_COUNT);
    }

    /**
     * Creates a sample dataset.
     *
     * @param seriesCount  the number of series.
     * @param itemCount  the number of items.
     */
    public ScatterXY(int seriesCount, int itemCount) {

        this.xValues = new Double[seriesCount][itemCount];
        this.yValues = new Double[seriesCount][itemCount];
        this.seriesCount = seriesCount;
        this.itemCount = itemCount;

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (int series = 0; series < seriesCount; series++) {
            for (int item = 0; item < itemCount; item++) {

                double x = (Math.random() - 0.5) * DEFAULT_RANGE;
                this.xValues[series][item] = new Double(x);
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }

                double y = (Math.random() + 0.5) * 6 * x + x;
                this.yValues[series][item] = new Double(y);
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }

            }
        }

        this.domainMin = new Double(minX);
        this.domainMax = new Double(maxX);
        this.domainRange = new Range(minX, maxX);

        this.rangeMin = new Double(minY);
        this.rangeMax = new Double(maxY);
        this.range = new Range(minY, maxY);

    }

    /**
     * Returns the x-value for the specified series and item.  Series are numbered 0, 1, ...
     *
     * @param series  the index (zero-based) of the series.
     * @param item  the index (zero-based) of the required item.
     *
     * @return the x-value for the specified series and item.
     */
    public Number getX(int series, int item) {
        return this.xValues[series][item];
    }

    /**
     * Returns the y-value for the specified series and item.  Series are numbered 0, 1, ...
     *
     * @param series  the index (zero-based) of the series.
     * @param item  the index (zero-based) of the required item.
     *
     * @return  the y-value for the specified series and item.
     */
    public Number getY(int series, int item) {
        return this.yValues[series][item];
    }

    /**
     * Returns the number of series in the dataset.
     *
     * @return the series count.
     */
    public int getSeriesCount() {
        return this.seriesCount;
    }

    /**
     * Returns the key for the series.
     *
     * @param series  the index (zero-based) of the series.
     *
     * @return The key for the series.
     */
    public Comparable getSeriesKey(int series) {
        return "Sample " + series;
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param series  the index (zero-based) of the series.
     *
     * @return the number of items in the specified series.
     */
    public int getItemCount(int series) {
        return this.itemCount;
    }

    /**
     * Returns the minimum domain value.
     *
     * @return The minimum domain value.
     */
    public double getDomainLowerBound() {
        return this.domainMin.doubleValue();
    }

    /**
     * Returns the lower bound for the domain.
     * 
     * @param includeInterval  include the x-interval?
     * 
     * @return The lower bound.
     */
    public double getDomainLowerBound(boolean includeInterval) {
        return this.domainMin.doubleValue();
    }
    
    /**
     * Returns the maximum domain value.
     *
     * @return The maximum domain value.
     */
    public double getDomainUpperBound() {
        return this.domainMax.doubleValue();
    }

    /**
     * Returns the upper bound for the domain.
     * 
     * @param includeInterval  include the x-interval?
     * 
     * @return The upper bound.
     */
    public double getDomainUpperBound(boolean includeInterval) {
        return this.domainMax.doubleValue();
    }
    
    /**
     * Returns the range of values in the domain.
     *
     * @return the range.
     */
    public Range getDomainBounds() {
        return this.domainRange;
    }

    /**
     * Returns the bounds for the domain.
     * 
     * @param includeInterval  include the x-interval?
     * 
     * @return The bounds.
     */
    public Range getDomainBounds(boolean includeInterval) {
        return this.domainRange;
    }
    
    /**
     * Returns the range of values in the domain.
     *
     * @return the range.
     */
    public Range getDomainRange() {
        return this.domainRange;
    }

    /**
     * Returns the minimum range value.
     *
     * @return The minimum range value.
     */
    public double getRangeLowerBound() {
        return this.rangeMin.doubleValue();
    }
    
    /**
     * Returns the lower bound for the range.
     * 
     * @param includeInterval  include the y-interval?
     * 
     * @return The lower bound.
     */
    public double getRangeLowerBound(boolean includeInterval) {
        return this.rangeMin.doubleValue();
    }

    /**
     * Returns the maximum range value.
     *
     * @return The maximum range value.
     */
    public double getRangeUpperBound() {
        return this.rangeMax.doubleValue();
    }

    /**
     * Returns the upper bound for the range.
     * 
     * @param includeInterval  include the y-interval?
     * 
     * @return The upper bound.
     */
    public double getRangeUpperBound(boolean includeInterval) {
        return this.rangeMax.doubleValue();
    }
    
    /**
     * Returns the range of values in the range (y-values).
     *
     * @param includeInterval  include the y-interval?
     * 
     * @return The range.
     */
    public Range getRangeBounds(boolean includeInterval) {
        return this.range;
    }
    
    /**
     * Returns the range of y-values.
     * 
     * @return The range.
     */
    public Range getValueRange() {
        return this.range;
    }
    
    /**
     * Returns the minimum domain value.
     * 
     * @return The minimum domain value.
     */
    public Number getMinimumDomainValue() {
        return this.domainMin;
    }
    
    /**
     * Returns the maximum domain value.
     * 
     * @return The maximum domain value.
     */
    public Number getMaximumDomainValue() {
        return this.domainMax;
    }
    
    /**
     * Returns the minimum range value.
     * 
     * @return The minimum range value.
     */
    public Number getMinimumRangeValue() {
        return this.domainMin;
    }
    
    /**
     * Returns the maximum range value.
     * 
     * @return The maximum range value.
     */
    public Number getMaximumRangeValue() {
        return this.domainMax;
    }

}
