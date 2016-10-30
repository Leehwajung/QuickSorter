package com.lhj.main;

import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
		QuickSorter sorter = QuickSorter.getInstance();
		Integer[] e1 = {8, 21, 93, 9, 28, 16, 34, 26, 54, 1};
		Integer[] e2 = {8, 21, 93, 9, 28, 16, 34, 26, 54, 1};
		
		System.out.println("------------- Recursion -------------");
		System.out.println(Arrays.toString(e1));
		sorter.sort(e1);
		System.out.println(Arrays.toString(e1));
		
		System.out.println("--- Tail Recursion Optimization ---");
		System.out.println(Arrays.toString(e2));
		sorter.sortTRO(e2);
		System.out.println(Arrays.toString(e2));
	}
}
