package comp3350.ei.presentation.SwipeController;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class SwipeButton {
    private RectF buttonInstance = null;

    private final int backgroundColour;
    private final int textColour;
    private final String displayText;
    private final float textSize;

    public SwipeButton(int backgroundColour, int textColour, String text, float textSize) {
        this.backgroundColour = backgroundColour;
        this.textColour = textColour;
        this.displayText = text;
        this.textSize = textSize;
    }

    public RectF draw(Canvas canvas, RectF space, float cornerRounding) {
        Paint p = new Paint();

        buttonInstance = space;
        p.setColor(backgroundColour);
        canvas.drawRoundRect(buttonInstance, cornerRounding, cornerRounding, p);
        drawText(canvas, p);

        return buttonInstance;
    }

    private void drawText(Canvas canvas, Paint p) {
        p.setColor(textColour);
        p.setAntiAlias(true);
        p.setTextSize(textSize);
        float textWidth = p.measureText(displayText);
        canvas.drawText(displayText, buttonInstance.centerX()-(textWidth/2), buttonInstance.centerY()+(textSize/2), p);
    }

}
