/*
 * Copyright 2011-2015 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 */
package ch.ethz.globis.pht;

import java.util.List;

import ch.ethz.globis.pht.util.PhIteratorBase;
import ch.ethz.globis.pht.util.PhMapper;
import ch.ethz.globis.pht.util.PhTreeQStats;
import ch.ethz.globis.pht.v8.PhTree8;

/**
 * k-dimensional index (quad-/oct-/n-tree).
 * Supports key/value pairs.
 *
 *
 * @author ztilmann (Tilmann Zaeschke)
 *
 */
public interface PhTree<T> {

    
    public int size();
    
    public int getNodeCount();
    
    public PhTreeQStats getQuality();
    
    public abstract PhTreeHelper.Stats getStats();

    public abstract PhTreeHelper.Stats getStatsIdealNoNode();
    

    /**
     * Insert an entry associated with a k dimensional key.
     * @param key
     * @param value
     * @return the previously associated value or {@code null} if the key was found
     */
    public abstract T put(long[] key, T value);

    public abstract boolean contains(long ... key);

    public abstract T get(long ... key);

    
    /**
     * Remove the entry associated with a k dimensional key.
     * @param key
     * @return the associated value or {@code null} if the key was found
     */
    public abstract T remove(long... key);

    public abstract String toStringPlain();
    
    public abstract String toStringTree();
    
	public abstract PhIterator<T> queryExtent();


	/**
	 * Performs a range query. The parameters are the min and max keys.
	 * @param min
	 * @param max
	 * @return Result iterator.
	 */
	public abstract PhIterator<T> query(long[] min, long[] max);

	public abstract int getDIM();

	public abstract int getDEPTH();

	/**
	 * Locate nearest neighbours for a given point in space.
	 * @param nMin number of entries to be returned. More entries may be returned with several have
	 * 				the same distance.
	 * @param key
	 * @return List of neighbours.
	 */
	public abstract List<long[]> nearestNeighbour(int nMin, long... key);
	
	/**
	 * Locate nearest neighbours for a given point in space.
	 * @param nMin number of entries to be returned. More entries may be returned with several have
	 * 				the same distance.
	 * @param dist the distance function, can be {@code null}. The default is {@link PhDistanceL}.
	 * @param dims the dimension filter, can be {@code null}
	 * @param key
	 * @return List of neighbours.
	 */
	public abstract List<long[]> nearestNeighbour(int nMin, PhDistance dist, PhDimFilter dims, 
			long... key);

	/**
	 * Update the key of an entry. Update may fail if the old key does not exist, or if the new
	 * key already exists.
	 * @param oldKey
	 * @param newKey
	 * @return the value (can be {@code null}) associated with the updated key if the key could be 
	 * updated, otherwise {@code null}.
	 */
	public T update(long[] oldKey, long[] newKey);

	/**
	 * Same as {@link #queryIntersect(double[], double[])}, except that it returns a list
	 * instead of an iterator. This may be faster for small result sets. 
	 * @param lower
	 * @param upper
	 * @return List of query results
	 */
	public List<PhEntry<T>> queryAll(long[] min, long[] max);
	
	public <R> List<R> queryAll(long[] min, long[] max, int maxResults, 
			PhPredicate filter, PhMapper<T, R> mapper);
	
	/**
	 * Create a new tree with the specified number of dimensions.
	 * 
	 * @param dim number of dimensions
	 */
    public static <T> PhTree<T> create(int dim) {
    	return new PhTree8<T>(dim, 64);
    }

	/**
	 * Create a new tree with the specified number of dimensions.
	 * 
	 * @param dim number of dimensions
	 * @param depth the number of bits per dimension (1..64)
	 */
    public static <T> PhTree<T> create(int dim, int depth) {
    	return new PhTree8<T>(dim, depth);
    }

    public static interface PhIterator<T> extends PhIteratorBase<long[], T, PhEntry<T>> {}

    /**
     * Clear the tree.
     */
	void clear();
}

