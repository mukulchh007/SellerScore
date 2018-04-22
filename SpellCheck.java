import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author mukul
 */
public class SpellCheck {
    
    Scanner s = new Scanner(System.in);
    int[][] d;
    String s1,s2;
    
    public void check(){
        s1 = s.nextLine();
        s2 = s.nextLine();
        distance(s1,s2);
    }
    
    public void print(){
        for(int i=0;i<s1.length();i++){
            for(int j=0;j<s2.length();j++){
                System.out.print(d[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    /*
    the methode below finds the distance between two strings using lavanshtein's 
    distance
    */
    
    public void distance(String s1, String s2){
        d = new int[s1.length()][s2.length()];
        int l1 = s1.length();
        int l2 = s2.length();
        for(int i=0;i<l1;i++){
            for(int j=0;j<l2;j++){
                int v1=0,v2=0,v3=0;
                if(i==0 && j==0){
                    if(s1.charAt(i)==s2.charAt(j)){
                        //System.out.println("pehla pehla");
                        d[i][j]=0;
                    }
                    else{
                        //System.out.println("not equal");
                        d[i][j]=1;
                    }
                }
                if(i>0 && j>0){
                    //System.out.println("i>0 && j>0 "+s1.charAt(i)+" "+s2.charAt(j));
                    v1=d[i][j-1];
                    v2=d[i-1][j];
                    v3=d[i-1][j-1];
                }
                else if(i>0 || j>0){
                    if(i>0){
                        //System.out.println("i>0");
                        v1=d[i-1][j];
                        v2=Integer.MAX_VALUE;
                        v3=Integer.MAX_VALUE;
                    }
                    if(j>0){
                        //System.out.println("j>0");
                        v1=Integer.MAX_VALUE;
                        v2=d[i][j-1];
                        v3=Integer.MAX_VALUE;
                    }
                }
                int min = retmin(v1,v2,v3);
                    if(s1.charAt(i)==s2.charAt(j) && i==j){
                        d[i][j]=min;
                        //System.out.println("v1:"+v1+" v2:"+v2+" v3:"+v3);
                        //System.out.println("i=j "+s1.charAt(i)+" "+s2.charAt(j)+" min:"+d[i][j]);
                    }
                    else{
                        d[i][j]=min+1;
                        //System.out.println("i=j "+s1.charAt(i)+" "+s2.charAt(j)+" min:"+d[i][j]);
                    }
            }
        }
        print();
        System.out.println("distance:"+d[l1-1][l2-1]);
    }
    
    public int retmin(int n1, int n2, int n3){
        if(n1<=n2 && n1<=n3)
            return n1;
        if(n2<=n1 && n2<=n3)
            return n2;
        if(n3<=n2 && n3<=n1)
            return n3;
        return 0;
    }
}