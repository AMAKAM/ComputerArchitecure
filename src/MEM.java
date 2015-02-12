
public class MEM extends Start
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_required;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	
	MEM(int from_constructor)
	{
		is_in_scope=false;
		is_this_stage_required=false;
		is_this_stage_completed=false;
	}
	MEM(boolean from_constructor,int line_Number,String instruction_word)
	{
		is_in_scope=true;
		line_number=line_Number;
		is_this_stage_completed=true;
		occupied=true;
		Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_Number)+"MEM       ");		
		if(get_instruction_word(line_number).equals("L.D"))
		{
			F_Register.set(Integer.parseInt(get_ID_operand1(line_number)),get_ID_value1(line_number));
		}
		if(get_instruction_word(line_number).equals("S.D"))	
		{
			Memory_Content.set((int)(return_target_address(line_number)),get_ID_value1(line_number));
			Memory_Content_Modified.add((int)(return_target_address(line_number)));
		}
	}
	
	void set_scope()
	{
		this.is_in_scope=true;
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
