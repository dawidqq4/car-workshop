package carworkshop.client;

import java.util.List;

public interface CarPart {
	public String getName();
	public List<CarPart> getSubordinates();
}
