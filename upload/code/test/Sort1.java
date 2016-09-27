package sort;

public class Sort {
	public static void main(String[] args) {
		Sort sort = new Sort();
		int[] a = { 2, 4, 6, 1, 3, 4, 43, -6, 0 };
		int length = a.length;
		// sort.bubbleSort(a, 9);
		// sort.insertSort(a, length);
		// sort.selectSort(a, length);
		// sort.quickSort(a, length);
		// sort.shellSort(a, length);
		//sort.mergeSort(a, length);
		sort.heapSort(a, length);
		for (int i = 0; i < length; i++) {
			System.out.print(a[i] + " ");
		}
	}

	public void bubbleSort(int[] a, int length) {
		for (int i = length - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				if (a[j] > a[j + 1]) {
					int temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
	}

	public void insertSort(int[] a, int length) {
		for (int i = 1; i < length; i++) {
			int temp = a[i];
			int j = i;
			for (; j > 0 && a[j - 1] > temp; j--) {
				a[j] = a[j - 1];
			}
			a[j] = temp;
		}
	}
	/*
	 * public void insertSort(int[] a, int length) { for (int i = 1; i < length;
	 * i++) { for (int j = 0; j < i; j++) { if (a[i] < a[j]) { int temp = a[i];
	 * for (int k = i; k > j; k--) { a[k] = a[k - 1]; } a[j] = temp; } } } }
	 */

	public void selectSort(int[] a, int length) {
		for (int i = 0; i < length; i++) {
			int k = i;
			for (int j = i; j < length; j++) {
				if (a[j] < a[k]) {
					k = j;
				}
			}
			int temp = a[i];
			a[i] = a[k];
			a[k] = temp;
		}
	}

	public void quickSort(int[] a, int length) {
		qSort(a, 0, length - 1);
	}

	private void qSort(int[] a, int low, int high) {
		if (low >= high) {
			return;
		}
		int first = low;
		int last = high;
		int k = a[first];
		while (first < last) {
			while (first < last && a[last] >= k) {
				last--;
			}
			a[first] = a[last];
			while (first < last && a[first] <= k) {
				first++;
			}
			a[last] = a[first];
		}
		a[first] = k;
		qSort(a, low, first - 1);
		qSort(a, last + 1, high);
	}

	public void shellSort(int[] a, int length) {
		int t = (int) (Math.log(length + 1) / Math.log(2));
		for (int i = t; i > 0; i--) {
			int gap = (int) Math.pow(2, i) - 1;
			for (int j = gap; j < length; j += gap) {
				int temp = a[j];
				int k = j;
				for (; k > 0 && a[k - 1] > temp; k -= gap) {
					a[k] = a[k - gap];
				}
				a[k] = temp;
			}
		}
	}

	public void mergeSort(int[] a, int length) {
		mergeSortR(a, 1);
	}

	private void mergeSortR(int[] a, int len) {
		int size = a.length;
		int mid = size / (len << 1);
		if (mid == 0) {
			return;
		}
		int c = size & (len << 1 - 1);
		for (int i = 0; i < mid; i++) {
			merge(a, 2 * i * len, 2 * i * len + len, 2 * i * len + 2 * len - 1);
		}
		if (c != 0) {
			merge(a, size - c - 2 * len, size - c, size - 1);
		}
		mergeSortR(a, len * 2);
	}

	private void merge(int[] a, int s, int m, int t) {
		int[] b = new int[t - s + 1];
		int i = s, j = m, k = 0;
		while (i < m && j <= t) {
			if (a[i] <= a[j]) {
				b[k] = a[i];
				i++;
				k++;
			} else {
				b[k] = a[j];
				j++;
				k++;
			}
		}
		while (i < m) {
			b[k] = a[i];
			i++;
			k++;
		}
		while (j <= t) {
			b[k] = a[j];
			j++;
			k++;
		}
		System.arraycopy(b, 0, a, s, k);
	}

	public void heapSort(int[] a, int length) {
		for(int i = length / 2; i >= 0; i--){
			heapAdjust(a, i, length - 1);
		}
		for(int i = length - 1; i > 0; i--) {
			int temp = a[i];
			a[i] = a[0];
			a[0] = temp;
			heapAdjust(a, 0, i - 1);
		}
	}

	private void heapAdjust(int[] a, int s, int end) {
		int nChild;
		int temp = a[s];
		int i = s;
		for (; i * 2 + 1 <= end; i = nChild) {
			nChild = i * 2 + 1;
			if(nChild + 1 <= end && a[nChild + 1] > a[nChild]){
				nChild++;
			}
			if(temp < a[nChild]){
				a[i] = a[nChild];
			} else {
				break;
			}
		}
		a[i] = temp;
	}
	
}
