package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Address
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.AddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("address")
class AddressController(
    @Autowired val addressService: AddressService
) {
    @PostMapping
    fun createAddress(@RequestBody address:Address, @ActiveUser userPrincipal: com.thduc.eshop.request.UserPrincipal):Address{
        return addressService.addAddress(address,userPrincipal.currentUser);
    }
    @GetMapping
    fun getMyAddress(@ActiveUser userPrincipal: UserPrincipal,
                     @RequestParam(value = "page", defaultValue = "0") page: Int,
                     @RequestParam(value = "size", defaultValue = "10") size: Int,
                     @RequestParam(value = "sortBy", defaultValue = "id") sortBy: String,
                     @RequestParam(value = "sortOrder", defaultValue = "") sortOrder: String):Page<Address>{
        return if (sortOrder == "desc")
            addressService.getAllByUser(userPrincipal.currentUser, PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else
            addressService.getAllByUser(userPrincipal.currentUser, PageRequest.of(page, size, Sort.by(sortBy)))

    }
    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody address:Address, @ActiveUser userPrincipal: com.thduc.eshop.request.UserPrincipal):Address{
        return addressService.update(id,address,userPrincipal.currentUser);
    }
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long, @ActiveUser userPrincipal: com.thduc.eshop.request.UserPrincipal):SuccessActionResponse{
        return SuccessActionResponse("delete",addressService.delete(id,userPrincipal.currentUser))
    }
}