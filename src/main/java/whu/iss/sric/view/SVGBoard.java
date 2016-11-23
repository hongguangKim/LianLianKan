package whu.iss.sric.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import whu.iss.sric.android.R;
import whu.iss.sric.svgandroid.SVG;
import whu.iss.sric.svgandroid.SVGParser;

public class SVGBoard extends BoardView {
	public SVGBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public Bitmap getBitmap(int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);

		SVG svg = SVGParser.getSVGFromInputStream(getContext().getResources().openRawResource(R.raw.shape_heart), width, height);
		canvas.drawPicture(svg.getPicture());

		return bitmap;
	}

}
