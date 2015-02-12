
public class M2 extends Start
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_required;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	
	M2(int from_constructor)
	{
		is_in_scope=false;
		is_this_stage_required=false;
		is_this_stage_completed=false;
	}
	M2(boolean from_constructor,int line_Number)
	{
		is_in_scope=true;
		line_number=line_Number;
		is_this_stage_required=true;
		is_this_stage_completed=true;
		occupied=true;
		Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_Number)+"M2        ");
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
