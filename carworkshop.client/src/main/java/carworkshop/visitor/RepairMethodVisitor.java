package carworkshop.visitor;

public interface RepairMethodVisitor {
	public void visit(WheelsRepair wheelsRepair);
	public void visit(EngineRepair engineRepair);
	public void visit(OilsRepair oilsRepair);
	public void visit(OtherRepair otherRepair);
}
