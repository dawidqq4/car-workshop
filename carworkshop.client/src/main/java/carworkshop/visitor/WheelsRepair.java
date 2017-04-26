package carworkshop.visitor;

public class WheelsRepair implements RepairMethod {

	@Override
	public void accept(RepairMethodVisitor repairMethodVisitor) {
		repairMethodVisitor.visit(this);
	}
}
