package ru.iukhimenko.transportsystem.autotesting.core;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ObjectPool<T> {
    protected ConcurrentLinkedQueue<T> pool;

    public ObjectPool(int minNumberOfElements) {
        initializePool(minNumberOfElements);
    }

    public void initializePool(int minNumberOfElements) {
        pool = getExistingObjects();
        if (pool.isEmpty()) {
            for (int i = 0; i < minNumberOfElements; i++) {
                pool.add(create());
            }
        }
        else if (pool.size() < minNumberOfElements) {
            for (int i = 0; i < minNumberOfElements - pool.size(); i++) {
                pool.add(create());
            }
        }
    }

    public T get() {
        T object = pool.poll();
        if (object == null) {
            object = create();
        }
        return object;
    }

    protected abstract ConcurrentLinkedQueue<T> getExistingObjects();
    protected abstract T create();
}
