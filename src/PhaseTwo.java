import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

//class that runs second phase of TPMMS
public final class PhaseTwo {
	//variables
	static List<BufferedReader> filePointers = new ArrayList<BufferedReader>();//used to hold pointers to file
	static List<String> buffer =  new ArrayList<String>(); //hold a string value from each file
	static int passes = 0;//hold number of passes phase two will do
	static int endIndex = 0;//used to hold the end index value of the buffer files
	static int end = PhaseOne.fileCounter<=150 ? PhaseOne.fileCounter : 150;//used to hold the end index value of the buffer files
	
	//private constructor and final class all methods are static to simulate static class like C++
	private PhaseTwo() {
	}
	
	public static void start() 
	{//holds a pointer to each file and write one big file in sorted order
		System.out.println("Phase two started");
		try {
			xFiles();
			
		}catch(IOException io) {
		       io.printStackTrace();
		    }	

	}//end of start
	
	private static void xFiles() throws IOException
	{
		boolean flag = true; //used to see if all files created were merged or not
		while(flag)
		{//create pointer to each file created in phase one up to heap size
			for(int i=0; i<end;i++)
			{
				filePointers.add(Files.newBufferedReader(Paths.get("f"+(i+endIndex))));
				buffer.add(filePointers.get(i).readLine());
			}
			//merge files from buffer using filePointers
			merge();
			
			//will go around again if not fully merged			
			passes++;//increase pass number 
			endIndex = endIndex+150 <PhaseOne.fileCounter+passes-1 ? endIndex+150: endIndex+PhaseOne.fileCounter+passes-1; //this calculates the last file index
			end = end - endIndex; //get the end of the next forloop
			flag = endIndex < PhaseOne.fileCounter ? true:false;//this will end loop if greater then number of files	
			buffer.clear();//clear buffer ready for next round
			filePointers.clear();//clear the file pointers ready for next round
		}
	
		System.out.println(passes);
	}
	//merges files writes to new file 
	private static void merge() throws IOException 
	{//variables
		double leastValue ; //holds the max value in the buffer
        int leastIndex ;	//the index of the buffer where the max value exists
        boolean elementsLeft=true;//the loop continues while this is true
        FileWriter file = new FileWriter("f"+(PhaseOne.fileCounter+passes));
        BufferedWriter writer = new BufferedWriter(file);
        
		while(elementsLeft)
		{//for statement to find max value and index of max value
			leastValue = Double.parseDouble(buffer.get(0).substring(10));
			leastIndex = 0;
	        for (int i = 1; i < buffer.size(); i++) {
	            if (Double.parseDouble(buffer.get(i).substring(10)) < leastValue) {//enters if only if value is greater
	                leastValue = Double.parseDouble(buffer.get(i).substring(9));
	                leastIndex = i;
	            }
	    	}//endof for loop
            //write max to new file and numbered at end
            writer.write(buffer.get(leastIndex)+"\n");
            //get next round ready
            String newValue =filePointers.get(leastIndex).readLine();//get new value from the specific file
            if (newValue==null)//check to see if endof file is reached
            {//if reached remove value and pointer
            	filePointers.remove(leastIndex); //remove pointer from filePointer
            	buffer.remove(leastIndex); //remove value from index
            }
            else//put in new value in buffer
            {	
            	buffer.set(leastIndex, newValue);
            }
            leastValue=0;
            leastIndex = -1;
            if (buffer.size()==0)//if all values are removed end while loop
            	elementsLeft = false;
	        
		}//endof while loop
		writer.close();
	}
		
}//end of class
