package mine;
/// Authors: Ahmed Eshra, Hussain Hammad
        import java.awt.BorderLayout;
        import javax.swing.JFrame;
        import javax.swing.JLabel;
        import java.awt.Font;
public class Main extends JFrame {
    private JLabel status;

    public Main() {
        status = new JLabel("");
        status.setFont(new Font("Monospaced Bold", Font.PLAIN, 18));
        add(status, BorderLayout.SOUTH);
        add(new Board(status));
        setResizable(false);
        pack();
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main ms = new Main();
        ms.setVisible(true);
    }
}
