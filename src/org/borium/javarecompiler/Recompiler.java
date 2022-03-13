package org.borium.javarecompiler;

import java.io.*;
import java.util.*;

import org.borium.javarecompiler.classfile.*;
import org.borium.javarecompiler.cplusplus.*;

public class Recompiler
{
	public static boolean instructionComments = false;

	public static boolean stackComments = false;

	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			args = new String[] { //
					"-classpath", "bin", //
					"-outputpath", "../JrcPortCpp", //
					"-mainclass", "org.borium.javarecompiler.classfile.constants.ConstantPool", //
					"-vs", "2005", //
					"-comments", "all", //
			};
		}
		Recompiler recompiler = new Recompiler();
		for (int argc = 0; argc < args.length; argc += 2)
		{
			switch (args[argc])
			{
			case "-classpath":
				recompiler.addClassPath(args[argc + 1]);
				break;
			case "-outputpath":
				recompiler.setOutputPath(args[argc + 1]);
				break;
			case "-mainclass":
				recompiler.setMainClass(args[argc + 1]);
				break;
			case "-vs":
				recompiler.setVisualStudio(args[argc + 1]);
				break;
			case "-comments":
				recompiler.setCommentLevel(args[argc + 1]);
				break;
			default:
				throw new RuntimeException("Unsupported argument " + args[argc]);
			}
		}
		recompiler.run();
		System.out.println("Done.");
	}

	/** Main class name within source class path. There can be only one. */
	private String mainClass;

	/** List of all class paths to search for the class. */
	private ArrayList<String> classPaths = new ArrayList<>();

	protected ArrayList<HashMap<String, String>> dummy;

	/** Output path for generated files. There can be only one. */
	private String outputPath;

	/** Visual Studio version for generated project. There can be only one. */
	private String visualStudio;

	private HashMap<String, ClassFile> processedClasses = new HashMap<>();

	private ArrayList<CppClass> generatedClasses = new ArrayList<>();

	public void addClassPath(String classPath)
	{
		classPaths.add(classPath);
	}

	public void run()
	{
		ClassFile classFile = processClassFile(mainClass);
		processedClasses.put(classFile.getClassName(), classFile);
		List<String> newClassNames = new ArrayList<>();
		addReferencedClasses(newClassNames, classFile);
		while (newClassNames.size() > 0)
		{
			String newClassName = newClassNames.remove(0);
			classFile = processClassFile(newClassName);
			processedClasses.put(classFile.getClassName(), classFile);
			addReferencedClasses(newClassNames, classFile);
		}
		generateClasses();
		writeClasses();
	}

	public void setMainClass(String mainClass)
	{
		if (this.mainClass != null)
		{
			throw new RuntimeException(
					"Main class already set to '" + this.mainClass + "', not setting it to '" + mainClass + "'");
		}
		this.mainClass = mainClass;
	}

	public void setOutputPath(String outputPath)
	{
		if (this.outputPath != null)
		{
			throw new RuntimeException(
					"Output path already set to '" + this.outputPath + "', not setting it to '" + outputPath + "'");
		}
		this.outputPath = outputPath;
	}

	public void setVisualStudio(String visualStudio)
	{
		if (this.visualStudio != null)
		{
			throw new RuntimeException("Visual Studio already set to '" + this.visualStudio + "', not setting it to '"
					+ visualStudio + "'");
		}
		this.visualStudio = visualStudio;
	}

	private void addReferencedClasses(List<String> newClassNames, ClassFile classFile)
	{
		List<String> allReferences = classFile.getReferencedClasses();

		for (String reference : allReferences)
		{
			if (!reference.startsWith("java.") && !reference.startsWith("["))
			{
				if (!processedClasses.containsKey(reference) && !newClassNames.contains(reference))
				{
					newClassNames.add(reference);
				}
			}
		}
	}

	private void generateClass(String className)
	{
		CppClass cppClass = new CppClass(processedClasses.get(className));
		generatedClasses.add(cppClass);
	}

	private void generateClasses()
	{
		// TODO Auto-generated method stub
		generateClass(mainClass);
	}

	private ClassFile processClassFile(String classFileName)
	{
		if (classFileName.startsWith("java."))
		{
			return null;
		}
		String classPathFileName = classFileName.replace('.', '/') + ".class";
		String fileName = null;
		for (String classPath : classPaths)
		{
			File file = new File(classPath + "/" + classPathFileName);
			if (file.exists() && file.isFile())
			{
				fileName = classPath + "/" + classPathFileName;
				break;
			}
		}
		if (fileName == null)
		{
			System.out.println("Error: " + classFileName);
			throw new RuntimeException("Class " + classFileName + " not found");
		}
		ClassFile classFile = new ClassFile();
		try
		{
			classFile.read(fileName);
		}
		catch (ClassFormatError | IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			IndentedOutputStream stream = new IndentedOutputStream(
					fileName.substring(0, fileName.length() - 5) + "txt");
			classFile.dump(stream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return classFile;
	}

	private void setCommentLevel(String commentLevel)
	{
		switch (commentLevel)
		{
		case "all":
			instructionComments = true;
			stackComments = true;
			break;
		case "none":
			instructionComments = false;
			stackComments = false;
			break;
		default:
			throw new RuntimeException("Unsupported comment level " + commentLevel);
		}

	}

	private void writeClasses()
	{
		for (CppClass cppClass : generatedClasses)
		{
			cppClass.writeClass(outputPath);
		}
	}
}
