package com.thduc.eshop.acutor

import org.springframework.boot.actuate.health.Health

import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import org.springframework.stereotype.Component

//
//@Component
//class DownstreamServiceHealthIndicator : ReactiveHealthIndicator {
//    override fun health(): Mono<Health> {
//        return checkDownstreamServiceHealth().onErrorResume { ex -> Mono.just(Health.Builder().down(ex).build()) }
//    }
//
//    private fun checkDownstreamServiceHealth(): Mono<Health> {
//        // we could use WebClient to check health reactively
//        return Mono.just(Health.Builder().up().build())
//    }
//}