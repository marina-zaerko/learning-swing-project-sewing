import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProductForm extends JFrame{
    private JPanel panel1;
    private JTextField formname;
    private JTextField formarticul;
    private JTextField formnum;
    private JButton ok;
    private JButton cancel;
    private myClient M;
    private final int WIDTH = 400, HEIGHT = 300;
    private DefaultTableModel model;
    private Products product;
    private boolean editor;
    private JTable tableproducts;

    AddProductForm(String title, Products product, boolean editor, JTable tableproducts){
        setContentPane(panel1);
        setTitle(title);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - WIDTH / 2, (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - HEIGHT / 2);
        M = new myClient();
        this.product = product;
        this.editor = editor;
        this.tableproducts = tableproducts;
        if (product!=null){
            formname.setText(product.getName());
            formarticul.setText(product.getArticul());
            formnum.setText(Integer.toString(product.getNum()));
        }
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(formarticul.getText().trim().toString().length() > 0 && formname.getText().trim().toString().length() > 0 && formnum.getText().trim().toString().length() > 0){
                    if (M.initConnection() == 1){
                        M.execQuery("CREATE TABLE IF NOT EXISTS `sewing`.`product_exec` (`id` INT NOT NULL AUTO_INCREMENT,  `name` VARCHAR(45) NOT NULL, `arcticul` VARCHAR(45) NOT NULL, `num` INT NOT NULL, PRIMARY KEY (`id`), UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE, UNIQUE INDEX `arcticul_UNIQUE` (`arcticul` ASC) VISIBLE);");

                        Products product = new Products();
                        product.setNum(Integer.parseInt(formnum.getText().toString()));
                        product.setArticul(formarticul.getText().toString());
                        product.setName(formname.getText().toString());
                        if(editor == false) {
                            M.execQuery("INSERT INTO `sewing`.`product_exec` (`id`, `name`, `arcticul`, `num`) VALUES (DEFAULT, '" + formname.getText().toString() + "','" + formarticul.getText().toString() + "','" + formnum.getText().toString() + "');");
                            model.addRow(new String[]{product.getName(), product.getArticul(), Integer.toString(product.getNum())});
                        }
                        else {
                            M.execQuery("UPDATE product_exec SET name='" + product.getName() + "', num='" + product.getNum() + "' WHERE arcticul = '" + product.getArticul() + "'");
                            model.removeRow(tableproducts.getSelectedRow());
                            model.insertRow(tableproducts.getSelectedRow() + 1, new String[]{product.getName(), product.getArticul(), Integer.toString(product.getNum())});
                        }
                        dispose();
                    }
                    else{
                        showMessage("Ошибка", "Нет доступа к серверу", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    showMessage("Внимание","Заполните поля",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    void showMessage(String title, String message, int type){
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    void setModel(DefaultTableModel model){
        this.model = model;
    }
}
