package com.scaler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        String inputFolderPath = "C:\\Users\\Dell\\Downloads\\JavaProjects\\Assignment001\\txt";
        String outputPath = "C:\\Users\\Dell\\Downloads\\JavaProjects\\Assignment001\\txt\\out.txt";

        readAllFilesIntoOne(inputFolderPath, outputPath);
        readAndSortAndWriteFile(outputPath);
    }

    public static void readAllFilesIntoOne(String inputFolderPath, String outputPath) throws IOException {

        File folderPath = new File(inputFolderPath);
        File listOfFiles[] = folderPath.listFiles();

        FileOutputStream outputFile = new FileOutputStream(outputPath);
        DataOutputStream outputStream = new DataOutputStream(outputFile);

        StringBuffer sb = new StringBuffer();

        for(File file : listOfFiles) {
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()) {
                String input = sc.nextLine();
                sb.append(input + "\n");
            }
        }

        outputStream.write(sb.toString().getBytes());
        outputStream.flush();
        sb.delete(0, sb.length());
    }

    public static void readAndSortAndWriteFile(String filePath) throws ExecutionException, InterruptedException, IOException {

        List<Integer> list = new ArrayList<>();

        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        StringBuffer sb = new StringBuffer();

        while(sc.hasNextLine()) {
            String input = sc.nextLine();
            sb.append(input + "@");
        }

        String str = sb.toString();
        String[] stringList = str.split("@");
        sb.delete(0, sb.length());

        for(int i = 0; i < stringList.length; i++)
            list.add(Integer.valueOf(stringList[i]));

        List<Integer> sortedList = sortList(list);

        System.out.println(sortedList);

        StringBuffer sortedSb = new StringBuffer();

        for(int i = 0; i < sortedList.size(); i++)
            sortedSb.append(sortedList.get(i).toString() + "\n");

        FileOutputStream outputFile =
                new FileOutputStream("C:\\Users\\Dell\\Downloads\\JavaProjects\\Assignment001\\txt\\sortedOutput.txt");
        DataOutputStream outputStream = new DataOutputStream(outputFile);

        outputStream.write(sortedSb.toString().getBytes());
        outputStream.flush();
        sb.delete(0, sb.length());
        sortedSb.delete(0, sortedSb.length());
    }

    public static List<Integer> sortList(List<Integer> list) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Sorter sorter = new Sorter(list, executorService);

        Future<List<Integer>> sortedListFuture = executorService.submit(sorter);

        List<Integer> sortedList = sortedListFuture.get();

        executorService.close();

        return sortedList;
    }
}