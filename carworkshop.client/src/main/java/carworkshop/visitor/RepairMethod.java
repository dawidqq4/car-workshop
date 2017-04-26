package carworkshop.visitor;

public interface RepairMethod {
	public void accept(RepairMethodVisitor repairMethodVisitor);
}
