package LintCode;

import java.util.ArrayList;
import java.util.HashMap;

public class Chapter5 {
    private int n;
    private int[][] f;
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
        	for(int j = 0; j <= i; j++){//each row has i node in ith row
        		if(f[i-1][j] != Integer.MAX_VALUE){
        			f[i][j] = Math.min(f[i][j], f[i-1][j]);
        		}
        		if(j>0 && f[i-1][j-1] != Integer.MAX_VALUE){
        			f[i][j] = Math.min(f[i][j], f[i-1][j-1]);
        		}
        		f[i][j] += triangle.get(i).get(j);
        	}
        }
        
        //answer
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
        //init.
        int sum = 0;
        for(int i = 0; i < m; i++){
            sum = sum + grid[i][0];
            f[i][0] = sum;
        }
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
       
    public int uniquePaths(int m, int n) {
        // write your code here 
        if(m == 0 || n == 0){
            return 0;
        }
        int[][] f = new int[m][n];
        //init. of state set
        for(int i = 0; i < m; i ++){
            f[i][0] = 1;
        }
        for(int i = 0; i < n; i ++){
            f[0][i] = 1;
        }
        for(int i = 1; i < m; i ++){
            for(int j = 1; j < n; j++){
                f[i][j] = f[i-1][j] + f[i][j-1];
            }
        }
        return f[m-1][n-1];
    }
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
    	int[] num = {9,1,4,7,3,-1,0,5,8,-1,6};
    	int[][] matrix = {{0,0,0},{0,1,0},{0,0,0}};
    	Chapter5 cp5 = new Chapter5();
    	//System.out.print(cp5.minimumTotal_UpBottom(doubleArray));
    	System.out.println(cp5.uniquePathsWithObstacles(matrix));
    }
}
