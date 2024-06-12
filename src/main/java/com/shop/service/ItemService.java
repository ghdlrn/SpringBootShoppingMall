package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i==0) {
                itemImg.setRepimgYn("Y");   //첫번째 사진이 대표이미지
            } else {
                itemImg.setRepimgYn("N");
                itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
            }
        }
        return item.getId();
    }

    //    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        // ItemImg(상품 이미지) -> ItemImgDto 전달
        for(ItemImg itemImg : itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.ItemImgOfItemImgDto(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        // Item(상품정보) -> ItemFormDto 전달
        Optional<Item> result = itemRepository.findById(itemId);
        Item item = result.orElseThrow(EntityNotFoundException::new);

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if(!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);
        }
        if (StringUtils.isEmpty(savedItemImg.getImgName())) {
            fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
        }
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
        String imgUrl = "/images/item" + imgName;

        saveItemImg.updateItemImg(oriImgName, imgName, imgUrl);
    }
}
