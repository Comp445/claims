import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//class that runs first phase of TPMMS
public final class PhaseOne {
	 //variables
	static String line;//buffer to hold line of the file
	static List<String> buffer =  new ArrayList<String>(); //hold a list of CID and  Amount-paid as one string per tuple per
	static int tuples = 0;//number of tuples to insert in buffer
	static int fileCounter=0; /*used to name and count files saved on disk,
	 							NOTE will hold one more than then actual number of files created because count starts at one but file name starts at 0 */
	static int ioCount = 0 ;//used for calculating IO in phase 1
	static int numTuples=0;//used to count the number of tuples in input file
	
	//private constructor and final class all methods are static to simulate static class like in C++
	private PhaseOne(int heapSize, String fileName){
	}	
	
	//starts the phase one process
	public static void start() {

	try {
		//will enter the loop if there's any data in file		
		xFiles();	
		System.out.println("Phase one started");
		//oneFile();
      
    } catch(IOException io) {
       io.printStackTrace();
    	}
	}

	private static void writeTo_xFile() throws IOException
	{//sorts and writes to file
		Path path = Paths.get("f"+fileCounter);
//		List<String> sortedBuffer =buffer.stream()
//			.sorted((x,y)-> x.substring(18,27).compareTo(y.substring(18, 27)) )
//			.collect(toList());
		buffer.sort((x,y)-> x.substring(18,27).compareTo(y.substring(18, 27)));
		Files.write(path,buffer);
		/**
		 * I added a counter here
		 */
		ioCount++;
		fileCounter++;
	}

	private static void xFiles() throws IOException
	{
		//open input file to stream
		BufferedReader file = Files.newBufferedReader(Paths.get("input.txt"));
		
		/**
		 * I added a counter here
		 */
		ioCount++;
		while((line = file.readLine())!= null )
		{//this loop limits the buffer depending on heap size
			while(line!=null && tuples <Main.maxTuples)
			{
				buffer.add(line);//adds string to buffer
				tuples++;//keeps count of tuples in buffer
				line = file.readLine();//read new line
				numTuples++;
			}
			if(line!=null)//inserts last tuple in buffer if count is not reached
			{
				buffer.add(line);
				numTuples++;
			}
			writeTo_xFile();//write the files in sorted order
			tuples=0;//reset tuple count to one
			buffer.clear();//clear buffer to get it ready to fill again if necessary			
		}		
	}		
}//endof PhaseOne


	
