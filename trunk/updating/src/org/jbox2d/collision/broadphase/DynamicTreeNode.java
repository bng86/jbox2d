package org.jbox2d.collision.broadphase;

import org.jbox2d.collision.AABB;


public class DynamicTreeNode {
	/**
	 * This is the fattened AABB
	 */
	public final AABB aabb = new AABB();
	
	public Object userData;
	
	protected DynamicTreeNode parent;
	
	protected DynamicTreeNode child1;
	protected DynamicTreeNode child2;
	
	/**
	 * used for sorting purposes, don't modify
	 */
	public int key;
	
	public final boolean isLeaf(){
		return child1 == null;
	}
	
	/**
	 * Should never be constructed outside the engine
	 */
	public DynamicTreeNode(){}
}
