package com.example.corona19

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root
data class Header(@Element var resultCode: String,
                  @Element var resultMsg: String
)

@Root
data class Item(@Element var accDefRate: String,
                @Element var accExamCnt: String,
                @Element var accExamCompCnt: String,
                @Element var careCnt: String,
                @Element var clearCnt: String,
                @Element var createDt: String,
                @Element var deathCnt: String,
                @Element var decideCnt: String,
                @Element var examCnt: String,
                @Element var resultNegCnt: String,
                @Element var seq: String,
                @Element var stateDt: String,
                @Element var stateTime: String,
                @Element var updateDate: String
)

@Root
data class Items(@ElementList var item: List<Item>)

@Root
data class Body(@Element var items: Items,
                @Element var numOfRows: String,
                @Element var pageNo: String,
                @Element var totalCount: String
)

@Root
data class Response(@Element var header: Header,
                    @Element var body: Body)
