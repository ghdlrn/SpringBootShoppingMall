package com.shop.dto;

import com.shop.entity.ItemImg;
import org.modelmapper.ModelMapper;

public class ItemImgDto {

    private Long id;

    private String imgName; //이미지 명
    private String oriImgName;  //원본이미지명
    private String oriURl;  //이미지 경로

    private String repimgYn;    //대표 이미지(이미지가 여러장일 때, 메인페이지에서 보일 이미지)

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto ItemImgOfItemImgDto(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}