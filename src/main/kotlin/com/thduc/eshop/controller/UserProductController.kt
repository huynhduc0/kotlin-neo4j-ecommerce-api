package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Product
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import com.thduc.eshop.utilities.SearchBuilder
import org.springframework.web.bind.annotation.*

import java.util.regex.Matcher
import java.util.regex.Pattern


@RestController
@RequestMapping("products")
class UserProductController(
    @Autowired val productService:ProductService,
) {
    @GetMapping
    fun getMyProduct(@ActiveUser userPrincipal: UserPrincipal,
                     @RequestParam(value = "page",defaultValue = "0")  page: Int,
                     @RequestParam(value = "size", defaultValue = "10")  size: Int,
                     @RequestParam(value = "sortBy", defaultValue = "id")  sortBy: String,
                     @RequestParam(value = "sortOrder", defaultValue = "")  sortOrder: String
    ): Page<Product> {

        return if (sortOrder == "desc")
            productService.loadProductByUser(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else productService.loadProductByUser(userPrincipal.getUser()!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }
    @GetMapping("cart-relative")
    fun getCardRelative(@ActiveUser userPrincipal: UserPrincipal,
                     @RequestParam(value = "page",defaultValue = "0")  page: Int,
                     @RequestParam(value = "size", defaultValue = "10")  size: Int,
                     @RequestParam(value = "sortBy", defaultValue = "id")  sortBy: String,
                     @RequestParam(value = "sortOrder", defaultValue = "")  sortOrder: String
    ): Page<Product> {

        return if (sortOrder == "desc")
            productService.loadRelativeProduct(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else productService.loadRelativeProduct(userPrincipal.getUser()!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }
    @GetMapping("product-relative/{id}")
    fun getProductRelative(@ActiveUser userPrincipal: UserPrincipal,
                           @PathVariable id:Long,
                        @RequestParam(value = "page",defaultValue = "0")  page: Int,
                        @RequestParam(value = "size", defaultValue = "10")  size: Int,
                        @RequestParam(value = "sortBy", defaultValue = "id")  sortBy: String,
                        @RequestParam(value = "sortOrder", defaultValue = "")  sortOrder: String
    ): Page<Product> {
        return if (sortOrder == "desc")
            productService.loadRelativeProduct(id,userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else productService.loadRelativeProduct(id,userPrincipal.getUser()!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }

    @GetMapping("recommend")
    fun getMyRecommendProduct(@ActiveUser userPrincipal: UserPrincipal,
                              @RequestParam(value = "page",defaultValue = "0")  page: Int,
                              @RequestParam(value = "size", defaultValue = "10")  size: Int,
                              @RequestParam(value = "sortBy", defaultValue = "id")  sortBy: String,
                              @RequestParam(value = "sortOrder", defaultValue = "")  sortOrder: String
    ): Page<Product> {

        return if (sortOrder == "desc")
            productService.loadRecomendProduct(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else productService.loadRecomendProduct(userPrincipal.getUser()!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }

//    @GetMapping("/prod")
//    @ResponseBody
//    fun search(@RequestParam(value = "search") search: String?): Iterable<Product?>? {
//        val builder = SearchBuilder(clazz = Product::class.java)
//        if (search != null) {
//            val pattern: Pattern = Pattern.compile("""(w+?)(:|<|>)(w+?),""")
//            val matcher: Matcher = pattern.matcher("$search,")
//            while (matcher.find()) {
//                builder.with(matcher.group(1), matcher.group(2), matcher.group(3))
//            }
//        }
//        val exp: BooleanExpression = builder.build()!!
//        return productService.findAll(exp)
//    }
}