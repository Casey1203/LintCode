package OtherExercise;

import java.util.ArrayList;
import java.util.HashMap;

import LintCode.Chapter3.TreeNode;

public class ExerciseOnLintCode {

	class TreeNode{
		TreeNode left;
		TreeNode right;
		int key;
		public TreeNode(int key){
			this.left = null;
			this.right = null;
			this.key = key;
		}
	}
	
    public int[] twoSum(int[] numbers, int target) {
        // write your code here
        if(numbers.length == 0){
            //empty array
            return new int[0];
        }
        HashMap<Integer,Integer> hashmapHelper = new HashMap<Integer,Integer>();
        for(int i = 0; i < numbers.length; i++){
            hashmapHelper.put(numbers[i],i+1);//put index value into hashtable
        }
        int i = 0;
        while(i<numbers.length){
            if(hashmapHelper.containsKey(target - numbers[i])){
                
                int index1 = i+1;
                int index2 = hashmapHelper.get(target - numbers[i]);
                if(index1 < index2){
                	int[] index = {index1,index2};
                	return index;
                }
                else{
                    int[] index = {index2,index1};
                    return index;
                }
            }
            i++;
        }
        return new int[0];
    }
    
    public int removeDuplicates(int[] A) {
        if(A.length == 0){
            return 0;
        }
        int n = A.length;
        int i = 1;//traverse the array
        int j = 0;
        while(i < n){
            if(A[i] == A[j]){
            	while(++i < n && A[i] == A[j]){
            		continue;
            	}
            	if(i>=n){
            		break;
            	}
            	A[++j] = A[i];
            	i++;
            }
            else{
                i++;
                j++;
            }
        }
        return j+1;
    }
    
    public int maxPath(TreeNode root){
        if(root == null) return 0;
        int maxPathLeft = Integer.MIN_VALUE;
        int maxPathRight = Integer.MIN_VALUE;
        
        if(root.left != null){
            maxPathLeft = maxPath(root.left);
        }
        if(root.right != null){
        	maxPathRight = maxPath(root.right);
        }
        
        int max = Math.max(maxPathLeft,maxPathRight);
        return Math.max(max, single(root.left)+single(root.right)+1);        
    }

    public int single(TreeNode root){
        if(root == null) return 0;
        return Math.max(single(root.left),single(root.right)) + 1;
    }
    
    
    public void mergeSort(int[] num, int start, int end, int[] temp){
    	int mid = start + (end - start)/2;
    	if(start < end){
    		mergeSort(num,start,mid,temp);
    		mergeSort(num,mid+1,end,temp);
    		merge(num,start,mid,end,temp);
    	}
    	
    }
    public void merge(int[] num, int start, int mid, int end, int[] temp){
    	int i = start,j = mid + 1;
    	int m = mid, n = end;
    	int k = 0;
    	while(i <= m && j <= n){
    		if(num[i] < num[j]){
    			temp[k++] = num[i++];
    		}
    		else{
    			temp[k++] = num[j++];
    		}
    	}
		while(i<=m){
			temp[k++] = num[i++];
		}
		while(j <= m){
			temp[k++] = num[j++];
		}
    	for(int c = 0; c < k; c++){
    		num[start + c] = temp[c];
    	}
    }
    
	public static void main(String[] args){
		int[] numbers = {1,1};
		ExerciseOnLintCode eo = new ExerciseOnLintCode();
		//System.out.println(eo.removeDuplicates(numbers));
		
		//build binary tree
/*		TreeNode root = eo.new TreeNode(0);
		root.left = eo.new TreeNode(1);
		root.right = eo.new TreeNode(2);
		root.left.left = eo.new TreeNode(3);
		root.left.right= eo.new TreeNode(4);
		root.right.left= eo.new TreeNode(5);
		root.right.right= eo.new TreeNode(6);
		root.left.right.right= eo.new TreeNode(7);
		root.right.right.right= eo.new TreeNode(8);
		root.right.right.right.right= eo.new TreeNode(9);*/
		int[] num = {1,3,4,2,6,7,5,8};
		int[] array = new int[num.length];
		eo.mergeSort(num,0,num.length-1,array);
		for(int i = 0; i<num.length; i++){
			System.out.println(num[i]);
		}
	}
	
}
