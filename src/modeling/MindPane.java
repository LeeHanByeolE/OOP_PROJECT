package modeling;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import java.util.Queue;
import java.util.LinkedList;

import util.Tree;
import util.TreeNode;

public class MindPane extends JPanel{
	
	static boolean isChanged = false;
	static boolean readyToDraw = false;
	
	TreeNode root;
	Queue q = new LinkedList();
	
	public MindPane(){
		super(null);
	}
	
	
	public void paint(Graphics g)//라벨간 선을 잇게해주는 paint메소드
	{
		super.paint(g);
		
		if(readyToDraw == true)
		{
			root = Tree.getRoot();
			if(root == null) return;
			q.add(root);
			double theta = 0;
			Graphics2D g2d = (Graphics2D) g;
			
			
			while(!q.isEmpty())
			{
				int qSize = q.size();
				for(int i = 0; i < qSize; ++i)
				{
					TreeNode parent = (TreeNode) q.remove();
					for(int j = 0; j < parent.getChildren().size(); ++j)
					{
						TreeNode child = (TreeNode) parent.getChildren().get(j);
						q.add(child);
						theta = getAngle(parent,child);
						Point2D.Double pP = getPoint(theta, parent);
						theta = getAngle(child,parent);
						Point2D.Double cP = getPoint(theta, child);
						
						g.drawLine((int)pP.x, (int)pP.y, (int)cP.x, (int)cP.y);
						arrowHead(g2d, pP, cP, Color.BLACK);
					}
				}
			}
		}
	}
	
	private double getAngle(TreeNode n1, TreeNode n2)
	{
		double n1x = n1.getData().getX() + n1.getData().getWidth()/2;
		double n1y = n1.getData().getY() + n1.getData().getHeight()/2;
		double n2x = n2.getData().getX() + n2.getData().getWidth()/2;
		double n2y = n2.getData().getY() + n2.getData().getHeight()/2;
		
		double dy = n2y - n1y;
		double dx = n2x - n1x;
		return Math.atan2(dy, dx);
	}
	
	private Point2D.Double getPoint(double theta, TreeNode node)
	{
		double nodeX = node.getData().getX() + node.getData().getWidth()/2;
		double nodeY = node.getData().getY() + node.getData().getHeight()/2;
		double nodeW = node.getData().getWidth() / 2;
		double nodeH = node.getData().getHeight() / 2;
		double length = Point2D.distance(nodeX, nodeY, nodeX + nodeW, nodeY + nodeH);
		
		double x = nodeX + length * Math.cos(theta);
		double y = nodeY + length * Math.sin(theta);
		Point2D.Double p = new Point2D.Double();
		
		if(node.getData().getY() > y )
		// North
		{
			p.x = nodeX - nodeH * ((x - nodeX) / (y - nodeY));
			p.y = nodeY - nodeH;
		}
		else if(node.getData().getX() > x)
		// West
		{
			p.x = nodeX - nodeW;
			p.y = nodeY - nodeW * ((y - nodeY) / (x - nodeX));
		}
		else if(node.getData().getX() + node.getData().getWidth() < x)
		// East
		{
			p.x = nodeX + nodeW;
			p.y = nodeY + nodeW * ((y - nodeY) / (x - nodeX));
		}
		else if(node.getData().getY() + node.getData().getHeight() < y)
		// South
		{
			p.x = nodeX + nodeH * ((x - nodeX) / (y - nodeY));
			p.y = nodeY + nodeH;
		}
		
		return p;
	}
	
	double phi = Math.toRadians(40);
	int barb = 20;
	private void arrowHead(Graphics2D g2d, Point2D.Double parent, Point2D.Double child, Color color)
	{
		g2d.setPaint(color);
		double dy = child.y - parent.y;
		double dx = child.x - parent.x;
		double theta = Math.atan2(dy,dx);
		
		double x = 0, y = 0, rho = theta + phi;
		for(int i = 0; i < 2; ++i)
		{
			x = child.x - barb * Math.cos(rho);
			y = child.y - barb * Math.sin(rho);
			g2d.draw(new Line2D.Double(child.x, child.y, x, y));
			rho = theta - phi;
		}
	}
	
	//선을 그리도록 boolean값을 설정해주는 메소드
	public static void drawReady()
	{ readyToDraw = true; }
	public static void drawNotReady()
	{ readyToDraw = false; }
	
	
	//저장관련 옵션을 관리하는 메소드들
	public static void endChange()
	{ isChanged = false; }
	public static void isChange()
	{ isChanged = true; }
	public static boolean Changed()
	{ return isChanged; }
}
