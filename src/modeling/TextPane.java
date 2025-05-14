package modeling;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.EventHandling;
import util.TreeNode;

public class TextPane extends JPanel{
	static JTextArea textArea;
	JButton textButton;
	static int depth = 0;
	static String text = "";
	
	public TextPane()
	{	
		super(new BorderLayout());
		
		textArea = new JTextArea();
		JScrollPane textScroll = new JScrollPane(textArea);
		textButton = new JButton("����");
		textButton.setFont(new Font("����",Font.BOLD,20));
		
		textArea.setTabSize(1);
		textArea.setFont(new Font("����",Font.BOLD,30));
		add(textScroll, "Center");
		add(textButton, "South");
		//Text Editor Pane	
	}
	
	public static void clear()
	//������ �Ҷ����� textPane�� �����ϴ� �ֿ��� Ű������ �ʱ�ȭ�����ִ� �޼ҵ�
	{ textArea.setText(""); text = ""; depth = 0; }
	
	public static void fill(TreeNode node)
	//������ ���������� text�� ������ ������ִ� �޼ҵ�
	{
		for(int i = 0; i < depth; ++i)
			text += "\t";
		text += node.getData().getText();
		text += "\n";
		if(node.getChildren().size() == 0) { depth--; return; }
		
		
		for(int i = 0; i < node.getChildren().size(); ++i)
		{
			depth++; 
			fill((TreeNode) node.getChildren().get(i));
		}
		depth--;
		textArea.setText(text);
	}
	
	public void addEvent(EventHandling e)
	//��ư�� �̺�Ʈ �߰�
	{ textButton.addActionListener(e); }
	
	public String getText()
	//�ۼ��� text�� �������� �޼ҵ�
	{ return textArea.getText(); }
}
