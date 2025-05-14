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
		textButton = new JButton("적용");
		textButton.setFont(new Font("굴림",Font.BOLD,20));
		
		textArea.setTabSize(1);
		textArea.setFont(new Font("굴림",Font.BOLD,30));
		add(textScroll, "Center");
		add(textButton, "South");
		//Text Editor Pane	
	}
	
	public static void clear()
	//새로이 할때마다 textPane을 구성하는 주요한 키값들을 초기화시켜주는 메소드
	{ textArea.setText(""); text = ""; depth = 0; }
	
	public static void fill(TreeNode node)
	//파일을 오픈했을시 text를 적절히 출력해주는 메소드
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
	//버튼에 이벤트 추가
	{ textButton.addActionListener(e); }
	
	public String getText()
	//작성한 text를 가져오는 메소드
	{ return textArea.getText(); }
}
