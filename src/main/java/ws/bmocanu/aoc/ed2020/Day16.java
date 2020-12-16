package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.SBinder;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day16 extends SolutionBase {

    public static class Rule {
        String fieldName;
        int int1Min;
        int int1Max;
        int int2Min;
        int int2Max;
    }

    public static class Ticket {
        List<Integer> fields;
        int[] weight;
        List<Rule> posRules = new ArrayList<>();

        public Ticket(List<Integer> fields, int[] weight, List<Rule> ruleList) {
            this.fields = fields;
            this.weight = weight;
            this.posRules.addAll(ruleList);
        }
    }

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day16"));
        List<Rule> ruleList = new ArrayList<>();

        int index = 0;
        String line = stringLines.get(index);

        SBinder binder = new SBinder("([a-z\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)",
                "fieldName", "int1Min", "int1Max", "int2Min", "int2Max");
        while (!line.isEmpty()) {
            ruleList.add(binder.bind(line, Rule.class));
            index++;
            line = stringLines.get(index);
        }

        index += 2;
        List<Integer> ticketNumbers = Utils.csvIntListToIntList(stringLines.get(index));
        List<Ticket> validTickets = new ArrayList<>();

        index += 3;
        List<Integer> otherTicketFields;
        int sum = 0;
        while (index < stringLines.size()) {
            line = stringLines.get(index);
            otherTicketFields = Utils.csvIntListToIntList(line);
            boolean validField;
            boolean allValidFields = true;
            int[] weight = new int[ruleList.size()];
            for (int otherTicketField : otherTicketFields) {
                validField = false;
                for (int ruleIndex = 0; ruleIndex < ruleList.size(); ruleIndex++) {
                    Rule rule = ruleList.get(ruleIndex);
                    if ((otherTicketField >= rule.int1Min && otherTicketField <= rule.int1Max) ||
                            (otherTicketField >= rule.int2Min && otherTicketField <= rule.int2Max)) {
                        validField = true;
                        weight[ruleIndex]++;
                    }
                }
                if (!validField) {
                    sum += otherTicketField;
                    allValidFields = false;
                }
            }
            if (allValidFields) {
                validTickets.add(new Ticket(otherTicketFields, weight, ruleList));
            }
            index++;
        }

        Log.part1(sum);

        List<Rule>[] ruleMat = new List[validTickets.size()];
        for (index = 0; index < ruleMat.length; index++) {
            ruleMat[index] = new ArrayList<>();
            ruleMat[index].addAll(ruleList);
        }

        boolean onlyOneLeftEach = false;
        while (!onlyOneLeftEach) {
            onlyOneLeftEach = true;
            for (int ticketIndex = 0; ticketIndex < validTickets.size(); ticketIndex++) {
                Ticket ticket = validTickets.get(ticketIndex);
                for (int fieldIndex = 0; fieldIndex < ticket.fields.size(); fieldIndex++) {
                    int field = ticket.fields.get(fieldIndex);
                    Iterator<Rule> iterator = ruleMat[fieldIndex].iterator();
                    while (iterator.hasNext()) {
                        Rule rule = iterator.next();
                        if ((field < rule.int1Min || field > rule.int1Max) &&
                                (field < rule.int2Min || field > rule.int2Max)) {
                            iterator.remove();
                        }
                    }
                }
            }
            for (int i1 = 0; i1 < ruleMat.length; i1++) {
                if (ruleMat[i1].size() == 1) {
                    for (int i2 = 0; i2 < ruleMat.length; i2++) {
                        if (i1 != i2) {
                            ruleMat[i2].remove(ruleMat[i1].get(0));
                        }
                    }
                }
            }

            for (List<Rule> matList : ruleMat) {
                if (matList.size() > 1) {
                    onlyOneLeftEach = false;
                }
            }
        }

        int result = 1;
        for(index = 0; index < ruleMat.length; index++) {
            List<Rule> list = ruleMat[index];
            if (list.size() == 1 && list.get(0).fieldName.startsWith("departure")) {
                result *= ticketNumbers.get(index);
                System.out.println("X");
            }
        }

        Log.part2(result);
    }

}
