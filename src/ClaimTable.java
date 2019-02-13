import java.util.Date;
import java.text.DecimalFormat;
/**
 * 
 * @author negar
 *
 * This class implements the Claim table/relation mentioned in project description
 */
public class ClaimTable {

	/**
	 * Following is the schema of the table
	 * CNumber int this is the primary key in the table
	 * CDate Date  ”YYYY-MM-DD”
	 * CID int something like the social insurance number
	 * CName char is the client name
	 * CAddress char(28) 
	 * CEmail Address char(28)  is client's email
	 * Insured-Item Integer (2)  Insured-Item is a 2 digit number which indicates the item insured
	 * Amount-Damage Dec(9,2) Amount-Damage shows the assessment of the company of the damage to the item insured
	 * Amount-Paid Dec(9,2) shows what the client is being paid to compensate the damage
	 */
	
	int CNumber;
	Date CDate;
	int CID;
	char[] CName;
	char[] CAddress;
	char[] CEmail;
	Integer Insured_Item;
	public ClaimTable() {
		CName = new char[25];
		CAddress = new char[150];
		CEmail = new char[28];
		
	}
}
