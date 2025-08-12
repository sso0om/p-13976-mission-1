package com.ll.global.rq

class Rq(cmd :String) {
    val getActionName: String
    val params = mutableMapOf<String, String?>()

    init {
        val cmdBits = cmd.split("?", limit = 2)
        getActionName = cmdBits[0]

        val queryString = if(cmdBits.size == 2) cmdBits[1].trim() else null

        if (queryString != null) {
            val paramBits = queryString.split("=", limit = 2)
            val paramName = paramBits[0].trim();
            val paramVal = if(paramBits.size == 2) paramBits[1].trim() else null;

            params.put(paramName, paramVal);
        }
    }

    fun getParam(paramName: String): String? {
        return params.getOrDefault(paramName, null)
    }

    fun getParamInt(paramName: String): Int? {
        val paramVal = getParam(paramName)
        return paramVal?.toIntOrNull();
    }
}