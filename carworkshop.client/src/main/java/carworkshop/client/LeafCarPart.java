package carworkshop.client;

import java.util.List;

public class LeafCarPart implements CarPart {
	private String name;

	public LeafCarPart(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public List<CarPart> getSubordinates() {
		return null;
	}
}
