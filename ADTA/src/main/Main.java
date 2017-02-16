package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

public class Main {
	
	private static int PK_SIZE = 7;
	private static int NumTotalTuples;
	private static int numOfIOs;
	private static long sortTime;
	
	public static void main(String[] args) {
		System.out.println("Main Memory : " + Runtime.getRuntime().maxMemory() / 1000000 + " MB");
		System.out.println("===================");
		
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Employees filename:");
			String employeesFile = sc.nextLine();
			SortAndMerge(employeesFile);
			System.gc();
			System.out.print("Enter Projects filename:");
			String projectsFile = sc.nextLine();
			SortAndMerge(projectsFile);
			System.gc();
			Join(employeesFile, projectsFile);
			sc.close();
		}catch(IOException e) {
			System.out.println("something went wrong while reading file");
			System.exit(0);
		}
	}
	
	private static String SortAndMerge(String filename) throws IOException {
		System.out.println("Reading file " + filename + " into sorted sublists ...");
		sortTime = 0;
		long _startTime = System.nanoTime() ;
		int numOfEmployeesSublists = DivideIntoSortedSublists(filename);
		System.out.println ( "Sorting operations took " + sortTime + " seconds");
		System.out.println ( "Pass 1 : Number of disk I/O is " + numOfIOs);
		System.out.println ( "Pass 1 : Total operations took " + (float)( System.nanoTime() - _startTime ) / 1000000000 + " seconds to complete" ) ;
		System.out.println(numOfEmployeesSublists + " sublists created");
		System.out.println("Merging sorted sublists for " + filename + " ...");
		sortTime = 0;
		_startTime = System.nanoTime() ;
		MergeSublistsIntoSortedRelation(filename, numOfEmployeesSublists);
		System.out.println ( "Sorting operations took " + sortTime + " seconds");
		System.out.println ( "Pass 2 : Number of disk I/O is " + numOfIOs);
		System.out.println ( "Pass 2 : Total operations took " + (float)( System.nanoTime() - _startTime ) / 1000000000 + " seconds to complete" ) ;
		return filename;
	}
	
	private static void Join(String relationFile1, String relationFile2) throws IOException {
		System.out.println("Joining relations in files " + relationFile1 + " and " + relationFile2);
		FileReader fileReader1 = new FileReader("output" + "_" + relationFile1);
		FileReader fileReader2 = new FileReader("output" + "_" + relationFile2);
	    BufferedReader buffer1 = new BufferedReader(fileReader1);
	    BufferedReader buffer2 = new BufferedReader(fileReader2);
	    FileWriter fileWriter = new FileWriter("final_join.txt");
		BufferedWriter writer = new BufferedWriter(fileWriter);
		String tuple1 = buffer1.readLine();
		String tuple2 = buffer2.readLine();
		numOfIOs = 2;
		int numOfTuples = 0;
		while(true) {
			if(tuple1 == null || tuple2 == null) break;
			if(Integer.parseInt(tuple1.substring(0, PK_SIZE)) ==
					Integer.parseInt(tuple2.substring(0, PK_SIZE))) {
				writer.write(tuple1 + tuple2.substring(PK_SIZE, tuple2.length()));
				writer.newLine();
				tuple2 = buffer2.readLine();
				numOfIOs++;
				numOfTuples++;
			}
			tuple1 = buffer1.readLine();
			numOfIOs++;
		}
		writer.close();
		fileWriter.close();
		buffer1.close();
		buffer2.close();
		fileReader1.close();
		fileReader2.close();
		System.out.println ( "Join operation : Number of disk I/O is " + numOfIOs);
		System.out.println("Total number of joined relation tuples is " + numOfTuples);
	}
	
	private static int DivideIntoSortedSublists(String filename) throws IOException
	{
		NumTotalTuples = 0;
		int numSublists = 0;
		FileReader fileReader = new FileReader(filename);
	    BufferedReader buffer = new BufferedReader(fileReader);
	    String lastTuple;
		while(true) {
			lastTuple = ReadAndWriteSortedBlock(buffer, filename, numSublists);
		    numSublists++;
		    if(lastTuple == null)
		    	break;
		}
		buffer.close();
		fileReader.close();
		return numSublists;
	}
	
	private static String ReadAndWriteSortedBlock (BufferedReader buffer, 
												 String filename, int blockIndex) throws IOException {
		String tuple = "";
		List<Integer> pks = new ArrayList<Integer>();
		HashMap<Integer, String> pkTupleMap = new HashMap<Integer, String>();
		List<String> blockToWrite = new ArrayList<String>();
		int tmpPk = 0;
		try {
			while((tuple = buffer.readLine()) != null) {
				if(tuple == null || (tuple.trim()).equals("")) continue;
				numOfIOs++;
				tmpPk = Integer.parseInt(tuple.substring(0, PK_SIZE));
				pks.add(tmpPk);
				pkTupleMap.put(tmpPk, tuple);
				blockToWrite.add("");
			}
		} catch(OutOfMemoryError e) {
		} finally {
			long _startTime = System.nanoTime() ;
			Collections.sort(pks);
			sortTime += (float)( System.nanoTime() - _startTime ) / 1000000000;
			for(int i = 0; i < blockToWrite.size(); i++) {
				blockToWrite.set(i, pkTupleMap.get(pks.get(i)));
			}
			pks.clear();
			pkTupleMap.clear();
			FileWriter fileWriter = new FileWriter("sublist" + blockIndex + "_" + filename);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			for(String line : blockToWrite) {
				if((line.trim()).equals("")) continue;
				writer.write(line);
				writer.newLine();
				NumTotalTuples++;
				numOfIOs++;
			}
			// add the lost tuple
			if(tuple != null && !(tuple.trim()).equals("")) {
				writer.write(tuple);
				writer.newLine();
				NumTotalTuples++;
				numOfIOs++;
			}
			writer.close();
			fileWriter.close();
		}
		return tuple;
	}
	
	private static void MergeSublistsIntoSortedRelation(String filename, int numOfSublists) throws IOException {
		int numTuples = 0;
		FileWriter fileWriter = new FileWriter("output" + "_" + filename, true);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		FileReader [] fileReaders = new FileReader[numOfSublists];
	    BufferedReader [] buffers = new BufferedReader[numOfSublists];
	    for(int i = 0; i < numOfSublists; i++){
	    	fileReaders[i] = new FileReader("sublist" + i + "_" + filename);
	    	buffers[i] = new BufferedReader(fileReaders[i]);
	    }
	    int [] pks = new int [numOfSublists];
	    for(int i = 0; i < numOfSublists; i++)
	    	pks[i] = 0; // 0 identifies as empty
	    String [] tuples = new String[numOfSublists];
	    String tuple;
	    int minIndex;
	    List<String> blockToWrite = new ArrayList<String>();
		while(numTuples < NumTotalTuples) {
			try {
				for(int i = 0; i < numOfSublists; i++) {
					if(pks[i] == 0) {
						numOfIOs++;
						tuple = buffers[i].readLine();
						if(tuple == null || (tuple.trim()).equals("")) continue;
						pks[i] = Integer.parseInt(tuple.substring(0, PK_SIZE));
						tuples[i] = tuple;
					}
				}
				long _startTime = System.nanoTime() ;
				minIndex = ExtractMinIndex(pks);
				sortTime += (float)( System.nanoTime() - _startTime ) / 1000000000;
				if(pks[minIndex] != 0) {
					blockToWrite.add(tuples[minIndex]);
					pks[minIndex] = 0;
				}
			} catch(OutOfMemoryError e) {
			} finally {
				for(String line : blockToWrite) {
					if((line.trim()).equals("")) continue;
					numTuples++;
					writer.write(line);
					writer.newLine();
					numOfIOs++;
				}
				blockToWrite.clear();
			}
		}
		for(int i = 0; i < numOfSublists; i++){
			buffers[i].close();
	    	fileReaders[i].close();
	    }
		writer.close();
		fileWriter.close();
	}
	
	private static int ExtractMinIndex(int [] pks) {
		int min = Integer.MAX_VALUE;
		int minIndex = 0;
		for(int i = 0; i < pks.length; i++) {
			if(pks[i] != 0 && pks[i] < min) {
				min = pks[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
}