
/**
 *
 * @author mukul
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class AssignWeight {
    HashMap<String,Integer> hm;
    int count = 0;
    String[] words;
    int totalwords=0;
    
    public void assign(String s1, String s2, String s3) throws IOException{
        
        hm = new HashMap<String,Integer>();
        //hm.put("music", 0);
        //hm.put("like", 0);
        //hm.put("legit", 0);
        //hm.put("graphics", 0);
        //hm.put("enjoy", 0);
        //hm.put("fake", 0);
        
        FileReader fr = null;   //for file with reviews
        FileReader fr2 = null;  //for file with words
        BufferedReader br = null;
        BufferedReader br2 = null;
        
        try{
            fr2 = new FileReader(s1);
            fr = new FileReader(s2);
            /*fr2 = new FileReader("D:\\paypal project\\analysis\\input\\input.txt");
            fr=new FileReader("D:\\paypal project\\analysis\\revfile.txt");*/
            br2 = new BufferedReader(fr2);
            br = new BufferedReader(fr);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        String text = null;
        
        words = br2.readLine().split(",");
        totalwords=words.length;
        for(int i=0;i<words.length;i++){
            hm.put(words[i],0);
        }
        
        for(String s:hm.keySet()){
            System.out.println(s+","+hm.get(s));
        }
        
        long stt = System.nanoTime();
        
        while((text=br.readLine())!=null){
            System.out.println("text:"+text);
            count++;
            
            text.toLowerCase();
            
            for(int i=0;i<words.length;i++){
                patternMatch2(text,words[i]);
                //patternMatch(text,words[i]);
            }
            
            //buildHM(text);
            /*
            
            //do not use this
            
            
            patternMatch(text,"music");
            patternMatch(text,"like");
            patternMatch(text,"legit");
            patternMatch(text,"graphics");
            patternMatch(text,"enjoy");
            patternMatch(text,"fake");
            /**/
            
            
            /*
            String[] st = text.split(" ");
            int size = st.length;
            
            for(int i=0;i<size;i++){
                System.out.println(st[i]);
                if(hm.containsKey(st[i].toLowerCase())){
                    System.out.println("hm.contain");
                    int c = hm.get(st[i].toLowerCase());
                    hm.put(st[i].toLowerCase(),++c);
                }
                    
            }
            /**/
    }
        
        long et = System.nanoTime();
        
        System.out.println("time taken:"+(et-stt));
        
        br.close();
        fr.close();
        
        int total=0;
        
        for(String key:hm.keySet()){
            total+=hm.get(key);
        }
        
        double[] weights = new double[3];
        
        for(int i=0;i<totalwords;i++){
            int v = hm.get(words[i]);
            double wt = (double)v/(double)total;
            weights[i]=wt;
        }
        System.out.println("count:"+count);
        FileWriter fw;
        try{
            
            fw = new FileWriter(s3);
            
            BufferedWriter bw = new BufferedWriter(fw);
            
            /*for(String key:hm.keySet()){
                //System.out.println(key+": "+hm.get(key));
                bw.write(key+": "+hm.get(key)+" \n");
                
            }*/
            
            for(int i=0;i<totalwords;i++){
                bw.write(words[i]+"="+weights[i]+",");
            }
            
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("delivery:"+hm.get("deliver"));
        System.out.println("original:"+hm.get("original"));
        System.out.println("willing:"+hm.get("willing"));
    }
    
    public void GoThrough(String s){
        if(!hm.containsKey(s)){
        hm.put(s, 1);}
    }
    
    public void buildHM(String str){
        System.out.println(str);
        String[] broken=null;
        broken=str.split(" ");
        long size = broken.length;
        int l = 0;
        int c=0;
        while(l<size){
            if(!hm.containsKey(broken[l])){
                hm.put(broken[l], 0);
            }
            else{
                c=hm.get(broken[l]);
                hm.put(broken[l], ++c);
            }
            l++;
        }
        
    }
    
    //similar to karp-rabin but slower since string operations are used here instead of 
    //numerical values as suggested in original method
    
    public void patternMatch(String str, String pattern){
        //count++;
        System.out.println("inside pm str:"+str+" pattern:"+pattern);
        int sp=0;   //starting point
        int ep=0;   //ending point
        String PHash="";
        
        while(ep<=str.length()){
            if(ep<pattern.length()){
                PHash=PHash+str.charAt(ep);
                System.out.println("PHash:"+PHash);
                ep++;
            }
            else{
                System.out.println("inside else");
                if(PHash.equalsIgnoreCase(pattern)){
                    System.out.println("found at:"+sp);
                    int c = hm.get(pattern);
                    hm.put(pattern,++c);
                }
                sp++;
                ep++;
                System.out.println("bef PHash:"+PHash);
                PHash=PHash.substring(1);
                System.out.println("af PHash:"+PHash);
                PHash=PHash+str.charAt(ep-2);
            }
        }
    }
    
    public static final int prime = 199;
    
    public void patternMatch2(String str, String pattern){
        
        int l1=str.length();    //length of string to match
        int l2=pattern.length();    //length of pattern to match
        
        int c1=0;   //current position in string
        int c2=0;   //current position in pattern
        
        long h1t=0;   //temporary hash of string
        long h2t=0;   //temporary hash of pattern
        
        long h1=0;   //hash of string
        long h2=0;   //hash of pattern
        
        for(int i=0;i<l2;i++){
            h2t+=((int)pattern.charAt(i))*Math.pow(prime,c1);
            c1++;
        }
        h2=h2t%prime;
        
        for(int i=0;i<l1;i++){
            if(i<l2){
                h1t+=((int)str.charAt(i))*Math.pow(prime,c2);
                c2++;
            }
            h1=h1t%prime;
            if(i>=l2-1){
                //System.out.println("current substring:"+str.substring(i-l2+1,i+1));
                //System.out.println("h1:"+h1+" h2:"+h2+" h1t:"+h1t+" h2t:"+h2t);
            if(h1==h2){
                if(str.substring(i-l2+1,i+1).equals(pattern)){
                    System.out.println("pattern:"+pattern+" found at:"+(i-l2+1));
                    int count = hm.get(pattern);
                    hm.put(pattern, ++count);
                }
            }
            //System.out.println("subtracting:"+(int)str.charAt(i-l2+1));
            h1t=(h1t-str.charAt(i-l2+1))/prime;
            if(i<str.length()-1){
            //System.out.println("Adding:"+str.charAt(i+1));
            h1t+=((int)str.charAt(i+1))*Math.pow(prime, c2-1);
            }
            }
        }
    }
}