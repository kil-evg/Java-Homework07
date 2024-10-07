package ait.numbers.model;

import ait.numbers.task.OneGroupSum;
import ait.utils.pool.ThreadPool;

import java.util.Arrays;

public class CustomThreadPoolGroupSum extends GroupSum{
    public CustomThreadPoolGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        //TODO use ait.utils.pool.ThreadPool
        OneGroupSum[] tasks = new OneGroupSum[numberGroups.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numberGroups[i]);
        }
        int poolSize = Runtime.getRuntime().availableProcessors();
        ThreadPool executorService = new ThreadPool(poolSize);
        for (int i = 0; i < tasks.length; i++) {
            executorService.execute(tasks[i]);
        }
        executorService.shutDown();
        try {
            executorService.joinToPool(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.stream(tasks).mapToInt(OneGroupSum::getSum).sum();
    }

}
