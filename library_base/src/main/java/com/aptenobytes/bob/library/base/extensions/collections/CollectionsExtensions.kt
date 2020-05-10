package com.aptenobytes.bob.library.base.extensions.collections

fun <T> List<T>.toArrayList(): ArrayList<T>{
    return ArrayList(this)
}
