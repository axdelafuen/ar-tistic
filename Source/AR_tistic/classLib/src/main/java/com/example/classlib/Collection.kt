package com.example.classlib

import User


data class Collection (
    var users:HashMap<Int,User>,
    var interestPoints:HashMap<Int,InterestPoint>,
    var draws:HashMap<Int,Draw>
    )