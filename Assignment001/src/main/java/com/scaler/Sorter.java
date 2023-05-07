package com.scaler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Sorter implements Callable<List<Integer>> {

    private List<Integer> listToSort;
    private ExecutorService executorService;

    public Sorter(List<Integer> listToSort, ExecutorService executorService) {
        this.listToSort = listToSort;
        this.executorService = executorService;
    }

    public List<Integer> call() throws ExecutionException, InterruptedException {

        if(this.listToSort.size() <= 1) {
            return listToSort;
        }

        int mid = listToSort.size() >> 1;

        List<Integer> leftListToSort = new ArrayList<>();
        List<Integer> rightListToSort = new ArrayList<>();

        for(int i = 0; i < mid; i++)
                leftListToSort.add(this.listToSort.get(i));
        for(int i = mid; i < listToSort.size(); i++)
                rightListToSort.add(listToSort.get(i));

        Sorter leftSorter = new Sorter(leftListToSort, this.executorService);
        Sorter rightSorter = new Sorter(rightListToSort, this.executorService);

        Future<List<Integer>> leftSortedListFuture = this.executorService.submit(leftSorter);
        Future<List<Integer>> rightSortedListFuture = this.executorService.submit((rightSorter));

        List<Integer> leftList = leftSortedListFuture.get();
        List<Integer> rightList = rightSortedListFuture.get();

        List<Integer> sortedList = new ArrayList<>();
        int i = 0;
        int j = 0;

        while(i < leftList.size() && j < rightList.size()) {
            if(leftList.get(i) < rightList.get(j)) {
                sortedList.add(leftList.get(i));
                i++;
            } else {
                sortedList.add(rightList.get(j));
                j++;
            }
        }
        while(i < leftList.size()) {
            sortedList.add(leftList.get(i));
            i++;
        }
        while(j < rightList.size()) {
            sortedList.add(rightList.get(j));
            j++;
        }

        return sortedList;
    }
}
