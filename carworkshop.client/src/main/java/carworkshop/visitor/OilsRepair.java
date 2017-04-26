package carworkshop.visitor;

public class OilsRepair implements RepairMethod {

	@Override
	public void accept(RepairMethodVisitor repairMethodVisitor) {	
		repairMethodVisitor.visit(this);
	}
}
