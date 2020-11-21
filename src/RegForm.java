import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegForm extends JFrame{
    private JPanel regpanel;
    private JTextField login;
    private JPasswordField password;
    private JPasswordField password2;
    private JButton ok;
    private JButton exit;
    private final int WIDTH = 400, HEIGHT = 300;

    RegForm(){
        setContentPane(regpanel);
        setTitle("Регистрация в системе");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width/2) - WIDTH/2,(Toolkit.getDefaultToolkit().getScreenSize().height/2) - HEIGHT/2);

        myClient M = new myClient();

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (login.getText().trim().length() > 0 && password.getText().trim().length() > 0 && password2.getText().trim().length() > 0){
                    if (login.getText().trim().matches("((?=.*[a-z]).{5,30})")) {
                        if (password.getText().trim().length() >= 6 && password.getText().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%Л]).{6,30})")) {
                            if (password.getText().trim().equals(password2.getText().trim())){
                                if(M.initConnection() == 1){
                                    try {
                                        if(M.execQuery("insert into user (id, login, password, type) values (" + M.getLastIdFromTablePlusOne("user") + ", '" + login.getText().trim() + "','" + password.getText().trim() + "','employer');") == 1){
                                            showMessage("Сообщение", "Регистрация прошла успешно", JOptionPane.WARNING_MESSAGE);
                                        }
                                        M.connect.close();
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                }
                                else{
                                    showMessage("Внимание", "Нет соединения с сервером", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                            else{
                                showMessage("Внимание", "Введенные пароли не совпадают", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            showMessage("Внимание", "Пароль должен соответствовать следующим критериям:\n" +
                                    "· Минимум 6 символов\n" +
                                    "· Минимум 1 прописная буква\n" +
                                    "· Минимум 1 цифра\n" +
                                    "· Минимум один символ из набора: ! @ # $ % Л.", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else{
                        showMessage("Внимание", "Логин должен соответствовать следующим критериям:\n" +
                                "· Минимум 5 символов\n" +
                                "· Латинские строчные буквы\n" , JOptionPane.WARNING_MESSAGE);
                    }
                }
                else{
                    showMessage("Внимание", "Заполните поля", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthForm A = new AuthForm();
                A.setVisible(true);
                A.pack();
                dispose();
            }
        });
    }

    void showMessage(String title, String message, int type){
        JOptionPane.showMessageDialog(this, message, title, type);
    }
}
