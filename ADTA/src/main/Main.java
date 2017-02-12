package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

public class Main {
	
	private static int PK_SIZE = 7;
	
	public static void main(String[] args) {
		System.out.println("Main Memory : " + Runtime.getRuntime().maxMemory() / 1000000 + " MB");
		System.out.println("===================");
		Scanner sc = new Scanner (System.in);
		System.out.print("Enter Employees filename:");
		String employeesFilename = sc.nextLine();
		int numOfEmployeesSublists = DivideIntoSortedSublists(employeesFilename);
		String projectsFilename = sc.nextLine();
		int numOfProjectsSublists = DivideIntoSortedSublists(projectsFilename);
	}
	
	private static int DivideIntoSortedSublists(String filename)
	{
		int numSublists = 0;
		try {
			FileReader fileReader = new FileReader(filename);
		    BufferedReader buffer = new BufferedReader(new FileReader(filename));
		    while(true) {
		    	ReadAndWriteSortedBlock(buffer, filename, numSublists);
		    	numSublists++;
		    	if(buffer.readLine() == null)
		    		break;
		    }
		    buffer.close();
		    fileReader.close();
		} catch(IOException e) {
			System.out.println("Something went wrong while reading file");
		}
		return numSublists;
	}
	
	private static void ReadAndWriteSortedBlock (BufferedReader buffer, 
												 String filename, int blockIndex) throws IOException {
		String tuple = "";
		List<Integer> pks = new ArrayList<Integer>();
		HashMap<Integer, String> pkTupleMap = new HashMap<Integer, String>();
		List<String> blockToWrite = new ArrayList<String>();
		int tmpPk;
		try {
			while((tuple = buffer.readLine()) != null) {
				tmpPk = Integer.parseInt(tuple.substring(0, PK_SIZE));
				pks.add(tmpPk);
				pkTupleMap.put(tmpPk, tuple);
				blockToWrite.add(tuple);
			}
		} catch(OutOfMemoryError e) {
		} finally {
			Collections.sort(pks);
			for(int i = 0; i > pks.size(); i++)
				blockToWrite.set(i, pkTupleMap.get(pks.get(i)) + "\n");
			pks.clear();
			pkTupleMap.clear();
			FileWriter fileWriter = new FileWriter("sublist" + blockIndex + "_" + filename);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			for(String line : blockToWrite) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();
			fileWriter.close();
		}
	}
}