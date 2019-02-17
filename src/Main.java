import java.lang.management.ManagementFactory;
import java.util.List;

public class Main {

	static int maxTuples; // Maximum amount of files depending on heap size
	static int maxFiles; // maximum amount of files that can be opened depending on heap size
			
	public static void main(String[] args) {
		
		//check the VM heap argument
		List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		if(inputArguments.get(0).contentEquals("-Xmx5m"))
		{
			maxTuples = 45000;
			maxFiles = 150;
			System.out.println("Using -Xmx5m heapsize");
		}
		else if(inputArguments.get(0).contentEquals("-Xmx10m"))
		{
			maxTuples = 90000;
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
		
		
		PhaseOne.start();
		System.out.println("Number of disk I/Os for a block of 100 tuples in Phase One:" + PhaseOne.iocount);
		final long startTime =System.currentTimeMillis();
		PhaseTwo.start();
		System.out.println("Number of disk I/Os for a block of 100 tuples in Phase Two:" + PhaseTwo.iocount);
		final long endTime =System.currentTimeMillis();
		System.out.println("Execution Time: " + ((endTime-startTime)/1000.0) + " secs");
		FinalPrint.start();		
	}

}
