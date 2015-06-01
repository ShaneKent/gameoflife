import java.util.List;
import java.util.ArrayList;

public class AliveNode implements Node{
   private Point pt;
   
   public AliveNode(Point pt){
      this.pt = pt;
   }

   public boolean action(Main main){
      // true means stay alive. false means kill.
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
         if (pt != null && main.getNodeAtPoint(pt).getClass() == AliveNode.class){
            totalNeighbors += 1;
         }
      }

      if (totalNeighbors < 2 || totalNeighbors > 3){
         return false;//main.setNodeAtPoint(this.pt, new DeadNode(this.pt));
      }else{
         return true;
      }

      //System.out.println(this.pt.getX() + " " + this.pt.getY() + " " + totalNeighbors);
   }

   public void setPosition(Point pt){
      this.pt = pt;
   }

   public Point getPosition(){
      return this.pt;
   }
}
