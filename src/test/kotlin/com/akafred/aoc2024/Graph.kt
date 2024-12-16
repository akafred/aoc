package com.akafred.aoc2024

import java.util.*

data class DirectedEdge<N>(val from: N, val to: N, val weight: Int) {
    override fun toString(): String {
        return "$from -$weight-> $to"
    }
}

class EdgeWeightedDigraph<N>() {

    val adj: MutableMap<N, MutableSet<DirectedEdge<N>>> = mutableMapOf()


    fun addEdge(edge: DirectedEdge<N>) {
        if (!adj.containsKey(edge.from)) adj[edge.from] = mutableSetOf()
        adj[edge.from]!!.add(edge)
    }

    fun adj(node: N): Set<DirectedEdge<N>> = adj[node]!!

    fun edges(): Set<DirectedEdge<N>> = adj.values.flatten().toSet()

    val distTo: MutableMap<N, Int> = mutableMapOf<N, Int>().withDefault { Int.MAX_VALUE }
    val edgeTo: MutableMap<N, DirectedEdge<N>> = mutableMapOf()
    //val priorityQueue = sortedSetOf<Pair<N, Int>>({ a, b -> a.second.compareTo(b.second) })
    val priorityQueue = PriorityQueue<Pair<N, Int>>({ a, b -> a.second.compareTo(b.second) })

        fun runDijkstraSp(start: N) {
        distTo[start] = 0
        priorityQueue.add(Pair(start, 0))

        while (!priorityQueue.isEmpty()) {
            val (node, weight) = priorityQueue.first()
            priorityQueue.remove(Pair(node, weight))
            relax(node)
        }
    }

    fun relax(node: N) {
        for (edge in adj[node] ?: emptySet()) {
            val from = edge.from
            val to = edge.to
            if (distTo.getOrDefault(to, Int.MAX_VALUE) > distTo[from]!! + edge.weight) {
                distTo[to] = distTo[from]!! + edge.weight
                edgeTo[to] = edge
                priorityQueue.removeIf { it.first == to }
                priorityQueue.add(Pair(to, distTo[to]!!))
            }
        }
    }

    fun distTo(node: N): Int {
        return distTo[node] ?: Int.MAX_VALUE
    }

    fun pathTo(node: N): List<DirectedEdge<N>> {
        //if(!hasPathTo(node)) throw IllegalStateException("Path to node $node is not found")
        val path = mutableListOf<DirectedEdge<N>>()

        var edge = edgeTo[node]
        while (edge != null) {
            path.addFirst(edge)
            edge = edgeTo[edge.from]
        }
        return path.toList()
    }

    private fun hasPathTo(node: N): Boolean {
        TODO("Not yet implemented")
    }

    fun printEdges() {
        for (edge in edges()) {
            println(edge)
        }
    }

    fun print() {
        adj.keys.forEach { node ->
            println("$node :")
            adj[node]?.forEach { println (" -> $it ") }
            println()
        }
    }
}
