package main;

import java.util.*;
import java.io.*;

import schema.*;

public class Main {
	
	private static Scanner sc;
	
	private static List <Employee> employees;
	private static List <Employee> projects;
	
	public static void main(String[] args) {
		sc = new Scanner (System.in);
		employees = new ArrayList <Employee>();
		ReadEmployees();
		projects = new ArrayList <Employee>();
		ReadProjects();
	}
	
	private static void ReadEmployees() {
		System.out.print("Enter Employees filename:");
		try {
		    FileReader fr = new FileReader(sc.nextLine());
		    BufferedReader br = new BufferedReader(fr);
		    Employee e;
		    String tuple;
		    int index;
		    while((tuple = br.readLine()) != null) {
		    	e = new Employee();
		    	index = 0;
		    	e.primaryKey.value = Integer.parseInt(tuple.substring(index, e.primaryKey.length));
		    	index = e.primaryKey.length;
		    	e.name.value = tuple.substring(index, index + e.name.length);
		    	index += e.name.length;
		    	e.departementId.value = Integer.parseInt(tuple.substring(index, index + e.departementId.length));
		    	index += e.departementId.length;
		    	e.socialInsuranceNumber.value = Integer.parseInt(tuple.substring(index, index + e.socialInsuranceNumber.length));
		    	index += e.socialInsuranceNumber.length;
		    	e.address.value = tuple.substring(index, tuple.length());
		    	System.out.println(e.toString());
		    	employees.add(e);
		    }
		    br.close();
		} catch(Exception e) {
			System.out.println("Something went wrong while reading employess : " + e.getMessage());
		}
	}
	
	private static void ReadProjects() {
		System.out.print("Enter Projects filename:");
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
	}
}
