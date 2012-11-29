package com.example.touchdraw;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends View
{
    Paint paint = new Paint();
    Point point = new Point();
    List<Circle> circles = new ArrayList<Circle>();
    float radius;
    int color;

    public DrawView(Context context)
    {
        super(context);

        color = Color.WHITE;
        paint.setColor(color);
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int c)
    {
        color = c;
        paint.setColor(color);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        for (Circle circle : circles)
        {
            Point p = circle.center;
            float r = circle.radius;
            paint.setColor(circle.color);
            canvas.drawCircle(p.x, p.y, r, paint);
        }
        paint.setColor(color);
        canvas.drawCircle(point.x, point.y, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                point.x = event.getX();
                point.y = event.getY();
                invalidate();
                return true;
            }
            case MotionEvent.ACTION_MOVE:
            {
                radius = Math.max(Math.abs(event.getX() - point.x),
                                    Math.abs(event.getY() - point.y));
                invalidate();
                return true;
            }
            case MotionEvent.ACTION_UP:
            {
                Circle circle = new Circle(point, radius, color); 
                circles.add(circle);
                radius = 0;
                return true;
            }
        }
        return true;
    }

    public void clear()
    {
        if (circles.size() > 0)
        {
            circles.clear();
            invalidate();
        }
    }

    public void undo()
    {
        if (circles.size() > 0)
        {
            circles.remove(circles.size() - 1);
            invalidate();
        }
    }
}

class Point
{
    float x, y;

    public Point()
    {
        x = 0;
        y = 0;
    }

    public Point(float xcoord, float ycoord)
    {
        x = xcoord;
        y = ycoord;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}

enum Shape
{
    CIRCLE, RECTANGLE
}

class Circle extends ShapeObject
{
    Point center;
    float radius;
    int color;

    public Circle()
    {
        super(Shape.CIRCLE);
        center = new Point();
        radius = 0;
        color = Color.WHITE;
    }

    public Circle(Point p, float r, int c)
    {
        super(Shape.CIRCLE);
        center = new Point(p.x, p.y);
        radius = r;
        color = c;
    }
}

class Rectangle extends ShapeObject
{
    Point origo;
    float width, height;  // can be negative values

    public Rectangle()
    {
        super(Shape.RECTANGLE);
        origo = new Point();
        width = 0;
        height = 0;
    }

    public Rectangle(Point p, float w, float h)
    {
        super(Shape.RECTANGLE);
        origo = new Point(p.x, p.y);
        width = w;
        height = h;
    }
}


class ShapeObject
{
    Shape shape;

    public ShapeObject(Shape s)
    {
        shape = s;
    }
}
