

public class Main {

	
			
	public static void main(String[] args) {
		
		PhaseOne.start();
		final long startTime =System.currentTimeMillis();
		PhaseTwo.start();
		final long endTime =System.currentTimeMillis();
		System.out.println("Execution Time: " + ((endTime-startTime)/1000.0) + " secs");
		FinalPrint.start();		
	}

}
