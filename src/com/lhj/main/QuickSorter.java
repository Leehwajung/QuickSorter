package com.lhj.main;

public final class QuickSorter {
	
	private static QuickSorter instance = null;
	
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
	 * Quick Sort<br>
	 * 사후 조건: 원소 e[0], ..., e[length - 1] 전체가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열.
	 */
	public final <E extends Comparable<E>> void sort(E[] e) {
		sort(e, 1, e.length - 1);
	}
	
	/**
	 * Quick Sort<br>
	 * 사후 조건: 원소 e[first], ..., e[last]가 정렬됨.
	 * @param <E>   순서를 비교할 수 있는 원소의 타입. (Comparable<>을 구현)
	 * @param e     정렬할 배열, 원소 e[i]는 first <= i <= last 상에서 정의됨.
	 * @param first 정렬 범위 시작 인덱스.
	 * @param last  정렬 범위 끝 인덱스.
	 */
	public final <E extends Comparable<E>> void sort(E[] e, int first, int last) {
		if (first < last) {
			E pivot = e[first];
			int splitPoint = partition(e, pivot, first, last);
			e[splitPoint] = pivot;
			sort(e, first, splitPoint - 1);
			sort(e, splitPoint + 1, last);
		}
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
		int curr = high;
		while (curr > lowVac) {
			if (e[curr].compareTo(pivot) < 0) {
				e[lowVac] = e[curr];    // 성공
				highVac = curr;
				break;
			}
			curr--;                     // 계속 진행
		}
		return highVac;
	}
	
	/**
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
		int curr = low;
		while (curr < highVac) {
			if (e[curr].compareTo(pivot) >= 0) {
				e[highVac] = e[curr];   // 성공
				lowVac = curr;
				break;
			}
			curr++;                     // 계속 진행
		}
		return lowVac;
	}
}
