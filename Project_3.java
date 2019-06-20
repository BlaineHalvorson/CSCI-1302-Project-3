package Project3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


// I need to implement an if statement that will only search for the first column of names if M is typed for gender and will 
// only search the second column if F is typed. AKA separate the line returned into tokens. I also need to find a way to ensure
// that partial names aren't returned. ex Brian returns Brianna.

public class Project_3 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Boolean again = null;

		do{

			System.out.print("Enter the year: ");
			int year = scan.nextInt();
			System.out.print("Enter the gender: ");
			String gender = scan.next();
			String name = scan.nextLine();
			System.out.print("Enter the name: ");
			name = scan.nextLine();
			System.out.println("");

			try {
				System.out.println(matchFound(year, gender, name));
				try(FileWriter fw = new FileWriter("searchResults.txt", true);
						PrintWriter out = new PrintWriter(fw)) {
					Date now = new Date();
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy 'at' hh:mm:ss");
					out.println("Query Info: " + format.format(now) + " " + matchFound(year, gender, name));
					fw.close();
				} catch (IOException e) {
					e.getMessage();
				}
			}
			catch(Exception e)
			{
				System.out.println(name + " is not on record for that year. \n");
			}//Try/Catch name not found
			System.out.println("Would you like to search another name? Y/N ");
			String choice = scan.nextLine();
			switch (choice) {
			case "y":
				again = true;
				break;
			case "Y":
				again = true;
				break;
			case "n":
				System.out.println("Have a nice day!");
				again = false;
				break;
			case "N":
				System.out.println("Have a nice day!");
				again = false;
			default:
				System.out.println("Invalid input \n Would you like to search another name? Y/N ");
				again = null;
			}
		}while(again);
	}//Main

	public static String matchFound(int year, String gender, String name) {

		try {
			URL page = new URL("http://liveexample.pearsoncmg.com/data/babynameranking" + year + ".txt");
			URLConnection connection = page.openConnection();
			InputStream stream = (InputStream) connection.getInputStream();
			Scanner reader = new Scanner(new InputStreamReader(stream));

			while(reader.hasNextLine()) {
				String currentLine = reader.nextLine();
				String [] line = currentLine.split("\t");
				String fname = line[3].trim();
				String mname = line[1].trim();
				if(fname.equalsIgnoreCase(name)) {
					if(gender.equalsIgnoreCase("f")) {
						String[] token = currentLine.split("\t");
						token[3].trim();
						//												for (int i = 0; i < token.length; i++) {//------------------------------Used For Testing -----------------------------//
						//													System.out.println(token[i]);
						//												}
						if(token[3].contains(name)) {
							return name + " was ranked #" + token[0] + "in the year " + year + "\n";		

						}else {
							return "That name and gender combination did not make the list for " + year + "\n";
						}//if else name-gender error
					}//if gender female
				}
				if(mname.equalsIgnoreCase(name)) {
					if(gender.equalsIgnoreCase("m")) {
						String[] token = currentLine.split("\t");
						token[1].trim();
						//												for (int i = 0; i < token.length; i++) {//------------------------------Used For Testing -----------------------------//
						//													System.out.println(token[i]);
						//												}
						if(token[1].contains(name)) {
							return name + " was ranked #" + token[0] + "in the year " + year + "\n";							
						}else {
							return "That name and gender combination did not make the list for " + year + "\n";
						}//if else name-gender error
					}//if gender male
				}//if currentLine
			}//while reader

		} catch (IOException e) {
			e.getMessage();
		}//Try/Catch
		return name + " did not make the list for " + year + "\n";
	}//matchFound
}//Project_3
