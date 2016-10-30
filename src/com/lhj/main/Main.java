package com.lhj.main;

public class Main {
	
	public static void main(String[] args) {
		QuickSorter sorter = QuickSorter.getInstance();
		Integer[] e1 = {8, 21, 93, 9, 28};
		Integer[] e2 = {8, 21, 93, 9, 28};
		
		System.out.println(toString(e1));
		sorter.sort(e1);
		System.out.println(toString(e1));
		
		System.out.println("----------");
		
		System.out.println(toString(e2));
		sorter.sortTRO(e2);
		System.out.println(toString(e2));
	}
	
	private static <E> String toString(E[] e) {
		if (e.length == 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0;;) {
			sb.append(e[i++]);
			if (i >= e.length) {
				return sb.append(']').toString();
			}
			sb.append(',').append(' ');
		}
	}
}
