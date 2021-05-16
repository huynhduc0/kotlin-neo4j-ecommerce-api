package com.thduc.eshop.controller.admin

import com.thduc.eshop.entity.Recommend
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.service.RecommendService
import com.thduc.eshop.utilities.Helper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class RecommendController(
    @Autowired val recommendService: RecommendService
) {
    @GetMapping("/train")
    fun loadDataForTrain(): ResponseEntity<*>? {
        return ResponseEntity<Any?>(recommendService.getAllLikeUid(), HttpStatus.OK)
    }

    @GetMapping("/ptrain")
    fun loadPost(): ResponseEntity<*>? {
        return ResponseEntity<Any?>(recommendService.getPosts(), HttpStatus.OK)
    }

    @PostMapping("/addrec")
    fun addRec(@RequestBody recommend: Recommend): SuccessActionResponse {
        recommendService.saveRecommend(recommend)
        return SuccessActionResponse("saved done",true)
    }

}