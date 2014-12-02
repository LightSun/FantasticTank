package com.heaven7.fantastictank.level.arttext;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.ObjectMap;
import com.heaven7.fantastictank.level.arttext.digit.EightArtText;
import com.heaven7.fantastictank.level.arttext.digit.FiveArtText;
import com.heaven7.fantastictank.level.arttext.digit.FourArtText;
import com.heaven7.fantastictank.level.arttext.digit.NineArtText;
import com.heaven7.fantastictank.level.arttext.digit.OneArtText;
import com.heaven7.fantastictank.level.arttext.digit.SevenArtText;
import com.heaven7.fantastictank.level.arttext.digit.SixArtText;
import com.heaven7.fantastictank.level.arttext.digit.ThreeArtText;
import com.heaven7.fantastictank.level.arttext.digit.TwoArtText;
import com.heaven7.fantastictank.level.arttext.digit.ZeroArtText;
import com.heaven7.fantastictank.level.arttext.letter.AArtText;
import com.heaven7.fantastictank.level.arttext.letter.BArtText;
import com.heaven7.fantastictank.level.arttext.letter.CArtText;
import com.heaven7.fantastictank.level.arttext.letter.DArtText;
import com.heaven7.fantastictank.level.arttext.letter.EArtText;
import com.heaven7.fantastictank.level.arttext.letter.FArtText;
import com.heaven7.fantastictank.level.arttext.letter.GArtText;
import com.heaven7.fantastictank.level.arttext.letter.HArtText;
import com.heaven7.fantastictank.level.arttext.letter.IArtText;
import com.heaven7.fantastictank.level.arttext.letter.JArtText;
import com.heaven7.fantastictank.level.arttext.letter.KArtText;
import com.heaven7.fantastictank.level.arttext.letter.LArtText;
import com.heaven7.fantastictank.level.arttext.letter.MArtText;
import com.heaven7.fantastictank.level.arttext.letter.NArtText;
import com.heaven7.fantastictank.level.arttext.letter.OArtText;
import com.heaven7.fantastictank.level.arttext.letter.PArtText;
import com.heaven7.fantastictank.level.arttext.letter.QArtText;
import com.heaven7.fantastictank.level.arttext.letter.RArtText;
import com.heaven7.fantastictank.level.arttext.letter.SArtText;
import com.heaven7.fantastictank.level.arttext.letter.TArtText;
import com.heaven7.fantastictank.level.arttext.letter.UArtText;
import com.heaven7.fantastictank.level.arttext.letter.VArtText;
import com.heaven7.fantastictank.level.arttext.letter.WArtText;
import com.heaven7.fantastictank.level.arttext.letter.XArtText;
import com.heaven7.fantastictank.level.arttext.letter.YArtText;
import com.heaven7.fantastictank.level.arttext.letter.ZArtText;
/**
 * 一个快速创建ArtText、ArtTextGroup的帮助类. 内部有缓存机制(可选使用)
 * @author Administrator
 */
public class ArtTextHelper {

	private static final String SUFFIX = "ArtText";
	private static final String PREFIX_LETTER; /* 包含.的前缀  --字母类型 */
	private static final String PREFIX_DIGIT;  /* 包含.的前缀  --数字类型 */
	
	private static final ObjectMap<String, ArtText> MAP_TEXTS;
	private static final ObjectMap<String, String> DIGIT_WORDS;
	
	private static final ArrayList<String> DUPLICATE_CHARS = new ArrayList<String>(); 

	static {
		//反射创建对象--所需要的前缀
		String name = AArtText.class.getName();
		String name2 = ZeroArtText.class.getName();
		PREFIX_LETTER = name.substring(0, name.lastIndexOf(".")+1);
		PREFIX_DIGIT = name2.substring(0, name2.lastIndexOf(".")+1);
		
		//缓存所有字母，数字的ArtText一份
		MAP_TEXTS = new ObjectMap<String, ArtText>(46);//加载因子0.8

		MAP_TEXTS.put("A", new AArtText());
		MAP_TEXTS.put("B", new BArtText());
		MAP_TEXTS.put("C", new CArtText());
		MAP_TEXTS.put("D", new DArtText());
		MAP_TEXTS.put("E", new EArtText());
		MAP_TEXTS.put("F", new FArtText());
		MAP_TEXTS.put("G", new GArtText());

		MAP_TEXTS.put("H", new HArtText());
		MAP_TEXTS.put("I", new IArtText());
		MAP_TEXTS.put("J", new JArtText());
		MAP_TEXTS.put("K", new KArtText());
		MAP_TEXTS.put("L", new LArtText());
		MAP_TEXTS.put("M", new MArtText());
		MAP_TEXTS.put("N", new NArtText());

		MAP_TEXTS.put("O", new OArtText());
		MAP_TEXTS.put("P", new PArtText());
		MAP_TEXTS.put("Q", new QArtText());
		MAP_TEXTS.put("R", new RArtText());
		MAP_TEXTS.put("S", new SArtText());
		MAP_TEXTS.put("T", new TArtText());

		MAP_TEXTS.put("U", new UArtText());
		MAP_TEXTS.put("V", new VArtText());
		MAP_TEXTS.put("W", new WArtText());
		MAP_TEXTS.put("X", new XArtText());
		MAP_TEXTS.put("Y", new YArtText());
		MAP_TEXTS.put("Z", new ZArtText());

		// digitals
		MAP_TEXTS.put("0", new ZeroArtText());
		MAP_TEXTS.put("1", new OneArtText());
		MAP_TEXTS.put("2", new TwoArtText());
		MAP_TEXTS.put("3", new ThreeArtText());
		MAP_TEXTS.put("4", new FourArtText());
		MAP_TEXTS.put("5", new FiveArtText());
		MAP_TEXTS.put("6", new SixArtText());
		MAP_TEXTS.put("7", new SevenArtText());
		MAP_TEXTS.put("8", new EightArtText());
		MAP_TEXTS.put("9", new NineArtText());

		// 存储 数字-->英文的映射
		DIGIT_WORDS = new ObjectMap<String, String>(14);
		DIGIT_WORDS.put("0", "Zero");
		DIGIT_WORDS.put("1", "One");
		DIGIT_WORDS.put("2", "Two");
		DIGIT_WORDS.put("3", "Three");
		DIGIT_WORDS.put("4", "Four");
		DIGIT_WORDS.put("5", "Five");
		DIGIT_WORDS.put("6", "Six");
		DIGIT_WORDS.put("7", "Seven");
		DIGIT_WORDS.put("8", "Eight");
		DIGIT_WORDS.put("9", "Nine");
	}

	/**
	 * @param key  a digital or a letter,eg: "0","P"
	 */
	public static ArtText get(String key) {
		return MAP_TEXTS.get(key.toUpperCase());
	}
	
	/** 不用缓存的ArtText对象,直接创建新的ArtText(默认水平布局) */
	public static ArtTextGroup newGroup(String key){
		return newGroup(key, true);
	}
	/** 不用缓存的ArtText对象,直接创建新的ArtText */
	public static ArtTextGroup newGroup(String key,boolean horizontal){
		if (key == null || key.length() == 0)
			return null;
		key = key.toUpperCase();
		
		final ArtTextGroup group = new ArtTextGroup();
		group.setMode(horizontal ? ArtTextGroup.MODE_HORIZONTAL : ArtTextGroup.MODE_VERTICAL);
		
		int len = key.length();
		for (int i = 0; i < len; i++) {
			group.addArtText(create(key.charAt(i) + ""));
		}
		return group;
	}

	/**  用 缓存的ArtText组合创建一个group(如果有重复字符，则会自动创建新的ArtText)。默认水平布局  
	 * @see #getGroup(String, boolean)*/
	public static ArtTextGroup getGroup(String key) {
		return getGroup(key, true);
	}

	/**
	 *  用 缓存的ArtText组合创建一个group(如果有重复字符，则会自动创建新的ArtText)
	 * @param key  艺术文字的整个单词
	 * @param horizontal 水平/竖直布局
	 */
	public static ArtTextGroup getGroup(String key, boolean horizontal) {
		if (key == null || key.length() == 0)
			return null;
		key = key.toUpperCase();
		final List<String> dupChars = checkDuplicateChar(key);
		final boolean empty = dupChars.isEmpty();
		
		final ArtTextGroup group = new ArtTextGroup();
		group.setMode(horizontal ? ArtTextGroup.MODE_HORIZONTAL : ArtTextGroup.MODE_VERTICAL);

		int len = key.length();
		for (int i = 0; i < len; i++) {
			String chz = key.charAt(i) + "";
			if(!empty && dupChars.contains(chz))
			    group.addArtText(create(chz));
			else
				group.addArtText(MAP_TEXTS.get(chz));
		}
		dupChars.clear();
		return group;
	}

	/** 创建一个单一的ArtText(A-Z,0-9)，不支持ArtGroup */
	public static ArtText create(String chz) {
		if (chz == null || chz.length() == 0)
			return null;
		if (chz.length() != 1)
			throw new IllegalArgumentException("only support a char");
		chz = chz.toUpperCase();

		try {
			final char ch = chz.charAt(0);
			if (Character.isDigit(ch)) {
				return (ArtText) Class.forName(PREFIX_DIGIT+DIGIT_WORDS.get(chz) + SUFFIX)
						.newInstance();
			} else if(Character.isLetter(ch)){
				return (ArtText) Class.forName(PREFIX_LETTER+chz + SUFFIX).newInstance();
			}
			throw new IllegalArgumentException("invalid char ="+ch);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	// 检查该字符串是否有重复的字符,如果有返回重复的字符
	private static List<String> checkDuplicateChar(String str) {
		str = str.toUpperCase();
		int len = str.length();
 		
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (str.charAt(i) == str.charAt(j)) {
					DUPLICATE_CHARS.add(str.charAt(i)+"");
				}
			}
		}
		return DUPLICATE_CHARS;
	}
}
