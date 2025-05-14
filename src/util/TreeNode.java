package util;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;

public class TreeNode implements Iterable<TreeNode>{ // ���
	JLabel data; // ����� ������ ��
	TreeNode parent = null; // ������ �����Ѿ� ��.
	LinkedList<TreeNode> children; // �ڼ��� ���� ���������ʾ���
	
	TreeNode(JLabel A){ // ������
		this.data = A;
		this.children = new LinkedList<TreeNode>();
	}
	
	TreeNode MakeChild(JLabel child){ // �θ� ��忡 �ڽ� ��带 ���̴� �޼ҵ�
		TreeNode childNode = new TreeNode(child);
		childNode.parent = this;
		this.children.add(childNode);
		return childNode;
	}
	
	TreeNode MakeBChild(){ // �θ� ���� �̵��ϴ� �޼ҵ�
		return this.parent;
	}
		
	@Override
	public Iterator<TreeNode> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public JLabel getData() {
	//JLabel�� �������� �޼ҵ�
		return this.data;
	}
	public LinkedList getChildren()
	//child�� �������� �޼ҵ�
	{
		return this.children;
	}
}