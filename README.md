LianLianKan
================

  做了一个相册连连看游戏。为了换工作简单做了一个。<br>
  游戏介绍：<br>
  1.启动应用首选是引导页只有一个play图片按钮。<br>
  2.当点击play图片按钮会跳转到选择相册界面，在其中的图片最多能选18个。这是为了通过相册中的图片用于连连看中的一对一对的item。<bar>
  3.可以通过点击重新排序按钮（左下按钮）重新random布局（最多3次）。<br>
  4.可以通过点击提示按钮（右下按钮）来消除一对item（最多3次）。<br>
  5.最上面是倒计时progress。<br>
  6.progress到0时游戏结束，会弹出dialog提醒玩家输赢以及用时。<br>
  7.在dialog中可以终止游戏，也可以重新开始游戏，或下一关，下一关只是减少了时间而以。<br>
  <br>
  由于UI相关没有投入时间比较lo～请谅解～<br>
  引用了一些open source lib可参考gradle脚本。<br>
  图片的形状可以为原型，心形，星形等，可在代码中更改<br>
Demo
================
![demo](https://raw.githubusercontent.com/hongguangKim/LianLianKan/master/Demo/welcome.png)![demo](https://raw.githubusercontent.com/hongguangKim/LianLianKan/master/Demo/selectimage.png)![demo](https://raw.githubusercontent.com/hongguangKim/LianLianKan/master/Demo/frame1.png)![demo](https://raw.githubusercontent.com/hongguangKim/LianLianKan/master/Demo/frame2.png)![demo](https://raw.githubusercontent.com/hongguangKim/LianLianKan/master/Demo/frame3.png)![demo](https://raw.githubusercontent.com/hongguangKim/LianLianKan/master/Demo/over.png)

Source
================
图片的形状取决于GameView extends谁<bar>
例如:圆形<bar>
```
public class GameView extends CircleBoard //圆形item
...
//核心代码，实现父类的getBitmap方法（mask bitmap）
@Override
	public Bitmap getBitmap(int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		canvas.drawOval(new RectF(0.0f, 0.0f, width, height), paint);
		return bitmap;
	}
```
例如：SVG蒙版应用
```
public class GameView extends SVGBoard //SVG蒙版
...
//核心代码，实现父类的getBitmap方法（mask bitmap）
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
```

Gradle
================
```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'

    compile 'com.github.bumptech.glide:glide:3.6.1'
    compile 'com.commit451:PhotoView:1.2.4'
    compile 'com.isseiaoki:simplecropview:1.0.13'
    compile 'com.yongchun:com.yongchun.imageselector:1.1.0'
}
```
Tips
================
在AndroidManifest.xml中添加ImageSelector相关activity时<br>
```
  <activity android:name="com.yongchun.library.view.ImageSelectorActivity" android:theme="@style/AppTheme.NoActionBar"/>
  <activity android:name="com.yongchun.library.view.ImagePreviewActivity" android:theme="@style/AppTheme.NoActionBar"/>
  <activity android:name="com.yongchun.library.view.ImageCropActivity" android:theme="@style/AppTheme.NoActionBar"/>
```
需要添加 android:theme="@style/AppTheme.NoActionBar"属性，要不然会报错<br>
```
<!-- Base application theme. -->
	<style name="AppTheme" parent="Theme.AppCompat">
		<!-- Customize your theme here. -->
	</style>
	<style name="AppTheme.NoActionBar">
		<item name="windowNoTitle">true</item>
		<item name="windowActionBar">true</item>
	</style>
```
