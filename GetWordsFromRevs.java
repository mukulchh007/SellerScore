
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

/**
 *
 * @author mukul
 */
public class GetWordsFromRevs {
    
    HashMap<String, Integer> hm;
    
    public void DoIt() throws Exception{
        FileReader fr = null;
        BufferedReader br = null;
        
        try{
            fr=new FileReader("D:\\paypal project\\analysis\\revfile.txt");
             br = new BufferedReader(fr);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        String[] broken = null;
        String text = null;
        
        int count =0;
        
        hm=new HashMap<String, Integer>();
        
        while((text=br.readLine())!=null){
            System.out.println("text:"+text);
            
            broken=text.split(" ");
            int size=broken.length;
            
            for(int i=0;i<size;i++){
            if(hm.containsKey(broken[i].toLowerCase())){
                System.out.println("hm.contain");
                int c = hm.get(broken[i].toLowerCase());
                hm.put(broken[i].toLowerCase(),++c);
            }
            else{
                hm.put(broken[i].toLowerCase(),1);
            }
            }
            
            count++;
        }
        
        br.close();
        fr.close();
        
        int total=0;
        
        for(String key:hm.keySet()){
            total+=hm.get(key);
        }
        
        int k=0;
        double[] weights = new double[6];
        
        for(String key:hm.keySet()){
            int v = hm.get(key);
            /*double wt = (double)v/(double)total;
            weights[k]=wt;
            if(k<6){
            k++;}*/
            System.out.println(key+" "+v);
        }
        System.out.println("count:"+count);
        k=0;
        FileWriter fw;
        try{
            
            fw = new FileWriter("D:\\paypal project\\analysis\\opfile12.txt");
            
            BufferedWriter bw = new BufferedWriter(fw);
            
            for(String key:hm.keySet()){
                //System.out.println(key+": "+hm.get(key));
                bw.write(key+": "+hm.get(key)+" \n");
                
            }
            
            /*for(String key:hm.keySet()){
                //System.out.println(key+": "+hm.get(key));
                bw.write(key+": "+hm.get(key)+" weightage:"+weights[k]+" \n");
                if(k<6){
                    k++;}
            }*/
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
