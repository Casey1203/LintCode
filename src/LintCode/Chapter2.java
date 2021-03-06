package LintCode;

import java.util.ArrayList;


public class Chapter2{


    //using the binarySearch in the following function
    public int binarySearch(int[] nums, int target) {
        //write your code here
    	int ret = binarySearch(nums, 0, nums.length-1, target);
    	while((ret)>0){
    		if(nums[ret] == nums[ret-1]) ret--;
    		else break;
    	}
    	return ret;
    }
    
    public int binarySearch(int[] nums, int lo, int hi, int target){
    	if(lo<= hi){
    		int mi = (lo + hi)/2;
        	//System.out.println(mi);
        	if(nums[mi] == target){
        		return mi;
        	}
        	else if(target < nums[mi]){
        		return binarySearch(nums, lo, mi-1, target);
        	}
        	else{
        		return binarySearch(nums, mi+1, hi, target);
        	}
    	}
    	return -1;
    }
    
    public int binarySearch1(int[] A, int target){
    	if(A.length == 0) return -1;
        int start = 0;
        int end = A.length - 1;
        
        while(start + 1 < end){
            int mid = start + (end - start)/2;
            if(A[mid] == target){
                end = mid;
            }
            else if(A[mid] < target){
                start = mid;
            }
            else if(target < A[mid]){
                end = mid;
            }
        }
        
        if(A[start] == target){
            return start;
        }
        if(A[end] == target){
            return end;
        }
        return -1;
    }
    //Given a sorted array and a target value, return the index if the target is found. 
    //If not, return the index where it would be if it were inserted in order.
    public int searchInsert(int[] A, int target){
    	if(A.length == 0) return 0;
    	int start = 0;
    	int end = A.length-1;
    	while(start + 1 < end){
    		int mid = start + (end - start)/2;
    		if(A[mid] == target){
    			end = mid;
    		}
    		else if(A[mid] < target){
    			start = mid;
    		}
    		else if (target < A[mid]){
    			end = mid;
    		}
    	}
    	//经过while后要么是start，要么是end
    	//否则表示找不到，表示target比数组中的所有数都大
		if(A[start]>=target){//先判断前面一个指针
			return start;
		}
		else if(A[end]>=target){//然后再判断后一个指针
			return end;
		}
		else{
			return end + 1;
		}
    }
    
    //Given a sorted array of integers, find the starting and ending position of a given target value.
    //Your algorithm's runtime complexity must be in the order of O(log n).
    //If the target is not found in the array, return [-1, -1].
    public ArrayList<Integer> searchRange(ArrayList<Integer> A, int target) {
        //write your code here
        ArrayList<Integer> bound = new ArrayList<Integer>();
        if(A.size()==0) {
        	bound.add(-1);
        	bound.add(-1);
        }
        else{
            int[] nums = new int[A.size()];
            for(int i = 0; i< nums.length; i++){
            	nums[i] = A.get(i);
            }
        	int ret = binarySearch(nums, 0, nums.length-1, target);
        	if(ret == -1) {
            	bound.add(-1);
            	bound.add(-1);
        	}
        	else{//add all the duplicate part
            	while(ret >0 && nums[ret] == nums[ret - 1]){
            		ret--;
            	}
            	bound.add(ret);
            	while(ret < nums.length-1 && nums[ret] == nums[ret + 1]){
            		ret++;
            	}
            	bound.add(ret);
        	}
        }
        return bound;
    }
    //twice binary search
    //Write an efficient algorithm that searches for a value in an m x n matrix.
    //This matrix has the following properties:
    //* Integers in each row are sorted from left to right.
    //* The first integer of each row is greater than the last integer of the previous row.
    public boolean searchMatrix(int[][] matrix, int target) {
        // write your code here
        if(matrix.length == 0) return false;
        int start = 0;
        int end = matrix.length - 1;//# row
        int elem_num = matrix[0].length;//# element each row
        int mid = 0;
        while(start <= end){
            mid = start + (end - start)/2;
            if(target >= matrix[mid][0] && target <= matrix[mid][elem_num-1]){
            	break;
            }
            else if(target < matrix[mid][0] && mid > 0 && target <= matrix[mid-1][elem_num-1]){
                end = mid-1;
            }
            else if(matrix[mid][elem_num-1] < target && mid < matrix.length-1 && matrix[mid+1][0] <= target){
                start = mid+1;
            }
            else{
            	return false;
            }
        }
        int new_start = 0;
        int new_end = elem_num - 1;
        while(new_start + 1 < new_end){
            int new_mid = new_start + (new_end - new_start)/2;
            if(target == matrix[mid][new_mid]){
                new_end = new_mid;
            }
            else if(target < matrix[mid][new_mid]){
                new_end = new_mid;
            }
            else if(matrix[mid][new_mid] < target){
                new_start = new_mid;
            }
        }
        if(matrix[mid][new_start] == target || matrix[mid][new_end] == target){
        	return true;
        }
        return false;
    }
    
    //Write an efficient algorithm that searches for a value in an m x n matrix, return the occurrence of it.
    //This matrix has the following properties:
        //* Integers in each row are sorted from left to right.
        //* Integers in each column are sorted from up to bottom.
        //* No duplicate integers in each row or column.
    
    
    public int searchMatrix2(int[][] matrix, int target) {
        // write your code here
        int m = matrix.length - 1;//row
        int n = 0;//column
        int count = 0;
        while(m >= 0 && n < matrix[0].length){
        	//System.out.println(m + "," + n);
            if(target == matrix[m][n]){
                count ++;
                m--;
                n++;//diagnose
            }
            else if(target > matrix[m][n]){
                n++;//
            }
            else{
                m--;
            }
            //System.out.println(count);
        }
        return count;
    }
    
    //There is an integer array which has the following features:
        //* The numbers in adjacent positions are different.
        //* A[0] < A[1] && A[A.length - 2] > A[A.length - 1].
    //We define a position P is a peek if A[P] > A[P-1] && A[P] > A[P+1].
    //Find a peak element in this array. Return the index of the peak.
    public int findPeak(int [] A){
    	int start = 0;
    	int end = A.length-1;
        
        int peak = A[1];
        while(start<= end){
        	int mid = start + (end - start)/2;
            int mid_val = A[mid];
            int pre = A[mid-1];
            int aft = A[mid+1];     
            if(relation(mid_val,pre,aft) == 1){
            	peak = mid;
                return mid;
            }
            else if(relation(mid_val,pre,aft) == 2){
                start = mid + 1;
            }
            else if(relation(mid_val,pre,aft) == -1){
                end = mid;
            }
            else if(relation(mid_val,pre,aft) == -2){
                end = mid - 1;
            }
        }
        return peak;
    }
    public int relation(int mid, int pre, int aft){
        
        if(mid>pre && mid>aft){
            return 1;
        }
        else if (mid > pre && aft > mid){
            return 2;
        }
        else if (mid < pre && mid <aft ){
            return -1;
        }
        else if (mid < pre && aft < mid){
            return -2;
        }
        else
            return 0;
    }
    //Suppose a sorted array is rotated at some pivot unknown to you beforehand.

    //(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

    //You are given a target value to search. If found in the array return its index, otherwise return -1.

    //You may assume no duplicate exists in the array.
    public int search(int[] A, int target) {
        // write your code here
    	if (A.length == 0)
    		return -1;
        int start = 0;
        int end = A.length-1;
        while(start + 1 < end){
            int mid = start + (end - start) / 2;
            //System.out.println(start + "," + end);
            //System.out.println(mid);
            if(target == A[mid])
            	return mid;
            if(A[start] < A[mid]){
            	if(target < A[mid] && target >= A[start]){
            		end = mid;
            	}
            	else{
            		start = mid;
            	}
            }
            else {
            	if(target > A[mid] && target <= A[end]){
            		start = mid;
            	}
            	else {
            		end = mid;
            	}
            }
        }
        
        if(A[end] == target){
            return end;
        }
        else if(A[start] == target){
            return start;
        }
        return -1;
    }
    //假设一个旋转排序的数组其起始位置是未知的（比如0 1 2 4 5 6 7 可能变成是4 5 6 7 0 1 2）。

    //你需要找到其中最小的元素。

    //你可以假设数组中不存在重复的元素。
    public int findMin(int[] num) {
        // write your code here
        if (num.length == 0) return -1;
        int start = 0;
        int end = num.length - 1;
        while(start + 1 < end){
            int mid = start + (end - start)/2;
            if(num[mid]<=num[start]){//mid is at the second increase array
                end = mid;
            }
            else if(num[mid]>start && num[end] >= num[start]){
                end = mid;
            }
            else{
            	start = mid;
            }
        }
        if(num[start]<=num[end]){
            return num[start];
        }
        else{
            return num[end];
        }
    }
    
    public int findMin1(int[] num) {
        // write your code here
        if(num.length==0) return -1;
        int min = num[0];
        for(int i = 1; i<num.length; i++){
            if(min >= num[i]){
            	min = num[i];
            }
        }
        return min;
    }

    public char reverse(String s){
    	if(s.length() > 0){
    		System.out.println(s.charAt(s.length()-1) + reverse(s.substring(0, s.length() - 1)));
    	}
    	return 0;
    }
    
    public int removeDuplicates(int[] nums) {
        // write your code here
        if (nums.length == 0){
            return 0;
        }
        
        int i = 1;
        int j = 0;
        while(i < nums.length){
            if(nums[i] == nums[j]){
                i++;
            }
            else{
                nums[++j] = nums[i];
                i++;
            }
        }
        return j+1;
    }
    public int removeDuplicates1(int[] nums) {
        // write your code here
    	if(nums.length <= 2){
    		return nums.length;
    	}
    	int i = 2;
    	int j = 1;
    	//int flag = 0;
    	while(i < nums.length){
    		if(nums[i] == nums[j] && nums[i] == nums[j-1]){
    			i++;
    		}
    		else{
    			nums[++j] = nums[i];
    			i++;
    		}
    	}
    	return j + 1;
    }

    public ArrayList<Integer> mergeSortedArray(ArrayList<Integer> A, ArrayList<Integer> B) {
        // write your code here
        ArrayList<Integer> C = new ArrayList<Integer>();
        int i = 0, j = 0;
        while(i < A.size() && j < B.size()){
            if(A.get(i) <  B.get(j)){
                C.add(A.get(i));
                i++;
            }
            else if(A.get(i) > B.get(j)){
                C.add(B.get(j));
                j++;
            }
            else{
                C.add(A.get(i));
                C.add(B.get(j));
                i++;
                j++;
            }
        }
        if(i == A.size() && j<B.size()){//Array A is shorter than B
            //put rest in B into C
            while(j < B.size()){
                C.add(B.get(j));
                j++;
            }
        }
        else if(j == B.size() && i < A.size()){
            while(i < A.size()){
                C.add(A.get(i));
                i++;
            }
        }
        return C;
    }
    
    
    public void mergeSortedArray(int[] A, int m, int[] B, int n) {
        // write your code here
        if(A.length == 0) A = B;
        int i = m-1;//index
        int j = n-1;//index
        int Alen = A.length-1;
        while(i >= 0 && j >= 0){
            if(A[i] > B[j]){
                A[Alen--] = A[i];
                i--;
            }
            else if(A[i] < B[j]){
                A[Alen--] = B[j];
                j--;
            }
            else{
                A[Alen--] = A[i];
                A[Alen--] = B[j];
                i--;
                j--;
            }
        }
        if(j>=0){
        	while(j>=0){
        		A[Alen--]=B[j--];
        	}
        }
        else if(i>=0){
        	while(i >=0){
        		A[Alen--] = A[i--];
        	}
        }
    }
    
    public int findKth(int[] A, int[] B, int Astart, int Bstart, int k){
        if(Astart >= A.length){//A array is done scan
            return B[Bstart + k - 1];
        }
        else if(Bstart >= B.length){
            return A[Astart + k - 1];
        }
        
        if(k==1)
        	return Math.min(A[Astart],B[Bstart]);
        
        int Akey = 0;
        if(Astart + k/2 -1 > A.length){//there is no enough element in array A, say, over k/2
            Akey = Integer.MAX_VALUE;
        }
        else{
        	Akey = A[Astart + k/2 -1];
        }
        int Bkey = 0;
        if(Bstart + k/2 - 1 > B.length){
        	Bkey = Integer.MAX_VALUE;
        }
        else{
        	Bkey = B[Bstart + k/2 - 1];
        }
       
        if(Akey>Bkey){
            return findKth(A,B,Astart, Bstart+k/2,k-k/2);
        }
        else {
        	return findKth(A,B,Astart+k/2,Bstart,k-k/2);
        }
    }
    public double findMedianSortedArrays(int[] A, int[] B) {
        // write your code here
    	int Alen = A.length;
    	int Blen = B.length;
    	int len = Alen + Blen;
    	if(len%2==1){//odd
    		return findKth(A,B,0,0,len/2 + 1);
    	}
    	else{
    		return (findKth(A,B,0,0,len/2) + findKth(A,B,0,0,len/2+1))/2.0;
    	}
    }
    
    public void recoverRotatedSortedArray(ArrayList<Integer> nums) {
        // write your code
        //three step reverse
        for(int i = 0; i < nums.size() - 1; i++){
        	
            if(nums.get(i) > nums.get(i + 1)){
            	System.out.println(i);
                reverse(nums,0,i);
                reverse(nums,i+1,nums.size()-1);
                reverse(nums,0,nums.size()-1);
                break;
            }
        }
    }
    //two point, i from start, j from end
    public void reverse(ArrayList<Integer> nums, int start, int end){
        int i = start;
        int j = end;
        while(i <= j){
            int temp = nums.get(j);
            nums.set(j, nums.get(i));
            nums.set(i,temp);
            i++;
            j--;
        }
    }
    //also use three reverse method
    public char[] rotateString(char[] A, int offset) {
        // wirte your code here
    	if(A.length == 0) {
    		return A;
    	}
        int index = (A.length -1 ) -(offset % A.length);
        reverse(A,0,index);
        reverse(A,index+1,A.length-1);
        reverse(A,0,A.length-1);
        return A;
        
    }
    //reverse char array
    public void reverse(char[] A, int start, int end){
        int i = start;
        int j = end;
        while(i <= j){
            char temp = A[i];
            A[i] = A[j];
            A[j] = temp;
            i++;
            j--;
        }
    }
    
    public String reverseWords(String s) {
        // write your code
    	if(s.length()==0) return s;
        ArrayList<String> sList = new ArrayList<String>();
        String temp = "";
        int i = 0;
        while(i < s.length()){
            if(s.charAt(i) != ' '){
                temp = temp + s.charAt(i);
                i++;
                if(i == s.length()){
                	sList.add(temp);
                	temp = "";
                }
            }
            else{
                if(temp != ""){
                    sList.add(temp);
                    temp = "";
                }
                else{
                    i++;
                }
            }
        }
        s = "";
        //reconstruction from arraylist
        if(!sList.isEmpty()){
            for(int j = sList.size()-1; j > 0; j--){
            	s = s + sList.get(j) + " ";
            }
            s = s + sList.get(0);
        }
        return s; 
    }
    //reverse string
    public void reverse(String s, int start, int end){
        int i = start;
        int j = end;
        char[] chars = new char[s.length()];
        for(int k = 0; k < s.length(); k ++){
        	chars[k] = s.charAt(k);
        }
        while(i <= j){
        	char temp = chars[i];
        	chars[i] = chars[j];
        	chars[j] = temp;
            i++;
            j--;
        }
        s = "";
        for(int k = 0; k < chars.length; k++){
        	s += chars[k];
        }
        System.out.println(s);
    }
    
    public static void main(String[] args){
		int[] array = {4,5,1,2,3};

		int[] array1 = {4,5};
		String s = " ";
		
/*		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		for(int i = 0; i < array.length; i++){
			a.add(array[i]);
		}
		for (int i = 0; i < array1.length; i++){
			b.add(array1[i]);
		}*/
		
/*		String s = "dcba";
		ArrayList<Integer> A = new ArrayList<Integer>();
		for(int i = 0; i < array.length; i++){
			A.add(array[i]);
		}*/
		Chapter2 cp2 = new Chapter2();
		//int[][] array1 = {{ 1, 3, 5, 7},{2,4,7,8},{3,5,9,10}};
		System.out.println(cp2.reverseWords(s));
		
	}
	
}
