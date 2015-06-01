import java.util.List;
import java.util.ArrayList;

public class DeadNode implements Node{
   private Point pt;
   
   public DeadNode(Point pt){
      this.pt = pt;
   }

   public void setPosition(Point pt){
      this.pt = pt;
   }
   
   public Point getPosition(){
      return this.pt;
   }

   public boolean action(Main main){
      // true means create alive. false means stay dead.
      List<Point> neighbors = new ArrayList<Point>();

      Point topLeft = main.checkAndReturnPoint(new Point(this.pt.getX() - 1, this.pt.getY() - 1));
      Point topMid  = main.checkAndReturnPoint(new Point(this.pt.getX(), this.pt.getY() - 1));
      Point topRight= main.checkAndReturnPoint(new Point(this.pt.getX() + 1, this.pt.getY() - 1));
      Point midLeft = main.checkAndReturnPoint(new Point(this.pt.getX() - 1, this.pt.getY()));
      Point midRight= main.checkAndReturnPoint(new Point(this.pt.getX() + 1, this.pt.getY()));
      Point botLeft = main.checkAndReturnPoint(new Point(this.pt.getX() - 1, this.pt.getY() + 1));
      Point botMid  = main.checkAndReturnPoint(new Point(this.pt.getX(), this.pt.getY() + 1));
      Point botRight= main.checkAndReturnPoint(new Point(this.pt.getX() + 1, this.pt.getY() + 1));  
      
      neighbors.add(topLeft);
      neighbors.add(topMid);
      neighbors.add(topRight);
      neighbors.add(midLeft);
      neighbors.add(midRight);
      neighbors.add(botLeft);
      neighbors.add(botMid);
      neighbors.add(botRight);

      int totalNeighbors = 0;

      for (Point pt : neighbors){
         if (pt != null && main.getNodeAtPoint(main.world, pt).getClass() == AliveNode.class){
            totalNeighbors += 1;
         }
      }

      if (totalNeighbors == 3){
         return true;
      }else{
         return false;
      }

      //System.out.println(this.pt.getX() + " " + this.pt.getY() + " " + totalNeighbors);
   }
}
