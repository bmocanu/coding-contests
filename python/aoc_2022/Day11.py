class Monkey:
    def __init__(self, opMultiplyByItself, opMultiply, opAdd, opValue,
                 divBy, trueMonkeyIndex, falseMonkeyIndex):
        self.opMultiplyByItself = opMultiplyByItself
        self.opMultiply = opMultiply
        self.opAdd = opAdd
        self.opValue = opValue
        self.divBy = divBy
        self.trueMonkeyIndex = trueMonkeyIndex
        self.falseMonkeyIndex = falseMonkeyIndex


class MonkeyLink:
    obj = 0
    next = None

    def __init__(self, obj, next):
        self.obj = obj
        self.next = next


monkeyInput = []
monkeyInput.append(Monkey(False, True, False, 17, 3, 4, 2))
monkeyInput.append(Monkey(False, True, False, 11, 5, 3, 5))
monkeyInput.append(Monkey(False, False, True, 4, 2, 6, 4))
monkeyInput.append(Monkey(True, False, False, 0, 13, 0, 5))
monkeyInput.append(Monkey(False, False, True, 7, 11, 7, 6))
monkeyInput.append(Monkey(False, False, True, 8, 17, 0, 2))
monkeyInput.append(Monkey(False, False, True, 5, 19, 7, 1))
monkeyInput.append(Monkey(False, False, True, 3, 7, 1, 3))

monkeyObj = [[99, 67, 92, 61, 83, 64, 98],
             [78, 74, 88, 89, 50],
             [98, 91],
             [59, 72, 94, 91, 79, 88, 94, 51],
             [95, 72, 78],
             [76],
             [69, 60, 53, 89, 71, 88],
             [72, 54, 63, 80]]

monkeyCount = 8
monkeyInsp = [0] * monkeyCount
linkStart = [None] * monkeyCount
linkEnd = [None] * monkeyCount

for index in range(monkeyCount):
    prev = None
    for obj in monkeyObj[index]:
        current = MonkeyLink(obj, None)
        linkEnd[index] = current
        if prev is not None:
            prev.next = current
        if linkStart[index] is None:
            linkStart[index] = current
        prev = current

for round in range(1, 21):
    print("Round" + str(round))
    for monkeyIndex in range(monkeyCount):
        print("Monkey " + str(monkeyIndex))
        while linkStart[monkeyIndex] is not None:
            currentLink = linkStart[monkeyIndex]
            linkStart[monkeyIndex] = linkStart[monkeyIndex].next
            monkey = monkeyInput[monkeyIndex]
            if monkey.opMultiplyByItself:
                currentLink.obj = currentLink.obj * currentLink.obj
            elif monkey.opMultiply:
                currentLink.obj = currentLink.obj * monkey.opValue
            else:
                currentLink.obj = currentLink.obj + monkey.opValue
            currentLink.obj = currentLink.obj // 3
            if currentLink.obj % monkey.divBy == 0:
                if linkStart[monkey.trueMonkeyIndex] is None:
                    linkStart[monkey.trueMonkeyIndex] = currentLink
                if linkEnd[monkey.trueMonkeyIndex] is None:
                    linkEnd[monkey.trueMonkeyIndex] = currentLink
                else:
                    linkEnd[monkey.trueMonkeyIndex].next = currentLink
                    linkEnd[monkey.trueMonkeyIndex] = currentLink
                currentLink.next = None
            else:
                if linkStart[monkey.falseMonkeyIndex] is None:
                    linkStart[monkey.falseMonkeyIndex] = currentLink
                if linkEnd[monkey.falseMonkeyIndex] is None:
                    linkEnd[monkey.falseMonkeyIndex] = currentLink
                else:
                    linkEnd[monkey.falseMonkeyIndex].next = currentLink
                    linkEnd[monkey.falseMonkeyIndex] = currentLink
                currentLink.next = None
            monkeyInsp[monkeyIndex] += 1
    # print("-------------------------------------------------- round " + str(round))
    # for monkeyIndex in range(monkeyCount):
    #     print("Monkey " + str(monkeyIndex) + ": ")
    #     for obj in monkeyObj[monkeyIndex]:
    #         print(str(obj) + ", ")

max = -1
max1Index = -1
for index in range(monkeyCount):
    if monkeyInsp[index] > max:
        max = monkeyInsp[index]
        max1Index = index

max = -1
max2Index = -1
for index in range(monkeyCount):
    if (monkeyInsp[index] > max) and (index != max1Index):
        max = monkeyInsp[index]
        max2Index = index

print("Part 1: " + str(monkeyInsp[max1Index] * monkeyInsp[max2Index]))
