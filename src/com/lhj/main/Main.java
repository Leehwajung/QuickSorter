/**
 * 본인의 Quicksort 프로그램에 다음과 같은 기능을 추가하시오.
 * 
 * 1. random number 함수를 이용하여 1부터 1000 사이의 정수 데이터 100개를 생성해서 q_input이라는 파일에 저장을 하고 저장된 파일로부터 100개의 데이터를 읽어들여 quicksort 알고리즘을 실행하고 그 결과를 q_output이라는 파일에 출력
 * 2. partition 주에서 데이터의 개수가 많은 것을 stack에 저장하고 partition의 길이와 내용을 출력
 * 3. 실행 중인 partition의 내용과 일련번호로 출력하는 기능
 * 4. partition의 개수가 10개 이하인 경우에는 insertion sort를 이용하여 sort를 하는 기능
 * 5. pivot 원소를 median 값으로 선택하여 실행되도록 하는 기능
 */

package com.lhj.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {
	
	private static final String InputFileName = "q_input";
	private static final String OutputFileName = "q_output";
	
	/**
	 * 1. random number 함수를 이용하여 1부터 1000 사이의 정수 데이터 100개를 생성해서 
	 * q_input이라는 파일에 저장을 하고 저장된 파일로부터 100개의 데이터를 읽어들여 
	 * quicksort 알고리즘을 실행하고 그 결과를 q_output이라는 파일에 출력
	 * @param args  
	 */
	public static void main(String[] args) {
		
		// random number 함수를 이용하여 1부터 1000 사이의 정수 데이터 100개를 생성해서 q_input이라는 파일에 저장
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(InputFileName))) {
			Random random = new Random();
			for (int i = 0; i < 100; i++) {
				dos.writeInt(random.nextInt(1000) + 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 저장된 파일로부터 100개의 데이터를 읽어들임
		Integer[] arr = new Integer[100];
		try (DataInputStream dis = new DataInputStream(new FileInputStream(InputFileName))) {
			for (int i = 0; i < 100; i++) {
				arr[i] = dis.readInt();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// quicksort 알고리즘을 실행
		QuickSorter sorter = QuickSorter.getInstance();
		System.out.println(Arrays.toString(arr));
		sorter.sortUsingStack(arr, true);
		System.out.println(Arrays.toString(arr));
		
		// 그 결과를 q_output이라는 파일에 출력
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OutputFileName))) {
			for (int i = 0; i < 100; i++) {
				dos.writeInt(arr[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
