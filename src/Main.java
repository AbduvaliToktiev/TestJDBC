import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "abdutokt2004";

    public static void main(String[] args) throws SQLException {
        // ������ ��� ���������� ����������
        Scanner sc = new Scanner(System.in);
        // ������� �����������
        Connection connection = DriverManager.getConnection(url, user, password);


        // ����������� ����� ����
        while (true) {
            System.out.println("1. �������� ������ ���� �����");
            System.out.println("2. ��������� ������");
            System.out.println("3. ������� ������");
            System.out.println("4. �����");
            // ��������� ������� ������������
            int command = sc.nextInt();

            if (command == 1) {
                // ������ ������� ����� ���������� ������� � ��
                Statement statement = connection.createStatement();
                String SQL_SELECT_TASKS = "select * from task order by id desc";
                // ������ ������� ������ ���������� �������
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_TASKS);
                // ������������� ��� ������ ������� ��������� �� �� � ������� �� �� �����
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name")
                            + " " + resultSet.getString("STATE"));
                }
            } else if (command == 2) {
                // ��������� ������ �� ���� ����� �������� ��� �����
                String sql = "update task set state = 'DONE' where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("������� ������������� ������:");
                int taskId = sc.nextInt();
                // ����� ��������, ������� �� ������� � ������� � ������ �������
                preparedStatement.setInt(1, taskId);
                preparedStatement.executeUpdate();
            } else if (command == 3) {
                String sql = "insert into task (name, state) values (?, 'IN_PROCESS')";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("������� �������� ������:");
                sc.nextLine();
                String taskName = sc.nextLine();
                preparedStatement.setString(1, taskName);
                preparedStatement.executeUpdate();
            } else if (command == 4) {
                System.exit(0);
            } else {
                System.err.println("������� �� ����������!");
            }
        }
    }
}
