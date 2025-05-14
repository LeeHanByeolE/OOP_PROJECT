package modeling;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import util.EventHandling;
import util.MouseActionHandling;

public class GUI extends JFrame {
	EventHandling event;
	MouseActionHandling mouseEvent;
	
	public GUI()
	{
		setTitle("OOP Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		TextPane textPane = new TextPane();
		JScrollPane textScroll = new JScrollPane(textPane);
		//Text Pane
		
		MindPane mindPane = new MindPane(); 
		JScrollPane mindScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mindPane.setPreferredSize(new Dimension(6400,3600));
		mindScroll.getViewport().add(mindPane);
		//Mind Map Pane
		
		AttributePane attributePane = new AttributePane();
		JScrollPane attributeScroll = new JScrollPane(attributePane);
		//Attribute Pane
		
		event = new EventHandling(textPane, mindPane, attributePane);
		textPane.addEvent(event); //textPane���ִ� button�� �̺�Ʈ �߰�
		attributePane.addEvent(event); //attributePane�� �ִ� button�� �̺�Ʈ �߰�
		mouseEvent = new MouseActionHandling(mindPane,attributePane);
		mindPane.addMouseListener(mouseEvent); //mindPane�� ���콺 �̺�Ʈ�߰�
		mindPane.addMouseMotionListener(mouseEvent); //mindPane�� ���콺��� �̺�Ʈ�߰�
		//adding event
		
		MenuBar menuBar = new MenuBar(mindPane, attributePane);
		ToolBar toolbar = new ToolBar(mindPane, attributePane);
		//menu & toolbar
		toolbar.addEvent(event); //�̹� �ۼ��س��� �̺�Ʈ Ȱ��
		menuBar.addEvent(event); //�̹� �ۼ��س��� �̺�Ʈ Ȱ��
		
		setJMenuBar(menuBar);
		add(toolbar,"North");
		
		JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textScroll, mindScroll);
		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, attributeScroll);
		//�г��� ����
		
		setSize(1280, 720); 
		//16:9
		
		sp1.setDividerLocation(this.getWidth() / 5);
		sp2.setDividerLocation(this.getWidth() / 5 * 4);
		// 1 : 3 : 1
		add(sp2, "Center");
		
		setVisible(true);
		
		Rectangle bounds = mindScroll.getViewport().getViewRect();
		Dimension size = mindPane.getPreferredSize();
		int x = (size.width - bounds.width) / 2;
		int y = (size.height - bounds.height) / 2;
		mindScroll.getViewport().setViewPosition(new Point(x,y));
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				//������ ������ ���� �� �ڵ� ����
				JFrame current = (JFrame)e.getComponent();
				sp1.setDividerLocation(current.getWidth() / 5);
				sp2.setDividerLocation(current.getWidth() / 5 * 4);
				
				//������ ������ �ڵ����� ��ü ���ε��г��� ������
				revalidate();
				Rectangle bounds = mindScroll.getViewport().getViewRect();
				Dimension size = mindPane.getPreferredSize();
				int x = (size.width - bounds.width) / 2;
				int y = (size.height - bounds.height) / 2;
				mindScroll.getViewport().setViewPosition(new Point(x,y));
			}
		}); //SizeChanging Event
	}

	public static void main(String[] args) {
		new GUI();
	}
}
