/*
 * JBox2D - A Java Port of Erin Catto's Box2D
 * 
 * JBox2D homepage: http://jbox2d.sourceforge.net/ 
 * Box2D homepage: http://www.box2d.org
 * 
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented; you must not
 * claim that you wrote the original software. If you use this software
 * in a product, an acknowledgment in the product documentation would be
 * appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 * misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package org.jbox2d.common;

import org.jbox2d.pooling.TLVec3;

// djm created from build 218
public class Mat33 {
	
	public static final Mat33 IDENTITY = new Mat33(new Vec3(1,0,0), new Vec3(0,1,0), new Vec3(0,0,1));
	
	public Vec3 col1,col2,col3;
	
	public Mat33(){
		col1 = new Vec3();
		col2 = new Vec3();
		col3 = new Vec3();
	}
	
	public Mat33(Vec3 argCol1, Vec3 argCol2, Vec3 argCol3){
		col1 = argCol1.clone();
		col2 = argCol2.clone();
		col3 = argCol3.clone();
	}
	
	public void setZero(){
		col1.setZero();
		col2.setZero();
		col3.setZero();
	}
	
	/// Multiply a matrix times a vector.
	public static final Vec3 mul( Mat33 A,  Vec3 v){
		return new Vec3(v.x * A.col1.x + v.y * A.col2.x + v.z + A.col3.x,
		                v.x * A.col1.y + v.y * A.col2.y + v.z * A.col3.y,
		                v.x * A.col1.z + v.y * A.col2.z + v.z * A.col3.z);
	}
	
	public static final void mulToOut(Mat33 A, Vec3 v, Vec3 out){
		final float tempy = v.x * A.col1.y + v.y * A.col2.y + v.z * A.col3.y;
		final float tempz = v.x * A.col1.z + v.y * A.col2.z + v.z * A.col3.z;
		out.x = v.x * A.col1.x + v.y * A.col2.x + v.z + A.col3.x;
		out.y = tempy;
		out.z = tempz;
	}
	
	/**
	 * Solve A * x = b, where b is a column vector. This is more efficient
	 * than computing the inverse in one-shot cases.
	 * @param b
	 * @return
	 */
	public final Vec2 solve22(Vec2 b){
		Vec2 x = new Vec2();
		final float a11 = col1.x, a12 = col2.x, a21 = col1.y, a22 = col2.y;
		float det = a11 * a22 - a12 * a21;
		assert(det != 0.0f);
		
		det = 1.0f / det;
		x.x = det * (a22 * b.x - a12 * b.y);
		x.y = det * (a11 * b.y - a21 * b.x);
		return x;
	}
	
	/**
	 * Solve A * x = b, where b is a column vector. This is more efficient
	 * than computing the inverse in one-shot cases.
	 * @param b
	 * @return
	 */
	public final void solve22ToOut(Vec2 b, Vec2 out){
		final float a11 = col1.x, a12 = col2.x, a21 = col1.y, a22 = col2.y;
		float det = a11 * a22 - a12 * a21;
		assert(det != 0.0f);
		
		det = 1.0f / det;
		out.x = det * (a22 * b.x - a12 * b.y);
		out.y = det * (a11 * b.y - a21 * b.x);
	}
	
	// djm pooling from below
	/**
	 * Solve A * x = b, where b is a column vector. This is more efficient
	 * than computing the inverse in one-shot cases.
	 * @param b
	 * @return
	 */
	public final Vec3 solve33(Vec3 b){
		Vec3 x = new Vec3();
		final Vec3 crossed = tlCrossed.get();
		Vec3.crossToOut(col2, col3, crossed);
		float det = Vec3.dot(col1, crossed);
		assert(det != 0.0f);
		
		det = 1.0f / det;
		Vec3.crossToOut(col2, col3, crossed);
		x.x = det * Vec3.dot(b, crossed);
		Vec3.crossToOut(b, col3, crossed);
		x.y = det * Vec3.dot(col1, crossed);
		Vec3.crossToOut(col2, b, crossed);
		x.z = det * Vec3.dot(col1, crossed);
		return x;
	}
	
	
	// djm pooling
	private static final TLVec3 tlCrossed = new TLVec3();
	/**
	 * Solve A * x = b, where b is a column vector. This is more efficient
	 * than computing the inverse in one-shot cases.
	 * @param b
	 * @param out the result
	 */
	public final void solve33ToOut(Vec3 b, Vec3 out){
		final Vec3 crossed = tlCrossed.get();
		Vec3.crossToOut(col2, col3, crossed);
		float det = Vec3.dot(col1, crossed);
		assert(det != 0.0f);
		
		det = 1.0f / det;
		Vec3.crossToOut(col2, col3, crossed);
		out.x = det * Vec3.dot(b, crossed);
		Vec3.crossToOut(b, col3, crossed);
		out.y = det * Vec3.dot(col1, crossed);
		Vec3.crossToOut(col2, b, crossed);
		out.z = det * Vec3.dot(col1, crossed);
	}
}
