package ru.ssau.templet.liza.model

/**
 * @author Yuriy Artamonov 
 */
class Context(val environment: Environment,
              val requirements: Traversable[Requirement]) {
}