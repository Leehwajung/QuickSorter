package com.lhj.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {
	
	private static final String InputFileName = "q_input";
	private static final String OutputFileName = "q_output";
	
	/**
	 * 1. random number 함수를 이용하여 1부터 1000 사이의 정수 데이터 100개를 생성해서 q_input이라는 파일에 저장을 하고 저장된 파일로부터 100개의 데이터를 읽어들여 quicksort 알고리즘을 실행하고 그 결과를 q_output이라는 파일에 출력
	 * @param args
	 */
	public static void main(String[] args) {
		
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(InputFileName))) {
			Random random = new Random();
			for (int i = 0; i < 100; i++) {
				dos.writeInt(random.nextInt(1000) + 1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Integer[] arr = new Integer[100];
		try (DataInputStream dis = new DataInputStream(new FileInputStream(InputFileName))) {
			for (int i = 0; i < 100; i++) {
				arr[i] = dis.readInt();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		QuickSorter sorter = QuickSorter.getInstance();
		System.out.println(Arrays.toString(arr));
		sorter.sort(arr);
		System.out.println(Arrays.toString(arr));
		
		
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OutputFileName))) {
			for (int i = 0; i < 100; i++) {
				dos.writeInt(arr[i]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
