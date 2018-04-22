
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mukul
 */
public class ReqHandle {
    
    static int port = 8000;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		if(args.length==1){
        // TODO code application logic here
    try{
        HttpServer server = HttpServer.create(new InetSocketAddress(port),0);
        System.out.println("server started at "+port);
        server.createContext("/",new RootHandler());
        server.createContext("/echoHeader",new EchoHeaderHandler());
        server.createContext("/echoHeader1",new EchoGetHandler());
        server.createContext("/echoHeader2",new EchoPostHandler(args[0]));
        server.setExecutor(null);
        server.start();
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }
	else{
		System.out.println("Usage:\nRequired path of one file\n1)File containing weights");
	}
	}
    
    static class RootHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange he) throws IOException {
        String response = "<h1>Server start success</h1>"+"<h1>port:"+port+"</h1>";
        he.sendResponseHeaders(200,response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

    static class EchoHeaderHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange he) throws IOException {
            Headers headers = he.getRequestHeaders();
            Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
            String response = "";
            for(Map.Entry<String, List<String>> entry : entries)
                response+=entry.toString()+"\n";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }
    
    static class EchoGetHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange he) throws IOException {
            Map<String,Object> parameters = new HashMap<String,Object>();
            URI requestedUri = he.getRequestURI();
            String query = requestedUri.getRawQuery();
            //parseQuery(query,parameters);
            
            String response="";
            for(String key:parameters.keySet()){
                response+=key+"="+parameters.get(key)+"\n";
            }
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }
    
    static class EchoPostHandler implements HttpHandler{
		
		String path="";
		
		EchoPostHandler(String path){
			this.path=path;
		}

        @Override
        public void handle(HttpExchange he) throws IOException {
            //parse request
            Map<String,Object> parameters = new HashMap<String,Object>();
            InputStreamReader isr = new InputStreamReader(he.getRequestBody(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            System.out.println(query);
            float score = getScore(query,path);
            //System.out.println("method:"+he.getRequestMethod());
            
            //send response
            String response = "";
            for(String key:parameters.keySet())
                response+=key+"="+parameters.get(key)+"\n";
            response=response+" "+score;
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }
    
    public static float getScore(String query,String path) throws IOException{
        HashMap<String,Integer> hm = new HashMap<String,Integer>();
        String[] arr = new String[3];
        arr=query.split("&");
        int[] scores = new int[3];
        for(int i=0;i<3;i++){
            scores[i] = Integer.parseInt(arr[i].split("=")[1]);
        }
        
        FileReader fr = null;
        BufferedReader br = null;
        
        try{
            fr = new FileReader(path);
            br = new BufferedReader(fr);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        String text = br.readLine();
        String[] d = text.split(",");
        float score=0;
        
        for(int i=0;i<3;i++){
            score+=Float.parseFloat(d[i].split("=")[1])*scores[i];
        }
        
        return score;
    }
}