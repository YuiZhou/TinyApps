import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileInput {

	/**
	 * @description
	 * This program is written for subtitles. 
	 * When a subtitle is faster or slower than the video, 
	 * this program can fix the subtitle file with a given offset time
	 */
	public static void main(String[] args) {
		/********************************************************
		 * Fill these two parameters before running this program
		 * 
		 *  filename - the pathname of the subtitle
		 *             the new file is "filename(1)"
		 *  offset - the time you want to change for the subtitle
		 ********************************************************/
		final String filename = "";
		final int offset = 0;
		
		Timer timer = new Timer(offset);
		try {
			Scanner fileScanner = new Scanner(new File(filename));
			
			/* create new file */
			File out = new File(filename + "(1)");
			out.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(out));
			
			/* 
			 * subtitle file is split by a count of subtitle.
			 * I use a count to count the subtitle, 
			 * for the program will not recognize the integer subtitle to index
			 */
			int count = 1;
			while(fileScanner.hasNextLine()){
				String string = fileScanner.nextLine();
				/* 
				 * find the count
				 *
				 * If there is a NumberFormatException, this is a normal text
				 */
				try{
					int getNum = Integer.parseInt(string);
					output.write(string + "\n");
					/* get a piece of subtitle, and change the timestamp */
					if(getNum == count){
						count++;
						String timeLine = fileScanner.nextLine();
						/* time1 --> time2 */
						String[] timeArr = timeLine.split(" ");
						string = timer.fixTime(timeArr[0]);
						string += " --> ";
						string += timer.fixTime(timeArr[2]);
						output.write(string + "\n");
					}
				}catch(NumberFormatException e){
					output.write(string + "\n");
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR:"+e.getMessage());
			System.exit(1);
		} catch (IOException e1) {
			System.err.println("ERROR:"+e1.getMessage());
		}

	}
	
	

}
