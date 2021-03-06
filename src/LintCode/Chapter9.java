package LintCode;

import java.util.ArrayList;
import java.util.Arrays;

public class Chapter9 {
    public int partitionArray(ArrayList<Integer> nums, int k) {
	    //write your code here
    	int length = nums.size();
	    if(length == 0){
	        return 0;
	    }
	    int start = 0;//start pointer
	    int end = length - 1;//end pointer
	    while(start < end){
	    	while(nums.get(start) < k ){
	    		start++;
	    		if(start >= length){
	    			break;
	    		}
	    	}
	    	while(nums.get(end) >= k){
	    		end--;
	    		
	    		if(end <= 0){
	    			break;
	    		}
	    	}
	        if(start < end){
	        	swap(nums,start,end);
	        	start++;
	        	end--;
	        }
	    }
	    return start;
    }
    
    public void swap(ArrayList<Integer> nums, int start, int end){
        int temp = nums.get(start);
        nums.set(start,nums.get(end));
        nums.set(end,temp);
    }
    public static void main(String[] args){
    	Chapter9 cp9 = new Chapter9();
    	ArrayList<Integer> nums = new ArrayList<Integer>(Arrays.asList(6,6,8,6,7,9,8,7,9,6,8,6,8,9,8,7,8,6,7,6,6,8,6,6));
    
    	System.out.println(cp9.partitionArray(nums,5));
    }
    
}
