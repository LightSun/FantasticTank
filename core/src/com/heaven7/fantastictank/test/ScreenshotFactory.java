package com.heaven7.fantastictank.test;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.Logger;

public class ScreenshotFactory {
	
	static Logger log = new Logger("ScreenshotFactory");

    private static int counter = 0;
    public static void saveScreenshot(String path){
    	long start;
    	if(counter ++ <10){
    		 start = System.currentTimeMillis();
    		  FileHandle fh;
              //do{
                  //fh = new FileHandle("screenshot" + counter++ + ".png");
                  fh = new FileHandle(path+"/"+counter+".png");
             // }while (fh.exists());
              Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
              PixmapIO.writePNG(fh, pixmap);
              log.error("cost time = "+(System.currentTimeMillis()-start));
              pixmap.dispose();
    	}
    }

    private static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown){
        final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);

        if (yDown) {
            // Flip the pixmap upside down
            ByteBuffer pixels = pixmap.getPixels();
            int numBytes = w * h * 4;
            byte[] lines = new byte[numBytes];
            int numBytesPerLine = w * 4;
            for (int i = 0; i < h; i++) {
                pixels.position((h - i - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }
            pixels.clear();
            pixels.put(lines);
        }

        return pixmap;
    }
}
