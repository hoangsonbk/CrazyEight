package gemmy.crazyeight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;

public class TitleView extends View {
	
	private Bitmap titleGraphic;
	private Bitmap buttonStartUp;
	private Bitmap buttonStartDown;
	private int screenW; 
	private int screenH;
	private boolean buttonStarPress;
	private Context myContext;
	
	public TitleView (Context context){
		super(context);
		myContext = context;
		titleGraphic = BitmapFactory.decodeResource(getResources(),R.drawable.title_grahic);
		buttonStartUp = BitmapFactory.decodeResource(getResources(),R.drawable.button_start_up);
		buttonStartDown = BitmapFactory.decodeResource(getResources(),R.drawable.button_start_down);
	}
	
	/*
	 * This is called during layout when the size of this view has changed. 
	 * If you were just added to the view hierarchy, you're called with the 
	 * old values of 0.
	 * w Current width of this view.
	 * h Current height of this view.
	 * oldw Old width of this view.
	 * oldh Old height of this view.
	 */
	@Override
	public void onSizeChanged (int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		Paint paint = new Paint(); 
		Resources resources = myContext.getResources();
		float scale = resources.getDisplayMetrics().density;
		
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		canvas.drawPaint(paint); 
		paint.setColor(Color.BLACK); 
		paint.setTextSize(15*scale); 
		
		canvas.drawBitmap(titleGraphic, (screenW - titleGraphic.getWidth())/2, (int)(screenH*0.2), null); //float x, float y, Paint
		canvas.drawText("Created by Son Nguyen", 20, (int)(screenH*0.9), paint);
		canvas.drawText("A learning Android project",  20, (int)(screenH*0.9) + 20, paint);
		canvas.drawText("Debug info: "+ scale,  20, (int)(screenH*0.9) + 40, paint);
		if (buttonStarPress)
			canvas.drawBitmap(buttonStartDown, (screenW - buttonStartDown.getWidth())/2, (int)(screenH*0.7), null); //top of image at 70% screen height
		else
			canvas.drawBitmap(buttonStartUp, (screenW - buttonStartUp.getWidth())/2, (int)(screenH*0.7), null); //top of image at 70% screen height
	}
	
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event){
		int eventaction = event.getAction();
		int X = (int)event.getX();
		int Y = (int)event.getY();
		switch (eventaction ) {
		case MotionEvent.ACTION_DOWN:
			if (X > (screenW-buttonStartUp.getWidth())/2 && 
			    X < (((screenW-buttonStartUp.getWidth())/2) + buttonStartUp.getWidth()) && 
			    Y > (int)(screenH*0.7) && 
			    Y < (int)(screenH*0.7) + buttonStartUp.getHeight()) {
						buttonStarPress = true;
					}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if (buttonStarPress) {
				Intent gameIntent = new Intent(myContext, GameActivity.class);//pass  the local copy of context & the class name of the activity
				myContext.startActivity(gameIntent); 
				}
			buttonStarPress = false;
			break;
			}
		invalidate(); //invalidate the whole view
		return true;
	}
}
