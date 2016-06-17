public class Search {
    public static int binarySearch(int[] arr, int key){
        int l = 0, r = arr.length - 1;
        while (l <= r){
            int m = l + (r-l)/2;
            if(arr[m] == key){
                return m;
            } else if(arr[m] < key){
                l = m + 1;
            } else if(arr[m] > key){
                r= m - 1;
            }
        }
        return -1;
    }

    public static int interpolationSearch(int[] data, int target) {
        int l = 0;
        int r = data.length - 1;
        while (l <= r) {
            int m = (int) (l + (r - l) * ((target - data[l]) / (data[r] - data[l])));
            if (target < data[m]) {
                r = m - 1;
            } else if (target == data[m]) {
                return m;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }
}
