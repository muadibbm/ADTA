package main;

import java.util.*;
import java.io.*;
import schema.*;

public class Main {
	
	private static Scanner sc;
	
	public static void main(String[] args) {
		sc = new Scanner (System.in);
		Employee [] employees = ReadEmployees();
		Project [] projects = ReadProjects();
	}
	
	private static Employee [] ReadEmployees() {
		System.out.print("Enter Employees filename:");
		Employee [] employees = null;
		try {
		    FileReader fr = new FileReader(sc.nextLine());
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null) {
		        // TODO : read employess to array
		    }
		    br.close();
		} catch(Exception e) {
			System.out.println("Something went wrong while reading employess : " + e.getMessage());
		}
		return employees;
	}
	
	private static Project [] ReadProjects() {
		System.out.print("Enter Projects filename:");
		Project [] projects = null;
		try {
		    FileReader fr = new FileReader(sc.nextLine());
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null) {
		    	// TODO : read projects to array
		    }
		    br.close();
		} catch(Exception e) {
			System.out.println("Something went wrong while reading projects : " + e.getMessage());
		}
		return projects;
	}
}
