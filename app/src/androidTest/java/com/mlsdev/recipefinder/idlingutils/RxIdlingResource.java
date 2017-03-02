package com.mlsdev.recipefinder.idlingutils;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.core.deps.guava.base.Function;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RxIdlingResource implements IdlingResource, Function<Runnable, Runnable> {

    private static final String RESOURCE_NAME = RxIdlingResource.class.getSimpleName();

    private static final ReentrantReadWriteLock IDLING_STATE_LOCK = new ReentrantReadWriteLock();

    // Guarded by IDLING_STATE_LOCK
    private int taskCount = 0;

    // Guarded by IDLING_STATE_LOCK
    private ResourceCallback transitionCallback;

    @Override
    public String getName() {
        return RESOURCE_NAME;
    }

    @Override
    public boolean isIdleNow() {
        boolean result;

        IDLING_STATE_LOCK.readLock().lock();
        result = taskCount == 0;
        IDLING_STATE_LOCK.readLock().unlock();

        return result;
    }

    @Override
    public void registerIdleTransitionCallback(final ResourceCallback callback) {
        IDLING_STATE_LOCK.writeLock().lock();
        this.transitionCallback = callback;
        IDLING_STATE_LOCK.writeLock().unlock();
    }

    @Override
    public Runnable apply(final Runnable runnable) {
        return new Runnable() {
            @Override
            public void run() {
                IDLING_STATE_LOCK.writeLock().lock();
                taskCount++;
                IDLING_STATE_LOCK.writeLock().unlock();

                try {
                    runnable.run();
                } finally {
                    IDLING_STATE_LOCK.writeLock().lock();

                    try {
                        taskCount--;

                        if (taskCount == 0 && transitionCallback != null) {
                            transitionCallback.onTransitionToIdle();
                        }
                    } finally {
                        IDLING_STATE_LOCK.writeLock().unlock();
                    }
                }
            }
        };
    }
}
