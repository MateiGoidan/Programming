import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import java.util.Random;
public class Person {
    private static final Random random = new Random();
    private int x, y;
    private int speedX, speedY;
    private String status;
    private Color color;
    private final int width;
    private final int height;

    public Person (int width, int height) {
        this.width = width;
        this.height= height;
        x= random.nextInt(width);
        y=random.nextInt(height);
        speedX = random.nextInt(7)-3;
        speedY = random.nextInt(7)-3;
        int sickProbability = random.nextInt(100);
        if (sickProbability <80){
            status = "healthy";
            color = Color.BLUE;
        }else{
            status="sick";
            color= Color.RED;
        }
    }
    public void update(List<Person> people){
        x+= speedX;
        y+= speedY;
        boundaryCollision();
        interactWithOthers(people);
    }
    private void boundaryCollision(){
        if (x<0 || x>width) speedX= -speedX;
        if (y<0 || y>height) speedY = -speedY;
    }

    private void interactWithOthers(List<Person> people){
        for (Person other: people){
            if (other != this && getDistance(this, other)<12){
                speedX = -speedX;
                speedY = -speedY;
                if ("sick". equals(status) || "sick".equals(other.status)){
                    if (!"cured".equals(status)&& !"dead".equals(status)) status = "sick";
                    if (!"cured".equals(other.status) && !"dead".equals(other.status)) other.status ="sick";
                }
            }
        }
        //Check for recovery or death
        if("sick".equals(status)){
            if (random.nextInt(100)<1){
                status="cured";
                color= Color.GREEN;
            }
            if (random.nextInt(100)<1){
                status = "dead";
                color = Color.BLACK;
                speedX = 0;
                speedY = 0;
            }
        }
    }
    public void draw (Graphics g){
        g.setColor(color);
        g.fillOval(x,y,12,12);
    }
    private double getDistance(Person a,Person b){
        double dx=a.x -b.x;
        double dy=a.y -b.y;
        return Math.sqrt(dx * dx+dy * dy);
    }
}
