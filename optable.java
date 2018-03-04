import java.io.*;
import java.util.*;
import java.lang.*;

input.write(whereline+"\t"+location+"\t"+line+"\t"+ +"\r\n");

public class optable{
    public static void main(String[] args){
        static Hashtable<String,String> optab = new Hashtable<String,String>();

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
        optab.put("RD",'D8");
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
    }
}
