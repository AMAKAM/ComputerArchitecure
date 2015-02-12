import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Start 
{
	public static ArrayList<Integer> R_Register = new ArrayList<Integer>();
	public static ArrayList<Float> F_Register = new ArrayList<Float>();
	public static ArrayList<Float> Memory_Content=new ArrayList<Float>();
	public static Set<Integer> Memory_Content_Modified=new LinkedHashSet<Integer>();
	public static ArrayList <String> line_input =new ArrayList <String>();
	public static ArrayList <String> Instruction_Sequence =new ArrayList <String>();
	public static ArrayList<ArrayList<String>> Instruction_Sequence_string =new ArrayList<ArrayList<String>>();

	public static Set <Integer> Finished_Instructions = new LinkedHashSet<Integer>(); 

	public static int current_instruction_line=1;
	public static int max_line_finished=0;
	public static boolean YEP_ITS_ALL_OVER=false;
	public static int clock_cycle;
	//object ArrayList
	public static ArrayList<IF>   IF_ =new  ArrayList <IF>();
	public static ArrayList<ID>   ID_ =new  ArrayList <ID>();
	public static ArrayList<EX>   EX_ =new  ArrayList <EX>();
	public static ArrayList<A1>   A1_ =new  ArrayList <A1>();
	public static ArrayList<A2>   A2_ =new  ArrayList <A2>();
	public static ArrayList<M1>   M1_ =new  ArrayList <M1>();
	public static ArrayList<M2>   M2_ =new  ArrayList <M2>();
	public static ArrayList<M3>   M3_ =new  ArrayList <M3>();
	public static ArrayList<M4>   M4_ =new  ArrayList <M4>();
	public static ArrayList<MEM>  MEM_=new  ArrayList <MEM>();
	public static ArrayList<WB>   WB_=new  ArrayList <WB>();

	static File File_clock_cycles;
	static File File_FP_registers;
	static BufferedWriter bw;
	static FileWriter fw ;
	static BufferedWriter bw1;
	static FileWriter fw1;
	static PrintWriter fwx;
	static FileWriter writer;
	
	public static void main(String[] args) throws Exception
	{
		
		initialize_all_register();
		Fileread file_handler = new Fileread("input_file.txt");					//read the file
		//Fileread file_handler = new Fileread(read_file_name("of the input_file.txt"));
		file_handler.closed();
		initialize_all_Instruction_Sequence();
		initialize_all_objects();
		//System.out.println("Number_of)lines:"+line_input.size()-1);
		start_the_execution();
		System.out.println();
		//System.out.println(String.valueOf(EX_.get(1).get_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register()));
		print_status_of_objects();
		//print_status_of_objects();		
		//print_ID();
		max_line_finished=3;
		//Find_RAW("3", 7);
		print_ID();
		//String operand_reference=ID_.get(7).return_operand3();
		//System.out.println(operand_reference.equals(ID_.get(6).return_operand1()) && !ID_.get(6).return_is_instruction_Store());
		System.out.println("Halt");
		print_all_R_Register();
		
		
		
		// creating the new output file
	 	File_clock_cycles= new File((get_file_name("clock cycle output")));    	
	 	if (!File_clock_cycles.exists()) 
	 		File_clock_cycles.createNewFile();
	 	writer = new FileWriter(File_clock_cycles, true);  
	 	fwx = new PrintWriter(writer);  
	 	File_FP_registers= new File(get_file_name("Register output"));    	
	 	if (!File_FP_registers.exists()) 
	 		File_FP_registers.createNewFile();
	 	fw1= new FileWriter(File_FP_registers.getAbsoluteFile());
	 	bw1= new BufferedWriter(fw1);
		print_all_F_Register();
		print_Instruction_Sequence();

		}
		//print_all_Memory_Modified();			
	
	
	//Memory_Content.add(32,17.8f);
	//print_all_Memory_content();
	
	
	public static void start_the_execution() throws Exception
	{
		//&& WB_.get(line_input.size()).return_is_this_stage_completed() 
		for(clock_cycle=0;!YEP_ITS_ALL_OVER ;clock_cycle++)
		 {
			//print_status_of_objects();
			IF.occupied=false;
			ID.occupied=false;
			EX.occupied=false;
			A1.occupied=false;
			A2.occupied=false;
			M1.occupied=false;
			M2.occupied=false;
			M3.occupied=false;
			M4.occupied=false;
			MEM.occupied=false;
			WB.occupied=false;
			int start_at_line=max_line_finished+1;;
			System.out.print("\nclock_cycle:"+clock_cycle);
			for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					if(MEM_.get(i).return_is_this_stage_completed() && !WB_.get(i).return_is_this_stage_completed())	
						if(!WB.occupied)
								{
									WB_.set(i, new WB(true,i));
									//System.out.print(i +"T ");																	
								}
						else	
								{
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
									//System.out.print(i +"F ");
								}		
				}
			
			
			//System.out.print("END OF WRITE-->");	
				for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					/*if(clock_cycle>5 && current_instruction_line>4 )
						{
						System.out.print("CC:"+clock_cycle);
						System.out.print(" Checking line:"+i);
						System.out.print(" MaxFinished:"+max_line_finished);
						System.out.print(" "+"Started at:"+start_at_line+" "+"UpperBound:"+current_instruction_line);
						System.out.print(" "+"Finished_Instructions:"+Finished_Instructions);
						System.out.println(" "+"Maximum_Element:"+WB.find_max(Finished_Instructions));
						System.out.print(" T->"+(ID_.get(i).return_is_in_scope() && !MEM_.get(i).return_is_in_scope()));
						System.out.print(" F->"+(ID_.get(i).return_is_instruction_Load()  || ID_.get(i).return_is_instruction_Store()));
						System.out.print(" F->"+	ID_.get(i).return_is_instruction_Add());
						System.out.print(" T->"+ID_.get(i).return_is_instruction_Multiply());
						System.out.println(" T->"+M4_.get(i).return_is_in_scope());
						}*/
					if(ID_.get(i).return_is_this_stage_completed() && !MEM_.get(i).return_is_this_stage_completed())	
					{
						if(ID_.get(i).return_is_instruction_Load()  || ID_.get(i).return_is_instruction_Store())
						{
							
							if(EX_.get(i).return_is_this_stage_completed())
							{
								if(!MEM.occupied)
								{
									MEM_.set(i,new MEM(true,i,ID_.get(i).return_instruction_word()));
								}
								else
								{	
									MEM_.get(i).set_scope();
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
								}
							}
						}
						if(ID_.get(i).return_is_instruction_Add())
						{
					
							if(A2_.get(i).return_is_this_stage_completed())
							{
								if(!MEM.occupied)
								{
									MEM_.set(i,new MEM(true,i,ID_.get(i).return_instruction_word()));
								}
								else
								{
									MEM_.get(i).set_scope();
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
								}
							}
						}
						if(ID_.get(i).return_is_instruction_Multiply())
						{
							if(M4_.get(i).return_is_this_stage_completed())
							{
								
								if(!MEM.occupied)
								{	
									MEM_.set(i,new MEM(true,i,ID_.get(i).return_instruction_word()));
								}		
								else
								{
									MEM_.get(i).set_scope();
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
								}
							}	
						}		
					}				
				}
			//System.out.print("END OF MEM-->");
				for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					if(M3_.get(i).return_is_this_stage_completed() && !M4_.get(i).return_is_this_stage_completed())	
						if(!M4.occupied)
								{
									M4_.set(i, new M4(true,i));
									//System.out.print(i +"T ");																	
								}
						else	
								{
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
									//System.out.print(i +"F ");
								}	
				}
			//System.out.print("\n M4 ");
				for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					if(M2_.get(i).return_is_this_stage_completed() && !M3_.get(i).return_is_this_stage_completed())	
						if(!M3.occupied)
								{
									M3_.set(i, new M3(true,i));
									//System.out.print(i +"T ");																	
								}
						else	
								{
							    	Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
							    	//System.out.print(i +"F ");
								}	
				}
			//System.out.print("\n M3 ");
				for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					if(M1_.get(i).return_is_this_stage_completed() && !M2_.get(i).return_is_this_stage_completed())	
						if(!M2.occupied)
								{
									M2_.set(i, new M2(true,i));
									//System.out.print(i +"T ");																	
								}
						else	
								{
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
									//System.out.print(i +"F ");
								}	
				}
			//System.out.print("\n M2 ");		
				for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					if(ID_.get(i).return_is_this_stage_completed() && !M1_.get(i).return_is_this_stage_completed())	
						if(ID_.get(i).return_is_instruction_Multiply())
							{
								if(!M1.occupied)
								{
									M1_.set(i, new M1(true,i));
									//System.out.print(i +"T ");																	
								}
								else	
								{
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
									//System.out.print(i +"F ");
								}
							}
							else	
							{	
								//System.out.print(i +"F ");						
							}											
				}
				
			//System.out.print("\n M1 ");
				for(int i=start_at_line;i<=current_instruction_line && i<line_input.size();i++)
				{
					if(A1_.get(i).return_is_this_stage_completed() && !A2_.get(i).return_is_this_stage_completed())	
						if(!A2.occupied)
								{
									A2_.set(i, new A2(true,i));
									//System.out.print(i +"T ");																	
								}
						else	
								{
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
									//System.out.print(i +"F ");
								}
				}
			//System.out.print("\n A2 ");				
				for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					if(ID_.get(i).return_is_this_stage_completed() && !A1_.get(i).return_is_this_stage_completed())	
						if(ID_.get(i).return_is_instruction_Add())
							{
								if(!A1.occupied)
								{
									A1_.set(i, new A1(true,i));
									//System.out.print(i +"T ");																	
								}
								else	
								{
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");
									//System.out.print(i +"F ");
								}
							}
							else	
							{	
								//System.out.print(i +"F ");						
							}							
				}
			//System.out.print("\n A1 ");		
			for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					if(ID_.get(i).return_is_this_stage_completed() && !EX_.get(i).return_is_this_stage_completed())	
						if(ID_.get(i).return_is_instruction_Load()  || ID_.get(i).return_is_instruction_Store())
							{
								if(!EX.occupied)
								{
									EX_.set(i, new EX(true,i));
									//System.out.print(i +"T ");																	
								}
								else	
								{
									Instruction_Sequence.set(i, Instruction_Sequence.get(i)+"stall     ");		
								//System.out.print(i +"F ");
								}
							}
							else	
							{	
								//System.out.print(i +"F ");						
							}			
				}				
			//System.out.print("\n EX ");	
				for(int i=start_at_line;i<=current_instruction_line && i<line_input.size() ;i++)
				{
					if(IF_.get(i).return_is_this_stage_completed() && !ID_.get(i).return_is_this_stage_completed())
						if(!ID.occupied)
							{
								ID_.set(i, new ID(true,IF_.get(i).return_line_fetched(),i));	
								//System.out.print(i +"T ");								
							}
						else	
						{
							//System.out.print(i +"F ");
						}
					else	
					{	
						//System.out.print(i +"F ");						
					}
				}	
			
			//System.out.print("\n ID ");	
				for(int i=start_at_line;i<=current_instruction_line  && i<line_input.size() ;i++)
				{	
					if(!IF.occupied)
						{
						if(!IF_.get(i).return_is_this_stage_completed())
							{	
								IF_.set(i, new IF(true,i));
								//System.out.print(i +"T ");				
							}
						else	
							{	
								//System.out.print(i +"F ");
							}
						}
					else	
					{
						//System.out.print(i +"F ");
					}
				}
			//System.out.print("\n IF ");			
			if(current_instruction_line<line_input.size())
				current_instruction_line++;	
			else if(Finished_Instructions.contains(line_input.size()-1))
				YEP_ITS_ALL_OVER=true;
		
			
		/*for(int i=1;i<=current_instruction_line ;i++)
		{	
			if(Finished_Instructions.contains(i))
			{
			System.out.print("Line,"+i);
			System.out.println("-->"+IF_.get(i).return_line_fetched());
			System.out.print(""+ID_.get(i).return_operand1());
			System.out.print(","+ID_.get(i).return_operand2());
			System.out.print(","+ID_.get(i).return_operand3()); 
			//System.out.print(" Reg_IMM:"+ID_.get(i).return_operand3());
			System.out.print(" v1:"+ID_.get(i).return_value1());
			System.out.print(" v2:"+ID_.get(i).return_value2());
			System.out.print(" v3:"+ID_.get(i).return_value3()); 
			System.out.print(" WB:"+EX_.get(i).get_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register());
			System.out.print(" target["+EX_.get(i).return_target_address()+"]"); 	
			System.out.println(" negative_offset:"+ID_.get(i).return_has_a_negative_offset()); 
			}
		}
		print_all_R_Register();
		print_all_F_Register();
		print_all_Memory_Modified();		*/	
	
		//System.out.println("Enter something");
		//debug();	
		//System.out.print("current_instruction_line: "+current_instruction_line);
		 }
	}
	
	public static void debug() 
	{
		Scanner scan =new Scanner (System.in);
		String x=scan.next();
	}
	
	public static void print_Finished_Instructions()
	{
		System.out.println("Finished Instructions:"+ Finished_Instructions);
	}
	
	public static void print_status_of_objects()
	{
		System.out.println("\nClockCycle: "+(clock_cycle));
		System.out.printf(" %-4s ","XXXX");
		for(int i=1;i<line_input.size();i++)
			System.out.printf("%-3s", i);
		
		System.out.printf("\n %-4s ","XXXX");
		for(int i=1;i<line_input.size();i++)
			System.out.printf("%-2s ", "SR");
		
		System.out.printf("\n %-4s ","IF  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(IF_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(IF_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}
		System.out.printf("\n %-4s ","ID  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(ID_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(ID_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}	
		System.out.printf("\n %-4s ","EX  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(EX_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(EX_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}
		System.out.printf("\n %-4s ","A1  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(A1_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(A1_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}
		System.out.printf("\n %-4s ","A2  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(A2_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(A2_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}	
		System.out.printf("\n %-4s ","M1  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(M1_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(M1_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}	
		System.out.printf("\n %-4s ","M2  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(M2_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(M2_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}		
		System.out.printf("\n %-4s ","M3  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(M3_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(M3_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}		
		System.out.printf("\n %-4s ","M4  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(M4_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(M4_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}
		System.out.printf("\n %-4s ","MEM ");
			for(int i=1;i<line_input.size();i++)
			{
				if(MEM_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(MEM_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}			
		System.out.printf("\n %-4s ","WB  ");
			for(int i=1;i<line_input.size();i++)
			{
				if(WB_.get(i).return_is_in_scope())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				if(WB_.get(i).return_is_this_stage_completed())
					System.out.printf("%s","T");
				else
					System.out.printf("%s","F");
				System.out.print(" ");
			}			
			
	}
	
	public static String read_file_name(String x) throws Exception	
	{	
	String s=null;//test string
	boolean t =true;
	if(t){	
		Scanner scan_string = new Scanner(System.in); //to read a string(file) from user
		boolean file_exists=false; //tells if the string entered is actually a valid file
			while(file_exists!=true)
			{	
				System.out.println("Please Enter the file name "+ s);
				if(x.equals("of the input_file.txt"))
					System.out.println("if the textfile is named \"datafile\" enter datafile.txt ");	
				s=scan_string.next();
				File f= new File(s);
				file_exists=f.exists();
				if(file_exists==true)
					System.out.println("The File exits");
				else
					System.out.println("Sorry file does not exist");			
			}	
			//scan_string.close();
			System.out.println("Started computation");
			}	
	return s;
	}	
	


	static String get_file_name(String s)
	{
		System.out.println("Enter the file name to wish you write the file of"+s +" to");
		String file_name=null;
		boolean  file_can_be_createn=true;
		String g;
		do
		{
			System.out.println("Please enter only charactrs A-Z and a-z");
			Scanner input_recieve = new Scanner(System.in);
			g=input_recieve.next();
			Pattern p=Pattern.compile("[A-Za-z]*");
			Matcher matcher=p.matcher(g);
			System.out.println(matcher.matches());
			if(matcher.matches())
				file_can_be_createn=false;
		}while(file_can_be_createn);
		
		return g;
	}

	
	public static void initialize_all_register()
	{
		for (int i=0;i<32;i++)
		{
			R_Register.add(i, 0);
			F_Register.add(i, 0f);
		}
		for (int i=0;i<1000;i++)
		{
			Memory_Content.add(i,0f);
		}
	}
	
	public static void  initialize_all_Instruction_Sequence()
	{
		for(int i=0;i<line_input.size();i++)
			Instruction_Sequence.add(i,"");
	}
	
	public static void print_all_R_Register()
	{
		System.out.println("R_Register Contents");		
		for (int i=0;i<32;i++)
			System.out.println("R"+i+"\t"+R_Register.get(i));
	}
	
	public static void initialize_all_objects()
	{
		for(int i=0;i<line_input.size();i++)
		{
			IF_.add(i, new IF(0));
			ID_.add(i, new ID(0));
			EX_.add(i, new EX(0));
			A1_.add(i, new A1(0));
			A2_.add(i, new A2(0));
			M1_.add(i, new M1(0));
			M2_.add(i, new M2(0));
			M3_.add(i, new M3(0));
			M4_.add(i, new M4(0));
		  MEM_.add(i, new MEM(0));
			WB_.add(i, new WB(0));
			Instruction_Sequence.add(i,null);
		}
	}

	public static void print_Instruction_Sequence()
	{
		System.out.println("\n"+"INSTRUCTION-SEQUENCE");
		for(int i=1;i<=clock_cycle;i++)			
			fwx.printf("%-10d",i);
		fwx.println();
		for(int i=1;i<line_input.size();i++)
		{
			for(int j=1;j<i;j++)
				fwx.printf("%-10s","----      ");
			fwx.println(Instruction_Sequence.get(i));
		}
		fwx.close();
	}
	
	public static void print_all_F_Register() throws Exception
	{
		System.out.println("F_Register Contents");		
		for (int i=0;i<32;i++)
		{
			bw1.append("F"+i+"\t"+F_Register.get(i));
			bw1.newLine();
		}
		bw1.close();
	}
	public static void print_all_Memory_content()
	{
		System.out.println("Memory Contents");	
		for (int i=0;i<1000;i=i+8)
			System.out.println("ML["+i+"]\t"+Memory_Content.get(i));
			
	}

	public static void print_all_Memory_Modified()
	{
		for(int i:Memory_Content_Modified)
			{
			System.out.println("ML["+i+"]-->"+Memory_Content.get(i));
			}
	}
	
	public static void print_all_lines()
	{
		for(int j=1;j<line_input.size();j++)
		{
		
			System.out.println("New Line"+"j");
			Scanner scan=new Scanner(line_input.get(j));
			for(int i=0;scan.hasNext();i++)
				System.out.println();
		}
	}

	public static void print_ID()
	{
	for(int i=1;i<line_input.size();i++)
	{
		System.out.print("Line,"+i);
		System.out.println("-->"+IF_.get(i).return_line_fetched());
		System.out.print(""+ID_.get(i).return_operand1());
		System.out.print(","+ID_.get(i).return_operand2());
		System.out.print(","+ID_.get(i).return_operand3()); 
		//System.out.print(" Reg_IMM:"+ID_.get(i).return_operand3());
		System.out.print(" v1:"+ID_.get(i).return_value1());
		System.out.print(" v2:"+ID_.get(i).return_value2());
		System.out.print(" v3:"+ID_.get(i).return_value3()); 
		System.out.print(" WB:"+EX_.get(i).get_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register());
		System.out.print(" target["+EX_.get(i).return_target_address()+"]"); 	
		System.out.println(" negative_offset:"+ID_.get(i).return_has_a_negative_offset()); 
	
	}
	}

	public static String get_instruction_word(int line_number_reference)
	{
		return ID_.get(line_number_reference).return_instruction_word();
	}

	public static String return_operand(int line_number_reference,boolean true_if_operand2_false_if_operand3)
	{
		String x;
		if(true_if_operand2_false_if_operand3)
			x=ID_.get(line_number_reference).return_operand2();
		else		
			x=ID_.get(line_number_reference).return_operand3();
		return x;
	}

	
	public static void set_ID_values_of_operand1(int line_number_reference,float float_value_of_operand)
	{
		ID_.get(line_number_reference).set_value1(float_value_of_operand);
	}
	
	public static void set_ID_values_of_operand2(int line_number_reference,float float_value_of_operand)
	{
		ID_.get(line_number_reference).set_value2(float_value_of_operand);
	}
	
	public static void set_ID_values_of_operand3(int line_number_reference,float float_value_of_operand)
	{
		ID_.get(line_number_reference).set_value3(float_value_of_operand);
	}
	
	//ID_.get(line_number_reference).set_value1(value_of_operand1);
	//ID_.get(line_number_reference).set_value2(value_of_operand1);
	//ID_.get(line_number_reference).set_value3(value_of_operand1);
	public static String get_ID_operand1(int line_number_reference)
	{
		return ID_.get(line_number_reference).return_operand1();
	}
	public String get_ID_operand2(int line_number_reference)
	{
		return ID_.get(line_number_reference).return_operand2();
	}
	public String get_ID_operand3(int line_number_reference)
	{
		return ID_.get(line_number_reference).return_operand3();
	}
	
	public float get_ID_value1(int line_number_reference)
	{
		return ID_.get(line_number_reference).return_value1();
	}
	public float get_ID_value2(int line_number_reference)
	{
		return ID_.get(line_number_reference).return_value2();
	}
	public float get_ID_value3(int line_number_reference)
	{
		return ID_.get(line_number_reference).return_value3();
	}
	
	public static boolean get_is_offset_positive(int line_number_reference)
	{
		return ID_.get(line_number_reference).return_has_a_negative_offset();
	}

	public static float get_value_of_FP_Register(int index_value)
	{
		return F_Register.get(index_value);
	}
	
	public static int return_target_address(int line_number)
	{
		return EX_.get(line_number).return_target_address();
	}
	
	public static String Find_RAW (String operand_reference,int line_number_reference)
	{	
		if(line_number_reference==8)
			System.out.println(operand_reference);
		String Reg_Imm=null;
		boolean is_it_not_ready_to_read=false;
		boolean break_the_loop=false;
		
		for(int i=line_number_reference-1;i>max_line_finished && !break_the_loop &&  !is_it_not_ready_to_read;i--)
		{
			if(operand_reference.equals(ID_.get(i).return_operand1()) && !ID_.get(i).return_is_instruction_Store())
			{
				if((ID_.get(i).return_is_instruction_Load()&&EX_.get(i).return_has_finished_execution()) || (ID_.get(i).return_is_instruction_Add()&&MEM_.get(i).return_is_in_scope()) || (ID_.get(i).return_is_instruction_Multiply()&&MEM_.get(i).return_is_in_scope()) )
				{	
					if(line_number_reference==8)
					System.out.print("\nRAW operand_reference:"+operand_reference+" ");
					if(line_number_reference==8)
					System.out.print(" "+line_number_reference+" -->"+i+" ");
					break_the_loop=true;
					if(ID_.get(i).return_is_instruction_Load())
						Reg_Imm=String.valueOf(EX_.get(i).get_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register());
					if(ID_.get(i).return_is_instruction_Add())
						Reg_Imm=String.valueOf(A2_.get(i).return_Reg_imm());
					if(ID_.get(i).return_is_instruction_Multiply())				
						Reg_Imm=String.valueOf(M4_.get(i).return_Reg_imm());
				}
				else
				{
					if(line_number_reference==8)
					System.out.print("\nRAW operand_reference:"+operand_reference+" ");
					if(line_number_reference==8)
					System.out.print(" "+line_number_reference+" -->"+i+" ");
					is_it_not_ready_to_read=true;
				}
			}	
		}
		return is_it_not_ready_to_read+" "+break_the_loop+" "+Reg_Imm;
	}
	public static boolean Check_WAR (int line_number_reference)
	{
		String operand_reference=get_ID_operand1(line_number_reference);
		boolean is_it_not_ready_to_write=false;
		for(int i=line_number_reference-1;i>max_line_finished &&  !is_it_not_ready_to_write ;i--)
		{
			boolean x=operand_reference.equals(ID_.get(i).return_operand2()) && !ID_.get(i).return_is_instruction_Store();
			boolean y=operand_reference.equals(ID_.get(i).return_operand3());
			if( (x||y) && !ID_.get(i).return_is_instruction_Load())
			{
				if((ID_.get(i).return_is_instruction_Store() &&EX_.get(i).return_has_finished_execution()) || (ID_.get(i).return_is_instruction_Add() && A1_.get(i).return_is_this_stage_completed()) || (ID_.get(i).return_is_instruction_Multiply()&&M1_.get(i).return_is_this_stage_completed()) )
				{	
			
				}
				else
				{
					is_it_not_ready_to_write=true;
					System.out.print("\nWAR operand_reference:"+operand_reference+" ");
					System.out.print(" "+line_number_reference+" -->"+i+" ");
				}
			}	
		}
		return is_it_not_ready_to_write;
	}
	public static boolean Check_WAW (int line_number_reference)
	{
		String operand_reference=get_ID_operand1(line_number_reference);
		boolean is_it_not_ready_to_write=false;
		for(int i=line_number_reference-1;i>max_line_finished &&  !is_it_not_ready_to_write ;i--)
		{
			if(operand_reference.equals(ID_.get(i).return_operand1()) && !ID_.get(i).return_is_instruction_Store())
			{
				if(WB_.get(i).return_has_written())
				{	
				
				}
				else
				{
					is_it_not_ready_to_write=true;
					System.out.print("\nWAW operand_reference:"+operand_reference);
					System.out.print(" "+line_number_reference+" -->"+i+" ");
				}
			}	
		}
		return is_it_not_ready_to_write;
	}

}
