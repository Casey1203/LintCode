package LintCode;

import java.util.ArrayList;
import java.util.Collections;

public class Subset {
    
    public int strStr(String source, String target) {
        //write your code here
        if (null == source || null == target)
    		return -1;
        int slen = source.length();
        int tlen = target.length();
        if(0 == tlen)
        	return 0;
    	else{
        	int next[] = getNext(target);
            int i = 0;
            int j = 0;
            while(i<slen && j<tlen){
                //match, i++, j++
                if(-1 == j || source.charAt(i)==target.charAt(j))
                {
                    i++;
                    j++;
                }
                else{//not match, j return to next[j]
                    j = next[j];
                }
            }
            if(j == tlen)
            	return i - j;
            else
            	return -1;
    	}
    }
    public int[] getNext(String target){
        int tlen = target.length();
        int next[] = new int[tlen];
        next[0] = -1;
        int k = -1;
        int j = 0;
        while(j < tlen - 1){
            
            if (-1 == k || target.charAt(k) == target.charAt(j)){
                k++;
                j++;
                next[j] = k;
            }
            else{
                k = next[k];
            }
        }
        return next;
    }
    
    
    
	public ArrayList<ArrayList<Integer>> subsets(ArrayList<Integer> S){
		ArrayList<ArrayList<Integer>> subset = new ArrayList<ArrayList<Integer>>();
		Collections.sort(S);
		dfs(S,0,new ArrayList<Integer>(),subset);
		return subset;
	}
	
	public void dfs(ArrayList<Integer> S, int index, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> subset){//index is the current traverse position, we need to scan through the element before index.
		subset.add(new ArrayList<Integer>(path));
		for(int i = index; i<S.size(); i++){
			path.add(S.get(i));
			dfs(S,i + 1,path,subset);
			path.remove(path.size()-1);//return to the former level
		}
	}
	
    public ArrayList<ArrayList<Integer>> subsetsWithDup(ArrayList<Integer> S) {
        // write your code here
        ArrayList<ArrayList<Integer>> subset = new ArrayList<ArrayList<Integer>>();
        Collections.sort(S);
        dfsWithDup(S,0,new ArrayList<Integer>(),subset);
        return subset;
    }
	public void dfsWithDup(ArrayList<Integer> S, int index, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> subset){
		subset.add(new ArrayList<Integer>(path));
		for(int i = index; i<S.size(); i++){
			path.add(S.get(i));
			System.out.println(i + "," + path.toString());
			if(subset.contains(path)){
				
				System.out.println("continue");
				path.remove(path.size()-1);
				continue;
			}
			dfsWithDup(S,i + 1,path,subset);
			path.remove(path.size()-1);
		}
	}
    public ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> nums) {
        // write your code here
        ArrayList<ArrayList<Integer>> permutation = new ArrayList<ArrayList<Integer>>();
        if(null == nums) return permutation;
        dfsWithoutRec(nums,0,permutation);
        return permutation;
    }
	public void dfsWithoutRec(ArrayList<Integer> nums, int index, ArrayList<ArrayList<Integer>> permutation){
		ArrayList<Integer> nums_backup = new ArrayList<Integer>(nums);
		ArrayList<ArrayList<Integer>> frontier = new ArrayList<ArrayList<Integer>>();
		frontier.add(new ArrayList<Integer>());
		while(!frontier.isEmpty()){
			ArrayList<Integer> current_state = new ArrayList<Integer>();
			current_state = frontier.get(frontier.size()-1);
			frontier.remove(frontier.size()-1);
			if(current_state.size() == nums_backup.size())
				permutation.add(current_state);
			else{
				if(nums.isEmpty()){
					nums = new ArrayList<Integer>(nums_backup);
				}
				ArrayList<Integer> successor = getSuccessor(nums,current_state);
				if(!nums.isEmpty()){
					for(int a : successor){
						current_state.add(a);
						frontier.add(new ArrayList<Integer>(current_state));
						current_state.remove(current_state.size()-1);
					}
				}
			}
		}
	}
	
	public ArrayList<Integer> getSuccessor(ArrayList<Integer> nums, ArrayList<Integer> current_state){
		ArrayList<Integer> temp = new ArrayList<Integer>(nums);
		temp.removeAll(current_state);
		return temp;
	}
	
    public ArrayList<ArrayList<Integer>> permuteUnique(ArrayList<Integer> nums) {
        // write your code here
        ArrayList<ArrayList<Integer>> permutation = new ArrayList<ArrayList<Integer>>();
        if(null == nums) return permutation;
        dfsWithoutRecWithDup(nums,permutation);
        return permutation;
    }
	
	public ArrayList<Integer> getSuccessorWithDup(ArrayList<Integer> nums, ArrayList<Integer> current_state){
		ArrayList<Integer> temp = new ArrayList<Integer>(nums);
		for(int a : current_state){
			int index = temp.indexOf(a);
			if(-1 != index)
				temp.remove(index);
		}
		return temp;
	}
	
	public void dfsWithoutRecWithDup(ArrayList<Integer> nums, ArrayList<ArrayList<Integer>> permutation){
		ArrayList<Integer> nums_backup = new ArrayList<Integer>(nums);
		ArrayList<ArrayList<Integer>> frontier = new ArrayList<ArrayList<Integer>>();
		frontier.add(new ArrayList<Integer>());
		ArrayList<ArrayList<Integer>> closedSet = new ArrayList<ArrayList<Integer>>();
		while(!frontier.isEmpty()){
			ArrayList<Integer> current_state = new ArrayList<Integer>();
            //pop from frontier
			current_state = frontier.get(frontier.size()-1);
			frontier.remove(frontier.size()-1);
			closedSet.add(new ArrayList<Integer>(current_state));//add into closedSet
			//System.out.println(closedSet.toString());
			if(current_state.size() == nums_backup.size()){//done
				permutation.add(current_state);
			}
			else{
				if(nums.isEmpty()){//recover nums
					nums = new ArrayList<Integer>(nums_backup);
				}
				ArrayList<Integer> successor = getSuccessorWithDup(nums,current_state);//remove element from nums which is already in the current_state set
				if(!nums.isEmpty()){
					for(int a : successor){//add each of successor and put into frontier
						current_state.add(a);
						//System.out.println(current_state.toString());
						//System.out.println(closedSet.toString());
						if(closedSet.contains(current_state) == false){
							frontier.add(new ArrayList<Integer>(current_state));	
						}
						current_state.remove(current_state.size()-1);//recover to the state before entering the for loop
					}
				}
			}
		}
	}

	
	public static void main(String[] args){
		ArrayList<Integer> S = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		int[] sList = {1,2,2};
		//initialize string set
		for(int i = 0; i < sList.length; i++){
			S.add(sList[i]);
		}
		Subset ss = new Subset();
		result = ss.permuteUnique(S);
		System.out.println(result.toString());
		
//		ArrayList<Integer> list1 = new ArrayList<Integer>();  
//		ArrayList<Integer> list2 = new ArrayList<Integer>();
//		list1.add(1);  list1.add(2);
//		list1.add(3); 
//		list2.add(1);
//		list2.add(2);
//		System.out.println(list1.toString());
//		System.out.println(list2.toString());
//		list1.removeAll(list2);
//		System.out.println(list1.toString());
	}
	
}
