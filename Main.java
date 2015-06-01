import processing.core.*;

public class Main extends PApplet{

   private PImage nodeImage;
   private PImage mouseImage;

   private final int width = 640;
   private final int height = 480;

   private final int widthTiles = this.width / 16;
   private final int heightTiles = this.height / 16;

   private Node[][] world = new Node[widthTiles][heightTiles];

   public void setup(){
      nodeImage = loadImage("images/node.png");
      mouseImage = loadImage("images/mouse.png");

      size(this.width, this.height);
      background(color(255, 255, 255));
   }

   public void draw(){
      background(color(255, 255, 255));
      drawNodesWorld();      
      drawMouse();
   }

   public void drawMouse(){
      Point mouse = new Point(mouseX / 16, mouseY / 16);
      image(mouseImage, mouse.getX() * 16, mouse.getY() * 16);
   }

   public void drawNodesWorld(){
      for (int x = 0; x < widthTiles; x++){
         for (int y = 0; y < heightTiles; y++){
            if (world[x][y] != null){
               image(nodeImage, x * 16, y * 16);
            }
         }
      }
   }

   public void mousePressed(){
      Point mouse = new Point(mouseX / 16, mouseY / 16);
      Node node = getNodeAtPoint(mouse);

      if (node == null){
         setNodeAtPoint(mouse, new Node(mouse));
      }
      else if (node != null){
         setNodeAtPoint(mouse, null);
      }

      System.out.println(mouse.getX() + " " + mouse.getY());
   }

   public void setNodeAtPoint(Point pt, Node node){
      world[pt.getX()][pt.getY()] = node;
   }

   public Node getNodeAtPoint(Point pt){
      return world[pt.getX()][pt.getY()];
   }

   public static void main(String[] args){
      PApplet.main("Main");
   }

}
