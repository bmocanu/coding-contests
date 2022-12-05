package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day17TrickShotProbeVelocity extends SolutionBase {

    public static class Area {
        int x1;
        int x2;
        int y1;
        int y2;
    }

    public static int highestY = Integer.MIN_VALUE;
    public static int goodVelocities = 0;

    public static void main(String[] args) {
        // target area: x=207..263, y=-115..-63
        String input = XRead.fileAsStringPerLineToStringList(filePath("day17")).get(0);
        SBind<Area> bind = new SBind<>("target area: x=([-0-9]+)\\.\\.([-0-9]+), y=([-0-9]+)\\.\\.([-0-9]+)", Area.class, "x1", "x2", "y1", "y2");
        Area targetArea = bind.bindOne(input);
        System.out.println("Here");

        for (int velX = 1; velX < 2000; velX++) {
            for (int velY = -1000; velY < 2000; velY++) {
                calculatePath(targetArea, velX, velY);
            }
        }
        Log.part1(highestY);
        Log.part2(goodVelocities);
    }

    public static void calculatePath(Area targetArea, int velX, int velY) {
        int x = 0;
        int y = 0;
        int curVelX = velX;
        int curVelY = velY;
        int localHighestY = Integer.MIN_VALUE;
        boolean passedThroughTargetArea = false;
        while (!passedThroughTargetArea && x <= targetArea.x2 && y >= targetArea.y1) {
            x += curVelX;
            y += curVelY;
            if (y > localHighestY) {
                localHighestY = y;
            }
            if (x >= targetArea.x1 && x <= targetArea.x2 && y >= targetArea.y1 && y <= targetArea.y2) {
                passedThroughTargetArea = true;
            }
            if (curVelX > 0) {
                curVelX--;
            } else if (curVelX < 0) {
                curVelX++;
            }
            curVelY--;
        }
        if (passedThroughTargetArea) {
            goodVelocities++;
            if (localHighestY > highestY) {
                highestY = localHighestY;
            }
        }
    }

}
