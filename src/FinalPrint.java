import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

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
			BufferedReader FileR = Files.newBufferedReader(Paths.get("f"+PhaseOne.fileCounter));//opens our final file for printing
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
