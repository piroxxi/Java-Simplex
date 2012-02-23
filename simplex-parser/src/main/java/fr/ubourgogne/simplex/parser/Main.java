package fr.ubourgogne.simplex.parser;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		FileFormater ff = new FileFormater(new File("sample/sampleClass.java"));
		String formated = ff.format();
		
		FileParser fp = new FileParser(formated);
		
		fp.retrieveClassInfos();
		
		System.out.println(fp.getRepresentedObject());
	}

}
