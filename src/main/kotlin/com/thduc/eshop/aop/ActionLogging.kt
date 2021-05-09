package com.thduc.eshop.aop

import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Component
import java.util.*

private val logger: Logger? = LoggerFactory.getLogger(ActionLogging::class.java)
@Aspect
@Component
class ActionLogging(

) {

    @Around("@annotation(com.thduc.eshop.annotation.LogExecution)")
    fun executionTime(joinPoint: ProceedingJoinPoint):Any{
        val objectMapper  = ObjectMapper()

        val start = System.currentTimeMillis()
        val signature = joinPoint.signature
        val args =  objectMapper.writeValueAsString(joinPoint.args)
        val result = try {
            with(StringBuffer("Start -> Executing $signature, $args"),{
                logger!!.info(toString())

            })
            joinPoint.proceed()

        } catch (throwable: Throwable) {
            logger!!.error("*** Exception during executing $signature,", throwable)
            throw throwable
        }
        val duration = System.currentTimeMillis() - start
        logger!!.info("end -> Finished executing: $signature, returned: '$result', duration: $duration ms")
        return result
    }

    




}