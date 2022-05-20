package ma.emsi.i_mark;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class polygoneView extends View {
    private Paint drawPaint;
    float pointX;
    float pointY;
    List<Point> polygone = new ArrayList<>();
    private boolean isFinished = false;

    public polygoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    public void finishAnnotation() {
        if (polygone.size() != 0) {
            isFinished = true;
            polygone.add(polygone.get(0));
            this.invalidate();
        }
    }

    public void previousAnnotation() {
        if (polygone.size() > 0) {
            isFinished = false;
            polygone.remove(polygone.size() - 1);
            this.invalidate();
        }

    }

    public void clearAnnotation() {
        isFinished = false;
        polygone.clear();
        this.invalidate();
    }

    public List<Point> getPolygone() {
        return polygone;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.rgb(0, 255, 47));
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointX = event.getX();
        pointY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFinished == false)
                polygone.add(new Point(pointX, pointY));
        }

        invalidate();
        Log.d("Polygone", polygone.toString());
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (polygone.size() != 0) {
            for (Point p : polygone) {
                canvas.drawCircle(p.getX(), p.getY(), 8, drawPaint);
            }

            for (int i = 0; i < polygone.size() - 1; i++) {
                canvas.drawLine(polygone.get(i).getX(), polygone.get(i).getY(), polygone.get(i + 1).getX(), polygone.get(i + 1).getY(), drawPaint);
            }

            invalidate();
        }
    }
}