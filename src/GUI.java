import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GUI {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JFrame frame = new TestSelectFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class TestSelectFrame extends JFrame {
    public TestSelectFrame(){
        setTitle("Grammar Recognition");
        setSize(1200,800);

        JTextArea gta = new JTextArea();



//        TestSelectPanel tsp  =new TestSelectPanel();
//        JTextArea jta = new JTextArea();
//        add(tsp);
//        add(jta);
    }
}

class TestSelectPanel extends JPanel{
    public TestSelectPanel(){
        JCheckBox jcb1 = new JCheckBox("Java");
        JCheckBox jcb2 = new JCheckBox("#");
        JCheckBox jcb3 = new JCheckBox("C++");
        add(jcb1);
        add(jcb2);
        add(jcb3);

        ButtonGroup group = new ButtonGroup();
        JRadioButton b1= new JRadioButton("男");
        JRadioButton b2= new JRadioButton("女");
        group.add(b1);
        group.add(b2);
        add(b1);
        add(b2);
        JComboBox jcb = new JComboBox();
        jcb.addItem("计算机");
        jcb.addItem("医学");
        jcb.addItem("英语");
        add(jcb);
    }
}

class JTextArea extends JPanel {
    public JTextArea(){
        this.setBounds(100, 100, 250, 200);
//        contentPane.setBorder(new EmptyBorder(5,5,5,5));
//        contentPane.setLayout(new BorderLayout(0,0));
//        this.setContentPane(contentPane);
//        JPanel panel1=new JPanel();
//        FlowLayout f1_Pane1=(FlowLayout)panel1.getLayout();
//        f1_Pane1.setAlignment(FlowLayout.LEFT);
//        contentPane.add(panel1,BorderLayout.NORTH);
//        JLabel label=new JLabel("请输入您的评语：");
//        panel1.add(label);
//        JPanel panel2=new JPanel();
//        contentPane.add(panel2,BorderLayout.SOUTH);
//        JButton button1=new JButton("提交");
//        panel2.add(button1);
//        JButton button2=new JButton("关闭");
//        panel2.add(button2);
//        JScrollPane scrollPane=new JScrollPane();
//        contentPane.add(scrollPane,BorderLayout.CENTER);
//        JTextArea textArea=new JTextArea();
//        scrollPane.setViewportView(textArea);
//        this.setVisible(true);
    }
}
