package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@Transactional
@Log4j2
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

//    @Commit
    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = Item.builder()
                .itemNm("라면")
                .price(1500)
                .itemDetail("라면 상세 설명2")
                .itemSellStatus(ItemSellStatus.SELL)
                .stoackNumber(100)
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        Item savedItem = itemRepository.save(item);
        log.info(savedItem.toString());
    }

    @Test
    @DisplayName("ID번 검색")
    public void findByIdTest() {
        Item item = itemRepository.findById(5L).get();
        log.info("Id : " + item);
    }

    @Test
    @DisplayName("라면 검색")
    public void findByNameTest() {
        Item item = itemRepository.findItemByItemNm("라면");
        log.info("라면 : " + item);
    }
/*
    @Test
    @DisplayName("라면 검색")
    public void findByItemDetailTest() {
        Item item = itemRepository.findByItemDetail("라면 상세 설명");
        log.info("라면 상세 설명 : " + item);
    }

    @Test
    @DisplayName("라면 & 라면 상세 검색")
    public void findByItemNmAndItemDetailTest() {
        Item item = itemRepository.findByItemNmAndItemDetail("라면","라면 상세 설명");
        log.info("라면 & 라면 상세 설명 : " + item);
    }
*/
    @Test
    @DisplayName("만원 미만 검색")
    public void findByPriceLessThanTest() {
        List<Item> list = itemRepository.findByPriceLessThan(10000);
        list.forEach(log::info);
    }

    @Test
    @DisplayName("만원 미만 검색 and 내림차순 정렬")
    public void findByPriceLessThanOrderByPriceDescTest() {
        List<Item> list = itemRepository.findByPriceLessThanOrderByPriceDesc(10000);
        list.forEach(log::info);
    }

    @Test
    @DisplayName("라면 상세 설명")
    public void findByItemDetail() {
        List<Item> item = itemRepository.findByItemDetail("상품");
        item.forEach(log::info);
    }

    @Test
    @DisplayName("라면 & 라면 상세 설명")
    public void findByItemNmAndItemDetail() {
        List<Item> item = itemRepository.findByItemNmAndItemDetail("라면", "라면 상세 설명");
        item.forEach(log::info);
    }

    @Test
    @DisplayName("findByItemDetailByNative")
    public void findByItemDetailByNative() {
        List<Item> item = itemRepository.findByItemDetailByNative("라면");
        item.forEach(log::info);
    }
}