package com.lhj.main;

import java.util.LinkedList;

public final class QuickSorter {
	
	private static QuickSorter instance = null;
	private static final int SMALLSIZE = 10;    // Small Sort를 하는 서브 배열의 하한 크기
	
	/**
	 * Private Constructor for Singleton
	 */
	private QuickSorter() {
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
	 * Quick Sort By Recursion<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열.
	 */
	public final <E extends Comparable<E>> void sortByRecursion(E[] e) {
		sortByRecursion(e, 0, e.length - 1);
	}
	
	/**
	 * Quick Sort By Recursion<br>
	 * 사후 조건: 원소 e[first], ..., e[last]가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first <= i <= last 상에서 정의됨.
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 */
	public final <E extends Comparable<E>> void sortByRecursion(E[] e, int first, int last) {
		if (last - first + 1 > SMALLSIZE) {
			int splitPoint = swapPivotForSplitElem(e, first, last);
			sortByRecursion(e, first, splitPoint - 1);
			sortByRecursion(e, splitPoint + 1, last);
		} else {    // Small Sort (Insertion Sort)
			smallSort(e, first, last);
		}
	}
	
	/**
	 * Quick Sort By Tail Recursion Optimization<br>
	 * 큰 부분 범위는 순차적(While Loop)으로 수행하고, 작은 부분 범위는 재귀 호출.<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열.
	 */
	public final <E extends Comparable<E>> void sortByTRO(E[] e) {
		sortByTRO(e, 0, e.length - 1);
	}
	
	/**
	 * Quick Sort By Tail Recursion Optimization<br>
	 * 큰 부분 범위는 순차적(While Loop)으로 수행하고, 작은 부분 범위는 재귀 호출.<br>
	 * 사후 조건: 원소 e[first], ..., e[last]가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first <= i <= last 상에서 정의됨.
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 */
	public final <E extends Comparable<E>> void sortByTRO(E[] e, int first, int last) {
		int smallFirst, smallLast;
		while (last - first + 1 > SMALLSIZE) {
			int splitPoint = swapPivotForSplitElem(e, first, last);
			if (splitPoint <= (first + last) / 2) {
				smallFirst = first;
				first = splitPoint + 1;
				smallLast = splitPoint - 1;
			} else {
				smallFirst = splitPoint + 1;
				smallLast = last;
				last = splitPoint - 1;
			}
			if (smallLast - smallFirst + 1 > SMALLSIZE) {
				sortByTRO(e, smallFirst, smallLast);
			} else {    // Small Sort (Insertion Sort)
				smallSort(e, smallFirst, smallLast);
			}
			// first2, last2를 위한 loop를 계속함.
		}
		if (last - first + 1 <= SMALLSIZE) {    // Small Sort (Insertion Sort)
			smallSort(e, first, last);
		}
	}
	
	/**
	 * Quick Sort Using Stack<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열.
	 */
	public final <E extends Comparable<E>> void sortUsingStack(E[] e) {
		sortUsingStack(e, 0, e.length - 1, false);
	}
	
	/**
	 * Quick Sort Using Stack<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>               순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e                 정렬할 배열.
	 * @param printPartition    파티션의 내용 출력 여부 선택 (true: 출력함)
	 */
	public final <E extends Comparable<E>> void sortUsingStack(E[] e, boolean printPartition) {
		sortUsingStack(e, 0, e.length - 1, printPartition);
	}
	
	/**
	 * Quick Sort Using Stack<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열.
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 */
	public final <E extends Comparable<E>> void sortUsingStack(E[] e, int first, int last) {
		sortUsingStack(e, 0, e.length - 1, false);
	}
	
	/**
	 * Quick Sort Using Stack<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.<br>
	 * 2. Partition 중에서 데이터의 개수가 많은 것을 stack에 저장하고 partition의 길이와 내용을 출력<br>
	 * 3. 실행 중인 partition의 내용과 일련번호로 출력하는 기능<br>
	 * 4. Partition의 개수가 10개 이하인 경우에는 insertion sort를 이용하여 sort를 하는 기능<br>
	 * 5. pivot 원소를 median 값으로 선택하여 실행되도록 하는 기능
	 * @param <E>               순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e                 정렬할 배열.
	 * @param first             정렬 범위 시작 인덱스.
	 * @param last              정렬 범위 끝 인덱스.
	 * @param printPartition    파티션의 내용 출력 여부 선택 (true: 출력함) 
	 */
	public final <E extends Comparable<E>> void sortUsingStack(E[] e, int first, int last, boolean printPartition) {
		LinkedList<Partition> stack = new LinkedList<>();
		stack.push(new Partition(first, last));
		
		while (!stack.isEmpty()) {
			Partition part = stack.pop();
			
			// 3. 실행 중인 partition의 내용과 일련번호로 출력하는 기능
			if (printPartition) {
				System.out.println("실행 중인 Partition의 일련번호와 내용: " 
						+ part.getId() + ", " + part.getStringOfElements(e));
			}
			
			// Partition의 개수가 10개를 초과할 때 quick sort
			first = part.getFirst();
			last = part.getLast();
			while (last - first + 1 > SMALLSIZE) {
				// 5. pivot 원소를 median 값으로 선택하여 실행되도록 하는 기능
				int splitPoint = swapPivotForSplitElem(e, first, last);
				
				// 2. Partition 중에서 데이터의 개수가 많은 것을 stack에 저장하고 partition의 길이와 내용을 출력
				Partition bigPart = null;
				if (splitPoint > (first + last) / 2) {   // Partition 중에서 데이터의 개수가 많은 것을 고름
					bigPart = new Partition(first, splitPoint - 1);
					first = splitPoint + 1;
				} else {
					bigPart = new Partition(splitPoint + 1, last);
					last = splitPoint - 1;
				}
				stack.push(bigPart);    // stack에 저장
				if (printPartition) {   // partition의 길이와 내용을 출력
					System.out.println("Stack에 Push한, 개수가 많은 Partition의 길이와 내용: " 
							+ bigPart.getSize() + ", " + bigPart.getStringOfElements(e));
				}
				
				// first, last를 위한 loop를 계속함.
			}
			
			// 4. Partition의 개수가 10개 이하인 경우에는 insertion sort를 이용하여 sort를 하는 기능
			if (last - first + 1 <= SMALLSIZE) {    // Small Sort (Insertion Sort)
				smallSort(e, first, last);
			}
		}
	}
	
	/**
	 * Swap Pivot for Split Element<br>
	 * 5. pivot 원소를 median 값으로 선택하여 실행되도록 하는 기능
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first <= i <= last 상에서 정의됨.
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 * @return Split Point
	 */
	private <E extends Comparable<E>> int swapPivotForSplitElem(E[] e, int first, int last) {
		int pivotIndex = selectPivotIndex(first, last); // median 값으로 선택
		E pivot = e[pivotIndex];
		if (pivotIndex != first) {
			e[pivotIndex] = e[first];
			e[first] = pivot;   // 첫 위치로 이동
		}
		int splitPoint = partition(e, pivot, first, last);
		e[splitPoint] = pivot;
		return splitPoint;
	}
	
	/**
	 * Select Pivot Index
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 * @return 중간 값
	 */
	private final int selectPivotIndex(int first, int last) {
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
	 * Small Sort (by Insertion Sort)<br>
	 * 4. Partition의 개수가 10개 이하인 경우에는 insertion sort를 이용하여 sort를 하는 기능
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
	 * Partition Class for Quick Sort Using Stack
	 */
	private static final class Partition {
		
		private int first;
		private int last;
		private int id;
		private static int nextId = 1;
		
		/**
		 * @param first
		 * @param last
		 */
		private Partition(int first, int last) {
			this.first = first;
			this.last = last;
			this.id = nextId++;
		}
		
		/**
		 * @return the first
		 */
		private int getFirst() {
			return first;
		}
		
		/**
		 * @return the last
		 */
		private int getLast() {
			return last;
		}
		
		/**
		 * @return size
		 */
		private int getSize() {
			return last - first + 1;
		}
		
		/**
		 * @return the id
		 */
		private int getId() {
			return id;
		}
		
		/**
		 * @param e 원소를 가져올 배열
		 * @return 문자열로 변환된 원소들
		 */
		private <E> String getStringOfElements(E[] e) {
			if (first == last || e.length == 0) {
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
