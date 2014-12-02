package com.heaven7.fantastictank.res;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resource {

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
	
	//tank2-- faster
	public static TextureRegion t2_white_right;
	public static TextureRegion t2_white_top;
	public static TextureRegion t2_angry_bottom;
	public static TextureRegion t2_angry_left;
	
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
	public static TextureRegion smallBrickWall;
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
	
	//buff
	public static TextureRegion buff_addlife;
	public static TextureRegion buff_double_fire;
	public static TextureRegion buff_pause_time;
	public static TextureRegion buff_safe;
	public static TextureRegion buff_star;
	
	//sound with music
	public static Sound enter_game;
	public static Sound show_bonus;
	public static Sound catch_bonus;
	public static Sound crash_tank;
	public static Sound shoot;
	
	public static Music gameMusic;
	public static Sound move;
	
	public static BitmapFont font;
	
	private static TextureAtlas ItemsAtlas;
	
	public static void load(){
		loadItems2(); 
		loadBuffTexture();
		
		//sound ,music
		enter_game = Gdx.audio.newSound(Gdx.files.internal("data/enter_game.mp3"));
		show_bonus = Gdx.audio.newSound(Gdx.files.internal("data/show_bonus.mp3"));
		catch_bonus = Gdx.audio.newSound(Gdx.files.internal("data/catch_bonus.mp3"));
		crash_tank = Gdx.audio.newSound(Gdx.files.internal("data/crash_tank.mp3"));
		shoot = Gdx.audio.newSound(Gdx.files.internal("data/shoot.mp3"));
		move = Gdx.audio.newSound(Gdx.files.internal("data/move.ogg"));
		
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("data/game.ogg"));
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
	}

	private static void loadItems2() {
		ItemsAtlas = new TextureAtlas(Gdx.files.internal("data/items2.pack"));
		t1_white_bottom = ItemsAtlas.findRegion("1_white_bottom"); 
		t1_white_left = ItemsAtlas.findRegion("1_white_left"); 
		t1_angry_right = ItemsAtlas.findRegion("1_red_right"); 
		t1_angry_top = ItemsAtlas.findRegion("1_red_top"); 
		
		t2_white_right = ItemsAtlas.findRegion("2_white_right"); 
		t2_white_top = ItemsAtlas.findRegion("2_white_top"); 
		t2_angry_left = ItemsAtlas.findRegion("2_red_left"); 
		t2_angry_bottom = ItemsAtlas.findRegion("2_red_bottom"); 
		
		t4_angry_bottom = ItemsAtlas.findRegion("4_red_bottom"); 
		t4_angry_left = ItemsAtlas.findRegion("4_red_left"); 
		t4_green_left = ItemsAtlas.findRegion("4_green_left"); 
		t4_green_top = ItemsAtlas.findRegion("4_green_top"); 
		t4_white_right = ItemsAtlas.findRegion("4_white_right"); 
		t4_white_top = ItemsAtlas.findRegion("4_white_top"); 
		t4_yellow_right = ItemsAtlas.findRegion("4_yellow_right"); 
		t4_yellow_top = ItemsAtlas.findRegion("4_yellow_top"); 
		
		wood = ItemsAtlas.findRegion("wood"); 
		lake = ItemsAtlas.findRegion("lake"); 
		dirtWall = ItemsAtlas.findRegion("dirtwall"); 
		brickWall = ItemsAtlas.findRegion("brickwall"); 
		
		t_green_left = ItemsAtlas.findRegion("my_2_left"); 
		t_green_top = ItemsAtlas.findRegion("my_2_top"); 
		t_yellow_bottom= ItemsAtlas.findRegion("my_1_bottom"); 
		t_yellow_left = ItemsAtlas.findRegion("my_1_left"); 
		
		anim_explode = new Animation(0.2f, 
				ItemsAtlas.findRegion("tank_exp2"),
				ItemsAtlas.findRegion("tank_exp4"),
				ItemsAtlas.findRegion("tank_exp5"),
				ItemsAtlas.findRegion("tank_exp6")
				);
		loadBulletTexture();
	}

	private static void loadBuffTexture() {
		TextureAtlas buffAtlas = new TextureAtlas(Gdx.files.internal("data/buff.pack"));
		buff_addlife = buffAtlas.findRegion("buff_addlife");
		buff_double_fire = buffAtlas.findRegion("buff_double_fire");
		buff_pause_time = buffAtlas.findRegion("buff_pause_time");
		buff_safe = buffAtlas.findRegion("buff_safe");
		buff_star = buffAtlas.findRegion("buff_star");
		smallBrickWall = buffAtlas.findRegion("small_brickwall");
	}

	private static void loadBulletTexture() {
		Texture bullets = new Texture(Gdx.files.internal("data/bullets.png"));
		//normal
		bullet_normal_bottom = new TextureRegion(bullets, 12, 8, 4, 4);
		bullet_normal_top = new TextureRegion(bullets, 8, 8, 4, 4);
		bullet_normal_left = new TextureRegion(bullets, 0, 8, 4, 4);
		bullet_normal_right = new TextureRegion(bullets, 4, 8, 4, 4);
		//±äÒì×Óµ¯
		bullet_better_blue = new TextureRegion(bullets, 0, 0, 8, 8);
		bullet_better_green = new TextureRegion(bullets, 8, 0, 8, 8);
		bullet_better_dark_blue = new TextureRegion(bullets, 16, 0, 8, 8);
		bullet_better_red= new TextureRegion(bullets, 24, 0, 8, 8);
	}
	
	public static void playSound(Sound sound){
		sound.stop();
		if(Settings.soundEnabled)
			sound.play(1);
	}
}
