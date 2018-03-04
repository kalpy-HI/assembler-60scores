import java.io.*;
import java.util.*;
import java.lang.*;

public class pass1{
    static int location = 0, endaddr = 0, startaddr = 0, thefirst = 0;
    static String name = "";

    static Hashtable symtab = new Hashtable();

    public static void main(String [] argv) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your filename : ");
        String filename = sc.nextLine();

        FileReader fr = new FileReader("Figure2.1.txt");
		BufferedReader br = new BufferedReader(fr);

		System.out.print("Enter your output filename : ");
        String filename1 = sc.nextLine();
        FileWriter dataFile = new FileWriter(filename1);
        BufferedWriter input = new BufferedWriter(dataFile);

        String line;

        //pass1 start
        for(;(line = br.readLine())!= null;){
            StringTokenizer st = new StringTokenizer(line);
            if(st.countTokens()==0)continue;
            if(st.countTokens()==3){
                String symbol = st.nextToken();
                if(thefirst==0){
                    name = symbol;
                    thefirst = 1;
                }
                symtab.put(symbol,location);
                String Hex = Integer.toHexString(location);
                System.out.println(symbol+" "+Hex.toUpperCase());


            }
            String op = st.nextToken();
            if(op.charAt(0)=='.')continue;
            if(op.equals("START")){
                location = Integer.parseInt(st.nextToken(),16);
                startaddr = location;
                continue;
            }
            else if(op.equals("END")){
                endaddr = location;
                continue;
            }
            else if(op.equals("BYTE")){
                String bytecontent=st.nextToken();
                if(bytecontent.charAt(0)=='X'){
                    location += (bytecontent.length()-3)*4/8;
                }
                else location += bytecontent.length()-3;
            }
            else if(op.equals("WORD")){
                location += 3;
            }
            else if(op.equals("RESW")){
                location += 3*Integer.parseInt(st.nextToken());
            }
            else if(op.equals("RESB")){
                location += Integer.parseInt(st.nextToken());
            }
            else location += 3;
        }//pass1 end
    }
}
