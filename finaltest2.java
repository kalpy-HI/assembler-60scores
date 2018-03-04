import java.io.*;
import java.util.*;
import java.lang.*;

public class finaltest2{
    static int location = 0, endaddr = 0, startaddr = 0, thefirst = 0,whereline = 5;
    static String name = "";
    static Integer symval = 0;
    static String filename1;

    static Hashtable<String,Integer> symtab = new Hashtable<String,Integer>();
    static Hashtable<String,String> optab = new Hashtable<String,String>();

    public static void main(String [] argv) throws IOException {
        //optab start
        optab.put("ADD","18");
        optab.put("AND","40");
        optab.put("COMP","28");
        optab.put("DIV","24");
        optab.put("J","3C");
        optab.put("JEQ","30");
        optab.put("JGT","34");
        optab.put("JLT","38");
        optab.put("JSUB","48");
        optab.put("LDA","00");
        optab.put("LDCH","50");
        optab.put("LDL","08");
        optab.put("LDX","04");
        optab.put("MUL","20");
        optab.put("OR","44");
        optab.put("RD","D8");
        optab.put("RSUB","4C");
        optab.put("STA","0C");
        optab.put("STCH","54");
        optab.put("STL","14");
        optab.put("STSW","E8");
        optab.put("STX","10");
        optab.put("SUB","1C");
        optab.put("TD","E0");
        optab.put("TIX","2C");
        optab.put("WD","DC");
        //optab end

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your filename : ");
        String filename = sc.nextLine();

        FileReader fr = new FileReader(filename);
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
                /*String Hex = Integer.toHexString(location);
                System.out.println(symbol+" "+Hex.toUpperCase());
                */

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
        }
        //pass1 end

        //pass2 start
        // here is first line
		thefirst=0;
        FileReader fr2 = new FileReader(filename);
        BufferedReader br2 = new BufferedReader(fr2);
        line = br2.readLine();
        StringTokenizer st = new StringTokenizer(line);

        if(st.countTokens()==3){
            String symbol = st.nextToken();
            if(thefirst==0){
                name = symbol;
                thefirst = 1;
            }
            String op = st.nextToken();
            if(op.equals("START")){
                startaddr = Integer.parseInt(st.nextToken(),16);
                location = startaddr;
                input.write(whereline+"\t"+Integer.toHexString(startaddr).toUpperCase()+"\t"+line+"\r\n");
                whereline+=5;
            }
        }

        for(;(line = br2.readLine())!= null;){
            StringTokenizer st1 = new StringTokenizer(line);
            int many = st1.countTokens();
            String token1 = st1.nextToken();

            if(token1.charAt(0)=='.'){
                input.write(whereline+"\t"+"    "+"\t"+line+"\r\n");
                whereline+=5;
                continue;
            }
			if(many==1){
                if(token1.equals("RSUB")){
                    input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+optab.get(token1)+"0000"+"\r\n");
                    location += 3 ;
                    whereline+=5;
                }
                continue;
			}
            String token2 = st1.nextToken();

            //3 tokens
            if(many==3){
                if(token2.equals("BYTE")){
                    String token3 = st1.nextToken();
                    if(token3.charAt(0)=='X'){
                        input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+token3.substring(2,token3.length()-1)+"\r\n");
                        location += (token3.length()-3)*4/8;
                    }
                    else {
                        String a = "";
                        for(int i=2;i<token3.length()-1;i++){
                            a = a + Integer.toHexString((int)token3.charAt(i)) ;
                        }
                        input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+a.toUpperCase()+"\r\n");
                        location += token3.length()-3;
                    }
                }
                else if(token2.equals("WORD")){
                    String token3 = st1.nextToken();
                    input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+String.format("%06X",Integer.parseInt(token3))+"\r\n");
                    location += 3;
                }
                else if(token2.equals("RESW")){
                    input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+"\r\n");
                    location += 3*Integer.parseInt(st1.nextToken());
                }
                else if(token2.equals("RESB")){
                    input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+"\r\n");
                    location += Integer.parseInt(st1.nextToken());
                }
                else if(optab.containsKey(token2)){
                        String token3 = st1.nextToken();
                        if(symtab.containsKey(token3)){
                            String opcode = optab.get(token2);
                            symval = symtab.get(token3);
                            if(token3.charAt(token3.length()-2)==','){
                                int a = 32768 + symval;
                                input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+opcode+Integer.toHexString(a).toUpperCase()+"\r\n");
                            }
                            else input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+opcode+Integer.toHexString(symval).toUpperCase()+"\r\n");
                            location += 3;
                        }
                }
            }

            //2 tokens
            else if(many==2){
                     if(optab.containsKey(token1)){
                        String opcode = optab.get(token1);

                        if(token2.charAt(token2.length()-2)==','){
							symval = symtab.get(token2.substring(0,token2.length()-2));
                            int a = 32768 + symval;
                            input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+opcode+Integer.toHexString(a).toUpperCase()+"\r\n");
                        }
                        else{
                            symval = symtab.get(token2);
                            input.write(whereline+"\t"+Integer.toHexString(location)+"\t"+line+"\t"+opcode+Integer.toHexString(symval).toUpperCase()+"\r\n");
                        }
                        location += 3;
                    }
                    if(token1.equals("END")){
                        input.write(whereline+"\t"+"    "+"\t"+line+"\r\n");
                    }
            }
            whereline+=5;
        }
        input.close();
    }
}
