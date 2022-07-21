import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "abdutokt2004";

    public static void main(String[] args) throws SQLException {
        // Объект для считывания информации
        Scanner sc = new Scanner(System.in);
        // Создали подключение
        Connection connection = DriverManager.getConnection(url, user, password);


        // Бесконечный вывод меню
        while (true) {
            System.out.println("1. Показать список всех задач");
            System.out.println("2. Выполнить задачу");
            System.out.println("3. Создать задачу");
            System.out.println("4. Выйти");
            // Считываем команды пользователя
            int command = sc.nextInt();

            if (command == 1) {
                // Объект который умеет отправлять запросы в БД
                Statement statement = connection.createStatement();
                String SQL_SELECT_TASKS = "select * from task order by id desc";
                // Объект который хранит результаты запроса
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_TASKS);
                // Просматриваем все данные которые вернулись из бд и выводим их на экран
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name")
                            + " " + resultSet.getString("STATE"));
                }
            } else if (command == 2) {
                // описываем запрос не зная какие парамеры там будут
                String sql = "update task set state = 'DONE' where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("Введите идентификатор задачи:");
                int taskId = sc.nextInt();
                // кладём параметр, который мы считали с консоли в строку запроса
                preparedStatement.setInt(1, taskId);
                preparedStatement.executeUpdate();
            } else if (command == 3) {
                String sql = "insert into task (name, state) values (?, 'IN_PROCESS')";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("Введите название задачи:");
                sc.nextLine();
                String taskName = sc.nextLine();
                preparedStatement.setString(1, taskName);
                preparedStatement.executeUpdate();
            } else if (command == 4) {
                System.exit(0);
            } else {
                System.err.println("Команда не распознана!");
            }
        }
    }
}
