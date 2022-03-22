import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Window extends JFrame{
    String path = "";
    String[] target;
    String newPath = "";
    public Window() {

        this.setTitle("Search Local Files");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelFind = new JPanel();
        JTextArea findLabel = new JTextArea("所选查找区域：");
        findLabel.setEditable(false);
        JLabel jLabel = new JLabel();
        JButton jButton = new JButton("选择文件夹");
        jButton.setVisible(true);
        jLabel.add(jButton);
        jButton.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setMultiSelectionEnabled(false);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setDialogTitle("选择文件夹");
            int result = jFileChooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = jFileChooser.getSelectedFile();
                findLabel.setText("所选查找区域：\n"+file.getAbsolutePath());
                path = file.getAbsolutePath();
            }
        });
        panelFind.setLayout(new GridLayout(1, 2));
        panelFind.add(findLabel);
        panelFind.add(jButton);
        // replace
        JPanel panelReplace = new JPanel();
        panelReplace.setLayout(new GridLayout(2,1));
        JLabel replaceLabel = new JLabel("需要找的内容：");
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(true);
        panelReplace.add(replaceLabel);
        panelReplace.add(jTextArea);
        JTextArea jTextArea1 = new JTextArea();
        // find and replace
        JPanel panelArea1 = new JPanel();
        panelArea1.setLayout(new BoxLayout(panelArea1, BoxLayout.Y_AXIS));
        panelArea1.add(panelFind);
        panelArea1.add(panelReplace);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 1));
        JButton jButton1 = new JButton("打开该文件");
        jButton1.setVisible(true);
        jPanel.add(jButton1);


        // options
        JPanel panelOptions = new JPanel();
        panelOptions.setLayout(new GridLayout(3,2));
        JCheckBox checkBox1 = new JCheckBox("txt记事本");
        JCheckBox checkBox2 = new JCheckBox("word文档");
        JCheckBox checkBox3 = new JCheckBox("ppt演示文稿");
        JCheckBox checkBox4 = new JCheckBox("pdf");
        JCheckBox checkBox5 = new JCheckBox("excel工作表");
        panelOptions.add(checkBox1);
        panelOptions.add(checkBox2);
        panelOptions.add(checkBox3);
        panelOptions.add(checkBox4);
        panelOptions.add(checkBox5);
        panelOptions.setBorder(BorderFactory.createTitledBorder("选择需要寻找的文件格式"));

        JComboBox jComboBox = new JComboBox();

        // buttons
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(2, 2));
        JButton flush = new JButton("刷新预览区");
        JButton find = new JButton("查找");
        JButton openSo = new JButton("打开文件夹");
        JButton ffffff = new JButton("输出");
        panelButtons.add(find);
        jPanel.add(openSo);
        panelButtons.add(flush);
        //panelButtons.add(ffffff);
        ffffff.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setMultiSelectionEnabled(true);
            jFileChooser.setDialogTitle("选择文件夹");
            int result = jFileChooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = jFileChooser.getSelectedFile();
                findLabel.setText("所选查找区域：\n"+file.getAbsolutePath());
                newPath = file.getAbsolutePath();
            }
            Scan scan = new Scan();
            File file = new File(path);
            target = jTextArea.getText().split(" ");
            if(target.length!=0 && !path.equals("需要找的内容：")) {
                ArrayList<String> strings = new ArrayList<>();
                scan.allScan(file, target,
                        checkBox2.isSelected(),
                        checkBox4.isSelected(),
                        checkBox3.isSelected(),
                        checkBox5.isSelected(),
                        checkBox1.isSelected(),
                        strings,newPath);
                jComboBox.removeAllItems();
                for(String string : strings){
                    jComboBox.addItem(string);
                }
            }
            if(target.length==0){
                JOptionPane.showMessageDialog(null, "请输入想找的内容！", "错误！", JOptionPane.ERROR_MESSAGE);
            }
        });
        flush.addActionListener(e -> {
            String Path = jComboBox.getSelectedItem().toString();
            jTextArea1.setText("");
            if(Path.toLowerCase().endsWith("txt")){
                TXTExtractor txtExtractor = new TXTExtractor();
                jTextArea1.setText(txtExtractor.see(path,target[0]));
            }else if(Path.toLowerCase().endsWith("pdf")){
                PDFExtractor pdfExtractor = new PDFExtractor();
                jTextArea1.setText(pdfExtractor.see(Path,target[0]));
            }else if(Path.toLowerCase().endsWith("ppt")||Path.toLowerCase().endsWith("pptx")){
                PPTExtractor pptExtractor = new PPTExtractor();
                jTextArea1.setText(pptExtractor.see(Path,target[0]));
            }else if(Path.toLowerCase().endsWith("doc")||Path.toLowerCase().endsWith("docx")){
                DOCExtractor docExtractor = new DOCExtractor();
                jTextArea1.setText(docExtractor.see(Path,target[0]));
            }else if(Path.toLowerCase().endsWith("xls")||Path.toLowerCase().endsWith("xlsx")){
                EXCELExtractor excelExtractor = new EXCELExtractor();
                jTextArea1.setText(excelExtractor.see(Path,target[0]));
            }
            String temped = jTextArea1.getText();
            jTextArea1.requestFocus();
            for(int i=0;i<temped.length()-target[0].length();i++) {
                String w = (temped.substring(i, i + target[0].length()));
                if (w.equals(target[0])) {
                    jTextArea1.select(i, i + target[0].length());
                    break;
                }
            }
        });
        find.addActionListener(e -> {
            Scan scan = new Scan();
            File file = new File(path);
            target = jTextArea.getText().split(" ");
            if(target.length!=0 && !path.equals("需要找的内容：")) {
                ArrayList<String> strings = new ArrayList<>();
                scan.allScan(file, target,
                        checkBox2.isSelected(),
                        checkBox4.isSelected(),
                        checkBox3.isSelected(),
                        checkBox5.isSelected(),
                        checkBox1.isSelected(),
                        strings,newPath);
                jComboBox.removeAllItems();
                for(String string : strings){
                    jComboBox.addItem(string);
                }
            }
            if(target.length==0){
                JOptionPane.showMessageDialog(null, "请输入想找的内容！", "错误！", JOptionPane.ERROR_MESSAGE);
            }
        });
        jButton1.addActionListener(e -> {
            String string = Objects.requireNonNull(jComboBox.getSelectedItem()).toString();
            File file = new File(string);
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        openSo.addActionListener(e -> {
            String temp = jComboBox.getSelectedItem().toString();
            int pos = 0 ;
            for (int i = temp.length()-1; i >-1; i--) {
                if(temp.charAt(i) == '\\'){
                    pos = i;
                    break;
                }
            }
            temp = temp.substring(0,pos);
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File(temp));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        // panel south
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(panelButtons, BorderLayout.EAST);
        // panel north
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(5, 1));
        JScrollPane jScrollPane = new JScrollPane(jTextArea1);
        northPanel.add(panelArea1);
        northPanel.add(panelOptions);
        northPanel.add(jComboBox);
        northPanel.add(jPanel);
        northPanel.add(jScrollPane);

        this.setLayout(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
}