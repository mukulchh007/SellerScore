
/**
 *
 * @author mukul
 */
public class BagOfWords {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        //BasicImpl bi = new BasicImpl();
        //bi.readJSON();
        //CreateJSON cj = new CreateJSON();
        //cj.create();
		if(args.length!=3){
			System.out.println("Usage:\nenter paths of 3 files \n1)file containing words to be searched \n2)path of review file \n3)file where the weights go");
		}
		else{
        AssignWeight aw = new AssignWeight();
        aw.assign(args[0],args[1],args[2]);
		}
        //AssignWt2 asw = new AssignWt2();
        //asw.assign();
        //SpellCheck sc = new SpellCheck();
        //sc.check();
    }
    
}
