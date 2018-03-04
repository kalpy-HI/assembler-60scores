import java.io.*;
import java.util.*;
import java.lang.*;

public class pass2{
    static int location = 0, endaddr = 0, startaddr = 0, thefirst = 0, whereline = 5;
    static int symval = 0;
    static String name = "";
    static String filename1,filename2;

    public static void main(String[] args)throws IOException{
        FileReader fr = new FileReader("Figure2.1.txt");
		BufferedReader br = new BufferedReader(fr);

        FileWriter dataFile = new FileWriter("final.txt");
        BufferedWriter input = new BufferedWriter(dataFile);

        String line;

        //pass2 start

        // here is first line
        line = br.readLine();
        StringTokenizer st = new StringTokenizer(line);

        if(st.countTokens()==0){
                continue;
        }
        else if(st.countTokens()==3){
                String symbol = st.nextToken();
                if(thefirst==0){
                    name = symbol;
                    thefirst = 1;
                }
                String op = st.nextToken();
                if(op.equals("START")){
                    startaddr = Integer.parseInt(st.nextToken(),16);
                    location = startaddr;
                    input.write(whereline+"\t"+startaddr+"\t"+line+"\r\n");
                    whereline+=5;
                }
        }

        for(;(line = br.readLine())!= null;){
            StringTokenizer st1 = new StringTokenizer(line);
            String token1 = st1.nextToken();
            if(token1.charAt(0)=='.'){
                input.write(whereline+"\t"+"    "+"\t"+line+"\r\n");
                whereline+=5;
                continue;
            }
            String token2 = st1.nextToken();

            //3 tokens
            if(st1.countTokens()==3){
                if(token2.equals("BYTE")){
                    String token3 = st1.nextToken();
                    if(token3.charAt(0)=='X'){
                        input.write(whereline+"\t"+location+"\t"+line+"\t"+token3.substring(2,token3.length()-1)+"\r\n");
                        location += (token3.length()-3)*4/8;
                    }
                    else {
                        String a = "";
                        for(int i=2;i<token3.length()-1;i++){
                            a = Integer.toHexString((int)token3.charAt(i))+ a;
                        }
                        input.write(whereline+"\t"+location+"\t"+line+"\t"+a.toUpperCase()+"\r\n");
                        location += token3.length()-3;
                    }
                }
                else if(token2.equals("WORD")){
                    String token3 = st1.nextToken();
                    input.write(whereline+"\t"+location+"\t"+line+"\t"+String.format("%06d",Integer.parseInt(token3))+"\r\n");
                    location += 3;
                }
                else if(token2.equals("RESW"){
                    location += 3*Integer.parseInt(st.nextToken());
                }
                else if(token2.equals("RESB")){
                    location += Integer.parseInt(st.nextToken());
                }
                else if(optab.containsKey(token2)){
                        String token3 = st1.nextToken();
                        if(symtab.containsKey(token3)){
                            String opcode = optab.get(token2);
                            symval = symtab.get(token3);
                            if(token3.charAt(token3.length()-2)==','){
                                symval = 32768 + symval;
                            }
                            input.write(whereline+"\t"+location+"\t"+line+"\t"+opcode+Integer.toHexString(symval).toUpperCase()+"\r\n");
                            location += 3;
                        }
                }
            }

            //2 tokens
            else if(st1.nextTokens()==2){
                     if(optab.containsKey(token1)){
                        String opcode = optab.get(token1);
                        symval = symtab.get(token2);
                        if(token2.charAt(token2.length()-2)==','){
                            symval = 32768 + symval;
                        }
                        input.write(whereline+"\t"+location+"\t"+line+"\t"+opcode+Integer.toHexString(symval).toUpperCase()+"\r\n");
                        location += 3;
                     }
            }
            whereline+=5;
        }
        input.close();
    }
}
