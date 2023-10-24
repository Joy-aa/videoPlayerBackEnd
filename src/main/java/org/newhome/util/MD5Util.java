package org.newhome.util;

import java.security.MessageDigest;
import java.util.Random;

public class MD5Util {
	public static String md5(String source) {
		String des = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(source.getBytes());
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < result.length; i++) {
				byte b = result[i];
				buf.append(String.format("%02X", b));
			}
			des = buf.toString().toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("md5 failure");
		}
		return des;
	}

	public static String getSalt() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		return sb.toString();
	}

	private static final String salt = "1a2b3c4d";//前端存储一个固定盐
	public static String inputPassToFormPass(String inputPass) {
		String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

	public static String formPassToDBPass(String formPass, String salt) {
		String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

	public static String inputPassToDBPass(String inputPass, String salt) {
		String fromPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(fromPass, salt);
		return dbPass;
	}
	public static void main(String[] args) {
		System.out.println(MD5Util.md5("000000"));
	}

}
