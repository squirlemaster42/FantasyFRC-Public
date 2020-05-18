package com.fantasyfrc.utils;

interface Cache<T, U> extends DataProvider<T, U> {
    /**
     * Returns the number of cache misses since the object's instantiation.
     * @return the number of cache misses since the object's instantiation.
     */
    int getNumMisses ();
}
