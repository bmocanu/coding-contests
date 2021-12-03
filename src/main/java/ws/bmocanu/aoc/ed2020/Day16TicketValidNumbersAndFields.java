package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.SBinder;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day16TicketValidNumbersAndFields extends SolutionBase {

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day16"));
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
        List<Integer> myTicketNumbers = Utils.splitCsvStringToIntList(stringLines.get(index), ",");
        List<Ticket> validTickets = new ArrayList<>();

        index += 3;
        List<Integer> otherTicketFields;
        int sum = 0;
        while (index < stringLines.size()) {
            line = stringLines.get(index);
            otherTicketFields = Utils.splitCsvStringToIntList(line, ",");
            boolean validField;
            boolean allValidFields = true;
            for (int otherTicketField : otherTicketFields) {
                validField = false;
                for (Rule rule : ruleList) {
                    if ((otherTicketField >= rule.int1Min && otherTicketField <= rule.int1Max) ||
                        (otherTicketField >= rule.int2Min && otherTicketField <= rule.int2Max)) {
                        validField = true;
                        break;
                    }
                }
                if (!validField) {
                    sum += otherTicketField;
                    allValidFields = false;
                }
            }
            if (allValidFields) {
                validTickets.add(new Ticket(otherTicketFields, ruleList));
            }
            index++;
        }

        Log.part1(sum);

        List<Rule>[] ruleMat = new List[myTicketNumbers.size()];
        for (index = 0; index < ruleMat.length; index++) {
            ruleMat[index] = new ArrayList<>();
            ruleMat[index].addAll(ruleList);
        }

        for (Ticket ticket : validTickets) {
            for (int fieldIndex = 0; fieldIndex < ticket.fields.size(); fieldIndex++) {
                int field = ticket.fields.get(fieldIndex);
                ruleMat[fieldIndex].removeIf(
                    rule -> field < rule.int1Min || (field > rule.int1Max && field < rule.int2Min) || field > rule.int2Max);
            }
        }

        boolean allEqualTo1 = false;
        while (!allEqualTo1) {
            allEqualTo1 = true;
            for (int i1 = 0; i1 < ruleMat.length; i1++) {
                if (ruleMat[i1].size() == 1) {
                    for (int i2 = 0; i2 < ruleMat.length; i2++) {
                        if (i1 != i2 && ruleMat[i2].size() > 1) {
                            ruleMat[i2].remove(ruleMat[i1].get(0));
                        }
                    }
                } else if (ruleMat[i1].size() > 1 && ruleMat[i1].get(0).fieldName.startsWith("departure")) {
                    allEqualTo1 = false;
                }
            }
        }

        long result = 1;
        for (index = 0; index < ruleMat.length; index++) {
            List<Rule> list = ruleMat[index];
            if (list.size() == 1 && list.get(0).fieldName.startsWith("departure")) {
                result *= myTicketNumbers.get(index);
            }
        }

        Log.part2(result);
    }

    // ----------------------------------------------------------------------------------------------------

    public static class Rule {
        String fieldName;
        int int1Min;
        int int1Max;
        int int2Min;
        int int2Max;
    }

    public static class Ticket {
        List<Integer> fields;
        List<Rule> posRules = new ArrayList<>();

        public Ticket(List<Integer> fields, List<Rule> ruleList) {
            this.fields = fields;
            this.posRules.addAll(ruleList);
        }
    }

}
