import java.util.*
import kotlin.math.ceil

data class Robot(
    val oreGet: Int,
    val clayGet: Int,
    val obsidianGet: Int,
    val geodeGet: Int,
    val ore: Int,
    val clay: Int,
    val obsidian: Int
)

data class Plan(
    val id: Int,
    val robots: List<Robot>
)

data class State(
    val remaining: Int,
    val oreRobots: Int,
    val clayRobots: Int,
    val obsidianRobots: Int,
    val geodeRobots: Int,
    val ore: Int,
    val clay: Int,
    val obsidian: Int,
    val geodes: Int
)

fun bestWay(state: State, process: Int, timeBudget: Int): Boolean {
    val timeLeft = timeBudget - state.remaining
    val potentialProduction = (0 until timeLeft).sumOf { it + state.geodeRobots }
    return state.geodes + potentialProduction > process
}

fun state(robot: Robot, state: State): State {
    val time = maxOf(
        if (robot.ore <= state.ore) 1 else ceil((robot.ore - state.ore) / state.oreRobots.toFloat()).toInt() + 1,
        if (robot.clay <= state.clay) 1 else ceil((robot.clay - state.clay) / state.clayRobots.toFloat()).toInt() + 1,
        if (robot.obsidian <= state.obsidian) 1 else ceil((robot.obsidian - state.obsidian) / state.obsidianRobots.toFloat()).toInt() + 1
    )
    return State(
        state.remaining + time,
        state.oreRobots + robot.oreGet,
        state.clayRobots + robot.clayGet,
        state.obsidianRobots + robot.obsidianGet,
        state.geodeRobots + robot.geodeGet,
        state.ore - robot.ore + time * state.oreRobots,
        state.clay - robot.clay + time * state.clayRobots,
        state.obsidian - robot.obsidian + time * state.obsidianRobots,
        state.geodes + time * state.geodeRobots
    )
}

fun nextStates(state: State, plan: Plan, remaining: Int): List<State> {
    val nextStates = mutableListOf<State>()
    if (state.remaining < remaining) {
        if (plan.robots.maxOf { it.ore } > state.oreRobots && state.ore > 0) {
            nextStates += state(plan.robots[0], state)
        }
        if (plan.robots.maxOf { it.clay } > state.clayRobots && state.ore > 0) {
            nextStates += state(plan.robots[1], state)
        }
        if (plan.robots.maxOf { it.obsidian } > state.obsidianRobots && state.ore > 0 && state.clay > 0) {
            nextStates += state(plan.robots[2], state)
        }
        if (state.ore > 0 && state.obsidian > 0) {
            nextStates += state(plan.robots[3], state)
        }
    }
    return nextStates.filter { it.remaining <= remaining }
}

fun geodes(plan: Plan, time: Int): Int {
    var result = 0
    val states = LinkedList(listOf(State(1, 1, 0, 0, 0, 1, 0, 0, 0)).sortedBy { it.geodes })
    while (states.isNotEmpty()) {
        val state = states.poll()
        if (bestWay(state, result, time)) {
            nextStates(state, plan, time).forEach {
                states.add(it)
            }
        }
        result = maxOf(result, state.geodes)
    }
    return result
}

fun main() {
    fun part1(input: List<String>): Int =
        input.map {
            val split = it.split(" ")
            val oreRobot = Robot(1, 0, 0, 0, split[6].toInt(), 0, 0)
            val clayRobot = Robot(0, 1, 0, 0, split[12].toInt(), 0, 0)
            val obsidianRobot = Robot(0, 0, 1, 0, split[18].toInt(), split[21].toInt(), 0)
            val geodeRobot = Robot(0, 0, 0, 1, split[27].toInt(), 0, split[30].toInt())
            Plan(
                split[1].substring(0, split[1].lastIndex).toInt(),
                listOf(oreRobot, clayRobot, obsidianRobot, geodeRobot)
            )
        }.sumOf { it.id * geodes(it, 24) }

    fun part2(input: List<String>): Int {
        var result = 1
        input.map {
            val split = it.split(" ")
            val oreRobot = Robot(1, 0, 0, 0, split[6].toInt(), 0, 0)
            val clayRobot = Robot(0, 1, 0, 0, split[12].toInt(), 0, 0)
            val obsidianRobot = Robot(0, 0, 1, 0, split[18].toInt(), split[21].toInt(), 0)
            val geodeRobot = Robot(0, 0, 0, 1, split[27].toInt(), 0, split[30].toInt())
            Plan(
                split[1].substring(0, split[1].lastIndex).toInt(),
                listOf(oreRobot, clayRobot, obsidianRobot, geodeRobot)
            )
        }.take(3).map { geodes(it, 32) }.forEach { result *= it }
        return result
    }

    val input = readInput("Day19")
    println(part1(input))
    println(part2(input))
}
