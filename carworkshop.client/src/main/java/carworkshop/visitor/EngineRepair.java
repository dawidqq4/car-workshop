package carworkshop.visitor;

public class EngineRepair implements RepairMethod {

	@Override
	public void accept(RepairMethodVisitor repairMethodVisitor) {
		repairMethodVisitor.visit(this);
	}
}
