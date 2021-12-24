package org.borium.javarecompiler;

import java.io.*;
import java.util.*;

import org.borium.javarecompiler.classfile.*;

public class Recompiler
{
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			args = new String[] { //
					"-classpath", "bin", //
					"-outputpath", "../JrcPortCpp", //
					"-mainclass", "org.borium.javarecompiler.Recompiler", //
					"-vs", "2005",//
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

	/** Output path for generated files. There can be only one. */
	private String outputPath;

	/** Visual Studio version for generated project. There can be only one. */
	private String visualStudio;

	public void addClassPath(String classPath)
	{
		classPaths.add(classPath);
	}

	public void run()
	{
		processClassFile(mainClass);
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
			throw new RuntimeException("Class " + classFileName + " not found");
		}
		ClassFile classFile = new ClassFile();
		try
		{
			classFile.read(fileName);
			IndentedOutputStream stream = new IndentedOutputStream(
					fileName.substring(0, fileName.length() - 5) + "txt");
			classFile.dump(stream);
		}
		catch (ClassFormatError | IOException e)
		{
			e.printStackTrace();
		}
		return classFile;
	}
}
