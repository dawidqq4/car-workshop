package carworkshop.visitor;

public class PriceOfRepairOldCar implements RepairMethodVisitor {

	@Override
	public void visit(WheelsRepair wheelsRepair) {
		System.out.println("Wheels repair cost 199,99");
	}

	@Override
	public void visit(EngineRepair engineRepair) {	
		System.out.println("Engine repair cost 399,99");
	}

	@Override
	public void visit(OilsRepair oilsRepair) {
		System.out.println("Oils repair cost 99,99");
	}

	@Override
	public void visit(OtherRepair otherRepair) {
		System.out.println("Other repair cost between 99,99 and 399,99");
	}
}
