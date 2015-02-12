import java.util.Scanner;


public class EX extends Start
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_required;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;

	private boolean has_read;
	public boolean has_finished_execution;
	private float reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register;
	private int target_address;
	private boolean is_RAW3_true;
	
	
	EX(int from_constructor)
	{
		is_in_scope=false;
		is_this_stage_required=false;
		is_this_stage_completed=false;
		has_read=false;
		has_finished_execution=false;
	}
	
	EX(boolean from_constructor,int line_Number)
	{
		
		line_number=line_Number;
		is_in_scope=true;
		is_this_stage_required=true;
		is_RAW3_true=false;
		if(!get_instruction_word(line_number).equals("L.D"))
				check_for_RAW();
		else
			{
				target_address=compute_target_address(get_ID_operand3(line_number),get_is_offset_positive(line_number),get_ID_operand2(line_number));
				reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register=Memory_Content.get(target_address);
			}	
		if(!is_RAW3_true)
		{
			occupied=true;
			is_this_stage_completed=true;
			Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_number)+"EX        ");
			has_read=true;
			has_finished_execution=true;
		}
		else
		{
		//	System.out.println("yes stalled");
			Instruction_Sequence.set(line_number, Instruction_Sequence.get(line_number)+"stall     ");	
		}
		if(has_finished_execution)
		{
			set_ID_values_of_operand1(line_number, reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register);	
		}		
	}	
	boolean return_has_finished_execution()
	{
		return has_finished_execution;
	}
	
	boolean return_has_read()
	{
		return has_read;
	}
	
	void set_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register(float value)
	{
		this.reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register=value;
	}
	
	float get_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register()
	{
		return reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register;
	}
	
	private void check_for_RAW()
	{
		//System.out.print("l"+line_number+" ");
		{
			Scanner scan=new Scanner(Find_RAW(return_operand(line_number,false),line_number));	
			if(!scan.nextBoolean())
				{
					if(scan.nextBoolean())
					{
						reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register=scan.nextFloat();	
						target_address=compute_target_address(get_ID_operand1(line_number),get_is_offset_positive(line_number),get_ID_operand2(line_number));	
					}
					else
					{
						target_address=compute_target_address(get_ID_operand1(line_number),get_is_offset_positive(line_number),get_ID_operand2(line_number));
						reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register=target_address;
					}
				}
			else
				{
					is_RAW3_true=true;
				}
			//scan.close();
		}
		//System.out.print(" "+is_RAW3_true );
	}
	
	int compute_target_address(String operand_R_register,boolean is_offset_negative,String offset)
	{
		if(!is_offset_negative)
			return R_Register.get(Integer.parseInt(operand_R_register))+Integer.parseInt(offset);
		else
			return R_Register.get(Integer.parseInt(operand_R_register))-Integer.parseInt(offset);
	}
	
	int return_target_address()
	{
		return target_address;
	}
	
	boolean return_is_this_stage_completed()
	{
		return is_this_stage_completed;
	}
	
	boolean return_is_in_scope()
	{
		return is_in_scope;
	}

}
