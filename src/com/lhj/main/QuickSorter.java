package com.lhj.main;

import java.util.LinkedList;

public final class QuickSorter {
	
	private static QuickSorter instance = null;
	private static final int InitSmallSize = 9;      // 개수의 차와 1 차이나게 하여, 결과적으로 10 이하인 경우에 insertion sort를 하게 함
	private static int smallSize = InitSmallSize;    // Small Sort를 하는 서브 배열의 하한 크기
	private LinkedList<Partition> stack;
	
	/**
	 * Private Constructor for Singleton
	 */
	private QuickSorter() {
		stack = new LinkedList<>();
	}
	
	/**
	 * Singleton
	 * @return 유일한 인스턴스
	 */
	public static final QuickSorter getInstance() {
		if (instance == null) {
			instance = new QuickSorter();
		}
		return instance;
	}
	
	/**
	 * Small Sort를 수행할지 설정
	 * @param choose Small Sort 수행 여부
	 */
	public final void setSmallSort(boolean choose) {
		if (choose) {
			smallSize = InitSmallSize;
		} else {
			smallSize = 0;
		}
	}
	
	/**
	 * Quick Sort (Stack)<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열.
	 */
	public final <E extends Comparable<E>> void sort(E[] e) {
		stack.push(new Partition(0, e.length - 1));
		while (!stack.isEmpty()) {
			sort(e, stack.pop());
		}
	}
	
	/**
	 * 2. Partition 중에서 데이터의 개수가 많은 것을 stack에 저장하고 partition의 길이와 내용을 출력<br>
	 * 3. 실행 중인 partition의 내용과 일련번호로 출력하는 기능<br>
	 * Quick Sort (Stack)<br>
	 * 사후 조건: 원소 e[first], ..., e[last]가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first <= i <= last 상에서 정의됨.
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 */
	private final <E extends Comparable<E>> void sort(E[] e, Partition origPart) {
		System.out.println("3. 실행 중인 Partition의 일련번호와 내용: " + origPart.getId() + ", " + origPart.toString(e));
		Partition bigPart = null;
		int first = origPart.getFirst(), last = origPart.getLast();
		while (last - first > smallSize) {
			int pivotIndex = selectPivotIndex(first, last);
			E pivot = e[pivotIndex];
			e[pivotIndex] = e[first];
			e[first] = pivot;   // 첫 위치로 이동
			
			int splitPoint = partition(e, pivot, first, last);
			e[splitPoint] = pivot;
			if (splitPoint > (first + last) / 2) {   // 데이터가 많은 파티션 고르기
				bigPart = new Partition(first, splitPoint - 1);
				first = splitPoint + 1;
			} else {
				bigPart = new Partition(splitPoint + 1, last);
				last = splitPoint - 1;
			}
			stack.push(bigPart);
			System.out.println("2.Stack에 Push한 개수가 많은 Partition의 길이와 내용: " + bigPart.getSize() + ", " + bigPart.toString(e));
			// first, last를 위한 loop를 계속함.
		}
		if (last - first <= smallSize) {    // Small Sort (Insertion Sort)
			smallSort(e, first, last);
		}
	}
	
	/**
	 * Quick Sort (재귀)<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열.
	 */
	public final <E extends Comparable<E>> void sortR(E[] e) {
		sortR(e, 0, e.length - 1);
	}
	
	/**
	 * Quick Sort (재귀)<br>
	 * 사후 조건: 원소 e[first], ..., e[last]가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first <= i <= last 상에서 정의됨.
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 */
	public final <E extends Comparable<E>> void sortR(E[] e, int first, int last) {
		if (last - first > smallSize) {
			int pivotIndex = selectPivotIndex(first, last);
			E pivot = e[pivotIndex];
			e[pivotIndex] = e[first];
			e[first] = pivot;   // 첫 위치로 이동
			
			int splitPoint = partition(e, pivot, first, last);
			e[splitPoint] = pivot;
			sortR(e, first, splitPoint - 1);
			sortR(e, splitPoint + 1, last);
		} else {    // Small Sort (Insertion Sort)
			smallSort(e, first, last);
		}
	}
	
	/**
	 * 5. pivot 원소를 median 값으로 선택하여 실행되도록 하는 기능
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 * @return 중간 값
	 */
	public final int selectPivotIndex(int first, int last) {
		return (last + first) / 2;  // 중간 값 선택
		//return new Random().nextInt(last - first + 1) + first;
	}
	
	/**
	 * Partition<br>
	 * 사전 조건: first < last를 만족함.<br>
	 * 사후 조건: first + 1, ..., last까지의 원소들이 두 부분 범위로 재배열되며, 다음과 같은 특징이 있음.<br>
	 *     1. e[first], ..., e[splitPoint - 1]: pivot 보다 앞인 원소. (작음)<br>
	 *     2. e[splitPoint + 1], ..., e[last]: pivot 보다 뒤인 원소. (큼)<br>
	 *     first <= splitPoint <= last 이며, e[splitPoint]는 빈칸.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first + 1 <= i <= last 상에서 정의됨.
	 * @param pivot 피벗.
	 * @param first 시작 인덱스.
	 * @param last  끝 인덱스.
	 * @return Split Point
	 */
	private final <E extends Comparable<E>> int partition(E[] e, E pivot, int first, int last) {
		int low = first;
		int high = last;
		while (low < high) {
			int highVac = extendedLargeRegion(e, pivot, low, high);
			int lowVac = extendedSmallRegion(e, pivot, low + 1, highVac);
			low = lowVac;
			high = highVac - 1;
		}
		return low; // Split Point
	}
	
	/**
	 * 배열의 뒤에서 앞으로 이동하며 확인. (t)<br>
	 * 사후 조건: e[lowVac + 1], ..., e[high]의 우측 원소들 중에서, pivot보다 앞인 (작은) 원소가 e[lowVac]으로 옮겨짐.
	 * @param <E>       순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e         정렬할 배열.
	 * @param pivot     피벗.
	 * @param lowVac    범위의 하한.
	 * @param high      범위의 상한.
	 * @return 옮겨진 위치의 인덱스, 옮겨진 원소가 없다면 lowVac.
	 */
	private final <E extends Comparable<E>> int extendedLargeRegion(E[] e, E pivot, int lowVac, int high) {
		int highVac = lowVac;           // 실패로 가정
		for (int curr = high; curr > lowVac; curr--) {
			if (e[curr].compareTo(pivot) < 0) {
				e[lowVac] = e[curr];    // 성공
				highVac = curr;
				break;
			}
		}
		return highVac;
	}
	
	/**
	 * 배열의 앞에서 뒤로 이동하며 확인. (o)<br>
	 * 사후 조건: e[low], ..., e[highVac - 1]의 좌측 원소들 중에서, pivot보다 뒤인 (큰) 원소가 e[highVac]으로 옮겨짐.
	 * @param <E>       순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e         정렬할 배열.
	 * @param pivot     피벗.
	 * @param low       범위의 하한.
	 * @param highVac   범위의 상한.
	 * @return 옮겨진 위치의 인덱스, 옮겨진 원소가 없다면 highVac.
	 */
	private final <E extends Comparable<E>> int extendedSmallRegion(E[] e, E pivot, int low, int highVac) {
		int lowVac = highVac;           // 실패로 가정
		for (int curr = low; curr < highVac; curr++) {
			if (e[curr].compareTo(pivot) >= 0) {
				e[highVac] = e[curr];   // 성공
				lowVac = curr;
				break;
			}
		}
		return lowVac;
	}
	
	/**
	 * 4. Partition의 개수가 10개 이하인 경우에는 insertion sort를 이용하여 sort를 하는 기능<br>
	 * Small Sort (by Insertion Sort)
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first <= i <= last 상에서 정의됨.
	 * @param first 시작 인덱스.
	 * @param last  끝 인덱스.
	 */
	private <E extends Comparable<E>> void smallSort(E[] e, int first, int last) {
		for (int xindex = first; xindex <= last; xindex++) {
			E curr = e[xindex];
			int vacant = xindex - 1;
			for (; vacant >= 0 && e[vacant].compareTo(curr) > 0; vacant--) {
				e[vacant + 1] = e[vacant];
			}
			e[vacant + 1] = curr;
		}
	}
	
	/**
	 * Quick Sort (Tail Recursion Optimization)<br>
	 * 큰 부분 범위는 순차적(While Loop)으로 수행하고, 작은 부분 범위는 재귀 호출.<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열.
	 */
	public final <E extends Comparable<E>> void sortTRO(E[] e) {
		sortTRO(e, 0, e.length - 1);
	}
	
	/**
	 * Quick Sort (Tail Recursion Optimization)<br>
	 * 큰 부분 범위는 순차적(While Loop)으로 수행하고, 작은 부분 범위는 재귀 호출.<br>
	 * 사후 조건: 원소 e[first], ..., e[last]가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first <= i <= last 상에서 정의됨.
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 */
	public final <E extends Comparable<E>> void sortTRO(E[] e, int first, int last) {
		int first1, last1;
		int first2 = first, last2 = last;
		while (last2 - first2 >= 1) {
			E pivot = e[first2];
			int splitPoint = partition(e, pivot, first2, last2);
			e[splitPoint] = pivot;
			if (splitPoint <= (first2 + last2) / 2) {
				first1 = first2;
				first2 = splitPoint + 1;
				last1 = splitPoint - 1;
			} else {
				first1 = splitPoint + 1;
				last1 = last2;
				last2 = splitPoint - 1;
			}
			sortTRO(e, first1, last1);
			// first2, last2를 위한 loop를 계속함.
		}
	}
	
	public static class Partition {
		
		private int first;
		private int last;
		private int id;
		private static int idFactor = 1;
		
		/**
		 * @param first
		 * @param last
		 */
		public Partition(int first, int last) {
			this.first = first;
			this.last = last;
			this.id = idFactor++;
		}
		
		/**
		 * @return the first
		 */
		public int getFirst() {
			return first;
		}
		
		/**
		 * @return the last
		 */
		public int getLast() {
			return last;
		}
		
		/**
		 * @return size
		 */
		public int getSize() {
			return last - first + 1;
		}
		
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
		
		private <E> String toString(E[] e) {
			if (e.length == 0) {
				return "[]";
			}
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			for (int i = first;;) {
				sb.append(e[i++]);
				if (i > last || i >= e.length) {
					return sb.append(']').toString();
				}
				sb.append(',').append(' ');
			}
		}
	}
}
