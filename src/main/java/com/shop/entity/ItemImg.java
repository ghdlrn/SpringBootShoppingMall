package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "item_img")
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "item_im_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName; //이미지 명
    private String oriImgName;  //원본이미지명
    private String oriURl;  //이미지 경로

    private String repimgYn;    //대표 이미지(이미지가 여러장일 때, 메인페이지에서 보일 이미지)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItmeImg(String oriImgName, String oriURl, String repimgYn) {
        this.oriImgName = oriImgName;
        this.oriURl = oriURl;
        this.repimgYn = repimgYn;
    }
}
