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

public class DrawView extends View
{
    Paint paint = new Paint();
    Point point = new Point();
    List<Circle> circles = new ArrayList<Circle>();
    List<Shape> objects = new ArrayList<Shape>();
    Shape current = new Shape(ShapeType.CIRCLE);
    float radius;
    int color;
    Point point1 = new Point();
    Point point2 = new Point();
    ShapeType stateShape = ShapeType.CIRCLE;

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
        //canvas.drawCircle(point.x, point.y, radius, paint);
        drawShape(canvas);
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
                point1.x = event.getX();
                point1.y = event.getY();
                //invalidate();
                return true;
            }
            case MotionEvent.ACTION_MOVE:
            {
                radius = Math.max(Math.abs(event.getX() - point.x),
                                    Math.abs(event.getY() - point.y));
                point2.x = event.getX();
                point2.y = event.getY();
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

    private void drawShape(Canvas canvas)
    {
        switch (current.shapetype)
        {
            case CIRCLE:
            {
                float r = Math.max(Math.abs(point1.x - point2.x),
                                    Math.abs(point1.y - point2.y));
                canvas.drawCircle(point1.x, point1.y, r, paint);
                return;
            }
            case RECTANGLE:
            {
                float left = Math.min(point1.x, point2.x);
                float top = Math.min(point1.y, point2.y);
                float right = Math.max(point1.x, point2.x);
                float bottom = Math.max(point1.y, point2.y);
                canvas.drawRect(left, top, right, bottom, paint);
                return;
            }
        }
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

enum ShapeType
{
    CIRCLE, RECTANGLE
}

class Circle extends Shape
{
    public Point center;
    public float radius;

    public Circle()
    {
        super(ShapeType.CIRCLE, Color.WHITE);
        center = new Point();
        radius = 0;
    }

    public Circle(Point p, float r, int c)
    {
        super(ShapeType.CIRCLE, c);
        center = new Point(p.x, p.y);
        radius = r;
    }
}

class Rectangle extends Shape
{
    public Point origo;
    public float width, height;  // can be negative values

    public Rectangle()
    {
        super(ShapeType.RECTANGLE);
        origo = new Point();
        width = 0;
        height = 0;
    }

    public Rectangle(Point p, float w, float h)
    {
        super(ShapeType.RECTANGLE);
        origo = new Point(p.x, p.y);
        width = w;
        height = h;
    }
}

class Shape
{
    public ShapeType shapetype;
    public int color;

    public Shape(ShapeType s)
    {
        shapetype = s;
    }

    public Shape(ShapeType s, int c)
    {
        shapetype = s;
        color = c;
    }
}
