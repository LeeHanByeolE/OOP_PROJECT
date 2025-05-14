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
		textPane.addEvent(event); //textPane에있는 button에 이벤트 추가
		attributePane.addEvent(event); //attributePane에 있는 button에 이벤트 추가
		mouseEvent = new MouseActionHandling(mindPane,attributePane);
		mindPane.addMouseListener(mouseEvent); //mindPane에 마우스 이벤트추가
		mindPane.addMouseMotionListener(mouseEvent); //mindPane에 마우스모션 이벤트추가
		//adding event
		
		MenuBar menuBar = new MenuBar(mindPane, attributePane);
		ToolBar toolbar = new ToolBar(mindPane, attributePane);
		//menu & toolbar
		toolbar.addEvent(event); //이미 작성해놓은 이벤트 활용
		menuBar.addEvent(event); //이미 작성해놓은 이벤트 활용
		
		setJMenuBar(menuBar);
		add(toolbar,"North");
		
		JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textScroll, mindScroll);
		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, attributeScroll);
		//패널을 삼등분
		
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
				//사이즈 조정시 분할 바 자동 조정
				JFrame current = (JFrame)e.getComponent();
				sp1.setDividerLocation(current.getWidth() / 5);
				sp2.setDividerLocation(current.getWidth() / 5 * 4);
				
				//사이즈 조정시 자동으로 전체 마인드패널의 가운대로
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
