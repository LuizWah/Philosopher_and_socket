package src;

import java.util.concurrent.locks.ReentrantLock;


public class Mordomo {
    public ReentrantLock lock = new ReentrantLock();
    public boolean[] forks;

    public synchronized boolean  tryAllocateForks(int id) {
        if(id > forks.length){
            return false;
        }
        lock.lock();
        try {
            int leftFork = id - 1;
            int rightFork = id % forks.length;

            if (!forks[leftFork] && !forks[rightFork]) {
                forks[leftFork] = true;
                forks[rightFork] = true;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public synchronized void releaseForks(int id) {
        lock.lock();
        try {
            int leftFork = id - 1;
            int rightFork = id % forks.length;
            forks[leftFork] = false;
            forks[rightFork] = false;
        } finally {
            lock.unlock();
        }
    }
}
