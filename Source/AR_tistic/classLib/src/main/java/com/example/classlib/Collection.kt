package com.example.classlib

data class Collection (
    var users:HashMap<Int,User>,
    var interestPoints:HashMap<Int,InterestPoint>,
    var draws:HashMap<Int,Draw>
    )