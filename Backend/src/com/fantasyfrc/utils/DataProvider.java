package com.fantasyfrc.utils;

public interface DataProvider <K, V>{
    V get(K key);
}
