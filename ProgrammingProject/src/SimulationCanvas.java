import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class SimulationCanvas extends JPanel{
    private final int width;
    private final int height;
    private final List<Person> people= new ArrayList<>();

    public SimulationCanvas(int width,int height){
        this.width =width;
        this.height = height;
        setPreferredSize(new Dimension (width, height));
    }
    public void initPeople (int number){
        for (int i=0; i<number; i++) {
            people.add(new Person(width,height));
        }
    }
    public void gameLoop(){
        while (true){
            repaint();
            try{
                Thread.sleep(50);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        clearBackground(g);
        for (Person person: people) {
            person.update(people);
            person.draw(g);
        }
    }

    private void clearBackground(Graphics g){
        g.setcolor(color.WHITE);
        g.fillRect(0,0,width,height);
    }
}
