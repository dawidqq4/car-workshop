package carworkshop.client;

import java.util.ArrayList;
import java.util.List;

public class NodeCarPart implements CarPart{
   private String name;
   private List<CarPart> parts;

   public NodeCarPart(String name) {
      this.name = name;
      parts = new ArrayList<CarPart>();
   }

   public void add(CarPart carPart) {
	   parts.add(carPart);
   }

   public void remove(CarPart carPart) {
	   parts.remove(carPart);
   }

   public List<CarPart> getSubordinates() {
     return parts;
   }

   public String getName() {
      return name;
   }   
}