import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File stops = new File("stops.txt");
		File stop_times = new File("stop_times.txt");
		File transfers = new File("transfers.txt");
		TST tst = new TST();
		try {
			Scanner sc = new Scanner(stops);
			sc.next(); // disregard first line as shows the format
			sc.useDelimiter("\n");
			while (sc.hasNext()) {
				String line = sc.next();
				String[] arr = line.split(",");
				for(String a: arr) {
					System.out.println(a);
				}
			}
			sc.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
