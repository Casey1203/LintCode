package LintCode;

import java.util.ArrayList;

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
    	
    	Chapter5 cp5 = new Chapter5();
    	System.out.print(cp5.minimumTotal_UpBottom(doubleArray));
    	
    }
}