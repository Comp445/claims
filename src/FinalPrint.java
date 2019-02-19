import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

//this is used to print results and as per professor will not be part of the calculation time.
public final class FinalPrint {
	//private constructor and final class all methods are static to simulate static class like C++
	
	static String lines;	//hold the lines of final output file
	static String prevLines; //holds previous line to compare with lines to group and count value
	static boolean flag;//true if there are more tuples in final file false otherwise
	
	
	private FinalPrint(){		
	}
	
	static void start()
	{
		try {
			System.out.println("Final Print started");
			BufferedReader FileR = Files.newBufferedReader(Paths.get("f"+(PhaseOne.fileCounter-1)));//opens our final file for printing
			FileWriter fileW = new FileWriter("output.txt");
	        BufferedWriter writer = new BufferedWriter(fileW);
	        prevLines = FileR.readLine();
	        prevLines = prevLines.substring(0,9)+ " $"+ prevLines.substring(9);
	        flag = prevLines !=null ? true:false;	//set the flag to true if there are tuples in file
	        
			while(flag)
			{//will compare prevLine with lines and add claim value		
				lines=FileR.readLine();
				flag = lines !=null ? true:false;
				if(flag && lines.substring(0,9).equals(prevLines.substring(0,9))) 
				{	//BigDecimal is for double not to lose accuracy
					BigDecimal value1 = new BigDecimal(prevLines.substring(11));
					BigDecimal value2 = new BigDecimal(lines.substring(9));
					String total = (value1.add(value2)).toString();//adds line and prevLine together
					prevLines = prevLines.substring(0,9)+ " $" + total;
				}
				else if(flag)
				{
					writer.write(prevLines+"\n");
					prevLines =  lines.substring(0,9)+ " $"+ lines.substring(9);;
				}
							
				
				
			}//endof while loop
			writer.write(prevLines+"\n");//write the lastline
			writer.close();
			Top10Claims();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void Top10Claims() {
		try {
			//variables
			String line;//holds the read line
			List<String> buffer =  new ArrayList<String>(10);//holds top 10 claim values
			
			BufferedReader fileR = Files.newBufferedReader(Paths.get("output.txt"));//opens our final file for printing
			FileWriter fileW = new FileWriter("top10Claims.txt");
			BufferedWriter writer = new BufferedWriter(fileW);

			
			for (int i =0; i <10;i++)//fill buffer
				buffer.add(fileR.readLine());
			while((line=fileR.readLine())!=null)
			{
				for (int i =0; i <10;i++)//check if value is bigger than any in buffer
				{
					if((Double.parseDouble(line.substring(11))) > ( Double.parseDouble(buffer.get(i).substring(11))))
					{
						buffer.set(i,line);
						break;
					}

				}
			}
			//java 8 stream for sorting and writing the top 10 claims
			buffer.stream() .sorted( (x,y)-> -Double.compare(Double.parseDouble(x.substring(11)), Double.parseDouble(y.substring(11)) ))
			.forEach(x->{
				try {
					writer.write(x+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			writer.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
