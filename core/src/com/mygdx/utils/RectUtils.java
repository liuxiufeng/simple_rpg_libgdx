package com.mygdx.utils;

import com.badlogic.gdx.math.Rectangle;

public class RectUtils {
    public static boolean isOverlap(Rectangle rectA, Rectangle rectB) {
       float minx = Math.max(rectA.x, rectB.x);
 	   float miny = Math.max(rectA.y, rectB.y);
 	   float maxx = Math.min(rectA.x + rectA.width, rectB.x + rectB.width);
 	   float maxy = Math.min(rectA.y + rectA.height, rectB.y + rectB.height);
 	   
 	   if (minx >= maxx || miny >= maxy) {
 		   return false;
 	   } else {
 		   return true;
 	   }
    }
}
