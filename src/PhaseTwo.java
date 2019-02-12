import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//class that runs second phase of TPMMS
public final class PhaseTwo {
	//variables
	static List<BufferedReader> filePointers = new ArrayList<BufferedReader>();//used to hold pointers to file
	static List<String> buffer =  new ArrayList<String>(); //hold a string value from each file
	
	
	//private constructor and final class all methods are static to simulate static class like C++
	private PhaseTwo() {
	}
	
	public static void start() 
	{//holds a pointer to each file and write one big file in sorted order
		System.out.println("Phase two started");
		try {
			xFiles();
			System.out.println(buffer);		
					
		}catch(IOException io) {
		       io.printStackTrace();
		    }	

	}//end of start
	
	private static void xFiles() throws IOException
	{
		boolean flag = true; //used to see if all files created were merged
		int start=0;
		int end = PhaseOne.fileCounter<=150 ? PhaseOne.fileCounter:150;
		int passes = 0;
		
		while(flag)
		{//create pointer to each file created in phase one up to heap size
			for(int i=start; i<end;i++)
			{
				filePointers.add(Files.newBufferedReader(Paths.get("f"+i)));
				buffer.add(filePointers.get(i).readLine());
			}
			//merge files from buffer
			
			//will go around again if not fully merged
			flag = end < PhaseOne.fileCounter ? true:false;
			start = end;
			end = end+150 <PhaseOne.fileCounter ? end+150: PhaseOne.fileCounter;
			passes++;
			buffer.clear();
		}
	
		System.out.println(passes);
	}
	
	
	
}//end of class
