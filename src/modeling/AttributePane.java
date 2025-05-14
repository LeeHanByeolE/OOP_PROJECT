package modeling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import util.EventHandling;

public class AttributePane extends JPanel {

	JTextField textField;
	JTextField xField;
	JTextField yField;
	JTextField wField;
	JTextField hField;
	JTextField colorField;
	String fontType;
	JButton attributeButton;
	
	AttributePane()
	{
		super(new BorderLayout());
		
		fontType = "굴림";
		
		JPanel temp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0)); //텍스트
		JPanel temp2 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0)); //X좌표
		JPanel temp3 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0)); //Y좌표
		JPanel temp4 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0)); //W
		JPanel temp5 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0)); //H
		JPanel temp6 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0)); //색상
		JPanel temp = new JPanel();
		attributeButton = new JButton("변경");
		attributeButton.setFont(new Font(fontType,Font.BOLD,20));
		
		JLabel textLabel = new JLabel("TEXT:"); textLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel xLabel = new JLabel("X:"); xLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel yLabel = new JLabel("Y:"); yLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel wLabel = new JLabel("W:"); wLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel hLabel = new JLabel("H:"); hLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel colorLabel = new JLabel("Color:"); colorLabel.setFont(new Font(fontType,Font.BOLD,20));
		
		textField = new JTextField(8); textField.setFont(new Font(fontType,Font.BOLD,20));
		xField = new JTextField(5); xField.setFont(new Font(fontType,Font.BOLD,20));
		yField = new JTextField(5); yField.setFont(new Font(fontType,Font.BOLD,20));
		wField = new JTextField(5); wField.setFont(new Font(fontType,Font.BOLD,20));
		hField = new JTextField(5); hField.setFont(new Font(fontType,Font.BOLD,20));
		colorField = new JTextField(6); colorField.setFont(new Font(fontType,Font.BOLD,20));
		textField.setEditable(false);
		
		
		temp1.add(textLabel); temp1.add(textField);
		temp2.add(xLabel); temp2.add(xField);
		temp3.add(yLabel); temp3.add(yField);
		temp4.add(wLabel); temp4.add(wField);
		temp5.add(hLabel); temp5.add(hField);
		temp6.add(colorLabel); temp6.add(colorField);
		
		temp.setLayout(new BoxLayout(temp,BoxLayout.Y_AXIS));
		temp.add(temp1);
		temp.add(temp2);
		temp.add(temp3);
		temp.add(temp4);
		temp.add(temp5);
		temp.add(temp6);
		/*
		 * (1 <= ㅑ <= 6)
		 * temp패널에 BoxLayout을 이용하여 각 tempi마다 한칸씩 차지하게 올림
		 * 각각 tempi는 FlowLayout을 이용해 info와 input칸을 배치
		 * 전체패널은 BorderLayout으로  temp판과 button을 올림
		 */
		add(temp, "Center");
		add(attributeButton, "South");
		
		colorField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if(get_Color().equals("")) return;
				Color pick = JColorChooser.showDialog(null, "Color", Color.WHITE);
				if(pick != null)
					set_Color(pick);
			}
		});
	}
	
	public void addEvent(EventHandling e)
	//버튼에 이벤트 추가
	{ attributeButton.addActionListener(e); }
	
	//JLabel의 속성을 표기해주는 메소드들
	public void set_Text(String text)
	{ textField.setText(text); }
	public void set_X(int X)
	{ xField.setText(X+""); }
	public void set_Y(int Y)
	{ yField.setText(Y+""); }
	public void set_W(int W)
	{ wField.setText(W+""); }
	public void set_H(int H)
	{ hField.setText(H+""); }
	public void set_Color(Color c)
	{	//color값을 16진수로 변환
		String hexString = Integer.toHexString(c.getRGB() & 0xffffff);
		if(hexString.length() < 6)
		{
			hexString = "000000".substring(0,6 - hexString.length()) + hexString;
		}
		colorField.setText("#" + hexString);
	}
	
	//JLabel의 정보를 가져와주는 메소드들
	public String get_Text()
	{
		return textField.getText();
	}
	public int get_X()
	{ return Integer.parseInt(xField.getText()); }
	public int get_Y()
	{ return Integer.parseInt(yField.getText()); }
	public int get_W()
	{ return Integer.parseInt(wField.getText()); }
	public int get_H()
	{ return Integer.parseInt(hField.getText()); }
	public String get_Color()
	{ return colorField.getText(); }
}
