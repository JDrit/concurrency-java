package net.batchik.jd.heap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Heap<T extends Comparable> {

    void push(@Nonnull final T elem);

    @Nullable
    T pop();

    @Nullable
    T peek();

    int size();


}
