
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author mukul
 */
public class BasicImpl {
    
    
    
    public void readJSON() throws Exception{
        //String jsonData = readFile("G:\\paypal project\\analysis\\abc.json");
        
        FileReader fr = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        
        try{
            fr=new FileReader("D:\\paypal project\\analysis\\Video_Games_5.json");
             br = new BufferedReader(fr);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        String text = null;
        
        while((text=br.readLine())!=null){
        JSONObject obj = new JSONObject(text);
        //JSONArray arr = obj.getJSONArray("data");
        FileWriter fw;
        try{
            
            fw = new FileWriter("D:\\paypal project\\analysis\\revfile2.txt",true); //true for append mode
            
            bw = new BufferedWriter(fw);
            
                bw.write(obj.getString("reviewText"));
                bw.write("\n");
                 bw.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    }

/*public String readFile(String filename) {
    String result = "";
    try {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        result = sb.toString();
    } catch(Exception e) {
        e.printStackTrace();
    }
    return result;
} */  
}