package com.codegeneration;

import com.codegeneration.codegenerator.CodeGenerator;
import com.codegeneration.syntaxTree.AST;
import com.codegeneration.syntaxTree.ASTGenerator;
import com.codegeneration.syntaxTree.JSONArray;
import com.codegeneration.syntaxTree.JSONObject;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.file.Path;
import java.nio.file.Paths;

public class main {
    public static void main(String[] args) throws Exception {
        try {
            ASTGenerator astGenerator = new ASTGenerator();
            Path path = Paths.get("/Users/tangcong/Desktop/serviceMatrix/codegeneration/src/main/resources/coffeeMacine.json");
            ParseTree tree = astGenerator.getParserRuleContext(path);
            AST ast = new AST(tree);
            System.out.println(ast.getPayload());

            JSONObject jsonObject = new JSONObject();
            jsonObject = jsonObject.parseObject(path);
            JSONObject s = jsonObject.getJSONObject("coffeeMachine");
            CodeGenerator codeGenerator = new CodeGenerator();
            //codeGenerator.generate(ast);
            codeGenerator.generate2(path);
           // System.out.println(ast.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
