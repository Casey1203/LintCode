package LintCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Chapter5 {
    private int n;
    private int[][] f;
    //给定一个数字三角形，找到从顶部到底部的最小路径和。每一步可以移动到下面一行的相邻数字上。
    //注意
    //如果你只用额外空间复杂度O(n)的条件下完成可以获得加分，其中n是数字三角形的总行数。
    ArrayList<ArrayList<Integer>> triangle;
    //version 1: Memoization
    public int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
        // write your code here
        //start state at 0,0
    	this.triangle = triangle;
        this.n = triangle.size();//row number
        this.f = new int[n][n];
        //initialize f set
        if(n == 0){
            return 0;
        }
        //init. state set
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                f[i][j] = Integer.MAX_VALUE;
            }
        }
        return dfs(0,0);
    }
    public int dfs (int x, int y){
        if(x >= n){
            return 0;
        }
        //if f[x][y] is not infinite, means f(x,y) has been calculated, return it directly
        if(f[x][y] != Integer.MAX_VALUE){
            return f[x][y];
        }
        f[x][y] = Math.min(dfs(x+1,y),dfs(x+1,y+1)) + triangle.get(x).get(y);
        return f[x][y];
    } 
    //Version 2: Bottom-up
    public int minimumTotal_ButtomUp(ArrayList<ArrayList<Integer>> triangle) {
        // write your code here
        //start state at leaves
    	this.triangle = triangle;
        this.n = triangle.size();//row number
        this.f = new int[n][n];
        //initialize f set
        if(n == 0){
            return 0;
        }
        //init. the leave node into state set
        //leave nodes are at the lowest level.
        for(int i = 0; i < n; i++){
        	f[n-1][i] = triangle.get(n-1).get(i);
        }
        //calculating state set f
        for(int i = n-2; i >=0; i--){
        	for(int j = 0; j <= i; j++){
        		f[i][j] = Math.min(f[i+1][j],f[i+1][j+1]) + triangle.get(i).get(j);
        	}
        }
        //answer
        return f[0][0];
    }    
    //Version 3: Up-bottom
    public int minimumTotal_UpBottom(ArrayList<ArrayList<Integer>> triangle) {
        // write your code here
        
    	this.triangle = triangle;
        this.n = triangle.size();//row number
        this.f = new int[n][n];
        //initialize f set
        if(n == 0){
            return 0;
        }
        //init. state set into Max value
        for(int i = 0; i < n; i++){
        	for(int j = 0; j < n; j++){
        		f[i][j] = Integer.MAX_VALUE;
        	}
        }
        //start state 0,0
        f[0][0] = triangle.get(0).get(0);
        //calculating state set f
        for(int i = 1; i < n; i++){//row by row, down to bottom
        	for(int j = 0; j <= i; j++){//each row has i node in ith row, thus from 0 to i, to traverse this row
        		if(f[i-1][j] != Integer.MAX_VALUE){//要考虑右边界没有右肩
        			f[i][j] = Math.min(f[i][j], f[i-1][j]);
        		}
        		if(j>0 && f[i-1][j-1] != Integer.MAX_VALUE){//要考虑左边界没有左肩
        			f[i][j] = Math.min(f[i][j], f[i-1][j-1]);
        		}
        		f[i][j] += triangle.get(i).get(j);
        	}
        }
        //answer, minimum leave
        int answer = Integer.MAX_VALUE;
        for(int i = 0; i < f[n-1].length; i ++){
        	answer = Math.min(answer, f[n-1][i]);
        }
        return answer;
    }   
    
    public int longestConsecutive(int[] num) {
        // write you code here
        int returnMax = 1;
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        if(num.length == 0){
            return 0;
        }
        //init. hashMap
        for(int i:num){
            hashMap.put(i,0);
        }
        for(int temp : num){
        	int maxConseSeq = 1;
            if(hashMap.get(temp) == 1){//判断重复
                continue;
            }
            int temp_backup = temp;
            while(hashMap.containsKey(temp + 1)){
                maxConseSeq++;
                temp++;
                hashMap.put(temp,1);
            }
            temp = temp_backup;
            while(hashMap.containsKey(temp - 1)){
                maxConseSeq++;
                temp--;
                hashMap.put(temp, 1);
            }
            returnMax = Math.max(maxConseSeq, returnMax);
        }
        return returnMax;
    }
    
    public int minPathSum(int[][] grid) {
        // write your code here
        if(grid.length == 0){
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int[][] f = new int[m][n];
        //init. 边界初始化
        //row 0 为0 ~ m-1
        int sum = 0;
        for(int i = 0; i < m; i++){
            sum = sum + grid[i][0];
            f[i][0] = sum;
        }
        //column 为0 ~ n-1
        sum = 0;
        for(int i = 0; i < n; i++){
            sum = sum + grid[0][i];
            f[0][i] = sum;
        }
        for(int i = 1; i < m; i ++){
            for(int j = 1; j < n; j ++){
                f[i][j] = Math.min(f[i-1][j],f[i][j-1]) + grid[i][j];
            }
        }
        return f[m-1][n-1];
    }
    
    //写出多少种独立的方案
    public int uniquePaths(int m, int n) {
        // write your code here 
    	
        if(m == 0 || n == 0){
            return 0;
        }
        int[][] f = new int[m][n];
        //init. of state set
        //边界初始化
        for(int i = 0; i < m; i ++){//row 0 全部为1
            f[i][0] = 1;
        }
        for(int i = 0; i < n; i ++){//column 0 全部为1
            f[0][i] = 1;
        }
        for(int i = 1; i < m; i ++){
            for(int j = 1; j < n; j++){
                //i,j有多少种走法等于它上面的值加上左边的值
                f[i][j] = f[i-1][j] + f[i][j-1];
            }
        }
        return f[m-1][n-1];
    }
    //obstacle定义为0，以及它以它为开始的同一条row和column的值都为0
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // write your code here
        if(obstacleGrid == null){
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] f = new int[m][n];
        //init. of state set
        for(int i = 0; i < m; i++){
            if(obstacleGrid[i][0] != 1){
                f[i][0] = 1;
            }
            else{
                break;
            }
        }
        for(int i = 0; i < n; i++){
            if(obstacleGrid[0][i] != 1){
                f[0][i] = 1;
            }
            else{
                break;
            }
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
            	if(obstacleGrid[i][j] != 1){
            		f[i][j] = f[i-1][j] + f[i][j-1];
            	}
            	else{
            		f[i][j] = 0;
            	}
            }
        }
        return f[m-1][n-1];
    }
    
    //
    public int climbStairs(int n) {
        // write your code here
        //f[0] = 1
        if(n == 0){
            return 1;
        }
        //init. state set
        int[] f = new int[n + 1];
        f[0] = 1;
        for(int i = 1; i <= n; i++){
            if(i == 1){
                f[i] = 1;
            }
            else{
                f[i] = f[i-1] + f[i-2];
            }
        }
        return f[n];
    }
    
    public boolean canJump(int[] A) {
        // wirte your code here
        if(A == null){
            return true;
        }
        int n = A.length;
        boolean[] f = new boolean[n];
        f[0] = true;
        //init. state set
        for(int i = 1; i < n; i++){
            f[i] = false;
        }
        for(int i = 1; i < n; i++){
            for(int j = 0; j < i  ; j++){
                if(f[j] == true && A[j] >= i-j){
                    f[i] = true;
                    break;
                }
            }
        }
        return f[n - 1];
    }
    
    //O(n)
    public boolean isPalindrome(String s){
        if(s.length() == 0){
            return true;
        }
        int i = 0;
        int j = s.length()-1;
        while(i < j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    public int minCut(String s) {
        // write your code here
        if(s.length() == 0){
            return 0;
        }
        //malloc one more space than the length of String
        int n = s.length();
        int[] f = new int[n+1];
        
        //init. state set
        for(int i = 0; i <= n; i++){
            f[i] = i-1;
        }
        //function
        for(int i = 1; i <= n; i++){
            for(int j = 0; j < i; j++){
                if(isPalindrome(s.substring(j,i))){//if j~i is a Palindrome (index j in String s is the (j+1)th element)
                    f[i] = Math.min(f[i],f[j] + 1);
                }
            }
        }
        return f[n];
    }
    
    public boolean wordSegmentation(String s, Set<String> dict) {
        // write your code here   
        if(s.length() == 0 || s == null){
            return true;
        }
//        ArrayList<String> helper = new ArrayList<String>();
//        for(String word : dict){
//        	helper.add(word);
//        }
//        Collections.sort(helper);
//        dict = new TreeSet<String>();
//        for(int i = 0; i<helper.size(); i++){
//        	dict.add(helper.get(i));
//        }
        int n = s.length();//length of s
        boolean[] f = new boolean[n+1];
        //start state
        f[0] = true;
        //init. state set
        for(int i = 1; i <= n; i++){
            f[i] = false;
        }
        //function
        
       
        for(int i = 1; i <= n; i++){
        	StringBuilder myStringBuilder = new StringBuilder();
            for(int j = i-1; j >=0; j--){
            	myStringBuilder.insert(0, s.charAt(j));
                if(f[j] && dict.contains(myStringBuilder.toString())){
                	f[i] = true;
                	break;
                }
            }
        }
        return f[n];
    }
    
    public int longestIncreasingSubsequence(int[] nums) {
        // write your code here
        if(nums.length == 0){
            return 0;
        }
        int n = nums.length;
        int[] f = new int[n];
        //init. state set
        for(int i = 0; i < n; i++){//the increase sequence just includes theirselves
            f[i] = 1;
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++){
                if(nums[j]<=nums[i]){//make sure nums[i] can be as a part of this increasing series, thus f[i] = f[j] + 1
                    f[i] = Math.max(f[i],f[j]+1);
                }
            }
        }
        int answer = f[0];
        for(int i = 1; i < n; i++){
            answer = Math.max(answer,f[i]);
        }
        return answer;
    }
    
    
    
    public static void main(String[] args){
    	ArrayList<ArrayList<Integer>> doubleArray = new ArrayList<ArrayList<Integer>>();
    	ArrayList<Integer> a = new ArrayList<Integer>();
    	a.add(2);
    	doubleArray.add(a);
    	a = new ArrayList<Integer>();
    	a.add(3);
    	a.add(4);
    	doubleArray.add(a);
    	a = new ArrayList<Integer>();
    	a.add(6);
    	a.add(5);
    	a.add(7);
    	doubleArray.add(a);
    	a = new ArrayList<Integer>();
    	a.add(4);
    	a.add(1);
    	a.add(8);
    	a.add(3);
    	doubleArray.add(a);
    	int[] num = {4,2,4,5,3,7};
    	int[][] matrix = {{0,0,0},{0,1,0},{0,0,0}};
    	Chapter5 cp5 = new Chapter5();
    	//System.out.print(cp5.minimumTotal_UpBottom(doubleArray));
    	Set<String> dict = new TreeSet<String>();
    	dict.add("bc");
    	dict.add("cd");
    	dict.add("de");
    	dict.add("fg");
    	dict.add("abcd");
    	dict.add("efg");
    	System.out.println(cp5.wordSegmentation("abcdefg",dict));
    	
    	
    }
}
