import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CodeGenrator extends SPLADBaseVisitor<String>{
	private StringBuffer setupbuffer = new StringBuffer();
	private StringBuffer setupfirstbuffer = new StringBuffer();
	private StringBuffer loopbuffer = new StringBuffer();
	private List<Container> ListOfContainers = new ArrayList<Container>();
	private List<Drinks> ListOfDrinks = new ArrayList<Drinks>();
	private Drinks drinkHolder;
	private boolean haspour = false;
	private boolean hasrfidfound = false;
	
	@Override
	public String visitAssign(SPLADParser.AssignContext ctx) {
		return visit(ctx.callid()) + visit(ctx.assignend());
	}
	
	@Override
	public String visitTermsymbol(SPLADParser.TermsymbolContext ctx){
		if (ctx.getText().trim().equals("AND")){
			return "&&";
		}
		else {
			return "ERROR";
		}
	}
	
	@Override
	public String visitExprsymbol(SPLADParser.ExprsymbolContext ctx){
		if (ctx.getText().trim().equals("OR")){
			return "||";
		}
		else {
			return "ERROR";
		}
	}
	
	@Override
	public String visitNumeric(SPLADParser.NumericContext ctx) {
		return visit(ctx.plusminusorempty()) + ctx.DIGIT().getText() + visit(ctx.numericend());
	}
	
	@Override
	public String visitNontermswitch(SPLADParser.NontermswitchContext ctx) {
		return "switch(" + visit(ctx.expr()) + ")\n{\n" + visit(ctx.cases()) + "}\n";
	}
	
	@Override
	public String visitRoot(SPLADParser.RootContext ctx) {
		if(ctx.dcl() != null){
			return visit(ctx.dcl()) + ";\n";
		}
		else if (ctx.function() != null){
			return visit(ctx.function());
		}
		else if (ctx.assign() != null)
		{
			return visit(ctx.assign()) + ";\n";
		}
		else if (ctx.drinkdcl() != null){
			return visit(ctx.drinkdcl());
		}
		else{
			return "ERROR";
		}
	}
	
	@Override
	public String visitNontermwhile(SPLADParser.NontermwhileContext ctx) {
		return "while (" + visit(ctx.expr()) + ")\n" + visit(ctx.block());
	}
	
	@Override
	public String visitStmts(SPLADParser.StmtsContext ctx) {
		if (ctx.stmt() != null){
			return visit(ctx.stmt())+ visit(ctx.stmts());
		}
		return "";
	}
	
	@Override
	public String visitTermend(SPLADParser.TermendContext ctx) {
		if (ctx.term() != null){
			return visit(ctx.termsymbol()) + visit(ctx.term());
		}
		else{
			return "";
		}
	}
	
	@Override
	public String visitNumericend(SPLADParser.NumericendContext ctx) {
		if (!ctx.getText().equals("")){
			return "." + ctx.DIGIT().getText();
		}
		return "";
	}
	
	@Override
	public String visitExpr(SPLADParser.ExprContext ctx) {
		return visit(ctx.term()) + visit(ctx.exprend());
	}
	
	@Override
	public String visitFrom(SPLADParser.FromContext ctx) {
		return "for(" +	visit(ctx.assign()) + "; " + visit(ctx.assign().callid()) + "<=" +
			visit(ctx.expr()) + "; " + visit(ctx.assign().callid()) + "= " + visit(ctx.assign().callid()) +
			" + " + visit(ctx.plusminusorempty()) + ctx.DIGIT().getText() + ")\n" + visit(ctx.block());
	}
	
	@Override
	public String visitCallexpr(SPLADParser.CallexprContext ctx) {
		if (ctx.subcallexpr() != null){
			return visit(ctx.subcallexpr());
		}
		return "";
	}
	
	@Override
	public String visitType(SPLADParser.TypeContext ctx) {
		return visit(ctx.constant()) + visit(ctx.primitivetype()) + " ";
	}
	
	@Override
	public String visitConstant(SPLADParser.ConstantContext ctx) {
		if (!ctx.getText().equals("")){
			return "const ";
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitPrimitivetype(SPLADParser.PrimitivetypeContext ctx){
		switch (ctx.getText().trim()){
		case "string":
			return "String";
		case "int":
			return "int";
		case "double":
			return "double";
		case "bool":
			return "boolean";
		case "char":
			return "char";
		case "container":
			return "int";
		case "drink":
			return "double";
		default:
			return "";
		}
	}
	
	@Override
	public String visitFunction(SPLADParser.FunctionContext ctx) {
		if(ctx.id().getText().equals("setup")){
			setupbuffer.append(visit(ctx.functionmid().functionend().stmts()));
			return "";
		}
		else if (ctx.id().getText().equals("loop")){
			loopbuffer.append(visit(ctx.functionmid().functionend().stmts()));
			return "";
		}
		else {
			if (ctx.id().getText().equals("pour")){
				haspour = true;
			}
			if (ctx.id().getText().equals("RFIDFound")){
				hasrfidfound = true;
			}
			if (ctx.functionmid().type() != null){
			 return visit(ctx.functionmid().type()) + visit(ctx.id()) + visit(ctx.functionmid());
			}
			else {
				return "void " + visit(ctx.id()) + visit(ctx.functionmid());
			}
		}
	}
	
	@Override
	public String visitId(SPLADParser.IdContext ctx) {
		return ctx.getText() + " ";
	}
	
	@Override
	public String visitFunctioncall(SPLADParser.FunctioncallContext ctx) {
		return visit(ctx.id()) + "(" + visit(ctx.callexpr()) + ")";
	}
	
	@Override
	public String visitEndif(SPLADParser.EndifContext ctx) {
		if (ctx.nontermelse() != null){
			return "else\n" + visit(ctx.nontermelse());
		}
		return "";
	}
	
	@Override
	public String visitSubcallexprend(SPLADParser.SubcallexprendContext ctx) {
		if (ctx.subcallexpr() != null){
			return ", " + visit(ctx.subcallexpr());
		}
		return "";
	}
	
	@Override
	public String visitSubparamsend(SPLADParser.SubparamsendContext ctx) {
		if (ctx.subparams() != null){
			return ", " + visit(ctx.subparams());
		}
		return "";
	}
	
	@Override
	public String visitNontermif(SPLADParser.NontermifContext ctx) {
		return "if (" + visit(ctx.expr()) + ")\n" +	visit(ctx.block()) + visit(ctx.endif());
	}
	
	@Override
	public String visitAssignend(SPLADParser.AssignendContext ctx) {
		if (ctx.expr() != null) {
		return "= " + visit(ctx.expr());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitComparisonoperator(SPLADParser.ComparisonoperatorContext ctx) {
		switch (ctx.getText().trim()){
			case "=":
				return"==";
			case "<":
				return "<";
			case "<=":
				return "<=";
			case ">":
				return ">";
			case ">=":
				return ">=";
			case "!=":
				return "!=";
			default:
				return "ERROR";
		}
	}
	
	@Override
	public String visitExprend(SPLADParser.ExprendContext ctx) {
		if (ctx.exprsymbol() != null){
			return visit(ctx.exprsymbol()) + visit(ctx.expr());
		}
		return "";
	}
	
	@Override
	public String visitComp(SPLADParser.CompContext ctx) {
		return visit(ctx.addsub()) + visit(ctx.compend());
	}

	@Override
	public String visitAddsub(SPLADParser.AddsubContext ctx){
		return visit(ctx.muldiv()) + visit(ctx.addsubend());
	}
	
	
	
	@Override
	public String visitAddsubend(SPLADParser.AddsubendContext ctx){
		if (ctx.plusminus() != null){
			return visit(ctx.plusminus()) + visit(ctx.addsub());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitCases(SPLADParser.CasesContext ctx) {
		return "case " + visit(ctx.expr()) + ":\n{\n" + visit(ctx.stmts()) + "}\n" + visit(ctx.endcase());
	}
	
	@Override
	public String visitNontermelse(SPLADParser.NontermelseContext ctx) {
		if (ctx.nontermif() != null){
			return visit(ctx.nontermif());
		}
		else {
			return visit(ctx.block());
		}
	}
	
	@Override
	public String visitRoots(SPLADParser.RootsContext ctx) {
		if (ctx.root() != null){
			return visit(ctx.root()) + visit(ctx.roots());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitStmt(SPLADParser.StmtContext ctx) {
		if (ctx.assign() != null){
			return visit(ctx.assign()) + ";\n";
		}
		else if (ctx.nontermif() != null){
			return visit(ctx.nontermif());
		}
		else if (ctx.nontermwhile() != null){
			return visit(ctx.nontermwhile());
		}
		else if (ctx.from() != null){
			return visit(ctx.from());
		}
		else if (ctx.dcl() != null){
			return visit(ctx.dcl()) + ";\n";
		}
		else if (ctx.functioncall() != null){
			return visit(ctx.functioncall()) + ";\n";
		}
		else if (ctx.nontermswitch() != null){
			return visit(ctx.nontermswitch());
		}
		else {
			return "ERROR";
		}
	}
	
	@Override
	public String visitSubcallexpr(SPLADParser.SubcallexprContext ctx) {
		return visit(ctx.expr()) + visit(ctx.subcallexprend());
	}
	
	@Override
	public String visitParams(SPLADParser.ParamsContext ctx) {
		if (ctx.subparams() != null){
			return visit(ctx.subparams());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitFactor(SPLADParser.FactorContext ctx) {
		if(ctx.cast() != null){
			return visit(ctx.cast());
		}
		else if(ctx.string() != null){
			return visit(ctx.string());
		}
		else if(ctx.functioncall() != null){
			return visit(ctx.functioncall());
		}
		else if(ctx.numeric() != null){
			return visit(ctx.numeric());
		}
		else if(ctx.callid() != null){
			return visit(ctx.callid());
		}
		else {
			String[] splitarray = ctx.getText().trim().split(" ");
			if (splitarray[0].equals("LOW")){
				return "LOW";
			}
			else if (splitarray[0].equals("HIGH")){
				return "HIGH";
			}
			else if (splitarray[0].toCharArray()[0] == 'A'){
				return "A" + ctx.DIGIT().getText();
			}
			else if (splitarray[0].equals("true")){
				return "true";
			}
			else if (splitarray[0].equals("false")){
				return "false";
			}
			else if (splitarray[0].equals("INPUT")){
				return "INPUT";
			}
			else if (splitarray[0].equals("OUTPUT")){
				return "OUTPUT";
			}
			else if (splitarray[0].toCharArray()[0] == '!'){
				return "!(" + visit(ctx.expr()) + ")";
			}
			else if (splitarray[0].toCharArray()[0] == '('){
				return "(" + visit(ctx.expr()) + ")";
			}
			else {
				return "";
			}
			
		}
	}
	
	@Override
	public String visitFunctionmid(SPLADParser.FunctionmidContext ctx) {
		if (ctx.type() != null){
			return visit(ctx.functionend()) + visit(ctx.expr()) + ";\n}";
		}
		else {
			return visit(ctx.functionend()) + ";\n}\n";
		}
	}
	
	@Override
	public String visitDcl(SPLADParser.DclContext ctx) {
		if(ctx.type().primitivetype().getText().trim().equals("container") && ctx.assign().assignend().expr() != null){
			setupbuffer.append("pinMode(" + visit(ctx.assign().assignend().expr()) + ", OUTPUT);\n");
			Container tempcont = new Container();
			tempcont.containername = visit(ctx.assign().callid().id());
			tempcont.pinid = visit(ctx.assign().assignend().expr());
			ListOfContainers.add(tempcont);
		}
		
		return visit(ctx.type()) + visit(ctx.assign());
	}
	
	@Override
	public String visitArrayidend(SPLADParser.ArrayidendContext ctx) {
		if(ctx.arraycall() != null){
			return visit(ctx.arraycall()) + "[" + visit(ctx.expr()) + "] ";
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitArraycall(SPLADParser.ArraycallContext ctx){
		if (ctx.expr() != null){
			return "[" + visit(ctx.expr()) + "] " + visit(ctx.arraycall());
		}
		else if (ctx.arraycall() != null){
			return "[ ]" + visit(ctx.arraycall());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitSubparams(SPLADParser.SubparamsContext ctx) {
		if (ctx.type().primitivetype().getText().trim().equals("drink")){
			return visit(ctx.type()) + visit(ctx.callid()) + "[ ][2] " + visit(ctx.subparamsend());
		}
		else {
			return visit(ctx.type()) + visit(ctx.callid()) + visit(ctx.subparamsend());
		}
	}
	
	@Override
	public String visitPlusminus(SPLADParser.PlusminusContext ctx) {
		if (ctx.getText().equals("+")){
			return "+";
		}
		else if (ctx.getText().equals("-")){
			return "-";
		}
		else {
			return "ERROR";
		}
	}
	
	@Override
	public String visitCast(SPLADParser.CastContext ctx) {
		return "(" + visit(ctx.type()) + ") (" + visit(ctx.expr()) + ") ";
	}
	
	@Override
	public String visitEndcase(SPLADParser.EndcaseContext ctx) {
		if (ctx.cases() != null){
			return visit(ctx.cases());
		}
		else if (ctx.breakend() != null){
			return "break;\n" + visit(ctx.breakend());
		}
		else {
			return "default:\n" + visit(ctx.stmts()) + "break;\n";
		}
	}
	
	@Override
	public String visitTerm(SPLADParser.TermContext ctx) {
		return visit(ctx.comp()) + visit(ctx.termend());
	}
	
	@Override
	public String visitCallid(SPLADParser.CallidContext ctx) {
		return visit(ctx.id()) + visit(ctx.arrayidend());
	}
	
	@Override
	public String visitProgram(SPLADParser.ProgramContext ctx) {
		setupbuffer.append(PrintContentofFile("C:\\test\\setup.txt"));
		loopbuffer.append(PrintContentofFile("C:\\test\\loop.txt"));
		StringBuffer ContentBuffer = new StringBuffer();
		StringBuffer ContentString = new StringBuffer();
		ContentString.append(PrintContentofFile("C:\\test\\header.txt"));
		ContentString.append(visit(ctx.roots()));
		
		ContentBuffer.append("String ContainersnameSW407F13[" + ListOfContainers.size() + "];\n");
		ContentBuffer.append("int ContainerspinSW407F13[" + ListOfContainers.size() + "];\n");
		
		for (int i = 0; i < ListOfContainers.size(); i++){
			setupfirstbuffer.append("ContainersnameSW407F13[" + i + "] = \"" + ListOfContainers.get(i).containername + "\";\n");
			setupfirstbuffer.append("ContainerspinSW407F13[" + i + "] = " + ListOfContainers.get(i).pinid + ";\n");
		}
		ContentBuffer.append(ContentString);
		ContentBuffer.append("void setup() {\n" + setupfirstbuffer.toString() + "\n" +
				setupbuffer.toString() + "return;\n}\n" + "void loop() {\n" + loopbuffer.toString() + "return;\n}\n");
		if (haspour == false){
			System.out.println("Your program does not contain a pour function, please see to, it is made.");
		}
		if (hasrfidfound == false){
			System.out.println("Your program does not contain a RFIDFound function, please see to, it is made.");
		}
		return ContentBuffer.toString();
	}
	
	@Override
	public String visitString(SPLADParser.StringContext ctx) {
		return ctx.getText();
	}
	
	@Override
	public String visitFunctionend(SPLADParser.FunctionendContext ctx) {
		return "(" + visit(ctx.params()) + ")\n{\n" + visit(ctx.stmts()) + "return ";
	}
	
	@Override
	public String visitBreakend(SPLADParser.BreakendContext ctx) {
		if (ctx.cases() != null){
			return visit(ctx.cases());
		}
		else if (ctx.stmts() != null){
			return "default:\n" + visit(ctx.stmts()) + "break;\n";
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitCompend(SPLADParser.CompendContext ctx) {
		if(ctx.comparisonoperator() != null){
			return visit(ctx.comparisonoperator()) + visit(ctx.comp());
		}
		else {
			return "";
		}
	}
	
	private String PrintContentofFile(String path){
		BufferedReader in;
		StringBuffer result = new StringBuffer();
		try{
			in = new BufferedReader(new FileReader(path));
			while (in.ready()){
				String fileintxt = in.readLine();
				result.append(fileintxt+"\n");
			}
			in.close();
			return result.toString();
		}
		catch (IOException IOerror){
			return null;
		}
	}

	@Override
	public String visitTimesdivide(SPLADParser.TimesdivideContext ctx) {
		if (ctx.getText().equals("*")){
			return "*";
		}
		else if(ctx.getText().equals("/")) {
			return "/";
		}
		else{
			return "ERROR";
		}
	}

	@Override
	public String visitMuldivend(SPLADParser.MuldivendContext ctx) {
		if (ctx.timesdivide() != null){
			return visit(ctx.timesdivide()) + visit(ctx.muldiv());
		}
		else {
			return "";
		}
	}

	@Override
	public String visitPlusminusorempty(SPLADParser.PlusminusoremptyContext ctx) {
		if(ctx.plusminus() != null){
			return visit(ctx.plusminus());
		}
		else {
			return "";
		}
	}

	@Override
	public String visitBlock(SPLADParser.BlockContext ctx) {
		return "{" + visit(ctx.stmts()) + "}";
	}

	@Override
	public String visitMuldiv(SPLADParser.MuldivContext ctx) {
		return visit(ctx.factor()) + visit(ctx.muldivend());
	}

	@Override
	public String visitDrinkdcl(SPLADParser.DrinkdclContext ctx) {
		drinkHolder = new Drinks(visit(ctx.id(0)));
		StringBuffer tempreturnstring = new StringBuffer();
		if (ctx.drinkstmts() != null){
			visit(ctx.drinkstmts());
		}
		else {
			int k = 0;
			while(!ListOfDrinks.get(k).drinkid.equals(visit(ctx.id(1)))){
				k++;
			}
			
			for(Iterator<Ingredients> j = ListOfDrinks.get(k).ListOfIngredient.iterator(); j.hasNext();){
				Ingredients tempingredient = new Ingredients();
				Ingredients tempnextingredient = j.next();
				tempingredient.Ingredientid = tempnextingredient.Ingredientid;
				tempingredient.IngredientAmount = tempnextingredient.IngredientAmount;
				drinkHolder.ListOfIngredient.add(tempingredient);
			}
			visit(ctx.changedrinkstmts());
		}
		tempreturnstring.append("double " + drinkHolder.drinkid + "[" + (drinkHolder.getIngredientcount()+1) + "][2];\n");
		setupfirstbuffer.append(drinkHolder.drinkid + "[" + 0 + "][0] =" + drinkHolder.getIngredientcount() + ";\n" + drinkHolder.drinkid + "[" + 0 + "][1] =" + drinkHolder.getIngredientcount() + ";\n");
		for (int i = 0; i < drinkHolder.getIngredientcount(); i++){
			int counter = 0;
			while (!ListOfContainers.get(counter).containername.equals(drinkHolder.ListOfIngredient.get(i).Ingredientid)){
				counter++;
			}
			int place = i + 1;
			setupfirstbuffer.append(drinkHolder.drinkid + "[" + place + "][0] =" + counter + ";\n" + drinkHolder.drinkid + "[" + place + "][1] =" + drinkHolder.ListOfIngredient.get(i).IngredientAmount + ";\n");
		}
		ListOfDrinks.add(drinkHolder);
		return tempreturnstring.toString();
	}

	@Override
	public String visitDrinkstmtsend(SPLADParser.DrinkstmtsendContext ctx) {
		if (ctx.drinkstmts() != null){
			return ";" + visit(ctx.drinkstmts());
		}
		else {
			return ";";
		}
	}

	@Override
	public String visitDrinkstmt(SPLADParser.DrinkstmtContext ctx) {
		Ingredients temp = new Ingredients();
		temp.Ingredientid = visit(ctx.id());
		temp.IngredientAmount = Double.parseDouble(visit(ctx.numeric()));
		drinkHolder.ListOfIngredient.add(temp);
		return "";
	}

	@Override
	public String visitDrinkstmts(SPLADParser.DrinkstmtsContext ctx) {
		return visit(ctx.drinkstmt()) + visit(ctx.drinkstmtsend());
	}
	
	@Override
	public String visitChangedrinkstmts(SPLADParser.ChangedrinkstmtsContext ctx) {
		return visit(ctx.changedrinkstmt()) + visit(ctx.changedrinkstmtsend());
	}

	@Override
	public String visitChangedrinkstmtsend(SPLADParser.ChangedrinkstmtsendContext ctx) {
		if (ctx.changedrinkstmts() != null){
			return ";" + visit(ctx.changedrinkstmts());
		}
		else {
			return ";";
		}
	}

	@Override
	public String visitChangedrinkstmt(SPLADParser.ChangedrinkstmtContext ctx) {
		if (ctx.drinkstmt() != null){
			return visit(ctx.drinkstmt());
		}
		else {
			for(int i = 0; i < drinkHolder.getIngredientcount(); i++){
				if(drinkHolder.ListOfIngredient.get(i).Ingredientid.equals(visit(ctx.id()))){
					drinkHolder.ListOfIngredient.remove(i);
					i--;
				}
			}
			return "";
		}
	}
	
}
