package ws.bmocanu.aoc.ed2021;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import static ws.bmocanu.aoc.utils.XUtils.charArrayAsDigitsToIntList;
import static ws.bmocanu.aoc.utils.XUtils.hexToBinary;

public class Day16PacketDecoder extends SolutionBase {

    static int versionSum = 0;

    public static void main(String[] args) {
        String input = XRead.fileAsStringPerLineToStringList(filePath("day16")).get(0);
        List<Integer> bitList = charArrayAsDigitsToIntList(hexToBinary(input).toCharArray());
        Packet packet = parse(bitList);

        Log.part1(versionSum);
        Log.part2(packet.evaluate());
    }

    public static Packet parse(List<Integer> bitList) {
        Packet pack = new Packet();
        pack.version = toInt(bitList, 0, 3);
        versionSum += pack.version;
        pack.typeId = toInt(bitList, 3, 3);
        remove(bitList, 0, 6);
        if (pack.typeId == 4) { // literal value
            StringBuilder valBuilder = new StringBuilder(50);
            while (bitList.get(0) == 1) {
                valBuilder.append(bitList.get(1)).append(bitList.get(2)).append(bitList.get(3)).append(bitList.get(4));
                remove(bitList, 0, 5);
            }
            valBuilder.append(bitList.get(1)).append(bitList.get(2)).append(bitList.get(3)).append(bitList.get(4));
            remove(bitList, 0, 5);
            pack.literalValue = Long.parseLong(valBuilder.toString(), 2);
        } else {
            int lengthTypeId = bitList.get(0);
            if (lengthTypeId == 0) {
                int subPacketLength = (int) toLong(bitList, 1, 15);
                remove(bitList, 0, 16);
                int bitListSize = bitList.size();
                while (bitList.size() > bitListSize - subPacketLength) {
                    pack.subPacks.add(parse(bitList));
                }
            } else {
                int nextPackets = (int) toLong(bitList, 1, 11);
                remove(bitList, 0, 12);
                for (int index = 0; index < nextPackets; index++) {
                    pack.subPacks.add(parse(bitList));
                }
            }
        }
        return pack;
    }

    public static int toInt(List<Integer> bitList, int startIndex, int length) {
        StringBuilder str = new StringBuilder(length);
        for (int index = startIndex; index < startIndex + length; index++) {
            str.append(bitList.get(index));
        }
        return Integer.parseInt(str.toString(), 2);
    }

    public static long toLong(List<Integer> bitList, int startIndex, int length) {
        StringBuilder str = new StringBuilder(length);
        for (int index = startIndex; index < startIndex + length; index++) {
            str.append(bitList.get(index));
        }
        return Long.parseLong(str.toString(), 2);
    }

    public static void remove(List<Integer> bitList, int startIndex, int length) {
        for (int index = 0; index < length; index++) {
            bitList.remove(startIndex);
        }
    }

    static final int TYPE_SUM = 0;
    static final int TYPE_MULTIPLY = 1;
    static final int TYPE_MIN = 2;
    static final int TYPE_MAX = 3;
    static final int TYPE_VALUE = 4;
    static final int TYPE_GREATER_THAN = 5;
    static final int TYPE_LOWER_THAN = 6;
    static final int TYPE_EQ = 7;

    static class Packet {
        int version;
        int typeId;
        long literalValue;
        List<Packet> subPacks = new ArrayList<>();

        public long evaluate() {
            switch (typeId) {
                case TYPE_SUM: {
                    long result = 0;
                    for (Packet p : subPacks) {
                        result += p.evaluate();
                    }
                    return result;
                }
                case TYPE_MULTIPLY: {
                    long result = 1;
                    for (Packet p : subPacks) {
                        result *= p.evaluate();
                    }
                    return result;
                }
                case TYPE_MIN: {
                    long result = Long.MAX_VALUE;
                    for (Packet p : subPacks) {
                        long ev = p.evaluate();
                        if (ev < result) {
                            result = ev;
                        }
                    }
                    return result;
                }
                case TYPE_MAX: {
                    long result = Long.MIN_VALUE;
                    for (Packet p : subPacks) {
                        long ev = p.evaluate();
                        if (ev > result) {
                            result = ev;
                        }
                    }
                    return result;
                }
                case TYPE_VALUE: {
                    return literalValue;
                }
                case TYPE_GREATER_THAN: {
                    long ev0 = subPacks.get(0).evaluate();
                    long ev1 = subPacks.get(1).evaluate();
                    return ev0 > ev1 ? 1 : 0;
                }
                case TYPE_LOWER_THAN: {
                    long ev0 = subPacks.get(0).evaluate();
                    long ev1 = subPacks.get(1).evaluate();
                    return ev0 < ev1 ? 1 : 0;
                }
                case TYPE_EQ: {
                    long ev0 = subPacks.get(0).evaluate();
                    long ev1 = subPacks.get(1).evaluate();
                    return ev0 == ev1 ? 1 : 0;
                }
                default:
                    throw new IllegalStateException("Invalid typeId=" + typeId);
            }
        }
    }


}
