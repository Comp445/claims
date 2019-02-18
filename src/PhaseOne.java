import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.*;

//class that runs first phase of TPMMS
public final class PhaseOne {
	 //variables
	static String line;//buffer to hold line of the file
	static List<String> buffer =  new ArrayList<String>(); //hold a list of CID and  Amount-paid as one string per tuple per
	static int tuples = 1;//number of tuples to insert in buffer
	static int fileCounter=0; /*used to name and count files saved on disk,
	 							NOTE will hold one more than then actual number of files created because count starts at one but file name starts at 0 */
	static int iocount = 0 ;//used for calculating IO in phase 2
	
	//private constructor and final class all methods are static to simulate static class like in C++
	private PhaseOne(int heapSize, String fileName){
	}	
	
	//starts the phase one process
	public static void start() {

	try {
		//will enter the loop if there's any data in file
		System.out.println("Phase one started");
		xFiles();	
		//oneFile();
      
    } catch(IOException io) {
       io.printStackTrace();
    	}
	}

	private static void writeTo_xFile() throws IOException
	{//sorts and writes to file
		Path path = Paths.get("f"+fileCounter);
		List<String> sortedBuffer =buffer.stream()
			.sorted()
			.collect(toList());
		Files.write(path, sortedBuffer);
		/**
		 * I added a counter here
		 */
		iocount++;
		fileCounter++;
	}
//	private static void writeTo_OneFile() throws IOException
//	{
//		List<String> sortedBuffer =buffer .stream()
//				.sorted()
//				.collect(toList());
//		Files.write(Paths.get("outPut.txt"),(Iterable<String>)sortedBuffer::iterator,StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//	}
	//writes multi-files of buffer size each
	private static void xFiles() throws IOException
	{
		//open input file to stream
		BufferedReader file = Files.newBufferedReader(Paths.get("input.txt"));
		/**
		 * I added a counter here
		 */
		iocount++;
		while((line = file.readLine()) != null )
		{//this loop limits the buffer depending on heap size
			while(line!=null && tuples <Main.maxTuples)
			{
				buffer.add(line.substring(18,27)+ line.substring(241,250));//adds string to buffer
				tuples++;//keeps count of tuples in buffer
				line = file.readLine();
			}
			if(line!=null)//inserts last tuple in buffer if count is reached
				buffer.add(line.substring(18,27)+ line.substring(241,250));
			
			writeTo_xFile();//write the files in sorted order
			tuples=1;//reset tuple count to one
			buffer.clear();//clear buffer to get it ready to fill again if necessary			
		}
		
	}		
	//write one file of buffers using seek
//	private static void oneFile() throws IOException
//	{
//		//open input file to stream
//		BufferedReader file = Files.newBufferedReader(Paths.get("input.txt"));
//		while((line = file.readLine()) != null )
//		{
//			while(line!=null && tuples <400)
//			{
//				buffer.add(line.substring(18,27)+ line.substring(241,250));//adds string to buffer
//				tuples++;//keeps count of tuples in buffer
//				line = file.readLine();
//			}
//			if(line!=null)//inserts last tuple in buffer if count is reached
//				buffer.add(line.substring(18,27)+ line.substring(241,250));
//			
//			writeTo_OneFile();//write the files in sorted order
//			tuples=0;//reset tuple count to zero
//			buffer.clear();//clear buffer to get it ready to fill again if necessary	
//		}		
//	}
	
}//endof PhaseOne


	
