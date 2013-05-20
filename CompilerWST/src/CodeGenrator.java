import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;


//CodeGenerator, which extends the Base Visitor generated by ANTLR
public class CodeGenrator extends AbstractParseTreeVisitor<String> implements SPLADVisitor<String>{
	//Lists for scopechecking
	List<List<String>> Scopecontrol = new ArrayList<List<String>>();
	List<String> CodeGeneratorErrors = new ArrayList<String>();
	List<String> listOfErrors = new ArrayList<String>();
	int count = 0 ;
	
	//Stringbuffer for the content of the function setup
	private StringBuffer setupbuffer = new StringBuffer();
	
	//Stringbuffer for the start of the content of the function setup
	private StringBuffer setupfirstbuffer = new StringBuffer();
	
	//Stringbuffer for the content of the function loop
	private StringBuffer loopbuffer = new StringBuffer();
	
	//A list of the containers declared in the program code
	private List<Container> ListOfContainers = new ArrayList<Container>();
	
	//A list of the drinks declared in the program code
	private List<Drinks> ListOfDrinks = new ArrayList<Drinks>();
	
	//The holder for the object of the class drink, which is beeing added.
	private Drinks drinkHolder;
	
	//Bools for verifying that the user has implemented the pour and RFIDFound functions.
	private boolean haspour = false;
	private boolean hasrfidfound = false;
	
	//From here, the visitors according the BNF for SPLAD will generate code using the parsetree.
	
	@Override
	public String visitAssign(SPLADParser.AssignContext ctx) {
		return visit(ctx.callid()) + visit(ctx.assignend());
	}
	
	@Override
	public String visitTermsymbol(SPLADParser.TermsymbolContext ctx){
		//Convert the "AND" from the SPLAD syntax to "&&" which is used on Arduino.
		if (ctx.getText().trim().equals("AND")){
			return "&&";
		}
		else {
			CodeGeneratorErrors.add("Syntax error");
			return "";
		}
	}
	
	@Override
	public String visitExprsymbol(SPLADParser.ExprsymbolContext ctx){
		//Convers the "OR" from the SPLAD syntax to "||" which is used on Arduino.
		if (ctx.getText().trim().equals("OR")){
			return "||";
		}
		else {
			CodeGeneratorErrors.add("Syntax error");
			return "";
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
			return visit(ctx.function()) + "\n";
		}
		else if (ctx.assign() != null)
		{
			return visit(ctx.assign()) + ";\n";
		}
		else if (ctx.drinkdcl() != null){
			return visit(ctx.drinkdcl()) + "\n";
		}
		else{
			CodeGeneratorErrors.add("Syntax error");
			return "";
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
		//Converting the "From"-statement in SPLAD to the for statement in Arduino's wiring.
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
		if(ctx.specialtype() != null)
		{
			return visit(ctx.specialtype());
		}else
		{
			return visit(ctx.constant()) + visit(ctx.primitivetype()) + " ";
		}
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
		default:
			return "";
		}
	}
	
	@Override
	public String visitFunction(SPLADParser.FunctionContext ctx) {
		//Add new scope to the list of scopes
		Scopecontrol.add(new ArrayList<String>());
		
		//If the function is the setupfunction, add the statements to the setupbuffer. The content will be added in the end.
		if(ctx.id().getText().equals("setup")){
			setupbuffer.append(visit(ctx.functionmid().functionend().stmts()));
			Scopecontrol.remove(Scopecontrol.size()-1);
			return "";
		}//If the function is the loopfunction, it will be added to the loopbuffer
		else if (ctx.id().getText().equals("loop")){
			loopbuffer.append(visit(ctx.functionmid().functionend().stmts()));
			Scopecontrol.remove(Scopecontrol.size()-1);
			return "";
		}
		else {
			//Check if the function is the required pour og RFIDFound functions
			if (ctx.id().getText().equals("pour")){
				haspour = true;
			}
			if (ctx.id().getText().equals("RFIDFound")){
				hasrfidfound = true;
			}
			
			String tempstring = "";
			
			if (ctx.functionmid().type() != null){
				tempstring = visit(ctx.functionmid().type()) + visit(ctx.id()) + visit(ctx.functionmid());
			}
			else {
				tempstring =  "void " + visit(ctx.id()) + visit(ctx.functionmid());
			}
			Scopecontrol.remove(Scopecontrol.size()-1);
			return tempstring;
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
				CodeGeneratorErrors.add("Syntax error: Not valid comparison operator");
				return "";
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
		//Add a scope to the list of scopes
		List<String> Templist = new ArrayList<String>();
		Scopecontrol.add(Templist);
		String Temp = "case " + visit(ctx.expr()) + ":\n" + visit(ctx.stmts()) + "\n break;\n" + visit(ctx.endcase());
		Scopecontrol.remove(Scopecontrol.size()-1);
		return Temp;
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
	//latex start nodexp
	public String visitRoots(SPLADParser.RootsContext ctx) {
		if (ctx.root() != null){
			return visit(ctx.root()) + visit(ctx.roots());
		}
		else {
			return "";
		}
	}
	//latex end
	
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
			CodeGeneratorErrors.add("Syntax error");
			return "";
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
				CodeGeneratorErrors.add("Syntax error");
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
		//If the type of the variable declared is container, do the following:
		if(ctx.type().specialtype() != null){
			if(ctx.type().specialtype().getText().trim().equals("container") && ctx.assign().assignend().expr() != null){
				//In the setupfunction, set the pin of the container to "OUTPUT"
				setupbuffer.append("pinMode(" + visit(ctx.assign().assignend().expr()) + ", OUTPUT);\n");
				
				//Add the container to the list of containers
				Container tempcont = new Container();
				tempcont.containername = visit(ctx.assign().callid().id());
				tempcont.pinid = visit(ctx.assign().assignend().expr());
				ListOfContainers.add(tempcont);
			}
		}
		
		//Add the name of the declarated variable to the present scope
		//latex start visitDCL
		String Temp = ctx.assign().callid().id().getText();
		Temp = Temp.replaceAll("\\s","");
		int ScopeRange = Scopecontrol.size()-1;
		Scopecontrol.get(ScopeRange).add(Temp);
		//latex end
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
		//Add the name of the param to the present scope
		String Temp = ctx.callid().id().getText();
		Temp = Temp.replaceAll("\\s","");
		Scopecontrol.get(Scopecontrol.size()-1).add(Temp);
		
		if (ctx.type().specialtype() != null && ctx.type().specialtype().getText().trim().equals("drink")){
			//If the type is a drink, add the arrays for the twodimensional array.
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
			CodeGeneratorErrors.add("Syntax error: This is not plus or minus");
			return "";
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
		else {
			//Add a scope to the list of scopes
			List<String> Templist = new ArrayList<String>();
			Scopecontrol.add(Templist);
			
			String Temp = "default:\n" + visit(ctx.stmts()) + "break;\n";
			
			Scopecontrol.remove(Scopecontrol.size()-1);
			return Temp;
		}
	}
	
	@Override
	public String visitTerm(SPLADParser.TermContext ctx) {
		return visit(ctx.comp()) + visit(ctx.termend());
	}
	
	@Override
	public String visitCallid(SPLADParser.CallidContext ctx) {
		//Check if the wanted callid is visible in the present scope.
		String Temp = visit(ctx.id());
		String Temp2 = "";
		Temp = Temp.replaceAll("\\s","");
		//latex start scopecallid
		for(int i = Scopecontrol.size()-1; i >= 0; i--)
		{
			for(int j = Scopecontrol.get(i).size()-1; j >= 0; j--)
			{
				Temp2 = Scopecontrol.get(i).get(j).toString();
				if(Temp.equals(Temp2))
				{
					return visit(ctx.id()) + visit(ctx.arrayidend());
					//latex end
				}
			}
		}
		listOfErrors.add("error: variable " + Temp + " can not be accessed ");
		
		return visit(ctx.id()) + visit(ctx.arrayidend());
	}
	
	@Override
	public String visitProgram(SPLADParser.ProgramContext ctx) {
		//Add the global scope to the scopecontrole
		List<String> GlobalScope = new ArrayList<String>();
		Scopecontrol.add(GlobalScope);
		
		//Add the predefined functions to the string and loopbuffer
		setupbuffer.append(PrintContentofFile("resources/setup.txt"));
		loopbuffer.append(PrintContentofFile("resources/loop.txt"));
		
		//String buffer for the whole content to return.
		StringBuffer ContentBuffer = new StringBuffer();
		StringBuffer HeaderBuffer = new StringBuffer();
		
		//Add the predefined content and visit roots.
		HeaderBuffer.append(PrintContentofFile("resources/header.txt"));
		HeaderBuffer.append(visit(ctx.roots()));
		
		//Add the arrays containing the names and pins of the containers.
		
		//latex start container
		ContentBuffer.append("String ContainersnameSW407F13[" + ListOfContainers.size() + "];\n");
		ContentBuffer.append("int ContainerspinSW407F13[" + ListOfContainers.size() + "];\n");
		
		
		//Add content to the arrays in the setupfirstbuffer.
		for (int i = 0; i < ListOfContainers.size(); i++){
			setupfirstbuffer.append("ContainersnameSW407F13[" + i + "] = \"" + ListOfContainers.get(i).containername + "\";\n");
			setupfirstbuffer.append("ContainerspinSW407F13[" + i + "] = " + ListOfContainers.get(i).pinid + ";\n");
		}
		
		//Add the content of the program to the ContentBuffer
		ContentBuffer.append(HeaderBuffer);
		//latex end
		
		//Add the setup and loop function
		ContentBuffer.append("void setup() {\n" + setupfirstbuffer.toString() + "\n" +
				setupbuffer.toString() + "return;\n}\n" + "void loop() {\n" + loopbuffer.toString() + "return;\n}\n");
		
		//Check if the required functions is implemented
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
	public String visitCompend(SPLADParser.CompendContext ctx) {
		if(ctx.comparisonoperator() != null){
			return visit(ctx.comparisonoperator()) + visit(ctx.comp());
		}
		else {
			return "";
		}
	}
	
	//Reed contendt of file, and return it.
	//latex start pcontent
	private String PrintContentofFile(String path){
		InputStream in;
		StringBuffer fileintxt = new StringBuffer();
		try{
			ClassLoader CLoader = this.getClass().getClassLoader();
			in = CLoader.getResourceAsStream(path);
			Scanner test = new Scanner(in,"UTF-8");
			while(test.hasNext()){
				fileintxt.append(test.useDelimiter("\\A").next());
			}
			in.close();
			test.close();
			return fileintxt.toString();
		}
		catch (IOException IOerror){
			System.out.println("Could not read the file");
			CodeGeneratorErrors.add("Syntax error");
			return "";
		}
	}
	//latex end
	
	@Override
	public String visitTimesdivide(SPLADParser.TimesdivideContext ctx) {
		if (ctx.getText().equals("*")){
			return "*";
		}
		else if(ctx.getText().equals("/")) {
			return "/";
		}
		else{
			CodeGeneratorErrors.add("Syntax error: This is not divide or times");
			return "";
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
		//Add a scope to the list of scopes
		//latex start visitBlock
		List<String> Templist = new ArrayList<String>();
		Scopecontrol.add(Templist);
		
		String Temp = "{" + visit(ctx.stmts()) + "}";
		
		Scopecontrol.remove(Scopecontrol.size()-1);
		return Temp;
		//latex end
	}

	@Override
	public String visitMuldiv(SPLADParser.MuldivContext ctx) {
		return visit(ctx.factor()) + visit(ctx.muldivend());
	}

	@Override
	public String visitDrinkdcl(SPLADParser.DrinkdclContext ctx) {
		//Add the name of the declarated variable to the present scope
		String Temp = visit(ctx.id(0));
		Temp = Temp.replaceAll("\\s","");
		int ScopeRange = Scopecontrol.size()-1;
		Scopecontrol.get(ScopeRange).add(Temp);
		
		//Add new empty drink to the drinkHolder.
		drinkHolder = new Drinks(visit(ctx.id(0)));
		
		StringBuffer tempreturnstring = new StringBuffer();
		
		//If a new empty drink is declared, visit the drinkstatements
		//latex start drinkdclup
		if (ctx.drinkstmts() != null){
			visit(ctx.drinkstmts());
		}
		//Else it must be a drink which inherits from an other drink
		else {
			//Find the drink to inherit from
			int k = 0;
			while(!ListOfDrinks.get(k).drinkid.equals(visit(ctx.id(1)))){
				k++;
			}
			
			//Add every ingredient from the inherited drink to the new empty drink in the drinkHolder.
			for(Iterator<Ingredients> j = ListOfDrinks.get(k).ListOfIngredient.iterator(); j.hasNext();){
				Ingredients tempingredient = new Ingredients();
				Ingredients tempnextingredient = j.next();
				tempingredient.Ingredientid = tempnextingredient.Ingredientid;
				tempingredient.IngredientAmount = tempnextingredient.IngredientAmount;
				drinkHolder.ListOfIngredient.add(tempingredient);
			}
			
			//Visit the statements
			visit(ctx.changedrinkstmts());
			//latex end
		}
		
		//Add the decleration of the drink as an twodimensional double array;
		//latex start drinkdcldown
		tempreturnstring.append("double " + drinkHolder.drinkid + "[" + (drinkHolder.getIngredientcount()+1) + "][2];\n");
		//The first element in the array will hold the size of the array.
		setupfirstbuffer.append(drinkHolder.drinkid + "[" + 0 + "][0] =" + drinkHolder.getIngredientcount() + ";\n" + drinkHolder.drinkid + "[" + 0 + "][1] =" + drinkHolder.getIngredientcount() + ";\n");
		
		//Add the ingrediens of the drink to the array.
		for (int i = 0; i < drinkHolder.getIngredientcount(); i++){
			int counter = 0;
			while (ListOfContainers.size() > counter && !ListOfContainers.get(counter).containername.equals(drinkHolder.ListOfIngredient.get(i).Ingredientid)){
				counter++;
			}
			int place = i + 1;
			setupfirstbuffer.append(drinkHolder.drinkid + "[" + place + "][0] =" + counter + ";\n" + drinkHolder.drinkid + "[" + place + "][1] =" + drinkHolder.ListOfIngredient.get(i).IngredientAmount + ";\n");
		}
		
		//add the drink to the list of drinks
		ListOfDrinks.add(drinkHolder);
		//latex end
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
		//Add the ingredient to the drinkHolder.
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
		//If it is an add, visit drink statement
		if (ctx.drinkstmt() != null){
			return visit(ctx.drinkstmt());
		}
		//else it must be a remove of an ingredient.
		else {
			//Find the ingredient to remove, and remove it.
			for(int i = 0; i < drinkHolder.getIngredientcount(); i++){
				if(drinkHolder.ListOfIngredient.get(i).Ingredientid.equals(visit(ctx.id()))){
					drinkHolder.ListOfIngredient.remove(i);
					i--;
				}
			}
			return "";
		}
	}

	@Override
	public String visitSpecialtype(SPLADParser.SpecialtypeContext ctx) {
		//The structure of the implementation of the type Drink is a two dimensional double array. The array implementation will be added in an other visitor.	
		if(ctx.getText().trim().equals("drink")){
			return "double ";
		}
		//In arduino, an output pin, which the value of the class Container specifies, is handled as an int
		else if(ctx.getText().trim().equals("container")){
			return "const int ";
		}else
		{
			CodeGeneratorErrors.add("Syntax error: Unknown special type");
			return "";
		}
	}
}
