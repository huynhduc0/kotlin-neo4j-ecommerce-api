package com.thduc.eshop.service

import com.thduc.eshop.constant.UploadType
import com.thduc.eshop.entity.Media
import com.thduc.eshop.entity.User
import com.thduc.eshop.repository.MediaRepository
import com.thduc.eshop.request.MediaForm
import com.thduc.eshop.service.ServiceImpl.MediaServiceImpl
import com.thduc.eshop.utilities.FileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MediaService(
    @Autowired val mediaRepository: MediaRepository,
    @Autowired val fileUtil: FileUtil
): MediaServiceImpl {
     fun getByUser(user:User,pageable: Pageable):Page<Media>{
        return mediaRepository.findByCreatedBy(user,pageable)
    }

    override fun addMedia(mediaForm: MediaForm, currentUser: User): Boolean {
        mediaForm.medias.forEach {
            mediaRepository.save(fileUtil.store(currentUser.username!!,currentUser,it,UploadType.PRODUCT))
        }
        return true;
    }
}