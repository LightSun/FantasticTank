package com.heaven7.fantastictank.res;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * use {@link Resource} instead
 * @author Administrator
 */
@Deprecated
public class Assets {
	
	public static Texture items;
	public static Texture item_t1_angry_right;
	public static Texture item_t1_angry_top;
	
	//the owner's tank
	public static TextureRegion t_yellow_bottom;
	public static TextureRegion t_yellow_left;
	public static TextureRegion t_green_left;
	public static TextureRegion t_green_top;
	
	//tank1
	public static TextureRegion t1_white_bottom;
	public static TextureRegion t1_white_left;
	public static TextureRegion t1_angry_right;
	public static TextureRegion t1_angry_top;
	
	//tank3-- faster
	public static TextureRegion t3_white_right;
	public static TextureRegion t3_white_top;
	public static TextureRegion t3_angry_bottom;
	public static TextureRegion t3_angry_left;
	
	//tank4
	public static TextureRegion t4_white_right;
	public static TextureRegion t4_white_top;
	public static TextureRegion t4_angry_bottom;
	public static TextureRegion t4_angry_left;
	public static TextureRegion t4_yellow_right;
	public static TextureRegion t4_yellow_top;
	public static TextureRegion t4_green_top;
	public static TextureRegion t4_green_left;
	
	//Ê÷ÁÖ£¬ºþ,ÄàÇ½,×©Ç½
	public static TextureRegion wood;
	public static TextureRegion lake;
	public static TextureRegion dirtWall;
	public static TextureRegion brickWall;
	//×Óµ¯
	public static TextureRegion bullet_normal_left;
	public static TextureRegion bullet_normal_right;
	public static TextureRegion bullet_normal_top;
	public static TextureRegion bullet_normal_bottom;
	public static TextureRegion bullet_better_blue;
	public static TextureRegion bullet_better_green;
	public static TextureRegion bullet_better_dark_blue;
	public static TextureRegion bullet_better_red;
	
	public static Animation anim_explode;
	
	//sound with music
	public static Sound enter_game;
	public static Sound show_bonus;
	public static Sound catch_bonus;
	public static Sound crash_tank;
	public static Sound shoot;
	
	public static Music gameMusic;
	public static Sound move;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load(){
		
		items = loadTexture("/data/tank_atlas.png");
		item_t1_angry_right = new Texture("/data/t1_red_right.png");
		item_t1_angry_top = new Texture("/data/t1_red_top.png");
		//tanks
		t_yellow_bottom = new TextureRegion(items, 32, 32, 32, 32);
		t_yellow_left = new TextureRegion(items, 32, 0, 32, 32);
		t_green_left = new TextureRegion(items, 0, 96, 32, 32);
		t_green_top = new TextureRegion(items, 0, 64, 32, 32);
		
		t1_white_bottom = new TextureRegion(items, 160, 32, 32, 32);
		t1_white_left = new TextureRegion(items, 224, 0, 32, 32);
		t1_angry_right = new TextureRegion(item_t1_angry_right, 0, 0, 32, 32);
		t1_angry_top = new TextureRegion(item_t1_angry_top, 0, 0, 32, 32);
		
		t3_white_right = new TextureRegion(items, 128, 96, 32, 32);
		t3_white_top = new TextureRegion(items, 128, 64, 32, 32);
		t3_angry_bottom = new TextureRegion(items, 192, 0, 32, 32);
		t3_angry_left = new TextureRegion(items, 160, 0, 32, 32);
		
		t4_white_right = new TextureRegion(items, 96, 32, 32, 32);
		t4_white_top = new TextureRegion(items, 96, 0, 32, 32);
		t4_green_left = new TextureRegion(items, 128, 32, 32, 32);
		t4_green_top = new TextureRegion(items, 128, 0, 32, 32);
		t4_yellow_right = new TextureRegion(items, 64, 96, 32, 32);
		t4_yellow_top = new TextureRegion(items, 64, 64, 32, 32);
		t4_angry_bottom = new TextureRegion(items, 96, 96, 32, 32);
		t4_angry_left = new TextureRegion(items, 96, 64, 32, 32);
		//natures
		wood = new TextureRegion(items, 64, 32, 32, 32);
		lake = new TextureRegion(items, 64, 0, 32, 32);
		dirtWall = new TextureRegion(items, 32, 96, 32, 32);
		brickWall = new TextureRegion(items, 32, 64, 32, 32);
		//³£¹æ×Óµ¯
		bullet_normal_bottom = new TextureRegion(items, 204, 40, 4, 4);
		bullet_normal_top = new TextureRegion(items, 200, 40, 4, 4);
		bullet_normal_left = new TextureRegion(items, 192, 40, 4, 4);
		bullet_normal_right = new TextureRegion(items, 196, 40, 4, 4);
		//±äÒì×Óµ¯
		bullet_better_blue = new TextureRegion(items, 192, 32, 8, 8);
		bullet_better_green = new TextureRegion(items, 200, 32, 8, 8);
		bullet_better_dark_blue = new TextureRegion(items, 208, 32, 8, 8);
		bullet_better_red= new TextureRegion(items, 216, 32, 8, 8);
		//±¬Õ¨¶¯»­
		anim_explode = new Animation(0.2f, 
				new TextureRegion(items, 224, 32, 16, 16),
				new TextureRegion(items, 0, 32, 32, 32),
				new TextureRegion(items, 0, 0, 32, 32),
				new TextureRegion(items, 240, 32, 16, 16)
		);
		
		//sound ,music
		enter_game = Gdx.audio.newSound(Gdx.files.internal("/data/enter_game.mp3"));
		show_bonus = Gdx.audio.newSound(Gdx.files.internal("/data/show_bonus.mp3"));
		catch_bonus = Gdx.audio.newSound(Gdx.files.internal("/data/catch_bonus.mp3"));
		crash_tank = Gdx.audio.newSound(Gdx.files.internal("/data/crash_tank.mp3"));
		shoot = Gdx.audio.newSound(Gdx.files.internal("/data/shoot.mp3"));
		move = Gdx.audio.newSound(Gdx.files.internal("/data/move.ogg"));
		
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("/data/game.ogg"));
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		if (Settings.soundEnabled)
			gameMusic.play();
	}
	
	public static void playSound(Sound sound){
		if(Settings.soundEnabled)
			sound.play(1);
	}
}
