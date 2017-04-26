package carworkshop.visitor;

public class OtherRepair implements RepairMethod {

	@Override
	public void accept(RepairMethodVisitor repairMethodVisitor) {
		repairMethodVisitor.visit(this);
	}
}
