import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
/*
Окно авторизации - необходимо для входа в систему,
осуществляет проверку вводимых пользователем данных и доступ к БД

Данные для входа:
Кладовщик - Логин: storer_1 Пароль: storer

Разработала: Марина Заерко
Почта: 227885@niuitmo.ru
 */

public class AuthForm extends JFrame{
    private JPanel authpanel;
    private JTextField login;
    private JPasswordField password;
    private JButton enter;
    private JButton exit;
    private JButton reg;
    private final int WIDTH = 400, HEIGHT = 300;
    private Connection connect;

    AuthForm(){
        setContentPane(authpanel);
        setTitle("Вход в систему");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width/2) - WIDTH/2,(Toolkit.getDefaultToolkit().getScreenSize().height/2) - HEIGHT/2);

        myClient M = new myClient();

        reg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegForm R = new RegForm();
                R.setVisible(true);
                R.pack();
                dispose();
            }
        });

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (login.getText().trim().length() > 0 && password.getText().trim().length() > 0){
                    if (M.initConnection() == 1) {
                        ResultSet resultUsers = M.execSelect("user");
                        try {
                            while (resultUsers.next()) {
                                if (login.getText().trim().equals(resultUsers.getString("login")) && password.getText().trim().equals(resultUsers.getString("password"))) {

                                    switch (resultUsers.getString("type")){
                                        case "employer":
                                            EmployerForm E = new EmployerForm();
                                            E.pack();
                                            E.setVisible(true);
                                            break;

                                        case "manager":
                                            ManagerForm N = new ManagerForm();
                                            N.pack();
                                            N.setVisible(true);
                                            break;

                                        case "storer":
                                            StorerForm S = new StorerForm();
                                            S.pack();
                                            S.setVisible(true);
                                            break;

                                        case "chief":
                                            ChiefForm C = new ChiefForm();
                                            C.pack();
                                            C.setVisible(true);
                                            break;
                                    }
                                    M.connect.close();
                                    dispose();
                                    return;
                                }
                            }

                            showMessage("Внимание", "Неправильный логин или пароль", JOptionPane.WARNING_MESSAGE);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                    else{
                        showMessage("Ошибка", "Нет доступа к серверу", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    showMessage("Внимание", "Поля не должны быть пустыми", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    void showMessage(String title, String message, int type){
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    public static void main(String[] args) {
        AuthForm A = new AuthForm();
        A.pack();
        A.setVisible(true);
    }
}
