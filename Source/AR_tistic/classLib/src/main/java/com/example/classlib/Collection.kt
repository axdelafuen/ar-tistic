package com.example.classlib


data class Collection (
    var users:HashMap<Int,User>,
    val interestPoints:HashMap<Int,InterestPoint>,
    val draws:HashMap<Int,Draw>
    )