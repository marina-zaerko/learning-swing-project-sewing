import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StorerForm extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTable tableproducts;
    private JButton ok;
    private JButton editForm;
    private JButton exitForm;
    private JButton deleteForm;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JButton button1;
    private JTextField textField1;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JButton button2;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton button3;
    private JTextField textField3;
    private JButton dateOkForm;
    private JTextField dateCheckFrom;
    private JButton pickFile;
    private JLabel pickedFile;
    private DefaultTableModel model;
    private final int WIDTH = 400, HEIGHT = 300;
    private Font font;


    StorerForm() {
        setContentPane(tabbedPane1);
        setTitle("Окно кладовщика");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - WIDTH / 2, (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - HEIGHT / 2);
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/1442053055_icons.png"));
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("resourses/futurafuturisc.otf")).deriveFont(Font.PLAIN, 12);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tableproducts.setFont(font);

        myClient M = new myClient();
        model = new DefaultTableModel();
        model.addColumn("Имя");
        model.addColumn("Артикул");
        model.addColumn("Количество");

        tableproducts.setModel(model);

        if(M.initConnection() == 1){
            ResultSet resultProduct = M.execSelect("product_exec");
            if (resultProduct != null) {
                try {
                    while (resultProduct.next()) {
                        model.addRow(new String[]{resultProduct.getString("name"), resultProduct.getString("arcticul"), resultProduct.getString("num")});
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else{
                showMessage("Внимание","Нет данных о продуктах",JOptionPane.WARNING_MESSAGE);
            }
        }
        else{
            showMessage("Внимание","Потеряно соединение с сервером",JOptionPane.WARNING_MESSAGE);
        }

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProductForm A = new AddProductForm("Добавить продукт", null, false, tableproducts);
                A.pack();
                A.setModel(model);
                A.setVisible(true);
            }
        });

        exitForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthForm A = new AuthForm();
                A.pack();
                A.setVisible(true);
            }
        });

        editForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableproducts.getSelectedRow() >=0) {
                    Products product = new Products();
                    product.setName(model.getValueAt(tableproducts.getSelectedRow(), 0).toString());//model.getValueAt(tableproducts.getSelectedRow(), 0));
                    product.setArticul(model.getValueAt(tableproducts.getSelectedRow(), 1).toString());
                    product.setNum(Integer.parseInt(model.getValueAt(tableproducts.getSelectedRow(), 2).toString()));
                    AddProductForm A = new AddProductForm("Редактировать продукт", product, true, tableproducts);
                    A.pack();
                    A.setModel(model);
                    A.setVisible(true);
                }
            }
        });

        deleteForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableproducts.getSelectedRow() >=0) {
                    M.execQuery("DELETE FROM product_exec WHERE arcticul = '" + model.getValueAt(tableproducts.getSelectedRow(), 1) + "';");
                    model.removeRow(tableproducts.getSelectedRow());
                }
            }
        });
        
        dateOkForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



    }

    void showMessage(String title, String message, int type){
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    Date checkDate(String s){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return d;
        }
        return d;
    };


}
