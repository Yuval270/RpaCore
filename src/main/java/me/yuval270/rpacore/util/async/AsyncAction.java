package me.yuval270.rpacore.util.async;

@FunctionalInterface
public interface AsyncAction<T> {
    T get() throws Throwable;
}