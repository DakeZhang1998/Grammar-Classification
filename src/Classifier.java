import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

public class Classifier {
    public static void main(String[] args) {
        String beginnningSymbol;
        String grammarStr;                    // Record the name of the grammar.  e.g. G[N]
        String classificationResult = "";
        Boolean nullExisted = false;                  // Record whether there is e(ε).
        ArrayList<String> productions = new ArrayList<String>();
        ArrayList<String> leftProductions = new ArrayList<String>();
        ArrayList<String> rightProductions = new ArrayList<String>();
        ArrayList<String> rightProductionsSliced = new ArrayList<String>();
        ArrayList<String> terminalSymbols = new ArrayList<String>();
        ArrayList<String> nonterminalSymbols0 = new ArrayList<String>();
        ArrayList<String> nonterminalSymbols = new ArrayList<String>();

        // Get the input.
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入文法：");
        grammarStr = sc.nextLine();
        System.out.println("请输入非终结符集合Vn（大写字母），用英文逗号和空格分割：");
        String nonterminalStr = sc.nextLine();
        System.out.println("一次输入产生式规则，例如：N::=ND|D，分割方式同上：");
        String productionsStr = sc.nextLine();

        // Process the data.
        beginnningSymbol = grammarStr.substring(grammarStr.indexOf('[') + 1, grammarStr.indexOf(']'));

        if (nonterminalStr.indexOf(",") > -1) {
            String[] nonterminalTemp = nonterminalStr.split(", ");
            for (String temp: nonterminalTemp)
                nonterminalSymbols.add(temp);
        }
        else
            nonterminalSymbols.add(nonterminalStr);

        String[] productionsTemp = new String[1];
        if (productionsStr.indexOf(",") > -1)
            productionsTemp = productionsStr.split(", ");
        else
            productionsTemp[0] = productionsStr;

        for (String temp: productionsTemp) {
            productions.add(temp);
            leftProductions.add(temp.substring(0, temp.indexOf("::=")));
            rightProductions.add(temp.substring(temp.indexOf("::=") + 3));
        }

        // Get the terminal symbols and check the validity.
        if (nonterminalSymbols.indexOf(beginnningSymbol) == -1) {
            System.out.println("错误：非终结符遗漏文法的开始符号，请检查错误！");
            return;
        }
        for (String leftItem: leftProductions) {
            char[] temp = leftItem.toCharArray();
            for(int i=0; i < temp.length; i++){
                if (temp[i] >= 'A' & temp[i] <= 'Z' & nonterminalSymbols.indexOf(String.valueOf(temp[i])) > -1)
                    if (nonterminalSymbols.indexOf(String.valueOf(temp[i])) > -1)
                        continue;
                    else
                        nonterminalSymbols.add(String.valueOf(temp[i]));
                else if (temp[i] == 'e')
                    System.out.println("错误：产生式左部不可以存在空字符串！");
                else if (terminalSymbols.indexOf(String.valueOf(temp[i])) > -1)
                    continue;
                else
                    terminalSymbols.add(String.valueOf(temp[i]));
            }
        }
        boolean isTrue = false;
        for (String str: leftProductions) {
            String[] temp = str.split("|");
            for (int i = 0; i < temp.length; i++) {
                if (nonterminalSymbols.indexOf(String.valueOf(temp[i])) > -1)
                    isTrue = true;
            }
        }
        if (!isTrue) {
            System.out.println("错误：产生式左部缺少非终结符，请重新检查！");
            return;
        }

        for (String rightItem: rightProductions) {
            char[] temp = rightItem.toCharArray();
            for(int i=0; i < temp.length; i++){
                if (temp[i] >= 'A' & temp[i] <= 'Z' & nonterminalSymbols.indexOf(String.valueOf(temp[i])) > -1)
                    if (nonterminalSymbols.indexOf(String.valueOf(temp[i])) > -1)
                        continue;
                    else
                        nonterminalSymbols.add(String.valueOf(temp[i]));
                else if (temp[i] == 'e')
                    nullExisted = true;
                else if (temp[i] == '|')
                    continue;
                else if (terminalSymbols.indexOf(String.valueOf(temp[i])) > -1)
                    continue;
                else
                    terminalSymbols.add(String.valueOf(temp[i]));
            }
        }

//        for (String str: nonterminalSymbols) {
//            if (nonterminalSymbols0.indexOf(str) == -1) {
//                System.out.println("错误：非终结符集合Vn或产生式规则输入错误，请检查是否遗漏！");
//                return;
//            }
//        }
//        for (String str: nonterminalSymbols0) {
//            if (nonterminalSymbols.indexOf(str) == -1) {
//                System.out.println("错误：非终结符集合Vn或产生式规则输入错误，请检查是否遗漏！");
//                return;
//            }
//        }

        // Classification phrase.
        boolean flag23 = true;
        for (int i = 0; i < leftProductions.size(); i++) {
            if (!Recognition23(leftProductions.get(i), rightProductions.get(i), nonterminalSymbols))
                flag23 = false;
        }
        if (flag23) {
            for (int i = 0; i < leftProductions.size(); i++) {
                if (rightProductions.get(i).indexOf("|") > -1) {
                    // If there are more than one right parts.
                    String[] tempRight = rightProductions.get(i).split("\\|");
                    for (String str: tempRight) {
                        if (Recognition3(str, terminalSymbols, nonterminalSymbols)
                                && !classificationResult.equals("Chomsky 2 型文法（即上下文无关文法）")
                                && !classificationResult.equals("扩充的 Chomsky 2 型文法")
                                && !classificationResult.equals("Chomsky 3 型文法（即正规文法，左线型文法）"))
                            classificationResult = "Chomsky 3 型文法（即正规文法）";
                        else if (nullExisted)
                            classificationResult = "扩充的 Chomsky 2 型文法";
                        else
                            classificationResult = "Chomsky 2 型文法（即上下文无关文法）";
                    }
                }
                else {
                    if (Recognition3(rightProductions.get(i), terminalSymbols, nonterminalSymbols)
                            && !classificationResult.equals("Chomsky 2 型文法（即上下文无关文法）")
                            && !classificationResult.equals("扩充的 Chomsky 2 型文法"))
                        classificationResult = "Chomsky 3 型文法（即正规文法）";
                    else if (nullExisted)
                        classificationResult = "扩充的 Chomsky 2 型文法";
                    else
                        classificationResult = "Chomsky 2 型文法（即上下文无关文法）";
                }
            }
            if (classificationResult.equals("Chomsky 3 型文法（即正规文法）") && nullExisted)
                classificationResult = "扩充的 Chomsky 3 型文法";
        }
        else {
            for (int i = 0; i < leftProductions.size(); i++) {
                if (rightProductions.get(i).indexOf("|") > -1) {
                    // If there are more than one right parts.
                    String[] tempRight = rightProductions.get(i).split(", ");
                    for (String str: tempRight) {
                        if (Recognition1(leftProductions.get(i), str)
                                && !classificationResult.equals("Chomsky 0 型文法"))
                            if (nullExisted)
                                classificationResult = "Chomsky 0 型文法";
                            else
                                classificationResult = "Chomsky 1 型文法（即上下文有关文法）";
                        else
                            classificationResult = "Chomsky 0 型文法";
                    }
                }
                else {
                    if (Recognition1(leftProductions.get(i), rightProductions.get(i))
                            && !classificationResult.equals("Chomsky 0 型文法"))
                        if (nullExisted)
                            classificationResult = "Chomsky 0 型文法";
                        else
                            classificationResult = "Chomsky 1 型文法（即上下文有关文法）";
                    else
                        classificationResult = "Chomsky 0 型文法";
                }
            }
        }

        // Generate the result.
        String nonterminalResult = "";
        String terminalResult = "";
        for (String str: nonterminalSymbols) {
            nonterminalResult += "," + str;
        }
        if (nonterminalResult.length() != 0)
            nonterminalResult = nonterminalResult.substring(1);
        for (String str: terminalSymbols) {
            terminalResult += "," + str;
        }

        if (terminalResult == null) {
            terminalResult = "";
        }
        if (nonterminalResult == null) {
            terminalResult = "";
        }
        terminalResult = terminalResult.substring(1);
        System.out.println();
        System.out.println("========================== Result ==========================");
        System.out.println("文法 " + grammarStr + " = " + "({" + nonterminalResult + "}, {" + terminalResult + "}, P, " + beginnningSymbol + "}");
        System.out.println("P:");
        for (String str: productions) {
            System.out.println("   "+str);
        }
        System.out.println("该文法是 " + classificationResult + "。");
    }


    public static boolean Recognition23(String left, String right, ArrayList<String> nonterminal) {
        if (nonterminal.indexOf(left) > -1)
            return true;
        else
            return false;
    }

    public static boolean Recognition3(String right, ArrayList<String> terminal, ArrayList<String> nonterminal) {
        char[] temp = right.toCharArray();
        // If it is started by a nonterminal symbol.
        boolean flag = false;   // Whether the nonterminal symbol has occurred.
        if (nonterminal.indexOf(String.valueOf(temp[0])) > -1)
            return false;
        for (int i = 0; i < temp.length; i++) {
            if (nonterminal.indexOf(String.valueOf(temp[i])) == -1 && flag)
                return false;
            else if (nonterminal.indexOf(String.valueOf(temp[i])) > -1 && !flag)
                flag = true;
            else if (nonterminal.indexOf(String.valueOf(temp[i])) > -1 && flag)
                return false;
        }
        return true;
    }

    public static boolean Recognition1(String left, String right) {
        if (left.length() <= right.length())
            return true;
        else
            return false;
    }
}