import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.*;

//class that runs first phase of TPMMS
public final class PhaseOne {
	 //variables
	final int maxTupple;//holds the max number of tuple depending on heap size
	final String fileName;
	static String line;//buffer to hold line of the file
	static List<String> buffer =  new ArrayList<String>(); //hold a list of CID and  Amount-paid as one string per tuple per
	static int tuples = 0;//number of tuples to insert in buffer
	static int fileCounter=0; //used to name and count files saved on disk
	
	//private constructor and final class all methods are static to simulate static class like in C++
	private PhaseOne(int heapSize, String fileName){
		maxTupple = heapSize;
		this.fileName = fileName;
	}	
	
	//starts the phase one process
	public static void start() {
	try {//open input file to stream
		BufferedReader file = Files.newBufferedReader(Paths.get("input.txt"));
		//will enter the loop if there's any data in file
		while((line = file.readLine()) != null )
		{//this loop limits the buffer depending on heap size
			while(line!=null && tuples <29999)
			{
				buffer.add(line.substring(18,27)+ line.substring(241,250));//adds string to buffer
				tuples++;//keeps count of tuples in buffer
				line = file.readLine();
			}
			if(line!=null)//inserts last tuple in buffer if count is reached
				buffer.add(line.substring(18,27)+ line.substring(241,250));
			
			writeToFile();//write the files in sorted order
			tuples=0;//reset tuple count to zero
			buffer.clear();//clear buffer to get it ready to fill again if necessary			
		}		
		file.close();
		
		
      
    } catch(IOException io) {
       io.printStackTrace();
    	}
	}

	private static void writeToFile() throws IOException
	{
		Path path = Paths.get("f"+fileCounter);
		List<String> sortedBuffer =buffer .stream()
			.sorted()
			.collect(toList());
		Files.write(path, sortedBuffer);
		fileCounter++;
	}
	
	
}
