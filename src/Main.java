import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	static int maxTuples; // Maximum amount of files depending on heap size
	static int maxFiles; // maximum amount of files that can be opened depending on heap size
			
	public static void main(String[] args) {
		
		//check the VM heap argument
		List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		if(inputArguments.get(1).contentEquals("-Xmx5m"))
		{
			maxTuples =45000;
			maxFiles = 150; 
			System.out.println("Using -Xmx5m heapsize");
		}
		else if(inputArguments.get(0).contentEquals("-Xmx10m"))
		{
			maxTuples = 80000;
			maxFiles = 150;
			System.out.println("Using -Xmx10m heapsize");
		}
		else 
		{
			System.out.println("You don't understand how lab1 works!!\n"
					+ "Add VM arguement -Xmx5m or -Xmx10m and nothing else\n"
					+ "Don't be that guy \n EXITING bye");
			System.exit(0);;
		}
		
		final long startTime =System.currentTimeMillis();
		PhaseOne.start();
		System.out.println("Number of disk I/Os for blocks of 15 tuples in Phase One:" + PhaseOne.ioCount);		
		PhaseTwo.start();
		System.out.println("Number of disk I/Os for blocks of 15 tuples in Phase Two:" + PhaseTwo.iocount);
		final long endTime =System.currentTimeMillis();
		System.out.println("Execution Time: " + ((endTime-startTime)/1000.0) + " secs");
		System.out.println("Total number of disk I/Os for blocks of 15 tuples:"+ (PhaseOne.fileCounter+PhaseTwo.iocount));
		FinalPrint.start();
		
		//cleanup files
		try {
			System.out.println("Cleaning File");
			for (int i =0; i<PhaseOne.fileCounter+1; i++)
			{
				Files.deleteIfExists(Paths.get("f"+i));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("done...");
	}

}
