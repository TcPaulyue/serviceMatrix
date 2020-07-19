package com.codegeneration.syntaxTree;

import com.codegeneration.antlr4.JSONLexer;
import com.codegeneration.antlr4.JSONParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Path;
public class ASTGenerator {
    public ParseTree getParserRuleContext (Path path) throws IOException {
        JSONLexer jsonLexer = new JSONLexer(CharStreams.fromPath(path));

        CommonTokenStream tokens = new CommonTokenStream(jsonLexer);

        JSONParser jsonParser = new JSONParser(tokens);

//        JSONParser.ObjContext objCtx = jsonParser.obj();
//        JSONObject jsonObject = new JSONObject(objCtx);
//        System.out.println(jsonObject);
        ParseTree parseTree = jsonParser.json();
        System.out.println(parseTree.toStringTree(jsonParser));

//        parseTreeQueue.add(entryPoint);
//        parseTreeList.add(entryPoint);
//        while(!parseTreeQueue.isEmpty()){
//            ParseTree parseTree = parseTreeQueue.remove();
//            parseTreeList.add(parseTree);
//            //System.out.println(parseTree.toStringTree(cpp14Parser));
//            for(int i=0;i<parseTree.getChildCount();i++){
//                parseTreeQueue.add(parseTree.getChild(i));
//            }
//        }
        //return entryPoint.toStringTree(cpp14Parser);
        return parseTree;
    }
}
