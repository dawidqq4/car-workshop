package carworkshop.visitor;

public class PriceOfRepairNewCar implements RepairMethodVisitor {

	@Override
	public void visit(WheelsRepair wheelsRepair) {
		System.out.println("Wheels repair cost 699,99");
	}

	@Override
	public void visit(EngineRepair engineRepair) {	
		System.out.println("Engine repair cost 999,99");
	}

	@Override
	public void visit(OilsRepair oilsRepair) {
		System.out.println("Oils repair cost 299,99");
	}

	@Override
	public void visit(OtherRepair otherRepair) {
		System.out.println("Other repair cost between 199,99 and 999,99");
	}
}
