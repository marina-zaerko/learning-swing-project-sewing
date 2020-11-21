import javax.swing.*;
import java.awt.*;

public class EmployerForm extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private final int WIDTH = 400, HEIGHT = 300;

    EmployerForm() {
        setContentPane(tabbedPane1);
        setTitle("Экран заказчика");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - WIDTH / 2, (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - HEIGHT / 2);
    }
}
