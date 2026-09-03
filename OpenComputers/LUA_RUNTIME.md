# Lua runtime

The modern OpenComputers port targets Lua 5.3 on the JVM. Luna is used as the embedded implementation because it is pure Java and explicitly targets sandboxed Lua programs.

The computer runtime must keep host-JVM, arbitrary file, process, reflection and network access outside the Lua environment. CPU execution is bounded by an instruction budget.

Luna is Apache-2.0 licensed; retain its license/notice when distributing it.
