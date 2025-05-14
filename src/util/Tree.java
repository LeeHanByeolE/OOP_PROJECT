package util;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.JOptionPane;

import modeling.AttributePane;
import modeling.MindPane;
import modeling.TextPane;
import util.MouseActionHandling;

class Make{ // �ؽ�Ʈ�� �޾� Ʈ���� ����
	
	TextPane Text;
	MindPane mindMap;
	AttributePane attribute;
	
	MouseActionHandling me; 
	Focus focus = new Focus();
	Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
	//�⺻ ���콺 �̺�Ʈ�� ��Ŀ���̺�Ʈ�� �׵θ�
	
	Make(TextPane Tt, MindPane mindMap, AttributePane attribute){
		Text = Tt; this.mindMap = mindMap; this.attribute = attribute;
		me = new MouseActionHandling(mindMap,attribute);
	}
	
	TreeNode MakeNode() throws Exception {
		
		
		JLabel Sto = null;
		int Level = 0;
		int oldLevel = 0;
		String Cpy = Text.getText(); //��� �ؽ�Ʈ ������ ��
		
		if(Cpy.charAt(0) == ' ')
			throw new Exception();
		
		StringTokenizer st = new StringTokenizer(Cpy,"\n");
		JLabel Jroot = new JLabel(st.nextToken());
		TreeNode root = new TreeNode(Jroot); //��Ʈ ��带 �ؽ�Ʈ ������ ���� ���� �ִ� ������ ����.
		int Num = st.countTokens();
		TreeNode CurrentNode = root;
		
		//node�� ���� �⺻����
		defaultSetting(Jroot);
			
		for(int i = 0; i < Num; i++) { // �ؽ�Ʈ�� /t�� ������ ���� Ž��
			Level = 0;
			String A = st.nextToken();
			char[] CpyA = A.toCharArray(); //String�� char�� �迭�� ��ȯ
			for(int j = 0; j < CpyA.length; j++) {
				if(CpyA[j]=='\t') {
					Level++;
				}
			}
			A = String.valueOf(CpyA); //char�� �迭�� String�� ��ȯ
			A = A.trim(); // ���� ����
			Sto = new JLabel(A);
			Sto.setHorizontalAlignment(JLabel.CENTER); //JLabel �ؽ�Ʈ �������
			//node�� ���� �⺻����
			defaultSetting(Sto);
			
			
			//��� ����
			if(oldLevel == (Level-1)) {
				CurrentNode = CurrentNode.MakeChild(Sto);
			}
			else if(oldLevel == Level) {
				CurrentNode = CurrentNode.MakeBChild();
				CurrentNode = CurrentNode.MakeChild(Sto);
			}
			else {
				for(int a=0; a<(oldLevel-Level)+1; a++) {
					CurrentNode = CurrentNode.MakeBChild();
				}
				CurrentNode = CurrentNode.MakeChild(Sto);
			}
			oldLevel = Level;
		}
		return root;
	}
	
	private void defaultSetting(JLabel node)
	{
		node.addMouseListener(me);
		node.addMouseMotionListener(me);
		node.addFocusListener(focus);
		node.setFocusable(true);
		node.setOpaque(true);
		node.setBackground(Color.orange);
		node.setBorder(border);
	}
}

class Focus extends FocusAdapter {

	public void focusGained(FocusEvent e)
	//focus�� �ްԵǸ� �׵θ��� ����
	{
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		JComponent comp = (JComponent)e.getSource();
		comp.setBorder(border);
		EventHandling.current = (JLabel)comp;
		//���� ����Ű�� ���� ����
	}
	public void focusLost(FocusEvent e)
	//focus�� ����� �������
	{
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		JComponent comp = (JComponent)e.getSource();
		comp.setBorder(border);
	}
}

public class Tree { // ��� ���� ���� ��  MindPane UI
	static TreeNode root;
	MindPane mindMap;
	
	Tree(TextPane text, MindPane mindMap, AttributePane attribute)
	{
		Make tree = new Make(text,mindMap,attribute);
		this.mindMap = mindMap;
		try {
			root = tree.MakeNode();
			new TreeUI(root);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�ٽ� �Է����ּ���","���",JOptionPane.WARNING_MESSAGE);
		}
	}
	public static TreeNode getRoot()
	//root�� ��ȯ�ϴ� �޼ҵ�
	{ return root; }
	public static void setRoot(TreeNode _root)
	//������ open������ ��Ʈ�� �����ϴ� �޼ҵ�
	{ root = _root; }
	
	class TreeUI{ // Ʈ���� UI�� ����
		RememberXY[] R; // ���� JLabel��  X, Y, Angle ���� 
		final int initX;
		final int initY;
		final int initW;
		final int initH;
		
		TreeUI(TreeNode Root){ //������
			this.R = new RememberXY[Root.children.size()];
			initW = 80;
			initH = 40;
			initX = mindMap.getWidth() / 2 - initW / 2;
			initY = mindMap.getHeight() / 2 - initH / 2;
			Root.data.setBounds(initX, initY, initW, initH);
			Root.data.setHorizontalAlignment(JLabel.CENTER);
			mindMap.add(Root.data);
			
			int Num = Root.children.size();
			RememberXY[] R = new RememberXY[Num];
			R = inXY(initX , initY, R, 0);
			
			TOUI(initX, initY, Root, mindMap, 0);
		}
		
		void TOUI(int x, int y, TreeNode Node, MindPane f, int Angle) { // UI�� ���� ��� �޼ҵ�
			int PrevAngle = Angle; //�θ� ����� Angle ����
			int Num = Node.children.size();
			RememberXY[] R = new RememberXY[Num];
			R = inXY(x , y , R, PrevAngle); // �ڽ� ��� ���� ����
			if(Num == 0) { //�ڽ� ��尡 ���� ��
				f.add(Node.data);
				Node.data.setBounds(x, y, initW, initH);
			}
			else { //�ڽ� ��尡 ���� ��
				for(int i=0; i<Num; i++) {
					f.add(Node.children.get(i).data);
					Node.children.get(i).data.setBounds(R[i].x,R[i].y,initW,initH);
					TOUI(R[i].x,R[i].y,Node.children.get(i),f ,R[i].Angle);
				}
			}
			f.repaint();
		}
	
		RememberXY[] inXY(int x, int y , RememberXY[] xy, int RememberAngle) { // ����� X, Y, Angle �� ����
			int Distance = 120; // MindPane UI�� �Ÿ� ����
			if(x==initX && y<initY) { // X�� �θ���� ����, Y�� �θ��庸�� Ŭ ��
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(270+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(270+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)(270+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
			else if(x==initX && y>initY) { // X�� �θ���� ����, Y�� �θ��庸�� ���� ��
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(90+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(90+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)(90+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
			else if(x>initX && y==initY) { // X�� �θ��庸�� �۰�, Y�� �θ���� ���� ��
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(0+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(0+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)(0+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
			else if(x<initX && y==initY) { // X�� �θ��庸�� ũ��, Y�� �θ���� ���� ��
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(180+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(180+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)(180+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
			else if(x==initX && y==initY) { // �ʱ� �θ���κ��� �ڽ� ��� ��ġ ����
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(360/xy.length*i))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(360/xy.length*i))*Distance));
					Temp.Angle = 360/xy.length*i;
					xy[i] = Temp;
				}
			}
			else { // �� ���� ��ġ�� ���� ��
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians((360-(180-90-RememberAngle))+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians((360-(180-90-RememberAngle))+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)((360-(180-90-RememberAngle))+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
				
			return xy;
		}
	}
	class RememberXY{ //X, Y, Angle���� ����
		int x;
		int y;
		int Angle;
		RememberXY(int x, int y, int Angle){
			this.x = x;
			this.y = y;
			this.Angle = Angle;
		}
	}
}
