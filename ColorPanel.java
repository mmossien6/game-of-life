import java.awt.*;
import javax.swing.*;

public class ColorPanel extends JPanel {
	public ColorPanel(Color background) {
		setBackground(background);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, 40, 40);
		Font font = new Font("Garamond", Font.BOLD, 15);
		g.setFont(font);
		if (getBackground() == Color.RED)
			g.drawString("2", 17, 23);
		else if (getBackground() == Color.YELLOW)
			g.drawString("1", 17, 23);
		else if (getBackground() == Color.BLUE)
			g.drawString("0", 17, 23);
	}
}
