package com.jamiexu.utils.android.dex.base;

import com.jamiexu.utils.android.dex.throwable.DexStringParseException;

public abstract class BaseDexParse<T> {
    public abstract T parse() throws DexStringParseException;
}
