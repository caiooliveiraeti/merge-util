package br.com.javacia.merge.util.inspector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.javacia.merge.util.Difference;
import br.com.javacia.merge.util.DifferenceInspector;
import br.com.javacia.merge.util.type.JavaCodeType;
import br.com.javacia.merge.util.type.JavaCodeType.JavaCodeTypes;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;

public class JavaCodeDifferenceInspector implements DifferenceInspector {
	
	private JavaDocBuilder javaParserLocal;
	private JavaDocBuilder javaParserRemote;

	public JavaCodeDifferenceInspector(File fileLocal, File fileRemote) {
		javaParserLocal = loadJavaParser(fileLocal);
		javaParserRemote = loadJavaParser(fileRemote);
	}

	public JavaCodeDifferenceInspector(Reader readerLocal, Reader readerRemote) {
		javaParserLocal = loadJavaParser(readerLocal);
		javaParserRemote = loadJavaParser(readerRemote);
	}

	public List<Difference> findDifferences() {
		List<Difference> differences = new ArrayList<Difference>();
		differences.addAll(findDifferencesField());
		differences.addAll(findDifferencesMethod());
		return differences;
	}

	private List<Difference> findDifferencesField() {
		List<Difference> differences = new ArrayList<Difference>();
		List<JavaField> fieldsRemote = findFields(javaParserRemote);
		List<JavaField> fieldsLocal = findFields(javaParserLocal);

		for (JavaField fieldLocal : fieldsLocal) {
			JavaField fieldRemote = findEqualsField(fieldsRemote, fieldLocal);
			if (fieldRemote == null) {
				differences.add(new Difference(new JavaCodeType(JavaCodeTypes.FIELD), fieldLocal.getName(), fieldLocal.getDeclarationSignature(true)));
			}
		}

		return differences;
	}

	private List<Difference> findDifferencesMethod() {
		List<Difference> differences = new ArrayList<Difference>();
		List<JavaMethod> methodsRemote = findMethods(javaParserRemote);
		List<JavaMethod> methodsLocal = findMethods(javaParserLocal);

		for (JavaMethod methodLocal : methodsLocal) {
			JavaMethod methodRemote = findEqualsMethod(methodsRemote, methodLocal);
			if (methodRemote != null) {
				if (!sourceCodeEquals(methodLocal, methodRemote)) {
					differences.add(new Difference(new JavaCodeType(JavaCodeTypes.METHOD), methodLocal.getName(), methodLocal.getSourceCode(), methodRemote.getSourceCode()));
				}
			} else {
				methodRemote = hasOneMethod(methodsRemote, methodLocal.getName());
				if (methodRemote != null && hasOneMethod(methodsLocal, methodLocal.getName()) != null) {
					differences.add(new Difference(new JavaCodeType(JavaCodeTypes.PARAMETER), methodLocal.getName(), methodLocal.getSourceCode(), methodRemote.getSourceCode()));
				} else {
					differences.add(new Difference(new JavaCodeType(JavaCodeTypes.METHOD), methodLocal.getName(),methodLocal.getSourceCode()));
				}
			}
		}

		return differences;
	}

	private JavaMethod hasOneMethod(List<JavaMethod> methods, String methodName) {
		JavaMethod method = null;

		for (JavaMethod javaMethod : methods) {
			if (javaMethod.getName().equals(methodName)) {
				method = javaMethod;
			}
		}

		return method;
	}

	private boolean sourceCodeEquals(JavaMethod methodLocal, JavaMethod methodRemote) {
		String sourceCodeLocal = methodLocal.getSourceCode().replaceAll("[\\n\\r\\t ]", "");
		String sourceCodeRemote = methodRemote.getSourceCode().replaceAll("[\\n\\r\\t ]", "");
		return sourceCodeLocal.equals(sourceCodeRemote);
	}

	private JavaField findEqualsField(List<JavaField> fields, JavaField javaField) {
		for (JavaField field : fields) {
			if (field.getName().equals(javaField.getName()) && field.getType().equals(javaField.getType())) {
				return field;
			}
		}

		return null;
	}

	private JavaMethod findEqualsMethod(List<JavaMethod> methods, JavaMethod javaMethod) {
		for (JavaMethod method : methods) {
			if (method.equals(javaMethod)) {
				return method;
			}
		}

		return null;
	}

	private List<JavaMethod> findMethods(JavaDocBuilder javaParser) {
		List<JavaMethod> methods = new ArrayList<JavaMethod>();

		JavaClass[] classes = javaParser.getClasses();
		for (JavaClass javaClass : classes) {
			methods.addAll(Arrays.asList(javaClass.getMethods()));
		}

		return methods;
	}

	private List<JavaField> findFields(JavaDocBuilder javaParser) {
		List<JavaField> fields = new ArrayList<JavaField>();

		JavaClass[] classes = javaParser.getClasses();
		for (JavaClass javaClass : classes) {
			fields.addAll(Arrays.asList(javaClass.getFields()));
		}

		return fields;
	}

	private JavaDocBuilder loadJavaParser(Reader reader) {
		JavaDocBuilder builder = new JavaDocBuilder();
		builder.addSource(reader);
		return builder;
	}

	private JavaDocBuilder loadJavaParser(File file) {
		JavaDocBuilder builder = new JavaDocBuilder();
		try {
			builder.addSource(new FileReader(file));
		} catch (FileNotFoundException e) {
			builder = null;
		}
		return builder;
	}

}