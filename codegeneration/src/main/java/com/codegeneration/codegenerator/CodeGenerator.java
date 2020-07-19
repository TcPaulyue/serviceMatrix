package com.codegeneration.codegenerator;

import com.codegeneration.syntaxTree.AST;
import com.codegeneration.syntaxTree.JSONObject;
import org.ainslec.picocog.PicoWriter;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
    private String myClassName;
    private String myPackageName;

    public void generate2(Path path) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject = jsonObject.parseObject(path);
        JSONObject jsonObject1 = new JSONObject();
        myClassName = jsonObject.getMap().keySet().toArray()[0].toString();
        jsonObject1 = jsonObject.getJSONObject(myClassName);

        myPackageName = "com.samplepackage";

        PicoWriter topWriter = new PicoWriter();

        topWriter.writeln ("package " + myPackageName + ";");
        topWriter.writeln ("");
        topWriter.writeln_r ("public class "+myClassName+" {");

        PicoWriter memvarWriter    = topWriter.createDeferredWriter();
//        topWriter.writeln_r ("{");
//        PicoWriter indentedSection = topWriter.createDeferredWriter();
//        topWriter.writeln_l ("}");
        topWriter.writeln("");
        // Reserve a place at the current row
        PicoWriter methodSection = topWriter.createDeferredWriter();

        for(String s : jsonObject1.getMap().keySet()) {
            memvarWriter.writeln("String " +s + ";" );
        }
        PicoWriter mainMethod = methodSection.createDeferredWriter();

        mainMethod.writeln_r("public static void main(String[] args) {");
        mainMethod.writeln_r("if (args.length == 0) {");
        mainMethod.writeln("System.out.println(\"Require more than one argument\");");
        mainMethod.writeln_lr("} else if (args.length == 1) {");
        mainMethod.writeln("doSomething();");
        mainMethod.writeln_lr("} else {");
        mainMethod.writeln("System.out.println(\"Too many arguments\");");
        mainMethod.writeln_l("}");
        mainMethod.writeln_l("}");
        mainMethod.writeln("");
        topWriter.writeln_l ("}");

        // To extract the source code, call .toString()
        System.out.println(topWriter.toString());
    }

    public void generate(AST ast){
        PicoWriter topWriter = new PicoWriter();

        myPackageName = "com.samplepackage";
        if (ast.getPayload() instanceof Token) {
            Token token = (Token) ast.getPayload();
            myClassName = token.getText();
        }

        topWriter.writeln ("package " + myPackageName + ";");
        topWriter.writeln ("");
        topWriter.writeln_r ("public class "+myClassName+" {");

        PicoWriter memvarWriter    = topWriter.createDeferredWriter();
        topWriter.writeln_r ("{");
        PicoWriter indentedSection = topWriter.createDeferredWriter();
        topWriter.writeln_l ("}");
        topWriter.writeln("");
        // Reserve a place at the current row
        PicoWriter methodSection = topWriter.createDeferredWriter();



        List<List<AST>> childListStack = new ArrayList<>();
        if (ast.getChildren().size() > 0) {
            List<AST> children = new ArrayList<>();
            for (int i = 0; i < ast.getChildren().size(); i++) {
                children.add(ast.getChildren().get(i));
            }
            childListStack.add(children);
        }
        while (!childListStack.isEmpty()) {

            List<AST> childStack = childListStack
                    .get(childListStack.size() - 1);

            if (childStack.isEmpty()) {
                childListStack.remove(childListStack.size() - 1);
            } else {
                ast = childStack.remove(0);
                if (ast.getPayload() instanceof Token) {
                    Token token1 = (Token) ast.getPayload();
                    if(token1.getType()==10){
                        memvarWriter.writeln("String "+token1.getText().replace("\"","")+";" );
                    }
                }
                if (ast.getChildren().size() > 0) {
                    List<AST> children = new ArrayList<>();
                    for (int i = 0; i < ast.getChildren().size(); i++) {
                        children.add(ast.getChildren().get(i));
                    }
                    childListStack.add(children);
                }
            }
        }
        // Reserve a place at the current row
        PicoWriter mainMethod = methodSection.createDeferredWriter();

        mainMethod.writeln_r("public static void main(String[] args) {");
        mainMethod.writeln_r("if (args.length == 0) {");
        mainMethod.writeln("System.out.println(\"Require more than one argument\");");
        mainMethod.writeln_lr("} else if (args.length == 1) {");
        mainMethod.writeln("doSomething();");
        mainMethod.writeln_lr("} else {");
        mainMethod.writeln("System.out.println(\"Too many arguments\");");
        mainMethod.writeln_l("}");
        mainMethod.writeln_l("}");
        mainMethod.writeln("");
        topWriter.writeln_l ("}");

        // To extract the source code, call .toString()
        System.out.println(topWriter.toString());
    }
}
