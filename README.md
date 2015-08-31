# PH-tree

The PH-tree is a multi-dimensional indexing and storage structure.
By default it stores k-dimensional keys consisting of k integers. However, it can also be used
to efficiently store floating point values and/or ranged objects such as k-dimensional boxes.

The PH-tree was developed at ETH Zurich and first published in:
"The PH-Tree: A Space-Efficient Storage Structure and Multi-Dimensional Index", 
Tilmann Zaeschke, Christoph Zimmerli and Moira C. Norrie, 
Proceedings of Intl. Conf. on Management of Data (SIGMOD), 2014

Contact:
{zaeschke,zimmerli,norrie)@inf.ethz.ch


# Interfaces / Abstract Classes

This archive contains four variants and multiple versions of the PH-tree.

The four variants are:

PhTree          For point data with integer coordinates. This is the native storage format.
PhTreeF         For point data with floating point coordinates.
PhTreeSolid     For intervals/rectangles/boxes (solids) with integer coordinates.
PhTreeSolidF    For intervals/rectangles/boxes (solids) with floating point coordinates.

They can be created with PhTreeXYZ.create(dimensions). The default key-width is 64bit per dimension.
The old non-value API is still available in the 'test' folder.
All queries return specialised iterators that give direct access to key, value or entry.
The queryAll() methods return lists of entries and are especially useful for small result sets. 

The packages ch.ethz.globis.pht.v* contain different versions of the PH-tree. They are the actual
implementations of the four interfaces mentioned above.
A higher version number usually (not always) indicates better performance in terms of base speed,
scalability (size and dimensionality) as well as storage requirements.


# Tuning possibilities


## UPDATE

For updating the keys of entries (aka moving objects index), consider using update(). This function
is about twice as fast for small displacements and at least as fast as a put()/remove() combo.


## MEMORY

There is little point in using 32bit instead of 64bit values, because prefix sharing takes care of
unused leading bits.
For floating point values, using a 32bit float instead of 64bit float should reduce memory usage
somewhat. However it is usually better to convert floating point values to integer values by a
constant. For example multiply by 10E6 to preserve 6 digit floating point precision.
Also, chose the multiplier such that it is not higher than the precision requires.
For example, if you have a precision of 6 digits after the decimal point, then multiply all values
by 1,000,000 before casting the to (long) and adding them to the tree.


## SPEED

The following suggestions concern mostly range queries.


### Choose a type of query

- queryExtent(): Fastest option when traversing (almost) all of the tree
- query():       Fastest option for for average result size > 50 (depending on data)
- queryAll():    Fastest option for for average result size < 50 (depending on data)


### Iterators

All iterators return by default the value of a stored key/value pair. All iterators also provide
three specialised methods nextKey(), nextValue() and nextEntry() to return only the key, only the 
value (just as next()) or the combined entry object. Iterating over the entry object has the 
disadvantage that the entries need to be created and create load on the GC. However, the entries
provide easy access to the key, especially for SOLID keys.


### Data preprocessing

To improve speed, similar measures can be applied as suggested for MEMORY. For example it makes 
sense to transform values into integers by multiplication with a constant.

If data is stored as floats in IEEE representation (BiitTools.toSortableLong()), consider adding
or multiplying a constant such that the whole value domain falls into a single exponent. I.e.
shift the values such that all values have the same exponent. It can also help to shift values
such that all values have a positive sign.

For heterogenous data (combination of floats, integers, boolean, ...) consider shifting the
values such that the min/max values in each dimension have a similar distance in the integer 
representation. For example a 3D tree: [0...10][10..30][0..1000] multiply the first dimension by
100 and the second by 50, so that all dimensions have a range of about 1000.

The above is true if all dimension are queried with similar selectivity. If range queries in the
above example would mainly constrain the 2nd and 3rd dimension, then the first dimension should
NOT be multiplied. In other words, the more selective queries are on a given dimension, the more
wide should the dimension spread over the tree, i.e. the dimension should be given a higher 
multiplier.

  


