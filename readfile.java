import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class readfile{
    public static void main(String [] argv) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your filename : ");
        String filename = sc.nextLine();

		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			System.out.println(br.readLine());
		}
		fr.close();
	}
}
